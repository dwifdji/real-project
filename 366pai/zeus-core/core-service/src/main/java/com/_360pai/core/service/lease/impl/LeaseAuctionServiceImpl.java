package com._360pai.core.service.lease.impl;


import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.condition.activity.AuctionActivityCondition;
import com._360pai.core.condition.asset.AssetLeaseDataCondition;
import com._360pai.core.condition.lease.TLeaseApplyCondition;
import com._360pai.core.condition.lease.TLeaseStaffCondition;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.dao.asset.AssetDao;
import com._360pai.core.dao.asset.AssetLeaseDataDao;
import com._360pai.core.dao.lease.TLeaseApplyDao;
import com._360pai.core.dao.lease.TLeaseAuditRecordDao;
import com._360pai.core.dao.lease.TLeaseStaffDao;
import com._360pai.core.facade.lease.req.LeaseReq;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.asset.AssetLeaseData;
import com._360pai.core.model.lease.TLeaseApply;
import com._360pai.core.model.lease.TLeaseAuditRecord;
import com._360pai.core.model.lease.TLeaseStaff;
import com._360pai.core.service.assistant.SmsHelperService;
import com._360pai.core.service.lease.LeaseAuctionService;

import com._360pai.core.vo.lease.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 描述
 *
 */
@Service
public class LeaseAuctionServiceImpl implements LeaseAuctionService {
    @Autowired
    private TLeaseApplyDao leaseApplyDao;

    @Autowired
    private AssetDao assetDao;

    @Autowired
    private TLeaseStaffDao leaseStaffDao;


    @Autowired
    private TLeaseAuditRecordDao leaseAuditRecordDao;

    @Autowired
    private AuctionActivityDao auctionActivityDao;

    @Autowired
    private GatewayProperties gatewayProperties;


    @Autowired
    private SmsHelperService smsHelperService;
    @Autowired
    private AssetLeaseDataDao assetLeaseDataDao;


    @Override
    public PageInfo myLeaseAuditList(LeaseReq.leaseAsset req) {

        PageHelper.startPage(req.getPage(),req.getPerPage());
        Map paramMap = new HashMap();
        paramMap.put("partyId",req.getPartyId());
        paramMap.put("name",req.getName());


        List<LeaseAuditVo> list =  leaseApplyDao.myLeaseAuditList(paramMap);

        return new PageInfo<>(list);
    }

    @Override
    public PageInfo competeApplyList(LeaseReq.leaseAsset req) {

        PageHelper.startPage(req.getPage(),req.getPerPage());

        TLeaseApplyCondition condition = new TLeaseApplyCondition();
        condition.setActivityId(Integer.valueOf(req.getActivityId()));
        condition.setDeleteFlag(false);

        List<LeaseCompeteApplyVo> list = leaseApplyDao.getLeaseCompeteApply(condition);

        return new PageInfo<>(list);
    }

    @Override
    public void updateLeaseApply(TLeaseApply apply) {
        leaseApplyDao.updateById(apply);
    }

    @Override
    public void saveLeaseApply(TLeaseApply apply) {
        leaseApplyDao.insert(apply);

    }

    @Override
    public PageInfo getLeadAuditList(LeaseReq.leaseAsset req) {

        PageHelper.startPage(req.getPage(),req.getPerPage());

        Map paramMap = new HashMap();
        paramMap.put("partyId",req.getPartyIdList());
        paramMap.put("subStatus",req.getSubStatusList());
        paramMap.put("beginTime",req.getBeginTime());
        paramMap.put("endTime",req.getEndTime());
        paramMap.put("name",req.getName());



        List<LeaseLeadAuditVo> list = leaseApplyDao.getLeadAuditList(paramMap);

        return new PageInfo<>(list);
    }

