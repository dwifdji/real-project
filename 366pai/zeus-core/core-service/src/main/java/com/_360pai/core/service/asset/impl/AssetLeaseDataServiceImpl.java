package com._360pai.core.service.asset.impl;

import ch.qos.logback.core.net.AbstractSSLSocketAppender;
import com._360pai.arch.common.utils.AssetUtil;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.JsonUtil;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.condition.activity.AuctionActivityCondition;
import com._360pai.core.condition.asset.AssetDataDraftsCondition;
import com._360pai.core.condition.asset.AssetLeaseDataCondition;
import com._360pai.core.condition.assistant.AssetLeaseTypeImageCondition;
import com._360pai.core.condition.lease.TLeaseStaffCondition;import com._360pai.core.dao.account.TAgencyDao;
import com._360pai.core.dao.account.TCompanyDao;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.dao.asset.AssetDao;
import com._360pai.core.dao.asset.AssetDataDraftsDao;
import com._360pai.core.dao.asset.AssetLeaseDataDao;
import com._360pai.core.dao.assistant.AssetLeaseTypeImageDao;import com._360pai.core.dao.lease.TLeaseStaffDao;import com._360pai.core.dao.payment.AuctionOrderDao;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.asset.req.AssetReq;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.account.TCompany;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.asset.AssetDataDrafts;
import com._360pai.core.model.asset.AssetLeaseData;
import com._360pai.core.model.assistant.AssetLeaseTypeImage;import com._360pai.core.model.lease.TLeaseStaff;import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.asset.AssetLeaseDataService;
import com._360pai.core.service.assistant.SmsHelperService;
import com._360pai.core.vo.asset.AssetLeaseDataVO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 描述:
 *
 * @author : liuhaolei
 * @date : 2018/8/17 15:17
 */
@Service
@Slf4j
public class AssetLeaseDataServiceImpl implements AssetLeaseDataService {

    @Autowired
    private AssetLeaseDataDao assetLeaseDataDao;
    @Autowired
    private AssetDao assetDao;
    @Autowired
    private AuctionActivityDao auctionActivityDao;
    @Autowired
    private AssetDataDraftsDao assetDataDraftsDao;
    @Autowired
    private SmsHelperService smsHelperService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private GatewayMqSender mqSender;
    @Autowired
    private TAgencyDao tAgencyDao;
    @Autowired
    private AuctionOrderDao auctionOrderDao;
    @Autowired
    private AssetLeaseTypeImageDao assetLeaseTypeImageDao;
    @Autowired
    private TLeaseStaffDao leaseStaffDao;
    @Autowired
    private TCompanyDao companyDao;


	private static final String LEASENAME = "对外招租拍卖";

	@Override
    @Transactional
    public Integer saveOrUpdateLeaseAsset(AssetReq.LeaseDataReq req) {
        Asset asset = null;

         if(req.getAssetId() == null) {
            asset = new Asset();
            asset.setCreatedAt(new Date());
            setAsset(asset, req);

             //设置初始状态
            Map<String,Object> assetInitStatus = getAssetInitStatus(req.getPartyId());
            asset.setStatus((AssetEnum.Status)assetInitStatus.get("status"));
            asset.setSubStatus((String)assetInitStatus.get("subStatus"));
            asset.setName(req.getName() + LEASENAME);
            assetDao.insert(asset);

            String assetCode = AssetUtil.getAssetCode(asset.getId());
            asset.setCode(assetCode);
            Integer saveCount = assetDao.updateById(asset);
            // 删除草稿箱
            deleteLeaseDraft(req.getPartyId());
            // 保存assetLease
            saveOrUpdateAssetLeaseData(req, asset, true);
            // 保存活动
            saveOrUpdateAuctionData(req, asset, true);

            //发送短信
            sendSms(asset,assetInitStatus);

            return saveCount;
         }else {
            asset = assetDao.selectById(req.getAssetId());
            setAsset(asset, req);
            asset.setName(req.getName());
             // 设置修改后的状态
            asset.setStatus(asset.getStatus());
            if(AssetEnum.LeaseStatus.FIRST_REVIEW_REJECTION.getKey().equals(asset.getSubStatus())
                || AssetEnum.LeaseStatus.SECOND_REVIEW_REJECTION.getKey().equals(asset.getSubStatus())){
                Map<String,Object> assetInitStatus = getAssetInitStatus(req.getPartyId());
                asset.setSubStatus((String)assetInitStatus.get("subStatus"));
            }else {
                asset.setSubStatus(asset.getSubStatus());
            }

            Integer updateCount = assetDao.updateById(asset);

            // 修改租赁权data
            saveOrUpdateAssetLeaseData(req, asset, false);
            // 修改活动
            saveOrUpdateAuctionData(req, asset, false);

            return updateCount;
        }
    }

