package com._360pai.core.provider.enrolling;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.JsonUtil;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.aspact.ExceptionEmailService;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.common.constants.ExceptionEmailEnum;
import com._360pai.core.common.constants.ServiceConfigEnum;
import com._360pai.core.condition.enrolling.EnrollingActivityCommissionOrderCondition;
import com._360pai.core.condition.enrolling.EnrollingActivityContractCondition;
import com._360pai.core.condition.enrolling.EnrollingDepositCondition;
import com._360pai.core.dao.enrolling.EnrollingActivityContractDao;
import com._360pai.core.dao.enrolling.EnrollingShareProfitOrderDao;
import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.dto.enrolling.EnrollingListReqDto;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.enrolling.EnrollingFacade;
import com._360pai.core.facade.enrolling.req.EnrollingReq;
import com._360pai.core.facade.enrolling.req.EnrollingReq.activitiesListReq;
import com._360pai.core.model.assistant.TServiceConfig;
import com._360pai.core.model.enrolling.*;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.assistant.TServiceConfigService;
import com._360pai.core.service.enrolling.*;
import com._360pai.core.vo.enrolling.*;
import com._360pai.gateway.common.alisms.AliSmsTemplateEnums;
import com._360pai.gateway.common.dfftpay.PayEnums;
import com._360pai.gateway.common.dfftpay.PayResultEnums;
import com._360pai.gateway.common.fddSignature.FddEnums;
import com._360pai.gateway.controller.req.alisms.FAliSmsSendReq;
import com._360pai.gateway.controller.req.dfftpay.LockOrReleaseOrDirectReq;
import com._360pai.gateway.controller.req.dfftpay.UnifiedPayReq;
import com._360pai.gateway.controller.req.dfftpay.UnifiedPayResp;
import com._360pai.gateway.controller.req.fdd.*;
import com._360pai.gateway.facade.FddSignatureFacade;
import com._360pai.gateway.facade.PayFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageSerializable;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * 描述：预招商Facade接口实现
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 14:16
 */
@Component
@Service(version = "1.0.0")
public class EnrollingProvider implements EnrollingFacade {

    private final Logger logger = LoggerFactory.getLogger(EnrollingProvider.class);


    @Autowired
    private ExceptionEmailService exceptionEmailService;


    @Autowired
    private EnrollingActivityService  enrollingActivityService;


    @Autowired
    private GatewayProperties gatewayProperties;


    @Autowired
    private EnrollingDepositService enrollingDepositService;


    @Autowired
    private NotifyPartyEnrollingActivityService notifyPartyEnrollingActivityService;


    @Autowired
    private EnrollingActivityShareStatsService enrollingActivityShareStatsService;


    @Autowired
    private EnrollingActivityProgressService enrollingActivityProgressService;

    @Autowired
    private EnrollingActivityResultService enrollingActivityResultService;


    @Autowired
    private AccountService accountService;


    @Autowired
    private EnrollingShareProfitOrderDao enrollingShareProfitOrderDao;


    @Autowired
    private EnrollingActivityContractDao enrollingActivityContractDao;



    @Autowired
    private EnrollingActivityCommissionOrderService enrollingActivityCommissionOrderService;



    @Autowired
    private FavoriteEnrollingActivityService favoriteEnrollingActivityService;

    @Autowired
    private EnrollingActivityContractService enrollingActivityContractService;


    @Autowired
    private TServiceConfigService tServiceConfigService;


    @Autowired
    private GatewayMqSender gatewayMqSender;



    @Reference(version = "1.0.0")
    private FddSignatureFacade fddSignatureFacade;


    @Reference(version = "1.0.0")
    private PayFacade payFacade;


    @Override
    public ResponseModel getEnrollingActivityList(EnrollingReq.activitiesListReq req) {

        EnrollingListReqDto params = new EnrollingListReqDto();
        BeanUtils.copyProperties(req, params);
        params.setStatus(EnrollingEnum.QUERY_STATUS.getDesc(req.getStatus()));
        PageInfo info = enrollingActivityService.getEnrollingActivityList(params);

        List<EnrollingActivityVo> list = info.getList();
        List<EnrollingActivityListVo>  respList = getEnrollingActivities(list);

        PageSerializable resp = new PageSerializable();
        resp.setList(respList);
        resp.setTotal(info.getTotal());


        return ResponseModel.succ(resp);
    }

    private List<EnrollingActivityListVo> getEnrollingActivities(List<EnrollingActivityVo> list) {

        List<EnrollingActivityListVo>  respList = new ArrayList<>();

        for(EnrollingActivityVo activity : list){

            EnrollingActivityListVo vo = new EnrollingActivityListVo();

            BeanUtils.copyProperties(activity, vo);

            vo.setStatus(EnrollingEnum.QUERY_STATUS.getType(EnrollingEnum.STATUS.getStatus(activity.getStatus())));
            vo.setStatusDesc(activity.getStatus());
            vo.setPartyName(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType()==activity.getThirdPartyStatus()?activity.getUserAgencyName():activity.getPartyName());
            vo.setAmount(StringUtils.isEmpty(activity.getRefPrice())?"暂无":activity.getRefPrice());
            vo.setInterest(activity.getDeposit());
            vo.setCreatedAt(activity.getCreatedAt());
            vo.setCreatedAtFrom(activity.getBeginAt());
            vo.setCreatedAtTo(activity.getEndAt());
            vo.setOperateAt(activity.getOperateAt());
            vo.setOperateName(activity.getOperateName());
            vo.setSource(activity.getThirdPartyStatus());
            vo.setSellerName(vo.getPartyName());
            vo.setCategoryName(activity.getType());
            vo.setAssetName(vo.getName());
            respList.add(vo);
        }

        return respList;
    }

    @Override
    public ResponseModel getBaseInfo(EnrollingReq.activityIdReq req) {
    	AgencyActivityDetailVO baseInfo = enrollingActivityService.getAgencyActivityInfo(req.getActivityId());

    	//



        return  ResponseModel.succ(baseInfo);
    }

