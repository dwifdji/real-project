package com._360pai.core.provider.lease;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.common.constants.AuctionOfflineEnum;
import com._360pai.core.common.constants.LeaseEnum;
import com._360pai.core.common.constants.OrderEnum;
import com._360pai.core.condition.lease.TLeaseApplyCondition;
import com._360pai.core.condition.lease.TLeaseStaffCondition;
import com._360pai.core.condition.payment.AuctionOrderCondition;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.dao.agreement.DealAgreementDao;
import com._360pai.core.dao.asset.AssetDao;
import com._360pai.core.dao.assistant.DepositDao;
import com._360pai.core.dao.assistant.FavoriteActivityDao;
import com._360pai.core.dao.assistant.NotifyPartyActivityDao;
import com._360pai.core.dao.lease.TLeaseApplyDao;
import com._360pai.core.dao.lease.TLeaseAuditRecordDao;
import com._360pai.core.dao.payment.AuctionOrderDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.account.resp.LeaseStaffResp;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.lease.LeaseAuctionFacade;
import com._360pai.core.facade.lease.LeaseFacade;
import com._360pai.core.facade.lease.req.LeaseReq;
import com._360pai.core.facade.lease.vo.LeaseStaffVO;
import com._360pai.core.model.account.TAccount;
import com._360pai.core.model.account.TCompany;
import com._360pai.core.model.account.TParty;
import com._360pai.core.model.account.TUser;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.agreement.DealAgreement;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.lease.TLeaseApply;
import com._360pai.core.model.lease.TLeaseStaff;
import com._360pai.core.model.payment.AuctionOrder;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.CompanyService;
import com._360pai.core.service.account.PartyService;
import com._360pai.core.service.account.UserService;
import com._360pai.core.service.activity.AuctionActivityService;
import com._360pai.core.service.assistant.SmsHelperService;
import com._360pai.core.service.lease.LeaseAuctionService;
import com._360pai.core.service.lease.LeaseStaffService;
import com._360pai.core.vo.activity.FavoriteActivityVo;
import com._360pai.core.vo.lease.*;
import com._360pai.gateway.controller.req.fdd.FOpenMemberReq;
import com._360pai.gateway.controller.req.fdd.FOpenMemberResp;
import com._360pai.gateway.facade.FddSignatureFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageSerializable;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 租赁权拍卖
 *
 */
@Component
@Service(version = "1.0.0")
public class LeaseAuctionProvider implements LeaseAuctionFacade {

    @Autowired
    private LeaseAuctionService leaseAuctionService;


    @Autowired
    private LeaseStaffService leaseStaffService;

    @Autowired
    private SmsHelperService smsHelperService;


    @Autowired
    private AccountService accountService;

    @Autowired
    private AuctionActivityService auctionActivityService;

    @Resource
    private FavoriteActivityDao favoriteActivityDao;

    @Autowired
    private NotifyPartyActivityDao notifyPartyActivityDao;


    @Autowired
    private DepositDao depositDao;

    @Resource
    private AuctionOrderDao auctionOrderDao;

    @Autowired
    private TLeaseAuditRecordDao leaseAuditRecordDao;

    @Autowired
    private AuctionActivityDao auctionActivityDao;

    @Autowired
    private AssetDao assetDao;

    @Autowired
    private TLeaseApplyDao applyDao;

    @Resource
    private DealAgreementDao dealAgreementDao;




    @Override
    public ResponseModel myLeaseAuditList(LeaseReq.leaseAsset req) {

        PageSerializable resp = new PageSerializable();


        PageInfo info = leaseAuctionService.myLeaseAuditList(req);

        List<LeaseAuditVo>  list = getLeaseAuditVo(info.getList());

        resp.setTotal(info.getTotal());
        resp.setList(list);

        return ResponseModel.succ(resp);
    }

    private List<LeaseAuditVo> getLeaseAuditVo(List<LeaseAuditVo> list) {

        for(LeaseAuditVo vo : list){
            vo.setModeDesc(AssetEnum.ExpectedMode.getKeyByValue(vo.getMode()));
            vo.setTypeDesc("租赁权拍卖");
            vo.setAuditNum(getAuditNum(vo));
        }

        return list;
    }


    /**
     *
     *获取标的待审核数量
     */
    private String getAuditNum(LeaseAuditVo vo) {


        TLeaseApplyCondition condition = new TLeaseApplyCondition();
        condition.setActivityId(vo.getActivityId());
        condition.setStatus(LeaseEnum.ApplyStatus.PENDING.getKey());
        condition.setDeleteFlag(false);
        Integer num = leaseAuctionService.getLeaseApplyList(condition).size();
        return String.valueOf(num);
    }