    @Override
    @Transactional
    public ResponseModel leadAudit(LeaseReq.leaseAsset req) {

        //获取标的信息
        Asset asset = assetDao.selectById(req.getAssetId());

        if(asset==null){
            return ResponseModel.fail(ApiCallResult.DATA_IS_EMPTY);
        }

        //获取当前登录人的 权限
        if(checkAuth(asset,req)){
            return ResponseModel.fail("未有权限审核该标的！");
        }

        String subStatus = null;
        //更新审核信息 看下公司是
        if(AssetEnum.LeaseStatus.WAITING_FIRST_REVIEW.getKey().equals(asset.getSubStatus())||AssetEnum.LeaseStatus.SECOND_REVIEW_REJECTION.getKey().equals(asset.getSubStatus())){

            subStatus = "1".equals(req.getType())?AssetEnum.LeaseStatus.WAITING_SECOND_REVIEW.getKey():AssetEnum.LeaseStatus.FIRST_REVIEW_REJECTION.getKey();
        }

        if(AssetEnum.LeaseStatus.WAITING_SECOND_REVIEW.getKey().equals(asset.getSubStatus())){

            subStatus = "1".equals(req.getType())?AssetEnum.LeaseStatus.WAITING_RELEASE.getKey():AssetEnum.LeaseStatus.SECOND_REVIEW_REJECTION.getKey();

        }

        asset.setSubStatus(subStatus);
        asset.setUpdatedAt(DateUtil.getSysTime());


        //拒绝原因
        if( "2".equals(req.getType())){
            asset.setRejectReason(req.getReason());
        }

        assetDao.updateById(asset);

        // 同步更新leaseData
        updateLeaseData(asset);

        //保存审核的记录
        saveAuditRecord(req,asset);

        //发送领导审核短信通知
        new Thread(()->sendLeadAuditSms(asset)).start();

        return ResponseModel.succ();
    }

    private void updateLeaseData(Asset asset) {
        AssetLeaseDataCondition assetLeaseDataCondition = new AssetLeaseDataCondition();
        assetLeaseDataCondition.setAssetId(asset.getId());
        assetLeaseDataCondition.setDeleteFlag(false);

        AssetLeaseData assetLeaseData = assetLeaseDataDao.selectOneResult(assetLeaseDataCondition);

        if(assetLeaseData != null) {
            assetLeaseData.setUpdatedAt(new Date());
            assetLeaseData.setSubStatus(asset.getSubStatus());

            assetLeaseDataDao.updateById(assetLeaseData);
        }
    }

    private void sendLeadAuditSms(Asset asset) {

        try {
            //根据partyId 获取公司信息
            TLeaseStaffCondition condition = new TLeaseStaffCondition();
            condition.setPartId(asset.getPartyId());
            condition.setForbidFlag(true);
            condition.setIsDelete(false);
            TLeaseStaff staff = leaseStaffDao.selectFirst(condition);

            TLeaseStaffCondition condition1 = new TLeaseStaffCondition();
            condition1.setComId(staff.getComId());
            condition1.setForbidFlag(true);
            condition1.setIsDelete(false);
            List<TLeaseStaff> staffList = leaseStaffDao.selectList(condition1);

            List<String> firstStaff = new ArrayList<>();

            List<String> secondStaff = new ArrayList<>();

            for(TLeaseStaff leaseStaff :staffList){
                if(leaseStaff.getTrialFlag()){
                    firstStaff.add(leaseStaff.getMobile());
                }
                if(leaseStaff.getFinalFlag()){
                    secondStaff.add(leaseStaff.getMobile());
                }
            }



            //获取审核状态
            String subStatus = asset.getSubStatus();

            //初审通过 通知终审
            if(AssetEnum.LeaseStatus.WAITING_SECOND_REVIEW.getKey().equals(subStatus)){

                for(String mobile :secondStaff){

                    smsHelperService.leaseFirstPush(mobile,asset.getName());
                }

                //初审退回  通知经办人
            }else if(AssetEnum.LeaseStatus.FIRST_REVIEW_REJECTION.getKey().equals(subStatus)){
                smsHelperService.firstReviewRejection(staff.getMobile(),asset.getName());

                //终审通过  通知经办人
            }else if(AssetEnum.LeaseStatus.WAITING_RELEASE.getKey().equals(subStatus)){
                smsHelperService.waitingRelease(staff.getMobile(),asset.getName());

                //终审退回 通知初审
            }else if(AssetEnum.LeaseStatus.SECOND_REVIEW_REJECTION.getKey().equals(subStatus)){
                for(String mobile :firstStaff){

                    smsHelperService.secondReviewRejection(mobile,asset.getName());


                }
            }







        }catch (Exception e){


        }
    }