    @Override
    public ResponseModel getDetail(EnrollingReq.activityIdReq req) {

        EnrollingActivity activity =enrollingActivityService.getEnrollingActivityById(req.getActivityId());

        EnrollingDetailVo vo = new EnrollingDetailVo();

        vo.setDetail(activity.getDetail());
        vo.setName(activity.getName());
        vo.setVisibilityLevel(EnrollingEnum.VISIBILITY_LEVEL_QUERY.getDesc(activity.getVisibilityLevel()));

        EnrollingActivityContractCondition contractCondition = new EnrollingActivityContractCondition();
        contractCondition.setActivityId(Integer.valueOf(req.getActivityId()));

        EnrollingActivityContract contract = enrollingActivityContractDao.selectFirst(contractCondition);
        if(contract!=null){
            vo.setDownUrl(contract.getDownloadUrl());
            vo.setShowUrl(contract.getViewpdfUrl());
        }

        return ResponseModel.succ(vo);
    }

    @Override
    public ResponseModel enrollingOrders(EnrollingReq.activityIdReq req) {

        ActivityIdReqDto dto = new ActivityIdReqDto();
        dto.setActivityId(req.getActivityId());
        dto.setPage(req.getPage());
        dto.setPerPage(req.getPerPage());

        PageInfo info = enrollingDepositService.getEnrollingDepositList(dto);

        List<EnrollingDepositListVo> list = info.getList();

        List<EnrollingDepositVo> respList = new ArrayList<>();

        //获取招商结果
        ActivityIdReqDto reqDto = new ActivityIdReqDto();
        reqDto.setActivityId(req.getActivityId());

        Boolean result = enrollingActivityResultService.getResult(reqDto)==null;

        //获取支付订单
        EnrollingActivityCommissionOrderCondition condition = new EnrollingActivityCommissionOrderCondition();
        condition.setActivityId(Integer.valueOf(req.getActivityId()));

        EnrollingActivityCommissionOrder order = enrollingActivityCommissionOrderService.getCommissionOrder(condition);

        boolean isBank = getIsBank(req.getActivityId());

        //是否有未锁定的订单
        boolean hasLockDeposit = getLockDeposit(req.getActivityId());


        PageSerializable resp = new PageSerializable();

        for(EnrollingDepositListVo deposit : list){
            EnrollingDepositVo vo = new EnrollingDepositVo();
            vo.setAgency(StringUtils.isEmpty(deposit.getAgency())?"无":deposit.getAgency());
            vo.setCreatedAt(deposit.getCreatedAt());
            vo.setMobile(deposit.getComMobile());
            vo.setPartyNumber(deposit.getComNumber());
            vo.setPartyName(deposit.getComName());
            vo.setId(deposit.getDepositId());
            if(!StringUtils.isEmpty(deposit.getUseMobile())){
                vo.setMobile(deposit.getUseMobile());
                vo.setPartyNumber(deposit.getUseNumber());
                vo.setPartyName(deposit.getUseName());
            }
            vo.setStatus(EnrollingEnum.QUERY_PAY_STATUS.getDesc(deposit.getStatus()));

            vo.setDeal("2");
            if(!result&&!hasLockDeposit&&order==null&&!isBank){
                vo.setDeal("1");
            }
            respList.add(vo);
        }
        resp.setList(respList);
        resp.setTotal(info.getTotal());
        return ResponseModel.succ(resp);
    }

    /**
     *
     *获取是否还有锁定的保证金
     */
    private boolean getLockDeposit(String activityId) {

        try {
            EnrollingDepositCondition condition = new EnrollingDepositCondition();
            condition.setActivityId(Integer.valueOf(activityId));
            condition.setStatus(EnrollingEnum.PAY_STATUS.LOCKED.getType());

            return  enrollingDepositService.getEnrollingDepositList(condition).size()>0;

        }catch (Exception e){

            logger.error("查询锁定保证金异常，异常信息为：{}",e);
        }


        return false;
    }


    private boolean getIsBank(String activityId) {

        try {
            EnrollingActivity activity = enrollingActivityService.getEnrollingActivityById(activityId);

            AccountBaseDto baseDto = accountService.getAccountBaseByPartyId(activity.getPartyId());

            return baseDto.isBank();
        }catch (Exception e){

            logger.error("判断预招商是否为银行用户异常，异常信息为：{}",e);

        }

        return false;
    }

    @Override
    public ResponseModel notifiedList(EnrollingReq.activityIdReq req) {

        ActivityIdReqDto dto = new ActivityIdReqDto();
        dto.setActivityId(req.getActivityId());
        dto.setPage(req.getPage());
        dto.setPerPage(req.getPerPage());

        PageInfo info = notifyPartyEnrollingActivityService.getNotifiedList(dto);

        PageSerializable resp = new PageSerializable();

        List<EnrollingNoticeVo> noticeList = new ArrayList<>();

        List<EnrollingInfoVo> respList = info.getList();

        for(EnrollingInfoVo notice:respList){
            EnrollingNoticeVo vo = new EnrollingNoticeVo();

            vo.setCreatedAt(notice.getCreatedAt());
            vo.setMobile(notice.getComMobile());
            vo.setName(notice.getComName());
            if(!StringUtils.isEmpty(notice.getUseMobile())){
                vo.setMobile(notice.getUseMobile());
                vo.setName(notice.getUseName());
            }
            noticeList.add(vo);
        }
        resp.setTotal(info.getTotal());
        resp.setList(noticeList);
        return ResponseModel.succ(resp);
    }