    /**
     *
     *发送短信
     */
    private void sendSms(Asset asset, Map<String,Object> assetInitStatus) {


        try {

            Integer comId =(Integer)assetInitStatus.get("comId");

            new Thread(()->threadSendSms(asset,comId)).start();

         }catch (Exception e){
            e.printStackTrace();
         }
    }


    private void threadSendSms(Asset asset,Integer comId) {




        List<String> firstReviewMobile = new ArrayList<>();

        List<String> secondReviewMobile = new ArrayList<>();


        TLeaseStaffCondition staffCondition = new TLeaseStaffCondition();
        staffCondition.setComId(comId);
        staffCondition.setIsDelete(false);
        staffCondition.setForbidFlag(true);
        List<TLeaseStaff> staffList = leaseStaffDao.selectList(staffCondition);

        for(TLeaseStaff staff :staffList){
            if(staff.getTrialFlag()){
                firstReviewMobile.add(staff.getMobile());
            }
            if(staff.getFinalFlag()){
                secondReviewMobile.add(staff.getMobile());

            }
        }

        //发送给终审
        if(AssetEnum.LeaseStatus.WAITING_SECOND_REVIEW.getKey().equals(asset.getSubStatus())){

            for(String mobile :secondReviewMobile){

                smsHelperService.leaseSubmitAudit(mobile,asset.getName());

            }

         //发送给初审
        }else if(AssetEnum.LeaseStatus.WAITING_FIRST_REVIEW.getKey().equals(asset.getSubStatus())){


            for(String mobile :firstReviewMobile){

                smsHelperService.leaseSubmitAudit(mobile,asset.getName());

            }

        }



    }

    private ActivityEnum.Status getActivityInitStatus(Integer partyId) {
        //获取经办人公司设置
        TCompany comInfo = getPartyComInfo(partyId);
        //根据公司的配置设置 标的的初始状态
        Integer leaseNum = comInfo.getLeaseNum();

        ActivityEnum.Status status = ActivityEnum.Status.PENDING;
        //0 级审核 直接到 等待平台审核
        if(leaseNum==0){
            status = ActivityEnum.Status.PLATFORM_REVIEWING;
        }

        return status;
    }

    private Map<String,Object> getAssetInitStatus(Integer partyId) {

        Map<String,Object> statusMap = new HashMap<>();

        //获取经办人公司设置
        TCompany comInfo = getPartyComInfo(partyId);
        //根据公司的配置设置 标的的初始状态
        Integer leaseNum = comInfo.getLeaseNum();
        statusMap.put("comId",comInfo.getId());

        //公司O 级审核
        if(leaseNum==0){
            statusMap.put("status",AssetEnum.Status.APPROVED);
            statusMap.put("subStatus",AssetEnum.LeaseStatus.WAITING_RELEASE.getKey());
            //1级审核
        }else if(leaseNum==1){

            statusMap.put("status",AssetEnum.Status.PENDING);
            statusMap.put("subStatus",AssetEnum.LeaseStatus.WAITING_SECOND_REVIEW.getKey());

            //二级审核
        }else{
            statusMap.put("status",AssetEnum.Status.PENDING);
            statusMap.put("subStatus",AssetEnum.LeaseStatus.WAITING_FIRST_REVIEW.getKey());
        }


        return statusMap;

    }

    private TCompany getPartyComInfo(Integer partyId) {

        TLeaseStaffCondition staffCondition = new TLeaseStaffCondition();
        staffCondition.setPartId(partyId);
        staffCondition.setIsDelete(false);

        TLeaseStaff staff = leaseStaffDao.selectFirst(staffCondition);
        
        return companyDao.selectById(staff.getComId());
    }