    @Override
    public ResponseModel competeApplyList(LeaseReq.leaseAsset req) {

        PageSerializable resp = new PageSerializable();

        PageInfo info = leaseAuctionService.competeApplyList(req);

        List<LeaseCompeteApplyVo> competeApplyVos = getLeaseCompeteApply(info.getList());

        resp.setTotal(info.getTotal());
        resp.setList(competeApplyVos);
        return ResponseModel.succ(resp);
    }

    private List<LeaseCompeteApplyVo> getLeaseCompeteApply(List<LeaseCompeteApplyVo> list) {
        for(LeaseCompeteApplyVo apply :list){
            apply.setStatusDesc(LeaseEnum.ApplyStatus.getDescByKey(apply.getStatus()));
            apply.setUserType("1".equals(apply.getUserType())?"自然人":"法人");
        }
        return list;


    }

    @Override
    public ResponseModel competeApply(LeaseReq.leaseAsset req) {
        //审核参拍资格
        TLeaseApply apply = new TLeaseApply();
        apply.setUpdateTime(DateUtil.getSysTime());
        apply.setId(req.getId());
        apply.setStatus("1".equals(req.getType())?LeaseEnum.ApplyStatus.APPROVED.getKey():LeaseEnum.ApplyStatus.REJECT.getKey());
        leaseAuctionService.updateLeaseApply(apply);

        //审核结果发送短信
        new Thread(()->sendCompeteApplySms(apply)).start();

        return ResponseModel.succ();
    }

    private void sendCompeteApplySms(TLeaseApply updateApply) {

        try {

            TLeaseApply apply = applyDao.selectById(updateApply.getId());

            //标的信息
            AuctionActivity activity = auctionActivityDao.selectById(apply.getActivityId());

            Asset asset =assetDao.selectById(activity.getAssetId());
            //竞买人信息

            AccountBaseDto baseDto =accountService.getAccountBaseByPartyId(apply.getPartId());

            if(LeaseEnum.ApplyStatus.APPROVED.getKey().equals(apply.getStatus())){
                smsHelperService.leasePeriodPass(baseDto.getMobile(),asset.getName());

            }else {
                smsHelperService.leasePeriodRejection(baseDto.getMobile(),asset.getName());

            }


        }catch (Exception e){

        }




    }

    @Override
    public ResponseModel getLeadAuditList(LeaseReq.leaseAsset req) {

        PageSerializable page = getLeadAuditListPageInfo(req);

        return ResponseModel.succ(page);

     }

    private PageSerializable getLeadAuditListPageInfo(LeaseReq.leaseAsset req) {

        //获取该领导下的业务员party_id 以及该领导的权限
        TLeaseStaffCondition condition = new TLeaseStaffCondition();

        condition.setAccountId(req.getAccountId());
        condition.setIsDelete(false);
        condition.setForbidFlag(true);

        TLeaseStaff staff = leaseStaffService.getLeaseStaffByCondition(condition);

        if(staff ==null){
            throw new BusinessException("未找到员工信息！");

        }

        //获取领导下的业务员信息
        TLeaseStaffCondition condition1 = new TLeaseStaffCondition();
        condition1.setComId(staff.getComId());
        condition1.setAgentFlag(true);
        condition1.setIsDelete(false);
        condition1.setForbidFlag(true);
        List<TLeaseStaff> list =  leaseStaffService.getLeaseStaffList(condition1);

        if(list.size()<1){
            throw new BusinessException("该领导下未有业务员！");

        }
        req.setPartyIdList(getPartysInfo(list));
        req.setSubStatusList(getSubStatus(req,staff));

        if(req.getSubStatusList().size()<1){
            throw new BusinessException("未有审核权限！");
        }
        PageSerializable page = new PageSerializable();

        PageInfo info = leaseAuctionService.getLeadAuditList(req);
        page.setTotal(info.getTotal());
        page.setList(formatData(info.getList()));


        return page;

    }

    private List formatData(List<LeaseLeadAuditVo> list) {

        for(LeaseLeadAuditVo vo:list){

            vo.setStatusDesc(AssetEnum.LeaseStatus.getShowValueByKey(vo.getStatus()));
            vo.setModeDesc(AssetEnum.ExpectedMode.getKeyByValue(vo.getMode()));
            vo.setType("3");
            vo.setTypeDesc("租赁权拍卖");
        }


        return list;
    }