    @Override
    public ResponseModel shareList(EnrollingReq.activityIdReq req) {
        ActivityIdReqDto dto = new ActivityIdReqDto();

        dto.setActivityId(req.getActivityId());
        dto.setPage(req.getPage());
        dto.setPerPage(req.getPerPage());
        PageInfo info = enrollingActivityShareStatsService.getShareList(dto);

        PageSerializable resp = new PageSerializable();

        List<EnrollingShareVo> noticeList = new ArrayList<>();

        List<EnrollingInfoVo> respList = info.getList();

        for(EnrollingInfoVo notice:respList){
            EnrollingShareVo vo = new EnrollingShareVo();

            vo.setCreatedAt(notice.getCreatedAt());
            vo.setMobile(notice.getComMobile());
            vo.setName(notice.getComName());
            if(!StringUtils.isEmpty(notice.getUseMobile())){
                vo.setMobile(notice.getUseMobile());
                vo.setName(notice.getUseName());
            }
            noticeList.add(vo);
        }
        resp.setTotal(info.getTotal());
        resp.setList(noticeList);
        return ResponseModel.succ(resp);
     }

     //预招商关注列表
    @Override
    public ResponseModel focusList(EnrollingReq.activityIdReq req) {

        ActivityIdReqDto dto = new ActivityIdReqDto();

        dto.setActivityId(req.getActivityId());
        dto.setPage(req.getPage());
        dto.setPerPage(req.getPerPage());

        PageSerializable resp = new PageSerializable();

        List<EnrollingFocusVo> focusList = new ArrayList<>();

        PageInfo info = favoriteEnrollingActivityService.getFocusList(dto);

        List<EnrollingInfoVo>  list = info.getList();

        for(EnrollingInfoVo share : list){
            EnrollingFocusVo vo = new EnrollingFocusVo();
            vo.setCreatedAt(share.getCreatedAt());
            vo.setMobile(share.getComMobile());
            vo.setName(share.getComName());
            if(!StringUtils.isEmpty(share.getUseMobile())){
                vo.setMobile(share.getUseMobile());
                vo.setName(share.getUseName());
            }
            focusList.add(vo);
        }

        resp.setTotal(info.getTotal());
        resp.setList(focusList);
        return ResponseModel.succ(resp);
    }

    @Override
    public ResponseModel progressRecords(EnrollingReq.activityIdReq req) {
        ActivityIdReqDto dto = new ActivityIdReqDto();
        dto.setActivityId(req.getActivityId());
        dto.setPage(req.getPage());
        dto.setPerPage(req.getPerPage());

        PageInfo info = enrollingActivityProgressService.getProgressList(dto);


        PageSerializable resp = new PageSerializable();

        List<EnrollingProgressVo> progressList = new ArrayList<>();

        List<EnrollingActivityProgress> respList = info.getList();

        for(EnrollingActivityProgress progress:respList){
            EnrollingProgressVo vo = new EnrollingProgressVo();

            vo.setTitle(progress.getTitle());
            vo.setContext(progress.getContent());

            progressList.add(vo);
        }
        resp.setTotal(info.getTotal());
        resp.setList(progressList);
        return ResponseModel.succ(resp);
    }

    @Override
    public ResponseModel result(EnrollingReq.activityIdReq req) {

        ActivityIdReqDto dto = new ActivityIdReqDto();
        dto.setActivityId(req.getActivityId());

        EnrollingActivityResult result = enrollingActivityResultService.getResult(dto);

        if(result == null){
            return  ResponseModel.fail("暂无结果");
        }


        EnrollingResultVo vo = new EnrollingResultVo();

        vo.setName(result.getName());
        vo.setNumber(result.getNumber());
        vo.setPrice(String.valueOf(result.getPrice()));
        return ResponseModel.succ(vo);
    }

    @Override
    public ResponseModel updateDetail(EnrollingReq.updateDetailReq req) {
        EnrollingActivity activity = new EnrollingActivity();
        activity.setId(Integer.valueOf(req.getActivityId()));
        activity.setDetail(req.getDetail());

        enrollingActivityService.updateEnrollingActivityById(activity);

        return ResponseModel.succ();
    }

    @Override
    public ResponseModel updateVisibilityLevel(EnrollingReq.updateVisibilityLevelReq req) {
        EnrollingActivity activity = new EnrollingActivity();
        activity.setId(Integer.valueOf(req.getActivityId()));
        activity.setVisibilityLevel(req.getVisibilityLevel());

        enrollingActivityService.updateEnrollingActivityById(activity);

        return ResponseModel.succ();
    }

    @Override
    public ResponseModel enrollmentsOrders(EnrollingReq.activitiesListReq req) {

        ActivityIdReqDto dto = new ActivityIdReqDto();
        dto.setPage(req.getPage());
        dto.setPerPage(req.getPerPage());

        PageInfo info = enrollingDepositService.getEnrollingDepositList(dto);


        List<EnrollingDepositListVo> list = info.getList();


        List<EnrollingEnrollmentsOrdersVo> respList = new ArrayList<>();


        PageSerializable resp = new PageSerializable();


        for(EnrollingDepositListVo deposit : list){
            EnrollingEnrollmentsOrdersVo vo = new EnrollingEnrollmentsOrdersVo();
            vo.setCreatedAt(deposit.getCreatedAt());
            vo.setPartyName(deposit.getUseName());
            vo.setCode("code_001");
            vo.setRefPrice("111");
            vo.setParticipantNumber("1000");
            vo.setName("name_001");
            respList.add(vo);
        }
        resp.setList(respList);
        resp.setTotal(info.getTotal());
        return ResponseModel.succ(resp);

     }