    private void deleteLeaseDraft(Integer partyId) {
        AssetDataDraftsCondition assetDataDraftsCondition = new AssetDataDraftsCondition();
        assetDataDraftsCondition.setPartyId(partyId.toString());
        assetDataDraftsCondition.setType(AssetEnum.AssetDraft.LEASE.getKey());
        assetDataDraftsCondition.setDelFlag(0);
        AssetDataDrafts assetDataDrafts = assetDataDraftsDao.selectOneResult(assetDataDraftsCondition);

        if(assetDataDrafts != null) {
            assetDataDrafts.setDelFlag(1);
            assetDataDraftsDao.updateById(assetDataDrafts);
        }
    }

    @Override
    public AssetLeaseDataVO getLeaseAssetById(Integer assetId, String webFlag) {
        AssetLeaseDataCondition assetLeaseDataCondition = new AssetLeaseDataCondition();
        assetLeaseDataCondition.setAssetId(assetId);

        AssetLeaseData assetLeaseData = assetLeaseDataDao.selectOneResult(assetLeaseDataCondition);
        if(assetLeaseData == null) {
            return null;
        }

        AssetLeaseDataVO assetLeaseDataVO = new AssetLeaseDataVO();
        if(assetLeaseData != null) {
            BeanUtils.copyProperties(assetLeaseData, assetLeaseDataVO);
        }
        // 后台和机构发布的时候的处理
        String reservePrice = assetLeaseData.getReservePrice().toString();
        reservePrice = reservePrice.contains(".00") ? reservePrice.substring(0, reservePrice.length()- 3):reservePrice;
        assetLeaseDataVO.setReservePrice(AssetEnum.WebFlag.WEB.getKey().equals(webFlag) ?
                reservePrice : AssetEnum.WebFlag.ADMIN.getValue());

        TAgency tAgency = tAgencyDao.selectById(assetLeaseData.getServerAgencyId());
        if(tAgency != null) {
            assetLeaseDataVO.setServerAgencyName(tAgency.getName());
        }

        //该标的的成交竞买人
        String name = auctionOrderDao.getBuyerNameByActivityId(assetId);
        assetLeaseDataVO.setBuyerName(name);

        return assetLeaseDataVO;
    }

    @Override
    public Integer saveOrUpdateLeaseDraft(AssetReq.LeaseDataReq req) {
        AssetDataDrafts assetDataDrafts = null;

        AssetDataDraftsCondition assetDataDraftsCondition = new AssetDataDraftsCondition();
        assetDataDraftsCondition.setPartyId(req.getPartyId().toString());
        assetDataDraftsCondition.setType(AssetEnum.AssetDraft.LEASE.getKey());
        assetDataDraftsCondition.setDelFlag(0);
        assetDataDrafts = assetDataDraftsDao.selectOneResult(assetDataDraftsCondition);

        if(assetDataDrafts == null) {
            assetDataDrafts = new AssetDataDrafts();
            assetDataDrafts.setCreatedTime(new Date());
            setLeaseDraft(assetDataDrafts, req);

            return assetDataDraftsDao.insert(assetDataDrafts);
        }

        setLeaseDraft(assetDataDrafts, req);

        return assetDataDraftsDao.updateById(assetDataDrafts);
    }

    @Override
    public AssetDataDrafts getLeaseDraft(Integer partyId) {
        AssetDataDraftsCondition assetDataDraftsCondition = new AssetDataDraftsCondition();
        assetDataDraftsCondition.setPartyId(partyId.toString());
        assetDataDraftsCondition.setType(AssetEnum.AssetDraft.LEASE.getKey());
        assetDataDraftsCondition.setDelFlag(0);

        AssetDataDrafts assetDataDrafts = assetDataDraftsDao.selectOneResult(assetDataDraftsCondition);
        return assetDataDrafts;
    }