    private void saveAuditRecord(LeaseReq.leaseAsset req, Asset asset) {
        TLeaseAuditRecord record = new TLeaseAuditRecord();
        record.setAccountId(req.getAccountId());
        record.setActivityId(asset.getId());
        record.setCreateTime(DateUtil.getSysTime());
        record.setReason(req.getReason());
        record.setSteps(asset.getSubStatus());
        record.setStatus(req.getType());
        leaseAuditRecordDao.insert(record);

    }

    private boolean checkAuth(Asset asset, LeaseReq.leaseAsset req) {

        //获取该用户的权限
        TLeaseStaffCondition condition = new TLeaseStaffCondition();
        condition.setAccountId(req.getAccountId());
        condition.setForbidFlag(true);
        TLeaseStaff staff = leaseStaffDao.selectFirst(condition);
        if(staff==null){
           return true;
        }
        //没有初审权限
        if(AssetEnum.LeaseStatus.WAITING_FIRST_REVIEW.getKey().equals(asset.getSubStatus())&&!staff.getTrialFlag()){
            return true;
        }

        //没有终审权限
        if(AssetEnum.LeaseStatus.WAITING_SECOND_REVIEW.getKey().equals(asset.getSubStatus())&&!staff.getFinalFlag()){
            return true;
        }

        return false;
    }

    @Override
    public TLeaseApply getLeaseApply(TLeaseApplyCondition condition) {
        return leaseApplyDao.selectFirst(condition);
    }

    @Override
    public List<TLeaseApply> getLeaseApplyList(TLeaseApplyCondition condition) {
        return leaseApplyDao.selectList(condition);
    }


    @Override
    @Transactional
    public ResponseModel leaseRelease(LeaseReq.leaseAsset req) {

        Asset asset = new Asset();
        asset.setId(Integer.valueOf(req.getAssetId()));
        asset.setStatus(AssetEnum.Status.APPROVED);
        assetDao.updateById(asset);

        AuctionActivityCondition condition = new AuctionActivityCondition();
        condition.setAssetId(Integer.valueOf(req.getAssetId()));

        AuctionActivity activity =auctionActivityDao.selectFirst(condition);
        activity.setStatus(ActivityEnum.Status.PLATFORM_REVIEWING);
        //添加拍卖师信息
        activity.setAuctioneerName(gatewayProperties.getLeaseAuctioneerName());
        activity.setAuctioneerPhone(gatewayProperties.getLeaseAuctioneerPhone());
        activity.setAuctioneerQq(gatewayProperties.getLeaseAuctioneerQq());
        auctionActivityDao.updateById(activity);


        return ResponseModel.succ();
    }


    @Override
    public PageInfo getAuditRecordList(LeaseReq.leaseAsset req) {


        PageHelper.startPage(req.getPage(),req.getPerPage());
        Map paramMap = new HashMap();
        paramMap.put("assetId",req.getAssetId());

        List<LeaseAuditRecordVo> list =  leaseAuditRecordDao.getAuditRecordList(paramMap);

        return new PageInfo<>(list);
     }

    @Override
    public PageInfo myApplyLeaseRecord(LeaseReq.leaseAsset req) {
        PageHelper.startPage(req.getPage(),req.getPerPage());

        List<LeaseApplyRecordVo> leaseApplyRecordVos = leaseAuditRecordDao.myApplyLeaseRecord(req.getName(), req.getPartyId().toString());

        if(leaseApplyRecordVos == null) {
            return new PageInfo<>(new ArrayList<>());
        }

        return new PageInfo<>(leaseApplyRecordVos);
    }
}
