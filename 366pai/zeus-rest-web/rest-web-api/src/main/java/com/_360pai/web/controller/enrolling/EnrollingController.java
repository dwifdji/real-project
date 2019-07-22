package com._360pai.web.controller.enrolling;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.facade.enrolling.EnrollingImportFacade;
import com._360pai.core.facade.enrolling.EnrollingRealImportFacade;
import com._360pai.core.facade.enrolling.EnrollingWebFacade;
import com._360pai.core.facade.enrolling.req.EnrollingImportReq;
import com._360pai.core.facade.enrolling.req.EnrollingReq;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：前端预招商接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 11:09
 */
@RestController
public class EnrollingController extends AbstractController {
	

    @Reference(version = "1.0.0")
    private EnrollingWebFacade enrollingWebFacade;



    @Reference(version = "1.0.0")
    private EnrollingImportFacade enrollingImportFacade;



    @Reference(version = "1.0.0")
    private EnrollingRealImportFacade enrollingRealImportFacade;

    /**
     * 预招商查询接口
     */
    @GetMapping(value = "/open/enrolling_activity/search_enrolling_activities")
    public ResponseModel searchEnrollingActivities(EnrollingReq.searchReq req) {
        //参数校验
        if("2".equals(req.getMortgage())){
            req.setMortgage("0");
        }

    	return enrollingWebFacade.searchHomeActivity(req);
    }

   
    /**
     * 预招商排行榜
     */
    @GetMapping(value = "/open/enrolling_activity/get_activity_ranking")
    public ResponseModel activityRanking() {

        return enrollingWebFacade.getActivityRanking();
    }
 
    
    /**
     * 预招商详情接口
     */
    @GetMapping(value = "/open/enrolling_activity/get_activity_info")
    public ResponseModel activityInfo(EnrollingReq.activityIdTypeReq req) {
        //当用户已经登录时
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(accountBaseInfo!=null){
        	Integer accountId = accountBaseInfo.getAccountId();
        	Integer partyPrimaryId = accountBaseInfo.getPartyPrimaryId();
        	req.setAccountId(accountId == null? null : String.valueOf(accountId));
            req.setPartyId(partyPrimaryId == null? null : String.valueOf(partyPrimaryId));
        }

       //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){

            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        req.setFoucsType(EnrollingEnum.FOCUS_TYPE.WEB.getType());

        return enrollingWebFacade.getBaseInfo(req);
    }

    
    /**
     * 设置预招商提醒
     */
    @PostMapping(value = "/confined/enrolling_activity/activity_remind")
    public ResponseModel activityRemind(@RequestBody EnrollingReq.activityIdTypeReq req) {
        //校验参数

        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();
        if(baseInfo==null){
            return ResponseModel.fail(ApiCallResult.NOT_LOGIN.getDesc());
        }

    	Integer partyId = baseInfo.getPartyPrimaryId();
    	Integer accountId = baseInfo.getAccountId();
//    	if(partyId == null) {
//    		return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
//    	}
        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        if (partyId != null)
            req.setPartyId(String.valueOf(partyId));
        req.setAccountId(String.valueOf(accountId));
        req.setMobile(baseInfo.getMobile());
        req.setPathType((short)0);
        return enrollingWebFacade.activityRemind(req);
    }

    
    /**
     * 预招商报名
     */
    @PostMapping(value = "/confined/enrolling_activity/activity_apply")
    public ResponseModel activityApply(@RequestBody EnrollingReq.activityIdTypeReq req) {
        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();
        if(baseInfo==null){
            return ResponseModel.fail(ApiCallResult.NOT_LOGIN.getDesc());
        }

    	Integer partyId = baseInfo.getPartyPrimaryId();
    	if(partyId == null) {
    		return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
    	}
    	
        if(StringUtils.isBlank(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        req.setPartyId(String.valueOf(partyId));
        return enrollingWebFacade.activityApply(req);
    }

    
    /**
     * 预招商关注接口
     */
    @PostMapping(value = "/confined/enrolling_activity/activity_focus")
    public ResponseModel activityFocus(@RequestBody EnrollingReq.activityIdTypeReq req) {
    	 //校验参数
        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();
        if(baseInfo==null){
            return ResponseModel.fail(ApiCallResult.NOT_LOGIN.getDesc());
        }

        Integer partyId = baseInfo.getPartyPrimaryId();
//    	if(partyId == null) {
//    		return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
//    	}
        if(StringUtils.isBlank(String.valueOf(req.getActivityId()))){
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        req.setAccountId(baseInfo.getAccountId().toString());
        if (partyId != null)
            req.setPartyId(String.valueOf(partyId));
        req.setFoucsType(EnrollingEnum.FOCUS_TYPE.WEB.getType());
        return enrollingWebFacade.activityFocus(req);
    }
    
    
    /**
     * 获取全网连拍列表
     */
    @GetMapping(value = "/open/enrolling_activity/activity_joint")
    public ResponseModel activityJoint(EnrollingReq.activityIdReq req) {

        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){

            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        return enrollingWebFacade.getActivityJoint(req);
    }

    
    /**
     * 动态获取预测招商模板 
     */
    @GetMapping(value = "/open/enrolling_activity/activity_announcement")
	public ResponseModel getAnnouncementById(EnrollingReq.activityAnnouncement activityAnnouncement) {
		if(StringUtils.isBlank(activityAnnouncement.getActivityId()) || 
				StringUtils.isBlank(activityAnnouncement.getAnnouncementId())) {
			return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
		}
		
		return enrollingWebFacade.getEAannouncementById(activityAnnouncement);
	}
    
    
    /**
     * 动态获获取预招商购买须知模板
     */
    @GetMapping(value = "/open/enrolling_activity/activity_instruction")
    public ResponseModel activityInstruction(EnrollingReq.activityAnnouncement activityAnnouncement) {
    	if(StringUtils.isBlank(activityAnnouncement.getActivityId()) || 
				StringUtils.isBlank(activityAnnouncement.getAnnouncementId())) {
    		return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
		}
    	
    	return enrollingWebFacade.getEAannouncementById(activityAnnouncement);
	 
    }
    

    /**
     * 个人中心-获取预招商详情
     */
    @GetMapping(value = "/confined/enrolling_activity/my_activity_detail")
    public ResponseModel activityDetail(EnrollingReq.activityIdReq req) {

        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){

            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        return enrollingWebFacade.getDetail(req);
    }


    /**
     * 个人中心-我的招商会
     */
    @GetMapping(value = "/confined/enrolling_activity/my_activity")
    public ResponseModel myActivity(EnrollingReq.activitiesListReq req) {
    	String partyId = String.valueOf(loadCurLoginAccountInfo().getPartyPrimaryId());
    	if(StringUtils.isBlank(partyId)) {
    		return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
    	}
    	req.setPartyId(partyId);
        req.setThirdPartyStatus(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.WEB.getType());
        return enrollingWebFacade.myEnrollingActivities(req);
    }


    /**
     * 个人中心-我关注的招商会
     */
    @GetMapping(value = "/confined/enrolling_activity/my_focus_activity")
    public ResponseModel myFocusActivity(EnrollingReq.activitiesListReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
//        String partyId = String.valueOf(accountBaseInfo.getPartyPrimaryId());
//    	if(StringUtils.isBlank(partyId)) {
//    		return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
//    	}
        if (accountBaseInfo.getPartyPrimaryId() != null)
            req.setPartyId(accountBaseInfo.getPartyPrimaryId().toString());
        req.setAccountId(accountBaseInfo.getAccountId().toString());
        req.setFocusType(EnrollingEnum.FOCUS_TYPE.WEB.getType());
        return enrollingWebFacade.focusList(req);
    }

    
    /**
     * 个人中心-我的招商会提醒列表
     */
    @GetMapping(value = "/confined/enrolling_activity/my_remind_activity")
    public ResponseModel myRemindActivity(EnrollingReq.activityIdTypeReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
//    	String partyId = String.valueOf(loadCurLoginAccountInfo().getPartyPrimaryId());
//    	String accountId = String.valueOf(loadCurLoginAccountInfo().getAccountId());
//    	if(StringUtils.isBlank(partyId) || StringUtils.isBlank(accountId)) {
//    		return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
//    	}
        if (accountBaseInfo.getPartyPrimaryId() != null) req.setPartyId(String.valueOf(accountBaseInfo.getPartyPrimaryId()));
    	req.setAccountId(String.valueOf(accountBaseInfo.getAccountId()));
        return enrollingWebFacade.remindList(req);
    }

    
    /**
     * 个人中心-我报名的招商会
     */
    @GetMapping(value = "/confined/enrolling_activity/my_apply_activity")
    public ResponseModel myApplyActivity(EnrollingReq.activityIdTypeReq req) {
    	String partyId = String.valueOf(loadCurLoginAccountInfo().getPartyPrimaryId());
    	if(StringUtils.isBlank(partyId)) {
    		return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
    	}
    	req.setPartyId(partyId);
        return enrollingWebFacade.myApplyActivity(req);
    }

    
    /**
     *  个人中心-预招商详细信息
     */
    @GetMapping(value = "/confined/enrolling_activity/my_activity_info")
    public ResponseModel myActivityInfo(EnrollingReq.activityIdTypeReq req) {
    	  //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
    	return enrollingWebFacade.myActivityInfo(req);
    }

    
    /**
     *  预招商详情
     */
    @GetMapping(value = "/open/enrolling_activity/activity_detail")
    public ResponseModel myActivityDetail(EnrollingReq.activityIdReq req) {
    	 //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){

            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        
        return enrollingWebFacade.getDetail(req);
        
    }
    
    
    /**
     * 获取不同类型的预招商活动一次查询六条
     */
    @GetMapping(value = "/open/enrolling_activity/get_home_activity")
    public ResponseModel getAllTypeActivity(EnrollingReq.tabReq req) {
    	 if(StringUtils.isEmpty(req.getTabId())){
             return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
         }
    	return enrollingWebFacade.getActivityByTab(req);
    }
    
    
    /**
     * 查询所有的查询选择条件
     */
    @GetMapping("/open/enrolling_activity/search_details")
    public ResponseModel getCityStatusType() {
    	
    	return enrollingWebFacade.getCityStatusType();
    }



    /**
     * 个人中心 - 预招商活动提交
     */
    @PostMapping(value = "/confined/enrolling_activity/save_activity")
    public ResponseModel saveActivity(@RequestBody EnrollingReq.saveActivityReq req) {

        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();

        if(accountBaseInfo==null){
            return ResponseModel.fail("请先登录！");
        }

        if(accountBaseInfo.getPartyPrimaryId()==null){

            return ResponseModel.fail(ApiCallResult.NO_AUTH_CAN_NOT_UPLOAD_ERROR.getCode(),ApiCallResult.NO_AUTH_CAN_NOT_UPLOAD_ERROR.getDesc());
        }


        JSONObject jsonObject = JSON.parseObject(req.getFields());

        Integer categoryId = jsonObject.getInteger("templateId"); //获取当前的模板id

        //获取模板对应的类型
        String enrollingType = EnrollingEnum.TEMPLATE_TYPE.getDesc(String.valueOf(categoryId));
        //抵押物预招商 检查东方付通 或者是否是银行通道
        /*if(EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(enrollingType)){
             if(!accountBaseInfo.isIs_pay_bind()&&!accountBaseInfo.isIs_channel()){
                return ResponseModel.fail(ApiCallResult.NO_DFFT_CAN_NOT_UPLOAD_ERROR.getCode(),ApiCallResult.NO_DFFT_CAN_NOT_UPLOAD_ERROR.getDesc());
            }
        }*/

		//检查是否开通法大大 或者不需要签章账户
		if(StringUtils.isEmpty(accountBaseInfo.getFadadaId())&&!accountBaseInfo.getOperWithoutFadada()){
            return ResponseModel.fail(ApiCallResult.NO_FDD_CAN_NOT_UPLOAD_ERROR.getCode(),ApiCallResult.NO_FDD_CAN_NOT_UPLOAD_ERROR.getDesc());
		}

        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        req.setEnrollingType(enrollingType);
        req.setTemplateId(categoryId);

        return enrollingWebFacade.saveActivity(req);



    }


    
    @PostMapping("/confined/enrolling_activity/update_activity")
    public ResponseModel updateActivity(@RequestBody EnrollingReq.saveActivityReq req) {
    	AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();

        if(accountBaseInfo==null){
            return ResponseModel.fail(ApiCallResult.NOT_LOGIN.getDesc());
        }

        if(StringUtils.isBlank(req.getActivityId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return enrollingWebFacade.updateActivity(req);
    }
    
    
    /**
     * 取消提醒数据
     */
    @GetMapping("/confined/enrolling_activity/cancel_my_remind")
    public ResponseModel cancelMyRemind(EnrollingReq.activityIdTypeReq req) {
    	Integer partyId = loadCurLoginAccountInfo().getPartyPrimaryId();
    	Integer accountId = loadCurLoginAccountInfo().getAccountId();
    	if(partyId == null || accountId == null) {
    		return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
    	}
    	if(StringUtils.isBlank(req.getActivityId())) {
    		return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
    	}
    	req.setAccountId(String.valueOf(accountId));
    	req.setPartyId(String.valueOf(partyId));
    	
    	return enrollingWebFacade.cancelMyRemind(req);
    }
    
    
    /**
     * 取消关注数据
     */
    @PostMapping("/confined/enrolling_activity/cancel_my_focus")
    public ResponseModel cancelMyFocus(@RequestBody EnrollingReq.activityIdTypeReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
//    	if(partyId == null) {
//    		return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
//    	}
        if (accountBaseInfo.getPartyPrimaryId() != null)
            req.setPartyId(String.valueOf(accountBaseInfo.getPartyPrimaryId()));
        req.setAccountId(String.valueOf(accountBaseInfo.getAccountId()));
        req.setFoucsType(EnrollingEnum.FOCUS_TYPE.WEB.getType());

    	return enrollingWebFacade.cancelMyFocus(req);
    }
    
    
    /**
     * 批量取消我关注的预招商活动 
     */
    @PostMapping("/confined/enrolling_activity/cancel_my_focusList")
    public ResponseModel cancelMyFocusList(@RequestBody EnrollingReq.focusListReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
//    	if(partyId == null) {
//    		return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
//    	}
    	if(req.getFocusList() == null || req.getFocusList().length <= 0) {
    		return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
    	}
    	return enrollingWebFacade.cancelMyFocusList(req.getFocusList(), accountBaseInfo.getPartyPrimaryId(), accountBaseInfo.getAccountId());
    }


    /**
     * 个人中心-查看活动报名信息列表
     */
    @GetMapping("/confined/enrolling_activity/apply_activity_list")
    public ResponseModel applyActivityList(EnrollingReq.activityIdReq req) {
        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        return enrollingWebFacade.applyActivityList(req);
    }


    /**
     * 债权、物权 预招商报名列表
     */
    @GetMapping("/open/enrolling_activity/apply_activity_list")
    public ResponseModel openApplyActivityList(EnrollingReq.activityIdReq req) {
        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return enrollingWebFacade.applyActivityList(req);
    }

    /**
     * 个人中心-抵押物预招商发布招商进展
     */
    @PostMapping("/confined/enrolling_activity/save_progress")
    public ResponseModel saveProgress(@RequestBody EnrollingReq.saveProgress req) {


        return enrollingWebFacade.saveProgress(req);
    }


    /**
     * 个人中心-抵押物预招商发布招商进展
     */
    @PostMapping("/confined/enrolling_activity/save_result")
    public ResponseModel saveProgress(@RequestBody EnrollingReq.saveResult req) {


        return enrollingWebFacade.saveResult(req);
    }


    /**
     * 个人中心-抵押物预招商发布招商结果
     */
    @GetMapping("/confined/enrolling_activity/result_info")
    public ResponseModel resultInfo(EnrollingReq.activityIdTypeReq req) {

        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();

        if(baseInfo==null){
            return ResponseModel.fail("请先登录！");
        }
        req.setPartyId(String.valueOf(baseInfo.getPartyPrimaryId()));
        return enrollingWebFacade.resultInfo(req);
    }



    /**
     * 个人中心-抵押物预招商支付佣金
     */
    @GetMapping("/confined/enrolling_activity/pay_commission")
    public ResponseModel resultInfo(EnrollingReq.payCommission req) {

        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();

        if(baseInfo==null){
            return ResponseModel.fail("请先登录！");
        }
        if(!baseInfo.isIs_pay_bind()){
            return ResponseModel.fail(ApiCallResult.NO_DFFT_CAN_NOT_UPLOAD_ERROR.getCode(),ApiCallResult.NO_DFFT_CAN_NOT_UPLOAD_ERROR.getDesc());

         }
        req.setPartyId(String.valueOf(baseInfo.getPartyPrimaryId()));
        req.setMemCode(baseInfo.getDfftId());
        req.setName(baseInfo.getName());
        return enrollingWebFacade.payCommission(req);
    }


    /**
     * 个人中心-抵押物查看进度
     */
    @GetMapping("/confined/enrolling_activity/get_progress_list")
    public ResponseModel getProgressList(EnrollingReq.activityIdReq req) {


        return enrollingWebFacade.getProgressList(req);
    }


    /**
     * 个人中心-个人中心-撤销我的预招商活动
     */
    @PostMapping("/confined/enrolling_activity/backout_activity")
    public ResponseModel backOutActivity(@RequestBody EnrollingReq.activityIdReq req) {


        return enrollingWebFacade.backOutActivity(req);
    }


    /**
     * 个人中心-签署模板
     */
    @GetMapping("/confined/enrolling_activity/sign_contract")
    public ResponseModel signContract(EnrollingReq.activityIdTypeReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(SystemConstant.ACCOUNT_COMPANY_TYPE.equals(accountBaseInfo.getType()) && !accountBaseInfo.isAdmin()){
            return ResponseModel.fail(ApiCallResult.NOT_COMPANY_ADMIN_ERRPR);
        }
        return enrollingWebFacade.signContract(req);
    }



    /**
     * 获取标的信息
     */
    @GetMapping("/open/enrolling_activity/get_matter_info")
    public ResponseModel getMatterInfo(EnrollingReq.activityIdTypeReq req) {

        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();

        if (accountBaseInfo.getAccountId() != null) {
            if (accountBaseInfo.getPartyPrimaryId() == null) {
                req.setSeeStatus("isNotAuth");  //为认证
            } else {
                //登录  同时判断是否认证
                boolean accountAuth = accountBaseInfo.isAccountAuth();
                if (accountAuth) {
                    //认证通过
                    req.setSeeStatus("authorization");
                } else {
                    //未认证
                    req.setSeeStatus("login");
                }
            }
        }else{

            req.setSeeStatus("NotLogged");
        }
        return enrollingWebFacade.getMatterInfo(req);
    }


    /**
     * 查询预招商发布订单的支付状态
     */
    @GetMapping("/confined/enrolling_activity/get_order_status")
    public ResponseModel getOrderStatus(EnrollingReq.submitOrder req) {


        return enrollingWebFacade.getSubmitOrderStatus(req);
    }



    /**
     * 修改获取活动信息
     */
    @PostMapping("/confined/enrolling_activity/get_enrolling_detail")
    public ResponseModel getEnrollingDetail(@RequestBody EnrollingReq.activityIdReq req) {


        return enrollingWebFacade.getEnrollingDetail(req);
    }
    
    /**
     * 获取预招商模板填充基本信息
     */
    @GetMapping("/open/enrolling_activity/get_enrolling_info")
    public ResponseModel getActivityModelInfo(EnrollingReq.activityIdReq req) {
    	
    	if(StringUtils.isBlank(req.getActivityId())) {
    		return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());    	}
    	
    	return enrollingWebFacade.getActivityModelInfo(req);
    }


    /**
     * 获取预招商模板填充基本信息
     */
    @GetMapping("/open/enrolling_activity/share_activity")
    public ResponseModel shareActivity(EnrollingReq.activityIdTypeReq req) {

        if(StringUtils.isBlank(req.getActivityId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }


        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();


        if(accountBaseInfo!=null){
             Integer partyPrimaryId = accountBaseInfo.getPartyPrimaryId();
            req.setAccountId(accountBaseInfo.getAccountId() == null? null : String.valueOf(accountBaseInfo.getAccountId()));
            req.setPartyId(partyPrimaryId == null? null : String.valueOf(partyPrimaryId));
        }
        //登录人partyId 为空时 自己返回成功
        if(StringUtils.isEmpty(req.getAccountId())){

            return ResponseModel.succ();

        }

        return enrollingWebFacade.saveShareInfo(req);
    }

	/**
     * 我关注的预招商活动总数
     * @return
     */
    @GetMapping("/confined/enrolling_activity/getFoucseCount")
    public ResponseModel getFoucseCount() {
    	
    	AccountBaseInfo baseInfo = loadCurLoginAccountInfo();
//    	if(baseInfo.getPartyPrimaryId() == null) {
//            return ResponseModel.fail("请先登录！");
//    	}
    	
    	return enrollingWebFacade.getFoucseCount(baseInfo.getPartyPrimaryId(), baseInfo.getAccountId());
    }



    /**
     * 上传上拍凭证
     * @return
     */
    @PostMapping("/confined/enrolling_activity/save_proof")
    public ResponseModel saveProof(@RequestBody EnrollingReq.saveProofReq req) {

        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();
        if(baseInfo.getPartyPrimaryId() == null) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_CAN_NOT_UPLOAD_ERROR.getCode(),ApiCallResult.NO_AUTH_CAN_NOT_UPLOAD_ERROR.getDesc());
        }

        req.setPartyId(String.valueOf(baseInfo.getPartyPrimaryId()));

        return enrollingWebFacade.saveProof(req);
    }

    /**
     * 初始化老数据对所有已经上线的数据进
     * 行设置活动结束时间
     * @return
     */
    @GetMapping("/open/enrolling_activity/initEndTime")
    public ResponseModel initEnrollingActivityEndTime(EnrollingReq.activityEndTime req) {

        return  enrollingWebFacade.initEnrollingActivityEndTime(req);
    }


    /**
     * 微信支付接口
     */
    @PostMapping("/confined/enrolling_activity/wx_pay")
    public ResponseModel enrollingWxPay(@RequestBody EnrollingReq.activityIdTypeReq req ) {

        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();
        if(baseInfo.getPartyPrimaryId() == null) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_CAN_NOT_UPLOAD_ERROR.getCode(),ApiCallResult.NO_AUTH_CAN_NOT_UPLOAD_ERROR.getDesc());
        }

        req.setPartyId(String.valueOf(baseInfo.getPartyPrimaryId()));

        return  enrollingWebFacade.wxPay(req);
    }



    /**
     * 获取长城资产导入数据详情
     */
    @GetMapping("/open/enrolling_activity/get_import_enrolling_info")
    public ResponseModel getImportEnrollingInfo(EnrollingImportReq.getUploadActivityDetailsReq req) {

        if(StringUtils.isBlank(req.getActivityId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        req.setType("web");
        return enrollingImportFacade.getUploadActivityDetails(req);
    }




    /**
     * 保存处置/资金服务商信息
     */
    @PostMapping("/confined/enrolling_activity/save_fund_service_info")
    public ResponseModel saveFundServiceInfo(@RequestBody EnrollingImportReq.getFundServiceReq req ) {


        if(StringUtils.isEmpty(req.getActivityId())||StringUtils.isEmpty(req.getType())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();
        req.setName(baseInfo.getName());
        return enrollingImportFacade.saveFundServiceInfo(req);
    }



    /**
     * 数据修复
     */
    @GetMapping("/open/enrollingActivity/oldDataAddBusTypeName")
    public ResponseModel oldDataAddBusTypeName() {


        return enrollingWebFacade.oldDataAddBusTypeName();
    }




    /**
     * 获取物权招商导入详情
     */

    @GetMapping(value = "/open/enrollingActivity/getRealUploadActivityDetails")
    public ResponseModel getRealUploadActivityDetails(EnrollingImportReq.getUploadActivityDetailsReq req) {
        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        req.setType("web");
        return enrollingRealImportFacade.getRealUploadActivityDetails(req);
    }

}