    private void setLeaseDraft(AssetDataDrafts assetDataDrafts, AssetReq.LeaseDataReq req) {
        assetDataDrafts.setContent(JsonUtil.toJSONObject(req));
        assetDataDrafts.setPartyId(req.getPartyId().toString());
        assetDataDrafts.setType(AssetEnum.AssetDraft.LEASE.getKey());
        assetDataDrafts.setUpdatedTime(new Date());
        assetDataDrafts.setDelFlag(0);
    }


    private Integer saveOrUpdateAuctionData(AssetReq.LeaseDataReq req, Asset asset, Boolean saveFlag) {
        AuctionActivity auctionActivity = null;

        if(saveFlag) {
            auctionActivity = new AuctionActivity();

            setAuctionActivity(auctionActivity, req, asset);
            //活动表初始状态
            ActivityEnum.Status activityInitStatus = getActivityInitStatus(req.getPartyId());
            auctionActivity.setStatus(activityInitStatus);

            Integer insert = auctionActivityDao.insert(auctionActivity);

            if(insert <= 0) {
                log.error("保存租赁活动失败，数据为{}", JsonUtil.toJSON(auctionActivity));
            }
            return insert;
        }else {

            AuctionActivityCondition auctionActivityCondition = new AuctionActivityCondition();
            auctionActivityCondition.setAssetId(asset.getId());
            auctionActivityCondition.setDeleteFlag(false);
            auctionActivity = auctionActivityDao.selectOneResult(auctionActivityCondition);

            setAuctionActivity(auctionActivity, req, asset);

            auctionActivity.setStatus(auctionActivity.getStatus());
            return auctionActivityDao.updateById(auctionActivity);
        }
    }


    private void releaseLotNotify(Asset asset) {
        try {
            smsHelperService.releaseLotNotify(accountService.getNotifierMobile(asset.getPartyId()), asset.getName(), asset.getReservePrice());
            AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(asset.getPartyId());
            smsHelperService.releaseLotToAgencyNotify(accountService.getAgencyNotifierMobile(asset.getAgencyId()), asset.getName(), accountBaseDto.getName());
        } catch (Exception e) {
            e.printStackTrace();
            mqSender.sendTryCatchExceptionEmail(asset.getId(), e);
        }
    }

    private void setAuctionActivity(AuctionActivity auctionActivity, AssetReq.LeaseDataReq req, Asset asset) {
        auctionActivity.setAssetId(asset.getId());
        auctionActivity.setSubStatus(asset.getSubStatus());
        auctionActivity.setAssetName(asset.getName());
        auctionActivity.setBusTypeName(asset.getBusTypeName());
        auctionActivity.setCategoryId(-1);
        auctionActivity.setAssetCategoryGroupId(-1);
        //设置可见性
        auctionActivity.setVisibilityLevel(ActivityEnum.VisibilityLevel.PLATFORM_DEFAULT.getKey());
        auctionActivity.setCode(asset.getCode() + "-" + RandomNumberGenerator.numberGenerator(5));
        auctionActivity.setAgencyId(req.getServerAgencyId());
        auctionActivity.setMode(req.getExpectedMode());
        auctionActivity.setAssetType(asset.getAssetType().getKey());
        auctionActivity.setAuctioneerName(asset.getContactName());
//        auctionActivity.setCurrentPrice(req.getStartingPrice());
        auctionActivity.setAuctioneerQq(req.getContactQq());
        auctionActivity.setAuctioneerPhone(req.getContactPhone());
        auctionActivity.setReduction(req.getReduction());
        auctionActivity.setReductionPeriod(req.getReductionPeriod());
        auctionActivity.setIncrement(req.getIncrement());
        auctionActivity.setPreviewAt(req.getPreviewAt());
        auctionActivity.setBeginAt(req.getBeginAt());
        auctionActivity.setEndAt(req.getEndAt());
        auctionActivity.setDeposit(req.getDeposit());
        auctionActivity.setRefPrice(req.getRefPrice());
        if(!AssetEnum.WebFlag.ADMIN.getValue().equals(req.getReservePrice())) {
            auctionActivity.setReservePrice(new BigDecimal(req.getReservePrice()));
        }
        auctionActivity.setStartingPrice(req.getStartingPrice());
        auctionActivity.setCommissionPercentBuyer(new BigDecimal(req.getLesseeCommissionRate()));
        auctionActivity.setCommissionPercentSeller(new BigDecimal(req.getLessorCommissionRate()));

        if (req.getExpectedMode().equals(ActivityEnum.ActivityMode.DUTCH.getKey())) {
            Date date = AssetUtil.formatDate(req.getBeginAt(), req.getReductionPeriod());
            auctionActivity.setReducedAt(date);
        }
    }