    private List<String> getSubStatus(LeaseReq.leaseAsset req,TLeaseStaff staff) {

        List<String> subStatusList =  new ArrayList<>();

        //初审权限
        if(staff.getTrialFlag()){

            subStatusList.add(AssetEnum.LeaseStatus.WAITING_FIRST_REVIEW.getKey());
            subStatusList.add(AssetEnum.LeaseStatus.SECOND_REVIEW_REJECTION.getKey());


            if("2".equals(req.getType())){
                subStatusList.add(AssetEnum.LeaseStatus.FIRST_REVIEW_REJECTION.getKey());
                subStatusList.add(AssetEnum.LeaseStatus.WAITING_RELEASE.getKey());
                subStatusList.add(AssetEnum.LeaseStatus.REGISTRATION_PERIOD.getKey());
                subStatusList.add(AssetEnum.LeaseStatus.QUALIFICATION_REVIEW.getKey());
            }

        }
        //终审权限
        if(staff.getFinalFlag()){
            subStatusList.add(AssetEnum.LeaseStatus.WAITING_SECOND_REVIEW.getKey());
            if("2".equals(req.getType())){
                subStatusList.add(AssetEnum.LeaseStatus.SECOND_REVIEW_REJECTION.getKey());
                subStatusList.add(AssetEnum.LeaseStatus.WAITING_RELEASE.getKey());
                subStatusList.add(AssetEnum.LeaseStatus.REGISTRATION_PERIOD.getKey());
                subStatusList.add(AssetEnum.LeaseStatus.QUALIFICATION_REVIEW.getKey());

            }
        }


        return subStatusList;
    }

    private List<String> getPartysInfo(List<TLeaseStaff> list) {

        List<String> partyIdList =  new ArrayList<>();
        for(TLeaseStaff staff1:list){
            partyIdList.add(String.valueOf(staff1.getPartId()));

        }
         return partyIdList;
    }

    @Override
    public ResponseModel leadAudit(LeaseReq.leaseAsset req) {
        return ResponseModel.succ(leaseAuctionService.leadAudit(req));
    }

    @Override
    public ResponseModel applyActivity(LeaseReq.leaseAsset req) {
        //校验是否为经办人报名
        if(checkPartyInfo(req)){
            return ResponseModel.fail("依据相关规定，您作为本场拍卖关联人，不能参加此次拍卖");
        }

        //校验是否已经有申请记录了
        TLeaseApplyCondition condition = new TLeaseApplyCondition();

        condition.setPartId(req.getPartyId());
        condition.setActivityId(Integer.valueOf(req.getActivityId()));
        condition.setDeleteFlag(false);

        if(leaseAuctionService.getLeaseApply(condition)!=null){

            return ResponseModel.fail("您已经申请过啦，请勿重新申请！");
        }

        //添加申请记录
        TLeaseApply apply = new TLeaseApply();
        apply.setActivityId(Integer.valueOf(req.getActivityId()));
        apply.setCreateTime(DateUtil.getSysTime());
        apply.setPartId(req.getPartyId());
        apply.setProveUrl(req.getProveUrl());
        apply.setDeleteFlag(false);
        apply.setStatus(LeaseEnum.ApplyStatus.PENDING.getKey());
        leaseAuctionService.saveLeaseApply(apply);

        //发送短信
        new Thread(()->applyActivitySendSms(apply)).start();

        return ResponseModel.succ();
    }

    private boolean checkPartyInfo(LeaseReq.leaseAsset req) {

        AuctionActivity activity = auctionActivityDao.selectById(req.getActivityId());


        Asset asset = assetDao.selectById(activity.getAssetId());

        if(asset.getPartyId() ==req.getPartyId() ){
            return true;
        }

        //获取经办人 的公司信息
        TLeaseStaffCondition condition = new TLeaseStaffCondition();
        condition.setPartId(asset.getPartyId());
        condition.setAgentFlag(true);
        condition.setIsDelete(false);
        condition.setForbidFlag(true);
        TLeaseStaff staff = leaseStaffService.getLeaseStaffByCondition(condition);

        //当登录人为公司报名时
        if(req.getPartyId()==staff.getComId()){

            return true;
        }


        //获取公司下所有的初审终审信息
        TLeaseStaffCondition condition1 = new  TLeaseStaffCondition();
        condition1.setComId(staff.getComId());
        condition1.setIsDelete(false);
        condition1.setForbidFlag(true);

        List<TLeaseStaff> staffList = leaseStaffService.getLeaseStaffList(condition1);

        for(TLeaseStaff staff1 :staffList){

            if(staff1.getPartId()!=null&&staff1.getPartId()==req.getPartyId()){
                return true;
            }

        }



        return false;
    }