    @Override
    public ResponseModel commissionOrders(EnrollingReq.activitiesListReq req) {

        EnrollingListReqDto dto = new EnrollingListReqDto();
        dto.setPage(req.getPage());
        dto.setPerPage(req.getPerPage());
        if(StringUtils.isNotEmpty(req.getStatus())){
            dto.setStatus("1".equals(req.getStatus())?"0":"1");
        }
        dto.setInfo(req.getInfo());
        dto.setCreatedAtFrom(req.getCreatedAtFrom());
        dto.setCreatedAtTo(req.getCreatedAtTo());
        dto.setPartyType(req.getPartyType());
        dto.setPartyName(req.getPartyName());

        PageInfo info = enrollingActivityCommissionOrderService.getCommissionOrderList(dto);

        PageSerializable resp = new PageSerializable();

        List<CommissionOrderVo> commissionOrderList = new ArrayList<>();

        List<EnrollingActivityCommissionOrderVo> respList = info.getList();

        for(EnrollingActivityCommissionOrderVo order:respList){
            CommissionOrderVo vo = new CommissionOrderVo();

            vo.setAmount(String.valueOf(order.getAmount()));
            vo.setBuyer(order.getBuyerName());
            vo.setCreatedAt(order.getCreatedAt());
            vo.setPaid(order.getPaid());
            vo.setCode(order.getCode());
            vo.setAmount(order.getAmount());
            vo.setName(order.getName());
            vo.setPartyName(StringUtils.isEmpty(order.getUserName())?order.getComName():order.getUserName());
            if(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType()==order.getThirdPartyStatus()){
                vo.setPartyName(order.getAgencyName());

            }
             commissionOrderList.add(vo);
        }
        resp.setTotal(info.getTotal());
        resp.setList(commissionOrderList);
        return ResponseModel.succ(resp);
    }

    @Override
    public ResponseModel getAllCities() {


        return ResponseModel.succ();
    }

    @Override
    public ResponseModel finishActivity(EnrollingReq.activityIdReq req) {

        EnrollingActivity activity1 = enrollingActivityService.getEnrollingActivityById(req.getActivityId());
        if(activity1==null){
            return ResponseModel.fail("不存在该活动！");
        }

        //抵押物预招商的时候 校验是否还有为释放或者没收的保证金
        if(EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(activity1.getType())){

            EnrollingDepositCondition condition = new EnrollingDepositCondition();
            condition.setActivityId(Integer.valueOf(req.getActivityId()));
            condition.setStatus(EnrollingEnum.PAY_STATUS.LOCKED.getType());

            List<EnrollingDeposit> depositList = enrollingDepositService.getEnrollingDepositList(condition);
            if(depositList.size()>0){
                return ResponseModel.fail("该活动还存在锁定状态的保证金，不能结束该活动！");
            }
        }

        EnrollingActivity activity = new EnrollingActivity();
        activity.setId(Integer.valueOf(req.getActivityId()));
        activity.setStatus(EnrollingEnum.STATUS.CLOSED.getType());

        enrollingActivityService.updateEnrollingActivityById(activity);

        return ResponseModel.succ();
    }

    @Override
    public ResponseModel getAuditList(EnrollingReq.activitiesListReq req) {

        EnrollingListReqDto params = new EnrollingListReqDto();

        BeanUtils.copyProperties(req, params);

        params.setStatus(EnrollingEnum.QUERY_STATUS.getDesc(req.getStatus()));

        PageInfo info = enrollingDepositService.getAuditList(params);

        List<EnrollingAuditListVo> list = info.getList();

        List<EnrollingAuditVo> respList = new ArrayList<>();

        PageSerializable resp = new PageSerializable();

        for(EnrollingAuditListVo audit : list){
            EnrollingAuditVo vo = new EnrollingAuditVo();

            vo.setAmount(audit.getAmount());
            vo.setCode(audit.getCode());
            vo.setName(audit.getName());
            vo.setAmount(audit.getAmount());
            vo.setParticipateProof(audit.getParticipateProof());
            vo.setMobile(StringUtils.isEmpty(audit.getUseMobile())?audit.getComMobile():audit.getUseMobile());
            vo.setPartyName(StringUtils.isEmpty(audit.getUseName())?audit.getComName():audit.getUseName());
            vo.setCreatedAt(audit.getCreatedAt());
            respList.add(vo);

         }
        resp.setList(respList);
        resp.setTotal(info.getTotal());
        return ResponseModel.succ(resp);
     }

	@Override
	public ResponseModel getAvailableEnrollingActivities(activitiesListReq req) {
        EnrollingListReqDto params = new EnrollingListReqDto();
        BeanUtils.copyProperties(req, params);
        params.setStatus(EnrollingEnum.QUERY_STATUS.getDesc(req.getStatus()));
        
        PageInfo info = enrollingActivityService.getAvailableEnrollingActivities(params);
        PageSerializable resp = new PageSerializable();

        List<EnrollingActivityListVo>  respList = new ArrayList<>();
        List<EnrollingActivityVo> list = info.getList();

        for(EnrollingActivityVo activity : list){
            EnrollingActivityListVo vo = new EnrollingActivityListVo();
            BeanUtils.copyProperties(activity, vo);
            vo.setStatus(EnrollingEnum.STATUS.getDesc(activity.getStatus()));
            vo.setPartyName(StringUtils.isNotEmpty(activity.getUserName())?activity.getUserName():activity.getComName());
            respList.add(vo);
        }

        resp.setList(respList);
        resp.setTotal(info.getTotal());

        return ResponseModel.succ(resp);
	}