    private Integer saveOrUpdateAssetLeaseData(AssetReq.LeaseDataReq req, Asset asset, Boolean saveFlag) {
        AssetLeaseData assetLeaseData = null;

        if(saveFlag) {
            assetLeaseData = new AssetLeaseData();
            setAssetLeaseData(assetLeaseData, req, asset);
            assetLeaseData.setCreatedAt(new Date());

            return assetLeaseDataDao.insert(assetLeaseData);
        }else{
            AssetLeaseDataCondition assetLeaseDataCondition = new AssetLeaseDataCondition();
            assetLeaseDataCondition.setAssetId(asset.getId());
            assetLeaseDataCondition.setDeleteFlag(false);
            assetLeaseData = assetLeaseDataDao.selectOneResult(assetLeaseDataCondition);

            setAssetLeaseData(assetLeaseData, req, asset);
            return assetLeaseDataDao.updateById(assetLeaseData);
        }
    }


    private void setAssetLeaseData(AssetLeaseData assetLeaseData, AssetReq.LeaseDataReq req, Asset asset) {
        assetLeaseData.setOperatingType(req.getOperatingType());
        assetLeaseData.setPartyId(req.getPartyId());
        assetLeaseData.setAssetId(asset.getId());
        assetLeaseData.setCategoryId(-1);
        assetLeaseData.setAssetType(AssetEnum.AssetType.LEASEHOLD.getKey());
        assetLeaseData.setAreaId(asset.getAreaId());
        assetLeaseData.setBeginAt(req.getBeginAt());
        assetLeaseData.setDeleteFlag(false);
        JSONObject extra = handleExtra(req.getExtra(), req.getLeaseType());
        assetLeaseData.setExtra(extra);
        assetLeaseData.setName(asset.getName());
        assetLeaseData.setEndAt(req.getEndAt());
        assetLeaseData.setContactName(req.getContactName());
        assetLeaseData.setContactQq(req.getContactQq());
        assetLeaseData.setContactPhone(req.getContactPhone());
        assetLeaseData.setCityId(asset.getCityId());
        assetLeaseData.setProvinceId(asset.getProvinceId());
        assetLeaseData.setRefPrice(req.getRefPrice());
        assetLeaseData.setAreaType(req.getAreaType());
        assetLeaseData.setStartingPrice(req.getStartingPrice());
        assetLeaseData.setUpdatedAt(new Date());
        assetLeaseData.setCode(asset.getCode());
        assetLeaseData.setApplyEndTime(req.getApplyEndTime());
        assetLeaseData.setApprovalEndTime(req.getApprovalEndTime());
        assetLeaseData.setPreAuditMethod(req.getPreAuditMethod());
        assetLeaseData.setBusinessNow(req.getBusinessNow());
        assetLeaseData.setLeaseAddress(req.getLeaseAddress());
        assetLeaseData.setLeaseArea(req.getLeaseArea());
        assetLeaseData.setOriginalTenantName(req.getOriginalTenantName());
        assetLeaseData.setLessorContact(req.getLessorContact());
        assetLeaseData.setLeaseType(req.getLeaseType());
        assetLeaseData.setLeaseRoomNumber(req.getLeaseRoomNumber());
        assetLeaseData.setLeaseStartTime(req.getLeaseStartTime());
        assetLeaseData.setLeaseEndTime(req.getLeaseEndTime());
        assetLeaseData.setLegalPurposes(req.getLegalPurposes());
        assetLeaseData.setLevyAgreement(req.getLevyAgreement());
        assetLeaseData.setLesseeCommissionRate(req.getLesseeCommissionRate());
        assetLeaseData.setLessorCommissionRate(req.getLessorCommissionRate());
        assetLeaseData.setLessorName(req.getLessorName());
        assetLeaseData.setRemark(req.getRemark());
        assetLeaseData.setDefaultLiabilityAgreement(req.getDefaultLiabilityAgreement());
        assetLeaseData.setContractOtherAgreement(req.getContractOtherAgreement());
        assetLeaseData.setTerminationContractAgreement(req.getTerminationContractAgreement());
        assetLeaseData.setRenewalAgreement(req.getRenewalAgreement());
        assetLeaseData.setDisputeResolutionAgreement(req.getDisputeResolutionAgreement());
        assetLeaseData.setMaintainAgreement(req.getMaintainAgreement());
        assetLeaseData.setHouseStructureAgreement(req.getHouseStructureAgreement());
        assetLeaseData.setBusinessAgreement(req.getBusinessAgreement());
        assetLeaseData.setLesseeOtherAgreement(req.getLesseeOtherAgreement());
        assetLeaseData.setRentAgreement(req.getRentAgreement());
        assetLeaseData.setManagementCapacity(req.getManagementCapacity());
        assetLeaseData.setCurrentDesc(req.getCurrentDesc());
        assetLeaseData.setAnnualIncrementRate(req.getAnnualIncrementRate());
        assetLeaseData.setPriorityQualification(req.getPriorityQualification());
        if(!AssetEnum.WebFlag.ADMIN.getValue().equals(req.getReservePrice())) {
            assetLeaseData.setReservePrice(new BigDecimal(req.getReservePrice()));
        }
        assetLeaseData.setLessorAddress(req.getLessorAddress());
        assetLeaseData.setDeposit(req.getDeposit());
        assetLeaseData.setBrightSpot(req.getBrightSpot());
        assetLeaseData.setServerAgencyId(req.getServerAgencyId());
        assetLeaseData.setJointStatus(asset.getJointStatus());
        assetLeaseData.setIncrement(req.getIncrement());
        assetLeaseData.setHandoverDays(req.getHandoverDays());
        assetLeaseData.setPayDays(req.getPayDays());
        assetLeaseData.setPreviewAt(req.getPreviewAt());
        assetLeaseData.setReduction(req.getReduction());
        assetLeaseData.setReductionPeriod(req.getReductionPeriod());
        assetLeaseData.setSpecialDetail(req.getSpecialDetail());
        assetLeaseData.setHouseStructure(req.getHouseStructure());
        assetLeaseData.setStatus(asset.getStatus().getKey());
        assetLeaseData.setSafetyAgreement(req.getSafetyAgreement());
        assetLeaseData.setQuantity(req.getQuantity());
        assetLeaseData.setPowerSituation(req.getPowerSituation());
        assetLeaseData.setOldData(false);
        assetLeaseData.setOriginalTenantNumber(req.getOriginalTenantNumber());
        assetLeaseData.setRegisteredAddress(req.getRegisteredAddress());
        assetLeaseData.setLessorContactNumber(req.getLessorContactNumber());
        assetLeaseData.setDeedFlag(req.getDeedFlag());
        assetLeaseData.setBidderQualification(req.getBidderQualification());
        assetLeaseData.setSubStatus(asset.getSubStatus());
        assetLeaseData.setComeFrom(AssetEnum.ComeFrom.PLATFORM.getKey());
        assetLeaseData.setPaymentCycle(req.getPaymentCycle());
        assetLeaseData.setCostBearer(req.getCostBearer());
        assetLeaseData.setTownId(asset.getTownId());
        assetLeaseData.setAssetLeaseArea(req.getAssetLeaseArea());
        assetLeaseData.setAgencyId(req.getServerAgencyId());
        assetLeaseData.setLeaseCode(req.getLeaseCode());
        assetLeaseData.setExpectedMode(req.getExpectedMode());
        assetLeaseData.setViewEndTime(req.getViewEndTime());
        assetLeaseData.setViewBeginTime(req.getViewBeginTime());
        assetLeaseData.setActualUse(req.getActualUse());
       }