    /**
     *
     *有人申请 短信通知经办人
     */
    private void applyActivitySendSms(TLeaseApply apply) {

        try {
            //标的信息
            AuctionActivity activity = auctionActivityDao.selectById(apply.getActivityId());

            Asset asset =assetDao.selectById(activity.getAssetId());


            //竞买人信息
            AccountBaseDto baseDto =accountService.getAccountBaseByPartyId(apply.getPartId());

            //经办人信息

            AccountBaseDto leaseBaseDto =accountService.getAccountBaseByPartyId(asset.getPartyId());


            smsHelperService.leasePeriod(leaseBaseDto.getMobile(),baseDto.getName(),asset.getName());

        }catch (Exception e){

        }



    }


    /**
     *
     *租赁权发布
     */
    @Override
    public ResponseModel leaseRelease(LeaseReq.leaseAsset req) {

        return leaseAuctionService.leaseRelease(req);
    }


    @Override
    public ResponseModel auditRecordList(LeaseReq.leaseAsset req) {

        PageSerializable page = new PageSerializable();

        PageInfo info = leaseAuctionService.getAuditRecordList(req);
        page.setTotal(info.getTotal());
        page.setList(formatRecordData(info.getList()));
        return ResponseModel.succ(page);    }

    @Override
    public ResponseModel myApplyLeaseRecord(LeaseReq.leaseAsset req) {
        PageSerializable page = new PageSerializable();

        PageInfo info = leaseAuctionService.myApplyLeaseRecord(req);

        page.setTotal(info.getTotal());
        page.setList(info.getList());
        return ResponseModel.succ(page);
    }

    private List<LeaseAuditRecordVo> formatRecordData(List<LeaseAuditRecordVo> list) {

        for(LeaseAuditRecordVo vo :list){
             vo.setStatusDesc(AssetEnum.LeaseStatus.getRecordValueByKey(vo.getStatusDesc()));
        }


        return list;
    }

    @Override
    public ResponseModel getLeadFlag(LeaseReq.leaseAsset req) {

        LeadFlagVo vo = new LeadFlagVo();
        vo.setApplyFlag(getApplyFlag(req));
        vo.setCompeteFlag(getCompeteFlag(req));
        vo.setDealFlag(getDealFlag(req));
        vo.setFocusFlag(getFocusFlag(req));
        vo.setRemindFlag(getRemindFlag(req));
        vo.setAgentAuditNum(getAgentAuditNumInfo(req));
        vo.setLeadAuditNum(getLeadAuditNumInfo(req));
        vo.setAgentReleaseNum(getAgentReleaseNumInfo(req));
        vo.setAgentSignNum(getAgentSignNumInfo(req));
        vo.setUserSignNum(getUserSignNumInfo(req));



        return ResponseModel.succ(vo);
    }

    private String getUserSignNumInfo(LeaseReq.leaseAsset req) {

        if(req.getPartyId()==null){
            return "0";
        }

        Integer num = 0;

        try {


            //获取送拍成交订单
            AuctionOrderCondition condition = new AuctionOrderCondition();
            condition.setDeleteFlag(false);
            condition.setBuyerId(req.getPartyId());

            List<AuctionOrder> orderList = auctionOrderDao.selectList(condition);

            for(AuctionOrder order : orderList){

                //当不为签署状态时 查询
                if(OrderEnum.Status.NOT_SIGNED.getKey().equals(order.getStatus())){

                    DealAgreement dealAgreement = dealAgreementDao.getByOrderId(Long.valueOf(order.getId()), AuctionOfflineEnum.ContractType.DEAL.getKey());

                    if(dealAgreement ==null || !dealAgreement.getBuyerSigned()){
                        num++;
                    }
                }


                if(OrderEnum.Status.NOT_SIGNED_LEASE.getKey().equals(order.getStatus())){

                    DealAgreement dealAgreement = dealAgreementDao.getByOrderId(Long.valueOf(order.getId()), AuctionOfflineEnum.ContractType.LEASE.getKey());
                    if(dealAgreement ==null || !dealAgreement.getBuyerSigned()){
                        num++;
                    }
                }


            }



        }catch (Exception e){


        }



        return String.valueOf(num);
    }