	//预招商活动审核
    @Override
    public ResponseModel auditEnrollingActivities(EnrollingReq.auditEnrolling req) {

        //查询活动
        EnrollingActivity activity = enrollingActivityService.getEnrollingActivityById(req.getActivityId());
        if(StringUtils.isNotBlank(req.getOperateId())){
            activity.setOperatorAt(new Date());
            activity.setOperatorId(Integer.parseInt(req.getOperateId()));
        }

        if(activity==null||!EnrollingEnum.STATUS.AGENCY_APPROVED.getType().equals(activity.getStatus())){
            return ResponseModel.fail("活动不存在或者状态不为等待审核状态！");
        }

         //审核通过
        if(req.getStatus().equals("1")){
            activity.setStatus(EnrollingEnum.STATUS.PLATFORM_APPROVED.getType());
            if(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType()!=activity.getThirdPartyStatus()){
                //当用户属性为不用签署协议是 直接修改为上线状态
                AccountBaseDto accountInfo = accountService.getAccountBaseByPartyId(activity.getPartyId());
                if(accountInfo.getOperWithoutFadada()){
                    activity.setStatus(EnrollingEnum.STATUS.ONLINE.getType());
                }
            }
        }else{
            activity.setStatus(EnrollingEnum.STATUS.PLATFORM_REJECT.getType());
            activity.setRejectReason(req.getReason());

        }
        enrollingActivityService.updateEnrollingActivityById(activity);

        //当审核通过时生成签署的合同
        if(req.getStatus().equals("1")&&EnrollingEnum.STATUS.PLATFORM_APPROVED.getType().equals(activity.getStatus())){

            //异步生成合同
            new Thread(() -> generateContract(activity)).start();

        }
        //根据审核结果发送短信
        if(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType()!=activity.getThirdPartyStatus()) {
            sendSms(req, activity);
        }
        return ResponseModel.succ();
    }