    private void setAsset(Asset asset, AssetReq.LeaseDataReq req) {
        JSONObject assetLeaseArea = req.getAssetLeaseArea();
        JSONArray areaArray = assetLeaseArea.getJSONArray("areaArray");

        if(areaArray != null && areaArray.size() > 0) {
            setLeaseArea(asset, areaArray);
        }

        asset.setPartyId(req.getPartyId());
        asset.setJointStatus(req.getJointStatus());
        asset.setExpectedMode(AssetEnum.ExpectedMode.getModeByKey(req.getExpectedMode()));
        asset.setAssetType(AssetEnum.AssetType.LEASEHOLD);
        asset.setBeginAt(req.getBeginAt());
        asset.setDeleteFlag(false);
        // 处理extra
        JSONObject extra = handleExtra(req.getExtra(), req.getLeaseType());
        asset.setExtra(extra);
        asset.setEndAt(req.getEndAt());
        asset.setContactName(req.getContactName());
        asset.setContactQq(req.getContactQq());
        asset.setContactPhone(req.getContactPhone());
        asset.setRefPrice(req.getRefPrice());
        asset.setStartingPrice(req.getStartingPrice());
        asset.setUpdatedAt(new Date());
        asset.setCategoryId(-1);
        asset.setPayDays(req.getPayDays());
        asset.setHandoverDays(req.getHandoverDays());
        asset.setSpecialDetail(req.getSpecialDetail());
        asset.setBankAccountName(req.getBankAccountName());
        asset.setBankAccountNumber(req.getBankAccountNumber());
        asset.setTuneReport(req.getTuneReport());
        asset.setRemark(req.getRemark());
        asset.setDetail(req.getDetail());
        if(!AssetEnum.WebFlag.ADMIN.getValue().equals(req.getReservePrice())) {
            asset.setReservePrice(new BigDecimal(req.getReservePrice()));
        }
        asset.setTuneReportUrl(req.getTuneReportUrl());
        asset.setComeFrom(AssetEnum.ComeFrom.PLATFORM.getKey());

        asset.setAgencyId(req.getServerAgencyId());
     }