    /**
     *
     *经办人待签署协议数量
     */
    private String getAgentSignNumInfo(LeaseReq.leaseAsset req) {


        if(req.getPartyId()==null){
            return "0";
        }

        Integer num = 0;

        try {


            //获取送拍成交订单
            AuctionOrderCondition condition = new AuctionOrderCondition();
            condition.setDeleteFlag(false);
            condition.setSellerId(req.getPartyId());

            List<AuctionOrder> orderList = auctionOrderDao.selectList(condition);

            for(AuctionOrder order : orderList){

                //当不为签署状态时 查询
                if(OrderEnum.Status.NOT_SIGNED.getKey().equals(order.getStatus())){

                    DealAgreement dealAgreement = dealAgreementDao.getByOrderId(Long.valueOf(order.getId()), AuctionOfflineEnum.ContractType.DEAL.getKey());

                    if(dealAgreement ==null || !dealAgreement.getSellerSigned()){
                        num++;
                    }
                }


                if(OrderEnum.Status.NOT_SIGNED_LEASE.getKey().equals(order.getStatus())){

                    DealAgreement dealAgreement = dealAgreementDao.getByOrderId(Long.valueOf(order.getId()), AuctionOfflineEnum.ContractType.LEASE.getKey());
                    if(dealAgreement ==null || !dealAgreement.getSellerSigned()){
                        num++;
                    }
                }


            }



        }catch (Exception e){


        }



        return String.valueOf(num);
    }


    /**
     *
     *经办人待发布数量
     */
    private String getAgentReleaseNumInfo(LeaseReq.leaseAsset req) {

        Integer num = 0;

        try {

            //获取租赁权拍卖的 标的
            Map<String, Object> params = new HashMap<>();
            params.put("categoryId", "-1");
            params.put("partyId", req.getPartyId());

            List<Map> maps = assetDao.myAsset(params);
            for (Map map : maps) {
                if (AssetEnum.Status.PENDING.getKey().equals(String.valueOf(map.get("status")))) {
                    String subStatus = String.valueOf(map.get("subStatus"));
                    //等待发布状态
                    if(AssetEnum.LeaseStatus.WAITING_RELEASE.getKey().equals(subStatus)){
                        num++;
                    }
                }
            }
        }catch (Exception e){



        }

        return String.valueOf(num);
    }

    /**
     *
     *领导待审核数量
     */
    private String getLeadAuditNumInfo(LeaseReq.leaseAsset req) {

        String num = "0";
        try {
            PageSerializable page = getLeadAuditListPageInfo(req);

            num = String.valueOf(page.getTotal());

        }catch (Exception e){


        }

        return num;
    }

    /**
     *
     *经办人待审核数量
     */
    private String getAgentAuditNumInfo(LeaseReq.leaseAsset req) {
        int num = 0;
        try {

            PageInfo info = leaseAuctionService.myLeaseAuditList(req);

            List<LeaseAuditVo>  list = getLeaseAuditVo(info.getList());

            for(LeaseAuditVo vo :list){

                if(!"0".equals(vo.getAuditNum())){
                    num =++num;
                }
            }
        }catch (Exception e){

        }

        return String.valueOf(num);
    }



    private Boolean getRemindFlag(LeaseReq.leaseAsset req) {

        try {
            List<Map> maps = notifyPartyActivityDao.myNotify(req.getPartyId(), req.getAccountId(),"-1",null);

            return maps.size()>0;
        }catch (Exception e){


        }

        return false;

    }

    private Boolean getFocusFlag(LeaseReq.leaseAsset req) {


        try {
            Map<String, Object> params = new HashMap<>();
            params.put("categoryId", "-1");
            if (req.getPartyId() != null) {
                params.put("partyId", req.getPartyId());
            }
            params.put("accountId", req.getAccountId());

            List<FavoriteActivityVo> list = favoriteActivityDao.myFavor(params);

            return list.size()>0;

        }catch (Exception e){

        }


        return false;
    }

    private Boolean getDealFlag(LeaseReq.leaseAsset req) {

        try {

            if(req.getPartyId()==null){
                return false;
            }

            Map<String, Object> map = new HashMap<>();
            // 参拍人
            map.put("partyId", req.getPartyId());
            map.put("categoryId", "-1");

            List<Map<String, Object>> auctionOrders = auctionOrderDao.getAuctionOrders(map);

            return auctionOrders.size()>0;

        }catch (Exception e){

        }



        return false;
    }

    private Boolean getCompeteFlag(LeaseReq.leaseAsset req) {

        try {
            if(req.getPartyId()==null){
                return false;
            }
            List<Map> deposits = depositDao.myDepositList(req.getPartyId(),"-1",null);

            return deposits.size()>0;

        }catch (Exception e){

        }




        return false;
    }

    private Boolean getApplyFlag(LeaseReq.leaseAsset req) {


        try {
            if(req.getPartyId()==null){
                return false;
            }
            List<LeaseApplyRecordVo> leaseApplyRecordVos = leaseAuditRecordDao.myApplyLeaseRecord(req.getName(), req.getPartyId().toString());

            return leaseApplyRecordVos.size()>0;
        }catch (Exception e){

        }


        return false;
    }
}