    private void sendSms(EnrollingReq.auditEnrolling req, EnrollingActivity activity) {
         FAliSmsSendReq smsReq = new FAliSmsSendReq();
        try{

            AccountBaseDto baseDto = accountService.getAccountBaseByPartyId(activity.getPartyId());

            smsReq.setPhoneNumber(baseDto.getMobile());

            Map<String, Object> params = new HashMap<>();
            params.put("enrolling_code",activity.getCode());
            smsReq.setTemplateParam(JSON.toJSONString(params));
            smsReq.setTemplateCode(req.getStatus().equals("1")?AliSmsTemplateEnums.WEB_APPROVED.getCode():AliSmsTemplateEnums.WEB_AUDIT_FAIL.getCode());
            gatewayMqSender.sendSms(smsReq);

            //审核通过 还要弄一个支付提醒短信
            if(req.getStatus().equals("1")){
                smsReq.setTemplateCode(AliSmsTemplateEnums.WEB_APPROVED_PAY_NOTIFY.getCode());
                gatewayMqSender.sendSms(smsReq);
            }

        }catch (Exception e){

            exceptionEmailService.sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.ENROLLING,"审核发送邮件",JSON.toJSONString(smsReq),exceptionEmailService.exceptionToStr(e));

            logger.info("审核发送邮件异常，异常信息为：{}",e);

        }


    }


    //保证金操作
    @Override
    public ResponseModel marginOperation(EnrollingReq.marginOperation req) {
        //根据支付订单id 查询订单信息
        EnrollingDeposit deposit =enrollingDepositService.getEnrollingDepositById(req.getDepositId());

        if(deposit==null||deposit.getStatus().equals("TAKEN")||deposit.getStatus().equals("RELEASED")){
            return  ResponseModel.fail("订单不存在或者状态不是锁定状态！");
        }

        UnifiedPayReq payReq = new UnifiedPayReq();
        LockOrReleaseOrDirectReq memInfo = new LockOrReleaseOrDirectReq();
        //获取用户的东方付通信息
        AccountBaseDto info = accountService.getAccountBaseByPartyId(deposit.getPartyId());
        if(info==null ||!info.isPayBind()){
            return  ResponseModel.fail("支付订单用户不存在或为开通东方付通账号");
        }

        memInfo.setPayMemCode(info.getDfftId());
        memInfo.setPayMemName(info.getName());
        memInfo.setOriginalPayID(deposit.getOrderNum());

        payReq.setPayTo(PayEnums.PAY_TO.PAY_TO_WEB.getType());
        payReq.setLockTag(PayEnums.LOCK_TAG.DIRECT_PAY.getType());
        payReq.setAmount(deposit.getAmount());
        payReq.setBusId(String.valueOf(deposit.getId()));
        payReq.setPayParam(memInfo);
        //没收保证金 将保证金 保证金支付到平台账号 不锁定
        if("1".equals(req.getStatus())){
            payReq.setPayBusCode(PayEnums.PAY_BUS_CODE.DEPOSIT_PAY.getType());
            payReq.setPayType(PayEnums.PAY_TYPE.TRANSFER_DEPOSIT.getType());

            //保证金释放
        }else if ("2".equals(req.getStatus())){
            payReq.setPayType(PayEnums.PAY_TYPE.RELEASE_DEPOSIT.getType());
            payReq.setPayBusCode(PayEnums.PAY_BUS_CODE.ENROLLING_RELEASE_DEPOSIT.getType());
        }else{

            return  ResponseModel.fail("操作类型错误！");
        }
        UnifiedPayResp payResp = payFacade.unifiedPay(payReq);
        if(PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode())){

            if("1".equals(req.getStatus())){

                deposit.setStatus(EnrollingEnum.PAY_STATUS.TAKEN.getType());
            }else{
                deposit.setStatus(EnrollingEnum.PAY_STATUS.RELEASED.getType());
            }

            deposit.setUploadAt(DateUtil.getSysTime());
            enrollingDepositService.updateEnrollingDeposit(deposit);
            return ResponseModel.succ();

        }
        return ResponseModel.fail(payResp.getDesc());
    }

    //生成佣金订单
    @Override
    public ResponseModel saveCommissionOrder(EnrollingReq.saveCommissionOrder req) {

        //查询订单信息
        EnrollingActivity activity = enrollingActivityService.getEnrollingActivityById(req.getActivityId());

        if(activity==null){
            return  ResponseModel.fail("未找到预招商订单");
        }

        EnrollingActivityCommissionOrder order = new EnrollingActivityCommissionOrder();

        order.setActivityId(Integer.valueOf(req.getActivityId()));
        order.setPaid(false);
        order.setAmount(new BigDecimal(req.getAmount()));
        order.setPartyId(activity.getPartyId());
        order.setDepositId(req.getApplyId());
        order.setSource(activity.getThirdPartyStatus());

        order.setCreatedAt(DateUtil.getSysTime());
        enrollingActivityCommissionOrderService.saveCommissionOrder(order);
        return ResponseModel.succ();
    }



    private void generateContract(EnrollingActivity act) {

        EnrollingContractParamVO activity = enrollingActivityService.getEnrollingContractParam(String.valueOf(act.getId()));

        GenerateContractResp resp = new GenerateContractResp();
        //根据不同的预招商类型生成不同的合同信息
        //抵押物预招商合同
        if(EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(act.getType())){
            GenerateContractComReq req = new GenerateContractComReq();
            req.setActivityId(String.valueOf(act.getId()));
            req.setType(FddEnums.SING_TYPE.ENROLLING_DELEGATION.getType());
            req.setPartyId(String.valueOf(act.getPartyId()));
            GenerateEnrollingDelegationReq param = new GenerateEnrollingDelegationReq();
            param.setMerchantActivityCode(activity.getCode());
            param.setSeller(activity.getUserName());
            param.setSellerAddress(activity.getUserAddress());
            param.setSellerPhone(activity.getUserPhone());
            param.setSellerContactName(activity.getUserName());
            param.setSellerContactPhone(activity.getUserPhone());
            param.setSellerIdNumber(activity.getUserIdNumber());
            if(StringUtils.isEmpty(activity.getUserName())){
                param.setSeller(activity.getComName());
                param.setSellerAddress(activity.getComAddress());
                param.setSellerPhone(activity.getComPhone());
                param.setSellerContactName(activity.getComName());
                param.setSellerContactPhone(activity.getComPhone());
                param.setSellerIdNumber(activity.getComIdNumber());
            }
            if(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType()==act.getThirdPartyStatus()){
                param.setSeller(activity.getUserAgencyName());
                param.setSellerAddress(activity.getUserAgencyAddress());
                param.setSellerPhone(activity.getUserAgencyPhone());
                param.setSellerContactName(activity.getUserAgencyName());
                param.setSellerContactPhone(activity.getUserAgencyPhone());
                param.setSellerIdNumber(activity.getUserAgencylicense());
            }
            param.setSellerAgency(activity.getAgencyName());
            param.setMerchantObject(activity.getName());
            param.setReferencePrice(activity.getRefPrice());
            param.setBeginTime(activity.getBeginAt());
            param.setEndTime(activity.getEndAt());
            param.setCost(getCost(act));

            param.setSellerAgencyAddress(activity.getAgencyAddress());
            param.setSellerAgencyContactName(activity.getAgencyName());
            param.setSellerAgencyContactPhone(activity.getAgencyPhone());

            logger.info("请求生成抵押物预招商合同接口，请求参数为：{}",JSON.toJSONString(param));
            resp = fddSignatureFacade.generateContract(req,param);
        //债权预招商生成模板
        }else if(EnrollingEnum.ENROLLING_TYPE.PROPERTY_RIGHTS.getType().equals(act.getType())){

            GenerateContractComReq req = new GenerateContractComReq();
            req.setActivityId(String.valueOf(act.getId()));
            req.setType(FddEnums.SING_TYPE.ZHAIQUAN_DELEGATION.getType());
            req.setPartyId(String.valueOf(act.getPartyId()));
            GenerateCreditDelegationReq param = new GenerateCreditDelegationReq();
            param.setActivityCode(activity.getCode());
            param.setActivityName(activity.getName());
            param.setBeginTime(activity.getBeginAt());
            param.setEndTime(activity.getEndAt());
            param.setPrincipal(activity.getRefPrice());
            param.setInterest(activity.getDeposit());
            param.setCost(getCost(act));
            param.setSeller(activity.getUserName());
            param.setSellerAddress(activity.getUserAddress());
            param.setSellerPhone(activity.getUserPhone());
            if(StringUtils.isEmpty(activity.getUserName())){
                param.setSeller(activity.getComName());
                param.setSellerAddress(activity.getComAddress());
                param.setSellerPhone(activity.getComPhone());
            }
            //机构上拍时
            if(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType()==act.getThirdPartyStatus()){
                param.setSeller(activity.getUserAgencyName());
                param.setSellerAddress(activity.getUserAgencyAddress());
                param.setSellerPhone(activity.getUserAgencyPhone());
            }
            logger.info("请求债权预招商生成模板合同接口，请求参数为：{}",JSON.toJSONString(param));

            resp = fddSignatureFacade.generateContract(req,param);

            //物权预招商模板
        }else if(EnrollingEnum.ENROLLING_TYPE.CREDITOR_RIGHTS.getType().equals(act.getType())) {

            GenerateContractComReq req = new GenerateContractComReq();
            req.setActivityId(String.valueOf(act.getId()));
            req.setType(FddEnums.SING_TYPE.WUQUAN_DELEGATION.getType());
            req.setPartyId(String.valueOf(act.getPartyId()));
            GenerateRealDelegationReq param = new GenerateRealDelegationReq();
            param.setActivityCode(activity.getCode());
            param.setActivityName(activity.getName());
            param.setBeginTime(activity.getBeginAt());
            param.setEndTime(activity.getEndAt());
            param.setReference(activity.getRefPrice());
            param.setCost(getCost(act));

            param.setSeller(activity.getUserName());
            param.setSellerAddress(activity.getUserAddress());
            param.setSellerPhone(activity.getUserPhone());
            if(StringUtils.isEmpty(activity.getUserName())){
                param.setSeller(activity.getComName());
                param.setSellerAddress(activity.getComAddress());
                param.setSellerPhone(activity.getComPhone());
            }
            //机构上拍时
            if(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType()==act.getThirdPartyStatus()){
                param.setSeller(activity.getUserAgencyName());
                param.setSellerAddress(activity.getUserAgencyAddress());
                param.setSellerPhone(activity.getUserAgencyPhone());
            }

            logger.info("请求物权预招商模板合同接口，请求参数为：{}",JSON.toJSONString(param));

            resp = fddSignatureFacade.generateContract(req,param);
        }


        //生成合同成功
        if(resp.getCode().equals(ApiCallResult.SUCCESS.getCode())){

            EnrollingActivityContract contract = new EnrollingActivityContract();
            contract.setActivityId(act.getId());
            contract.setCreatedAt(DateUtil.getSysTime());
            contract.setContractId(resp.getContractId());
            contract.setDownloadUrl(resp.getDownloadUrl());
            contract.setViewpdfUrl(resp.getViewPdfUrl());
            enrollingActivityContractService.saveEnrollingActivityContract(contract);
        }else{
            //生成合同失败发送邮件
            exceptionEmailService.sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.ENROLLING,"预招商生成合同",JSON.toJSONString(activity),JSON.toJSONString(resp));

        }


    }

    private String getCost(EnrollingActivity activity) {

        if(checkNotPay(activity.getPartyId())){
            return "0";
        }

        //根据类型获取发布预招商所需的金额
        TServiceConfig tServiceConfig;
        if(EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(activity.getType())){
            tServiceConfig = tServiceConfigService.selectByConfigType(ServiceConfigEnum.ENROLLING_PAWN_PRICE);
            //债权类型
        }else if (EnrollingEnum.ENROLLING_TYPE.PROPERTY_RIGHTS.getType().equals(activity.getType())){
            tServiceConfig = tServiceConfigService.selectByConfigType(ServiceConfigEnum.ENROLLING_CREDITOR_PRICE);
            //物权类型
        }else{
            tServiceConfig = tServiceConfigService.selectByConfigType(ServiceConfigEnum.ENROLLING_REAL_PRICE);
        }

        if(!("0.00").equals(tServiceConfig.getConfigValue())&&!"0".equals(tServiceConfig.getConfigValue())){

            return  tServiceConfig.getConfigValue();

        }

        return "0";



    }


    private boolean checkNotPay(Integer partyId) {

        try{
            String partyIds = gatewayProperties.getEnrollingNotPay();

            String[] ids = partyIds.split(",");

            for(int i=0;i<ids.length;i++){

                if(ids[i].equals(String.valueOf(partyId))){
                    return true;
                }
            }

        }catch (Exception e){
            logger.error("获取不需要支付的partyId 异常，异常信息为{}",e);
        }
        return false;
    }





    @Override
	public ResponseModel updateAgencyActivity(EnrollingReq.agencyUpdateReq agencyUpdateReq) {
		Integer updateCount = enrollingActivityService.updateAgencyActivity(agencyUpdateReq);
		if(updateCount != 1) {
			return ResponseModel.fail(ApiCallResult.EXCEPTION);
		}
		return ResponseModel.succ();
	}

	/**
	 *
	 *佣金订单支付回调
	 */
    @Override
    public ResponseModel payCommissionCallBack(EnrollingReq.payCommissionCallBack payCommissionCallBack) {

        try{
            logger.info("收到佣金支付回调，回调参数为:{}", JSON.toJSONString(payCommissionCallBack));

            EnrollingActivityCommissionOrderCondition condition = new EnrollingActivityCommissionOrderCondition();
            condition.setId(Long.valueOf(payCommissionCallBack.getBusId()));
            EnrollingActivityCommissionOrder order = enrollingActivityCommissionOrderService.getCommissionOrder(condition);
            if(order==null||order.getPaid()){
                logger.info("回调订单为空或者以成功:{}", JSON.toJSONString(payCommissionCallBack));

                return ResponseModel.fail();

            }
            //支付成功更新订单状态
            if(PayResultEnums.PAY_SUCCESS.getCode().equals(payCommissionCallBack.getPayStatus())){
                order.setPaid(true);
                enrollingActivityCommissionOrderService.updateCommissionOrder(order);
                logger.info("回调订单为空或者以成功:{}", JSON.toJSONString(payCommissionCallBack));

                //支付成功分润
                enrollingShareProfit(order);

            }

            return ResponseModel.succ();

        }catch (Exception e){

            logger.error("回调佣金异常，异常信息为:{}", e);

            return ResponseModel.fail();

        }
    }



    /**
     *
     *支付分润
     */
    private void enrollingShareProfit(EnrollingActivityCommissionOrder order) {

        logger.info("开始预招商佣金订单分润，订单为：{}",JSON.toJSONString(order));
        try{
            //查询要分佣的数据
            EnrollingShareProfitInfo shareProfitInfo=  enrollingActivityCommissionOrderService.getEnrollingShareProfitInfo(String.valueOf(order.getId()));
             if(shareProfitInfo==null){
                 logger.error("查询分润数据为空，佣金订单为：{}",JSON.toJSONString(order));
             }

            //送拍分润
            sellerProfit(order,shareProfitInfo);

            //当承拍分润参数不为空时
            if(StringUtils.isNotEmpty(shareProfitInfo.getBuyerAgencyId())&&StringUtils.isNotEmpty(shareProfitInfo.getBuyerAgencyName())&&StringUtils.isNotEmpty(shareProfitInfo.getBuyerMemCode())){


                buyerProfit(order,shareProfitInfo);
            }






        }catch (Exception e){

            logger.error("预招商佣金分润异常，异常信息为：{}",e);
        }



    }

    private void sellerProfit(EnrollingActivityCommissionOrder order, EnrollingShareProfitInfo shareProfitInfo) {

        try {
            //获取送拍机构分润比例 四舍五入 保留两位小数
            BigDecimal sellerAmount = shareProfitInfo.getAmount().multiply(new BigDecimal(shareProfitInfo.getSellerPercent())).multiply(new BigDecimal("0.01")).setScale(2,BigDecimal.ROUND_HALF_DOWN);

            //插入分润表
            EnrollingShareProfitOrder shareProfitOrder = new EnrollingShareProfitOrder();

            shareProfitOrder.setActivityId(order.getActivityId());
            shareProfitOrder.setAgencyId(Integer.valueOf(shareProfitInfo.getSellerAgencyId()));
            shareProfitOrder.setAgencyMemCode(shareProfitInfo.getSellerMemCode());
            shareProfitOrder.setAgencyName(shareProfitInfo.getSellerAgencyName());
            shareProfitOrder.setAmount(sellerAmount);
            shareProfitOrder.setCreatedAt(DateUtil.getSysTime());
            shareProfitOrder.setStatus("0");
            shareProfitOrder.setType("1");

            shareProfitOrder.setCommissionId(String.valueOf(order.getId()));
            enrollingShareProfitOrderDao.insert(shareProfitOrder);

            //调用支付接口 支付佣金

            UnifiedPayResp  payResp = paySellerProfit(shareProfitOrder);

            shareProfitOrder.setStatus(payResp.getCode());
            shareProfitOrder.setOrderNum(payResp.getPayOrder());
            shareProfitOrder.setUpdateTime(DateUtil.getSysTime());

            enrollingShareProfitOrderDao.updateById(shareProfitOrder);

        }catch (Exception e){
            logger.error("送拍分润支付异常，异常信息为：{}",e);


        }
    }


    /**
     *
     *承拍机构分润
     */
    private void buyerProfit(EnrollingActivityCommissionOrder order, EnrollingShareProfitInfo shareProfitInfo) {


        try {
            //承拍分润比例 四舍五入 保留两位小数
            BigDecimal buyerAmount = shareProfitInfo.getAmount().multiply(new BigDecimal(shareProfitInfo.getBuyerPercent())).multiply(new BigDecimal("0.01")).setScale(2,BigDecimal.ROUND_HALF_DOWN);

            //插入分润表
            EnrollingShareProfitOrder shareProfitOrder = new EnrollingShareProfitOrder();

            shareProfitOrder.setActivityId(order.getActivityId());
            shareProfitOrder.setAgencyId(Integer.valueOf(shareProfitInfo.getBuyerAgencyId()));
            shareProfitOrder.setAgencyMemCode(shareProfitInfo.getBuyerMemCode());
            shareProfitOrder.setAgencyName(shareProfitInfo.getBuyerAgencyName());
            shareProfitOrder.setAmount(buyerAmount);
            shareProfitOrder.setCreatedAt(DateUtil.getSysTime());
            shareProfitOrder.setStatus("0");
            shareProfitOrder.setType("2");

            shareProfitOrder.setCommissionId(String.valueOf(order.getId()));
            enrollingShareProfitOrderDao.insert(shareProfitOrder);

            //调用支付接口 支付佣金

            UnifiedPayResp  payResp = paySellerProfit(shareProfitOrder);

            shareProfitOrder.setStatus(payResp.getCode());
            shareProfitOrder.setOrderNum(payResp.getPayOrder());
            shareProfitOrder.setUpdateTime(DateUtil.getSysTime());

            enrollingShareProfitOrderDao.updateById(shareProfitOrder);
        }catch (Exception e){

            logger.error("预招商承拍分润支付异常，异常信息为：{}",e);
        }




    }

    /**
     *
     *调用支付系统支付订单
     */
    private UnifiedPayResp paySellerProfit(EnrollingShareProfitOrder shareProfitOrder) {
        UnifiedPayResp resp = new UnifiedPayResp();
        try {
            UnifiedPayReq req = new UnifiedPayReq();

            LockOrReleaseOrDirectReq payReq = new LockOrReleaseOrDirectReq();
            payReq.setRecMemName(shareProfitOrder.getAgencyName());
            payReq.setRecMemCode(shareProfitOrder.getAgencyMemCode());

            req.setWhoPay(PayEnums.WHO_PAY.WEB_PAY.getType()); //平台扣款
            req.setJumpPay(PayEnums.JUMP_PAY_TYPE.AUTO_PAY.getType());
            req.setPayType(PayEnums.PAY_TYPE.DIRECT_PAY.getType());
            req.setAmount(shareProfitOrder.getAmount());
            req.setPayBusCode("1".equals(shareProfitOrder.getType())?PayEnums.PAY_BUS_CODE.ENROLLING_COMMISSION_SELLER_PAY.getType():PayEnums.PAY_BUS_CODE.ENROLLING_COMMISSION_BUYER_PAY.getType());
            req.setLockTag(PayEnums.LOCK_TAG.DIRECT_PAY.getType());
            req.setBusId(shareProfitOrder.getCommissionId());
            req.setPayParam(payReq);

             resp = payFacade.unifiedPay(req);


            return resp;
        }catch (Exception e){
            logger.error("调用支付系统异常，异常信息为：{}",e);
            resp.setCode(ApiCallResult.EXCEPTION.getCode());

            return resp;

        }


     }

    @Override
    public List<Map<String, Object>> getExportEnrollingActivities(activitiesListReq req) {

        EnrollingListReqDto params = new EnrollingListReqDto();
        BeanUtils.copyProperties(req, params);
        params.setStatus(EnrollingEnum.QUERY_STATUS.getDesc(req.getStatus()));

        List<EnrollingActivityVo> list = enrollingActivityService.getExportEnrollingActivities(params);
        List<Map<String, Object>> maps = JsonUtil.beanListToMapList(list);
        return maps;
    }

    @Override
    public PageInfo getExportEnrollingActivitiesInfo(activitiesListReq req) {

        EnrollingListReqDto params = new EnrollingListReqDto();
        BeanUtils.copyProperties(req, params);
        params.setStatus(EnrollingEnum.QUERY_STATUS.getDesc(req.getStatus()));

        PageInfo  info = enrollingActivityService.getExportEnrollingActivitiesInfo(params);

        List<Map<String, Object>> maps = JsonUtil.beanListToMapList(info.getList());



        PageInfo  pageInfo = new PageInfo();
        pageInfo.setList(maps);
        pageInfo.setTotal(info.getTotal());
        return pageInfo;
    }
}