    private JSONObject handleExtra(JSONObject extra, String leaseType) {
        JSONArray images = extra.getJSONArray("images");

        if(images != null && images.size() > 0 &&
                StringUtils.isNotBlank(images.get(0).toString())) {

            return extra;
        }

        AssetLeaseTypeImageCondition assetLeaseTypeImageCondition = new AssetLeaseTypeImageCondition();
        assetLeaseTypeImageCondition.setLeaseType(leaseType);
        assetLeaseTypeImageCondition.setDeleteFlag(false);

        AssetLeaseTypeImage assetLeaseTypeImage = assetLeaseTypeImageDao.selectOneResult(assetLeaseTypeImageCondition);
        if(assetLeaseTypeImage != null && assetLeaseTypeImage.getLeaseImage() == null) {
            extra.put("images", new JSONArray());
        }else{
            String[] split = assetLeaseTypeImage.getLeaseImage().split(",");
            extra.put("images", (JSONArray) JSONArray.toJSON(split));
        }
        return extra;
    }


    private void setLeaseArea(Asset asset, JSONArray areaArray) {
        Set provinceLinkedHashSet = new LinkedHashSet();
        Set cityLinkedHashSet = new LinkedHashSet();
        Set areaLinkedHashSet = new LinkedHashSet();
        Set townLinkedHashSet = new LinkedHashSet();

        for(int i = 0;i < areaArray.size(); i++) {
            JSONObject areaJson = areaArray.getJSONObject(i);
            provinceLinkedHashSet.add(areaJson.getString("provinceId"));
            cityLinkedHashSet.add(areaJson.getString("id"));
            areaLinkedHashSet.add(areaJson.getString("areaId"));
            townLinkedHashSet.add(areaJson.getString("townId"));
        }

        asset.setProvinceId(String.join(",", provinceLinkedHashSet).contains("null") ?
                null: String.join(",", provinceLinkedHashSet));
        asset.setCityId(String.join(",", cityLinkedHashSet).contains("null") ?
                null: String.join(",", cityLinkedHashSet));
        asset.setAreaId(String.join(",", areaLinkedHashSet).contains("null")?
                null: String.join(",", townLinkedHashSet));
        asset.setTownId(String.join(",", townLinkedHashSet).contains("null")?
                null : String.join(",", townLinkedHashSet));

    }
}