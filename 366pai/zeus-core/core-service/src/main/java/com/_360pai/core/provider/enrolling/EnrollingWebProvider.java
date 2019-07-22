package com._360pai.core.provider.enrolling;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.*;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.aspact.EnrollingEmailService;
import com._360pai.core.aspact.ExceptionEmailService;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.*;
import com._360pai.core.common.constants.EnrollingEnum.ENROLLING_FIELD_TYPE;
import com._360pai.core.common.constants.EnrollingEnum.FIELD_TYPE;
import com._360pai.core.condition.asset.AssetDataDraftsCondition;
import com._360pai.core.condition.enrolling.*;
import com._360pai.core.dao.account.TAgencyDao;
import com._360pai.core.dao.activity.TActivityPosterDao;
import com._360pai.core.dao.asset.*;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dto.AssetDto;
import com._360pai.core.dto.enrolling.*;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.AgencyReq;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.account.resp.AgencyResp;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.enrolling.EnrollingWebFacade;
import com._360pai.core.facade.enrolling.req.EnrollingReq;
import com._360pai.core.facade.enrolling.req.EnrollingReq.*;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.core.model.activity.TActivityPoster;
import com._360pai.core.model.asset.*;
import com._360pai.core.model.assistant.InstructionsContent;
import com._360pai.core.model.assistant.TServiceConfig;
import com._360pai.core.model.enrolling.*;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.AgencyService;
import com._360pai.core.service.account.TAccountViewRecordService;
import com._360pai.core.service.applet.TAppletMessageService;
import com._360pai.core.service.asset.AssetPropertyService;
import com._360pai.core.service.assistant.*;
import com._360pai.core.service.enrolling.*;
import com._360pai.core.vo.enrolling.*;
import com._360pai.core.vo.enrolling.web.*;
import com._360pai.gateway.common.alisms.AliSmsTemplateEnums;
import com._360pai.gateway.common.dfftpay.PayEnums;
import com._360pai.gateway.common.dfftpay.PayResultEnums;
import com._360pai.gateway.common.fddSignature.FddEnums;
import com._360pai.gateway.controller.req.alisms.FAliSmsSendReq;
import com._360pai.gateway.controller.req.dfftpay.*;
import com._360pai.gateway.controller.req.fdd.ExtSignContractReq;
import com._360pai.gateway.controller.req.fdd.ExtSignContractResp;
import com._360pai.gateway.controller.req.fdd.FddSignInfo;
import com._360pai.gateway.facade.FddSignatureFacade;
import com._360pai.gateway.facade.PayFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageSerializable;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * @author: liuhaolei
 * @Title: EnrollingWebProvider
 * @ProjectName: core-service
 * @Description: 预招商业务生产者
 * @Date: 2018-08-28
 */
@Component
@Service(version = "1.0.0")
@Slf4j
public class EnrollingWebProvider implements EnrollingWebFacade {


	@Autowired
	private ExceptionEmailService  exceptionEmailService;

	@Resource
	private AssetDataDraftsDao assetDataDraftsDao;


	@Autowired
	private EnrollingActivityService enrollingActivityService;


	@Autowired
	private NotifyPartyEnrollingActivityService notifyPartyEnrollingActivityService;


	@Autowired
	private FavoriteEnrollingActivityService favoriteEnrollingActivityService;

	@Autowired
	private InstructionsContentService instructionsContentService;

	@Autowired
	private CityService cityService;

	@Autowired
	private AssetPropertyService assetPropertyService;

	@Autowired
	private EnrollingDepositService enrollingDepositService;


	@Autowired
	private AgencyPortalEnrollingActivityService agencyPortalEnrollingActivityService;


	@Autowired
	private EnrollingActivityProgressService enrollingActivityProgressService;


	@Autowired
	private EnrollingActivityDataService enrollingActivityDataService;


	@Autowired
	private EnrollingActivityResultService enrollingActivityResultService;


	@Autowired
	private EnrollingActivityContractService enrollingActivityContractService;


	@Autowired
	private EnrollingActivityCommissionOrderService enrollingActivityCommissionOrderService;


	@Autowired
	private AccountService accountService;


	@Autowired
	private AgencyService agencyService;


	@Autowired
	private EnrollingActivityShareStatsService enrollingActivityShareStatsService;


	@Autowired
	private TServiceConfigService tServiceConfigService;


	@Autowired
	private EnrollingSubmitOrderService enrollingSubmitOrderService;

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@Resource
	private TAssetFieldDao tAssetFieldDao;

	@Resource
	private TAssetFieldGroupDao tAssetFieldGroupDao;
	@Resource
	private TAssetFieldModelDao assetFieldModelDao;
	@Resource
	private TAssetTemplateCategoryDao tAssetTemplateCategoryDao;

	@Autowired
	private GatewayMqSender gatewayMqSender;

	@Autowired
	private EnrollingEmailService enrollingEmailService;




	@Autowired
	private GatewayProperties gatewayProperties;

	@Autowired
	private GatewayMqSender mqSender;

	@Reference(version = "1.0.0")
	private PayFacade payFacade;

	@Reference(version = "1.0.0")
	private FddSignatureFacade fddSignatureFacade;

	@Autowired
	private TAccountViewRecordService tAccountViewRecordService;

	@Autowired
	private CityDao cityDao;
	@Autowired
	private TAppletMessageService appletMessageService;

    @Autowired
    private EnrollingActivityImportService enrollingActivityImportService;

    @Autowired
	private DetailViewRecodeService detailViewRecodeService;

    @Autowired
	private ActivityServiceProviderService activityServiceProviderService;
    @Autowired
	private TActivityPosterDao activityPosterDao;
    @Autowired
	private TAgencyDao agencyDao;

	@Override
	public ResponseModel getEnrollingActivityList(EnrollingReq.searchReq req) {
		//查询参数
		EnrollingListReqDto params = new EnrollingListReqDto();
		params.setStatus(req.getStatus());
		params.setPage(req.getPage());
		params.setPerPage(req.getPerPage());
		params.setCityId(req.getCityId());
		params.setType(req.getType());
		if (params.getCityId() != null) {
			if (params.getCityId().intValue() < 0) {
				params.setProvinceId(Math.abs(params.getCityId()));
				params.setCityId(null);
			}
		}
		PageInfo info = enrollingActivityService.getEnrollingActivityList(params);

		List<EnrollingActivityVo> list = info.getList();
		List<EnrollingSearchVo> searchList = getSearchList(list);

		PageSerializable page = new PageSerializable();
		page.setList(searchList);
		page.setTotal(info.getTotal());


		return ResponseModel.succ(page);
	}


	@Override
	@Transactional
	public ResponseModel getBaseInfo(EnrollingReq.activityIdTypeReq req) {
        Integer agencyId = getAgencyIdByCode(req.getAgencyCode(),"2");

        //当type = 4 时 为前端轮询时 浏览量不添加
        if(!"4".equals(req.getType())){
            updateBrowseNumber(req,agencyId);
			detailViewRecodeService.insertEnrollingWebRecode(Integer.valueOf(req.getActivityId()),
					StringUtils.isNotBlank(req.getAccountId())?Integer.parseInt(req.getAccountId()): null,
					StringUtils.isNotBlank(req.getPartyId())?Integer.parseInt(req.getPartyId()): null);
        }


		EnrollingDetailInfoVo base = enrollingActivityService.getEnrollingActivityBaseInfo(req.getActivityId());

		if(base==null){
			return ResponseModel.fail("未找到详情信息！");
		}
		base = getPersionStatus(req, base);
		base = setProjectManager(req, base);
		//添加处置和资金服务商信息
        base = setDisposalFund(base);

		JSONObject obj =JSON.parseObject(base.getExtra())  ;

		base.setImages((List)obj.get("images"));

		base.setUserType(getUserType(base));

		base.setStatusDesc(EnrollingEnum.STATUS.getDesc(base.getStatus()));
		base.setStatus(EnrollingEnum.QUERY_STATUS.getType(base.getStatus()));
		base.setPublishDate(DateUtil.format(DateUtil.strToDateLong(base.getBeginAt()),DateUtil.NORM_DATE_PATTERN));
		base.setIsPackage("8".equals(base.getCategoryId())?"是":"否");


		//新增用户浏览记录
		if(StringUtils.isNotBlank(req.getAccountId())) {
			tAccountViewRecordService.findAndSaveViewRecord(req.getAccountId(),req.getPartyId(), req.getActivityId(), AccountEnum.ViewType.ENROLLING.getKey());
		}
		
		//获取子站点的浏览数
//		if(agencyId!=null){
//			base.setBrowse(getChildSiteBrowse(req.getActivityId(),agencyId));
//		}
		return ResponseModel.succ(base);
	}

	private EnrollingDetailInfoVo setProjectManager(EnrollingReq.activityIdTypeReq req, EnrollingDetailInfoVo base) {
		if (StringUtils.isNotBlank(req.getAgencyCode())) {
			TAgency subWebsite = agencyDao.getByCode(req.getAgencyCode());
			if (subWebsite == null) {
				throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
			}
			String trustee = "";
			if (StringUtils.isNotBlank(subWebsite.getTrustee())) {
				trustee = subWebsite.getTrustee().substring(0,1) + "先生";
			} else {
				trustee = subWebsite.getName();
			}
			base.setProjectName(trustee);
			base.setProjectPhone(subWebsite.getTrusteePhone());
		}
		return base;
	}

    private EnrollingDetailInfoVo setDisposalFund(EnrollingDetailInfoVo base) {

	    if(StringUtils.isNotEmpty(base.getFundProvider())){
            base.setFundProvider(gatewayProperties.getEnrollingImportFundProvider());
            base.setFundPhone(gatewayProperties.getEnrollingImportFundPhone());
        }

        if(StringUtils.isNotEmpty(base.getDisposalService())){
            base.setDisposalService(gatewayProperties.getEnrollingImportDisposalService());
            base.setDisposalPhone(gatewayProperties.getEnrollingImportDisposalPhone());
        }

	    return base;
    }

    private void updateBrowseNumber(EnrollingReq.activityIdTypeReq req,Integer agencyId) {
        // 浏览量加一
        EnrollingActivity enrollingActivity = enrollingActivityService.getEnrollingActivityById(req.getActivityId());
        enrollingActivity.setId(Integer.valueOf(req.getActivityId()));
        Integer browseNumber = enrollingActivity.getBrowseNumber() == null ? 0 : Integer.valueOf(enrollingActivity.getBrowseNumber());
        enrollingActivity.setBrowseNumber(browseNumber + 1);
        enrollingActivityService.updateEnrollingActivityById(enrollingActivity);

        if(agencyId!=null){
            //保存或者根据子站点的浏览量
            saveOrUpdateChildSiteView(req,agencyId,1);
        }

    }




	 private String getUserType(EnrollingDetailInfoVo base) {

		if(String.valueOf(EnrollingEnum.THIRTY_PARTY_STATUS.AGENCY_INVESTMENT.getStatus()).equals(base.getSource())){

			return PartyEnum.Category.getFrontNameByKey(PartyEnum.Category.AUCTION_COMPANY.getKey());
		}

		String category = base.getCategory();
		if(StringUtils.isEmpty(category)){
			return "其他";
		}

		String userType = EnrollingEnum.UserType.getValueByKey(category);

		if(StringUtils.isEmpty(userType)){
			return "其他";
		}

		return userType;

	}


	/**
	 *
	 *获取子站点的浏览数
	 */
	private String getChildSiteBrowse(String activityId, Integer agencyId) {

		AgencyPortalEnrollingActivityCondition condition = new AgencyPortalEnrollingActivityCondition();
		condition.setAgencyId(agencyId);
		condition.setEnrollingActivityId(Integer.valueOf(activityId));

		AgencyPortalEnrollingActivity enrollingActivity = agencyPortalEnrollingActivityService.getAgencyPortalEnrollingActivity(condition);

		return String.valueOf(enrollingActivity.getViewCount());
	}

	/**
	 *
	 *保存获取更新子站浏览量
	 */
	private void saveOrUpdateChildSiteView(activityIdTypeReq req,Integer agencyId,Integer viewCount) {

		AgencyPortalEnrollingActivity enrollingActivity = new  AgencyPortalEnrollingActivity();
		enrollingActivity.setAgencyId(agencyId);
		enrollingActivity.setEnrollingActivityId(Integer.valueOf(req.getActivityId()));
		enrollingActivity.setViewCount(viewCount);
		enrollingActivity.setCreatedAt(DateUtil.getSysTime());
		agencyPortalEnrollingActivityService.saveOrUpdate(enrollingActivity,viewCount);

	}

	/**
	 * 通用方法得到详细信息状态标识
	 *
	 * @param req
	 * @return
	 */
	private EnrollingDetailInfoVo getPersionStatus(activityIdTypeReq req, EnrollingDetailInfoVo base) {

		//用户没没登录时
		base.setFocusStatus(false);
		base.setRemindStatus(false);
		base.setApplyStatus(false);

		//获取关注情况
		FavoriteEnrollingActivity favoriteEnrollingActivity = favoriteEnrollingActivityService.getFilterModel(req);
		if(null != favoriteEnrollingActivity){
			base.setFocusStatus(true);

		}

		//获取通知情况
		NotifyPartyEnrollingActivity notifyPartyEnrollingActivity = notifyPartyEnrollingActivityService.getFilterModel(req);
		if(null != notifyPartyEnrollingActivity){
			base.setRemindStatus(true);
		}


		if (StringUtils.isEmpty(req.getAccountId()) || StringUtils.isEmpty(req.getPartyId())) {

			return base;
		}
		//获取报名情况
		EnrollingDeposit enrollingDepositModel = enrollingDepositService.getFilterModel(req);

		if(null != enrollingDepositModel){
			base.setApplyStatus(true);

		}

		return base;
	}


	//获取排行榜信息
	@Override
	public ResponseModel getActivityRanking() {

		EnrollingListReqDto params = new EnrollingListReqDto();
		params.setPage(1);
		params.setPerPage(8);
		params.setStatus("");

		PageInfo info = enrollingActivityService.leaderboardEnrolling(params);
		List<HomeEnrollingActivityVO> homeList = info.getList();
		homeList = getHomeList(homeList);

		PageSerializable page = new PageSerializable();
		page.setList(homeList);
		page.setTotal(info.getTotal());

		return ResponseModel.succ(page);
	}

	//根据tabId 获取展示信息
	@Override
	public ResponseModel getActivityByTabId(EnrollingReq.tabReq req) {

		PageInfo info = enrollingActivityService.getActivityByTabId(req.getTabId());
		PageSerializable page = new PageSerializable();

		page.setList(info.getList());
		return ResponseModel.succ(page);

	}

	private List<EnrollingSearchVo> getSearchList(List<EnrollingActivityVo> list) {

		List<EnrollingSearchVo> searchList = new ArrayList<>();
		for (EnrollingActivityVo activity : list) {
			EnrollingSearchVo vo = new EnrollingSearchVo();
			vo.setCityName(activity.getCityName());
			vo.setAmount(activity.getDeposit());
			vo.setEndAt(activity.getEndAt());
			vo.setId(activity.getId());
			vo.setStatus(activity.getStatus());
			vo.setName(activity.getName());
			vo.setBeginAt(activity.getBeginAt());
			vo.setType(activity.getType());
			vo.setImages("https://oeqbkxgti.qnssl.com/FrFlG9RCqWYjnZdFT75gKGZif9AX");
			searchList.add(vo);
		}
		return searchList;
	}


	//个人中心 获取详情
	@Override
	public ResponseModel getDetail(activityIdReq req) {
		EnrollingActivity activity = enrollingActivityService.getEnrollingActivityById(req.getActivityId());
		EnrollingDetailVo vo = new EnrollingDetailVo();
		vo.setDetail(activity.getDetail());
		return ResponseModel.succ(vo);
	}


	@Override
	public ResponseModel remindList(activityIdTypeReq req) {
		ActivityPersionDto params = new ActivityPersionDto();
		BeanUtils.copyProperties(req, params);

		PageInfo info = enrollingActivityService.remindList(params);
		List<HomeEnrollingActivityVO> eaList = new ArrayList<HomeEnrollingActivityVO>();
		if (info != null) {
			eaList = info.getList();
		}
		if (eaList == null || eaList.size() <= 0) {
			return ResponseModel.fail(ApiCallResult.DATA_EMPTY);
		}

		List<PersonalEnrollingActivityVO> persionalList = getPersionalList(eaList);
		PageSerializable page = new PageSerializable();
		page.setList(persionalList);
		page.setTotal(info.getTotal());
		return ResponseModel.succ(page);
	}



	@Override
	public ResponseModel focusList(activitiesListReq req) {
		EnrollingListReqDto params = new EnrollingListReqDto();
		BeanUtils.copyProperties(req, params);

		PageInfo info = enrollingActivityService.focusList(params);
		List<HomeEnrollingActivityVO> eaList = new ArrayList<>();
		if (info != null) {
			eaList = info.getList();
		}

		PageInfoResp page = new PageInfoResp();
		if (eaList == null || eaList.size() <= 0) {
			page.setList(eaList);
			page.setTotal(info.getTotal());
			page.setHasNextPage(info.isHasNextPage());
			return ResponseModel.succ(page);
		}

		List<PersonalEnrollingActivityVO> persionalList = getPersionalList(eaList);


		page.setList(persionalList);
		page.setTotal(info.getTotal());
		page.setHasNextPage(info.isHasNextPage());
		return ResponseModel.succ(page);
	}


	@Override
	public ResponseModel enrollmentsOrders(activitiesListReq req) {
		return null;
	}

	@Override
	public ResponseModel myEnrollingActivities(activitiesListReq req) {
		EnrollingListReqDto params = new EnrollingListReqDto();
		BeanUtils.copyProperties(req, params);

		PageInfo info = enrollingActivityService.myEnrollingActivities(params);


		List<HomeEnrollingActivityVO> eaList = new ArrayList<HomeEnrollingActivityVO>();
		if (info != null) {
			eaList = info.getList();
		}

		PageSerializable page = new PageSerializable();
		if (eaList == null || eaList.size() <= 0) {
			page.setList(eaList);
			page.setTotal(info.getTotal());
			return ResponseModel.succ(page);
		}

		List<PersonalEnrollingActivityVO> persionalList = getPersionalList(eaList);

		page.setList(persionalList);
		page.setTotal(info.getTotal());
		return ResponseModel.succ(page);
	}

	@Override
	@Transactional
	public ResponseModel activityRemind(EnrollingReq.activityIdTypeReq req) {

		//判断是否已经设置过提醒
		NotifyPartyEnrollingActivity noticeModel;
		noticeModel = notifyPartyEnrollingActivityService.getFilterModel(req);
		if (noticeModel != null) {
			return ResponseModel.fail("您已经设置过提醒");
		}

		//校验距离活动剩余时间
		EnrollingActivity activity = enrollingActivityService.getEnrollingActivityById(String.valueOf(req.getActivityId()));
		Integer margin = DateUtil.getMarginMin(activity.getEndAt(), new Date());
		if(margin < 60) {
			return ResponseModel.fail("活动已经结束或者距离活动结束时间已不足一个小时，您不能设置提醒");
		}

		noticeModel = new NotifyPartyEnrollingActivity();
		noticeModel.setActivityId(Integer.valueOf(req.getActivityId()));
		if (req.getPartyId() != null)
			noticeModel.setPartyId(Integer.valueOf(req.getPartyId()));
		noticeModel.setAccountId(Integer.valueOf(req.getAccountId()));
		noticeModel.setBeginNotified(false);
		noticeModel.setEndNotified(false);
		noticeModel.setCreatedAt(new Date());
		noticeModel.setPathType(req.getPathType());
		notifyPartyEnrollingActivityService.saveNotify(noticeModel);

		//短信推送
		//String notifierMobile = accountService.getNotifierMobile(Integer.parseInt(req.getAccountId()));
		sendEnrollingMessage(req.getMobile(), AliSmsTemplateEnums.SET_ENROLLING_REMINDE.getCode(), null, null);

		//设置活动过期时间当时间过期报名活动结束
		stringRedisTemplate.opsForValue().set(SystemConstant.ENROLLING_REMIND_KEY + activity.getId(),
				"finish", (activity.getEndAt().getTime() - DateUtil.nextMinute(60).getTime()) / 1000 , TimeUnit.SECONDS);
		log.info("已经开始设置提醒时间" + (activity.getEndAt().getTime() - DateUtil.nextMinute(60).getTime()) / 1000);

		//被提醒数量加一
		EnrollingActivity enrollingActivity = enrollingActivityService.getEnrollingActivityById(req.getActivityId());
		Integer reminderNumber = enrollingActivity.getReminderNumber() == null ? 0 : enrollingActivity.getReminderNumber();
		enrollingActivity.setReminderNumber(reminderNumber + 1);
		enrollingActivityService.updateEnrollingActivityById(enrollingActivity);

		return ResponseModel.succ();
	}

	@Override
	@Transactional
	public synchronized ResponseModel activityApply(EnrollingReq.activityIdTypeReq req) {
		//判断是否已经申请过该活动
		EnrollingDeposit enrollingDepositModel = enrollingDepositService.getFilterModel(req);
		if (enrollingDepositModel != null) {
			return ResponseModel.fail("您已经申请过该活动");
		}

		EnrollingActivity enrollingActivity = enrollingActivityService.getEnrollingActivityById(req.getActivityId());
		if (enrollingActivity == null) {
			return ResponseModel.fail("未找到该预招商活动信息！");

		}
		//获取用户账号信息
		AccountBaseDto memInfo = accountService.getAccountBaseByPartyId(Integer.valueOf(req.getPartyId()));
		if (memInfo == null ) {
			return ResponseModel.fail("未找到登录用户！");
		}
		if(EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(enrollingActivity.getType())&&!memInfo.isPayBind()){
			return ResponseModel.fail("请先认证东方付通账号！");
		}


		if(req.getPartyId().equals(String.valueOf(enrollingActivity.getPartyId()))){
			return ResponseModel.fail("标的发布人不能报名！");
		}

		TAgency agency = agencyService.findByAgencyId(enrollingActivity.getPartyId());
		AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(Integer.parseInt(req.getPartyId()));

		if(agency != null && agency.getMobile().equals(accountBaseDto.getMobile())) {
			return ResponseModel.fail("标的发布人不能报名！");
		}
		//调用统一支付接口 锁定保证金
		UnifiedPayResp payResp = new UnifiedPayResp();
		payResp.setCode(PayResultEnums.PAY_SUCCESS.getCode());
		payResp.setDesc(PayResultEnums.PAY_SUCCESS.getDesc());

		//当为抵押物预招商时 调用东方付通保证金锁定
		if(EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(enrollingActivity.getType())){
			try {
				payResp = LockEnrollingDeposit(req, enrollingActivity, memInfo);

			} catch (Exception e) {

				exceptionEmailService.sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.ENROLLING,"抵押物预招商佣金支付",JSON.toJSONString(req),exceptionEmailService.exceptionToStr(e));

				return ResponseModel.fail("支付系统异常，请稍后重试！");
			}
		}

		//保证金锁定成功 或者 锁定异步返回结果
		if (PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode())||PayResultEnums.PAY_NOTICE.getCode().equals(payResp.getCode())) {

			saveAndUpdate(enrollingActivity, req,payResp);
			// 发送小程序下级好友报名招商成功消息
			appletMessageService.sendEnrollingActivityApplyMessage(memInfo.getAccountId(), enrollingActivity.getName());
			//发送报名邮件
			new Thread(()->sendApplyEmail(enrollingActivity)).start();

			return ResponseModel.succ();

		}


		return ResponseModel.fail(payResp.getDesc());
	}

	/**
	 *
	 *预招商报名发送邮件
	 */
	private void sendApplyEmail(EnrollingActivity enrollingActivity) {

		try {

			enrollingEmailService.sendEnrollingApply(enrollingActivity.getCode(),enrollingActivity.getType());

		}catch (Exception e){
			log.error("发送报名邮件异常，异常信息为:{}",e);
		}
	}

	private void saveAndUpdate(EnrollingActivity enrollingActivity, activityIdTypeReq req,UnifiedPayResp payResp) {

		//获取连拍机构信息
		Integer agencyId = getAgencyIdByCode(req.getAgencyCode(),"1");

		//统计申请数据加一
		Integer applyNumber = enrollingActivity.getApplyNumber() == null ? 0: enrollingActivity.getApplyNumber();
		enrollingActivity.setApplyNumber(applyNumber + 1);
		enrollingActivityService.updateEnrollingActivityById(enrollingActivity);

		EnrollingDeposit enrollingDeposit = new EnrollingDeposit();
		enrollingDeposit.setStatus(PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode())?EnrollingEnum.PAY_STATUS.LOCKED.getType():EnrollingEnum.PAY_STATUS.WAIT_LOCKED.getType());
		enrollingDeposit.setPartyId(Integer.valueOf(req.getPartyId()));
		enrollingDeposit.setAmount(enrollingActivity.getDeposit());
		enrollingDeposit.setCreatedAt(DateUtil.getSysTime());
		enrollingDeposit.setActivityId(enrollingActivity.getId());
		enrollingDeposit.setAgencyId(agencyId);
		enrollingDeposit.setType(enrollingActivity.getType());
		enrollingDeposit.setOrderNum(payResp.getPayOrder());

		enrollingDeposit.setShowName(getShowName(enrollingActivity.getType()));

		enrollingDepositService.saveEnrollingDeposit(enrollingDeposit);

	}

	/**
	 *
	 *获取报名人的展示信息
	 */
	private String getShowName(String type) {


		return EnrollingEnum.SHOW_NAME_MARK_CODE.getCode(type)+RandomNumberGenerator.numberGenerator(6);
	}

	/**
	 *
	 *根据机构的code 获取机构的id
	 */
	private Integer getAgencyIdByCode(String agencyCode,String type) {

		//为空并且为报名时 默认百昌
		if(StringUtils.isEmpty(agencyCode)&&!"1".equals(type)){
			return null;
		}else if(StringUtils.isEmpty(agencyCode)&&"1".equals(type)){
			agencyCode="SHBC";
		}

		TAgency agency = agencyService.findByAgencyCode(agencyCode);

		if(agency==null){
			return null;
		}
		return agency.getId();
	}


	private UnifiedPayResp LockEnrollingDeposit(activityIdTypeReq req,EnrollingActivity activity,AccountBaseDto memInfo ) {

		LockOrReleaseOrDirectReq payMemInfo = new LockOrReleaseOrDirectReq();
		payMemInfo.setPayMemName(memInfo.getName());
		payMemInfo.setPayMemCode(memInfo.getDfftId());

		//调用支付接口
		UnifiedPayReq payReq = new UnifiedPayReq();
		payReq.setPayType(PayEnums.PAY_TYPE.LOCK_DEPOSIT.getType());
		payReq.setPartyId(Integer.valueOf(req.getPartyId()));
		payReq.setPayBusCode(PayEnums.PAY_BUS_CODE.ENROLLING_LOCK_DEPOSIT.getType());
		payReq.setPayTo(PayEnums.PAY_TO.PAY_TO_WEB.getType());//锁定到平台

		payReq.setAmount(activity.getDeposit());
		payReq.setBusId(req.getActivityId());
		payReq.setPayParam(payMemInfo);

		UnifiedPayResp payResp = payFacade.unifiedPay(payReq);

		return payResp;
	}

	@Override
	@Transactional
	public ResponseModel activityFocus(EnrollingReq.activityIdTypeReq req) {
		//判断是否已经关注过该活动
		FavoriteEnrollingActivity focusModel = favoriteEnrollingActivityService.getFilterModel(req);
		if(focusModel != null) {
			return ResponseModel.fail("您已关注过该活动");
		}

		FavoriteEnrollingActivity focus = new FavoriteEnrollingActivity();
		focus.setActivityId(Integer.valueOf(req.getActivityId()));
		if (null != req.getPartyId())
			focus.setPartyId(Integer.valueOf(req.getPartyId()));
		focus.setCreatedAt(new Date());
		focus.setAccountId(Integer.valueOf(req.getAccountId()));
		focus.setType(req.getFoucsType());
		focus.setResourceId(req.getResourceId());
		favoriteEnrollingActivityService.saveFocus(focus);

		//关注之后，该预招商活动关注量加一
		EnrollingActivity enrollingActivity = enrollingActivityService.getEnrollingActivityById(req.getActivityId());
		Integer focusNumber = enrollingActivity.getFocusNumber() == null ? 0 : enrollingActivity.getFocusNumber();
		enrollingActivity.setFocusNumber(focusNumber + 1);
		enrollingActivityService.updateEnrollingActivityById(enrollingActivity);

		return ResponseModel.succ();
	}

	@Override
	public ResponseModel getActivityJoint(EnrollingReq.activityIdReq req) {

		PageInfo info = enrollingActivityService.getActivityJoint(req.getActivityId(),req.getPage(),req.getPerPage());
		List<LinkageEnrollingActivityVO> homeList = info.getList();
		if(info != null) {
			homeList = info.getList();
		}

		PageSerializable page = new PageSerializable();
		page.setList(homeList);
		page.setTotal(info.getTotal());
		return ResponseModel.succ(page);
	}


	@Override
	public ResponseModel getEAannouncementById(activityAnnouncement activityAnnouncement) {
		EnrollingActivity enrollingActivity = enrollingActivityService
				.getEnrollingActivityById(activityAnnouncement.getActivityId());
		JSONObject json = JsonUtil.toJSONObject(enrollingActivity);

		//获取具体的模板json字符串
		InstructionsContent params = new InstructionsContent();
		params.setId(Integer.parseInt(activityAnnouncement.getAnnouncementId()));
		InstructionsContent instructionsContent = instructionsContentService.findInstructionsContentById(params);

		String formtString = AnnouncementUtil.parse("{{", "}}", instructionsContent.getContent(), json);
		Map<String, Object> result = new HashMap<>();
		result.put("modelHtml", formtString);
		return ResponseModel.succ(result);
	}


	@Override
	public ResponseModel myApplyActivity(activityIdTypeReq req) {
		EnrollingListReqDto params = new EnrollingListReqDto();
		BeanUtils.copyProperties(req, params);
		PageInfo info = enrollingActivityService.myApplyActivity(params);

		List<HomeEnrollingActivityVO> eaList = new ArrayList<>();
		if(info != null) {
			eaList = info.getList();
		}
		PageSerializable page = new PageSerializable();
		if (eaList == null || eaList.size() <= 0) {
			page.setList(eaList);
			page.setTotal(info.getTotal());
			return ResponseModel.succ(page);
		}

		List<PersonalEnrollingActivityVO> persionalList = getPersionalList(eaList);

		page.setList(persionalList);
		page.setTotal(info.getTotal());
		return ResponseModel.succ(page);
	}


	@Override
	public ResponseModel getAllTypeActivity(activitiesListReq req) {
		PageReqDto params = new PageReqDto();
		BeanUtils.copyProperties(req, params);
		PageInfo info = enrollingActivityService.getAllTypeActivity(params);
		List<HomeEnrollingActivityVO> list = new ArrayList<>();
		if(info != null) {
			list = info.getList();
		}

		Map<String, Object> result = new HashMap<>();
		result.put("typeList", list);
		return ResponseModel.succ(result);
	}


	@Override
	public ResponseModel searchHomeActivity(searchReq req) {
		EnrollingListReqDto params = new EnrollingListReqDto();
		BeanUtils.copyProperties(req, params);
		params.setInfo(req.getQuery());
		params.setCategory(getCategoryInfo(params.getCategory()));
        setActivityPosterParams(req, params);
		log.info("招商搜索条件为：{}", JSON.toJSONString(params));
		PageInfo pageInfo = enrollingActivityService.searchHomeActivity(params);
		if (StringUtils.isNotEmpty(req.getTodayFlag()) && pageInfo.getList().size() == 0) {
			params.setTodayFlag(null);
			params.setPage(1);
			params.setPerPage(10);
			params.setOrderBy("recentUpload");
			params.setStatus("ONLINE");
			pageInfo = enrollingActivityService.searchHomeActivity(params);
			pageInfo.setHasNextPage(false);
			pageInfo.setTotal(pageInfo.getList().size());
		}
		PageSerializable page = new PageSerializable<>();
		page.setList(pageInfo.getList());
		page.setTotal(pageInfo.getTotal());
		return ResponseModel.succ(page);
	}

	private void setActivityPosterParams(searchReq req, EnrollingListReqDto params) {
        if (req.getActivityPosterId() == null) {
        	return;
        }
		TActivityPoster activityPoster = activityPosterDao.selectById(req.getActivityPosterId());
		if (activityPoster == null) {
			return;
		}
		if (activityPoster.getAutoFlag()) {
			if (StringUtils.isNotBlank(activityPoster.getCategory())) {
				List<String> categoryIdList = new ArrayList<>();
				List<String> categoryList = Arrays.asList(activityPoster.getCategory().split(","));
				for (String item : categoryList) {
					if (item.startsWith(ActivityServiceProviderEnum.ActivityType.Enrolling.getKey())) {
						categoryIdList.add(item.split("_")[1]);
					}
				}
				if (categoryIdList.size() > 0) {
					params.setTypeList(categoryIdList);
				} else {
					params.setTypeList(Arrays.asList(SystemConstant.NONE));
				}
			} else {
				params.setTypeList(Arrays.asList(SystemConstant.NONE));
			}
			if (StringUtils.isNotBlank(activityPoster.getStatus())) {
				List<String> status2List = new ArrayList<>();
				List<String> statusList = Arrays.asList(activityPoster.getStatus().split(","));
				for (String item : statusList) {
					if (item.startsWith(ActivityServiceProviderEnum.ActivityType.Enrolling.getKey())) {
						status2List.add(item.split("_")[1]);
					}
				}
				if (status2List.size() > 0) {
					params.setStatusList(status2List);
				} else {
					params.setStatusList(Arrays.asList(SystemConstant.NONE));
				}
			} else {
				params.setStatusList(Arrays.asList(SystemConstant.NONE));
			}
			if (StringUtils.isNotBlank(activityPoster.getBusType())) {
				List<String> busTypeList = Arrays.asList(activityPoster.getBusType().split(","));
				if (busTypeList.size() > 0) {
					params.setBusTypeList(busTypeList);
				} else {
					params.setBusTypeList(Arrays.asList(SystemConstant.NONE));
				}
			} else {
				params.setBusTypeList(Arrays.asList(SystemConstant.NONE));
			}
			if (activityPoster.getProvinceId() != null) {
				params.setActivityPosterProvinceId(activityPoster.getProvinceId());
			}
			if (activityPoster.getCityId() != null) {
				params.setActivityPosterCityId(activityPoster.getCityId());
			}
			if (activityPoster.getAreaId() != null) {
				params.setActivityPosterAreaId(activityPoster.getAreaId());
			}
		} else {
			if (StringUtils.isNotBlank(activityPoster.getActivityIds())) {
				JSONArray jsonArray = JSON.parseArray(activityPoster.getActivityIds());
				List<Integer> activityIdList = new ArrayList<>();
				for (Object item : jsonArray) {
					JSONObject jsonObject = (JSONObject) item;
					if (ActivityServiceProviderEnum.ActivityType.Enrolling.getKey().equals(jsonObject.getString("activityType"))) {
						activityIdList.add(jsonObject.getIntValue("activityId"));
					}
				}
				if (activityIdList.size() > 0) {
					params.setActivityIdList(activityIdList);
				} else {
					params.setActivityIdList(Arrays.asList(-1));
				}
			} else {
				params.setActivityIdList(Arrays.asList(-1));
			}
		}
    }

	private String getCategoryInfo(String category) {

		String categoryInfo = null;

		if ("1".equals(category)) {
			categoryInfo = PartyEnum.Category.BANK_COMPANY.getKey();
		} else if ("2".equals(category)) {
			categoryInfo = PartyEnum.Category.AMC_COMPANY.getKey();
		} else if ("3".equals(category)) {
			categoryInfo = PartyEnum.Category.FOLK_ASSET_COMPANY.getKey();
		} else if ("4".equals(category)) {
			categoryInfo =PartyEnum.Category.NORMAL_USER.getKey();
		} else if ("5".equals(category)) {
			categoryInfo =PartyEnum.Category.AUCTION_COMPANY.getKey();
		}else if("6".equals(category)){
			categoryInfo =PartyEnum.Category.NORMAL_COMPANY.getKey();

		}

		return categoryInfo;
	}


	@Override
	public ResponseModel getMyActivityInfo(activityIdReq req) {
		EnrollingDetailInfoVo enrollingActivityBaseInfo = enrollingActivityService
				.getEnrollingActivityBaseInfo(req.getActivityId());
		return ResponseModel.succ(enrollingActivityBaseInfo);
	}


	@Override
	public ResponseModel getCityStatusType() {
		Object pageCities = cityService.pageCities();
		List<AssetProperty> assetPropertyList = assetPropertyService.getAssetPropertyListByType("3");
		List<EnrollingStatusVO> statusList = new ArrayList<>();
		statusList.add(new EnrollingStatusVO(EnrollingEnum.STATUS.ONLINE.getType(), EnrollingEnum.STATUS.ONLINE.getTypeName()));
		statusList.add(new EnrollingStatusVO(EnrollingEnum.STATUS.FINISHED.getType(), EnrollingEnum.STATUS.FINISHED.getTypeName()));
		statusList.add(new EnrollingStatusVO(EnrollingEnum.STATUS.CLOSED.getType(), EnrollingEnum.STATUS.CLOSED.getTypeName()));
		Map<String, Object> result = new HashMap<>();

		result.put("allCity", pageCities);
		result.put("assetPropertyList", assetPropertyList);
		result.put("statusList", statusList);
		return ResponseModel.succ(result);
	}


	@Override
	public ResponseModel getActivityByTab(tabReq req) {
		ActivityTabDto params = new ActivityTabDto();
		BeanUtils.copyProperties(req, params);

		List<HomeEnrollingActivityVO> homeList = enrollingActivityService.getActivityByTab(params);
		if(homeList == null || homeList.size() <= 0) {
			return ResponseModel.succ();
		}
		homeList = getHomeList(homeList);

		//首页状态暂时没有显示
		Map<String, Object> result = new HashMap<>();
		result.put("homeList", homeList);
		return ResponseModel.succ(result);
	}

	/**
	 *
	 *保存预招商信息
	 */
	@Override
	@Transactional
	public ResponseModel saveActivity(EnrollingReq.saveActivityReq req) {

		//根据请求参数挑选组装必须的参数
		Map<String, Object> result = getActivityParams(req, req.getEnrollingType(), null);
		JSONArray errorArray = (JSONArray) result.get("errorArray");
		if(!errorArray.isEmpty()) {
			return ResponseModel.fail(ApiCallResult.EMPTY, errorArray);
		}

		EnrollingActivity params = (EnrollingActivity) result.get("activity");
		params.setType(req.getEnrollingType());
		params.setCategoryId(req.getTemplateId());
		params.setThirdPartyStatus(req.getThirdPartyStatus());

		enrollingActivityService.saveActivity(params);

		//保存预招商债权信息表
		EnrollingActivityData data = new EnrollingActivityData();

		data.setActivityId(params.getId());
		data.setContent(JSONObject.parseObject(req.getFields()));

		enrollingActivityDataService.saveActivityData(data);

		//发送邮件信息
		sendEnrollingEmail(params,req);

		//删除草稿箱
		delDrafts(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType()==req.getThirdPartyStatus()?SystemConstant.AGENCY_DRAFTS_PREFIX_KEY+req.getPartyId():String.valueOf(req.getPartyId()), AssetEnum.Drafts.ENROLLING.getKey());

		return ResponseModel.succ();
	}

	private void sendEnrollingEmail(EnrollingActivity params, saveActivityReq req) {
		//机构上拍
		if(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType()==req.getThirdPartyStatus()){
			//发送平台审核邮件
			if(EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(params.getType())) {
				new Thread(()->sendAuditEmail(params)).start();
			}
		}else{
			//短信通知机构审核人员进行审核
			if(EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(params.getType())) {
				Map<String, Object> paramsMap = new HashMap<>();
				paramsMap.put("enrolling_code", params.getCode());
				sendEnrollingMessage(null, AliSmsTemplateEnums.INVESTMENT_SUBMISSION.getCode(), paramsMap, params.getAgencyId());
			}
		}

		//债权物权发送审核邮件
		if(!EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(params.getType())){
			new Thread(()->sendAuditEmail(params)).start();
		}



	}


	/**
	 * 保存预招商信息
	 */
	@Override
	@Transactional
	public ResponseModel saveActivityFromThirdParty(EnrollingReq.saveActivityThirdPartyReq req) {

		//根据请求参数挑选组装必须的参数
		Map<String, Object> result     = getThirdPartyActivityParams(req, req.getEnrollingType(), null);
		JSONArray           errorArray = (JSONArray) result.get("errorArray");
		if (!errorArray.isEmpty()) {
			return ResponseModel.fail(ApiCallResult.EMPTY, errorArray);
		}

		EnrollingActivity params = (EnrollingActivity) result.get("activity");
		params.setType(req.getEnrollingType());
		params.setCategoryId(req.getTemplateId());

		//保存第三方信息
		params.setThirdPartyStatus(1);
		params.setThirdPartyTitle(req.getThirdPartyTitle());
		params.setThirdPartyUrl(req.getThirdPartyUrl());
		// 保存项目经理
		params.setOptions(req.getOptions());
		// 保存就设置为上线状态
		params.setStatus(EnrollingEnum.STATUS.ONLINE.getType());

        params.setOperatorAt(new Date());
        params.setOperatorId(Integer.valueOf(req.getOperatorId()));

		enrollingActivityService.saveActivity(params);

		//保存预招商债权信息表
		EnrollingActivityData data = new EnrollingActivityData();

		data.setActivityId(params.getId());
		data.setContent(JSONObject.parseObject(req.getFields()));

		enrollingActivityDataService.saveActivityData(data);

		//删除草稿箱
		if(EnrollingEnum.ENROLLING_TYPE.CREDITOR_RIGHTS.getType().equals(params.getType())) {
			delDrafts(String.valueOf(req.getPartyId()), "THIRDENROLLINGWQ");
		}else if (EnrollingEnum.ENROLLING_TYPE.PROPERTY_RIGHTS.getType().equals(params.getType())){
			delDrafts(String.valueOf(req.getPartyId()), "THIRDENROLLINGZQ");
		}

		return ResponseModel.succ();
	}


	private void delDrafts(String partyPrimaryId, String type) {
		AssetDataDraftsCondition assetDataDraftsCondition = new AssetDataDraftsCondition();
		assetDataDraftsCondition.setType(type);
		assetDataDraftsCondition.setPartyId(partyPrimaryId + "");
		assetDataDraftsCondition.setDelFlag(AssetDataDrafts.notDel);
		AssetDataDrafts assetDataDrafts = assetDataDraftsDao.selectOneResult(assetDataDraftsCondition);
		if (null != assetDataDrafts) {
			assetDataDrafts.setDelFlag(AssetDataDrafts.Del);  //删除
			assetDataDraftsDao.updateById(assetDataDrafts);
		}
	}


	private void sendEnrollingMessage(String notifierMobile, String code, Map<String, Object> params, Integer agencyId) {

		FAliSmsSendReq fAliSmsSendReq = new FAliSmsSendReq();
		try {
			if(StringUtils.isBlank(notifierMobile) && agencyId != null) {
				AgencyReq.BaseReq baseReq = new AgencyReq.BaseReq();
				baseReq.setAgencyId(agencyId);
				AgencyResp.DetailResp detailResp = agencyService.getAgencyById(baseReq);
				notifierMobile = detailResp.getAgency().getMobile();
			}

			fAliSmsSendReq.setPhoneNumber(notifierMobile);
			fAliSmsSendReq.setTemplateCode(code);
			fAliSmsSendReq.setTemplateParam(JSON.toJSONString(params));

			gatewayMqSender.sendSms(fAliSmsSendReq);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		log.info("预招商提醒信息已经发送，请求参数:{}",JSON.toJSONString(fAliSmsSendReq));
	}


	/**
	 *
	 *发送审核邮件
	 */
	private void sendAuditEmail(EnrollingActivity params) {

		try{

			enrollingEmailService.sendEnrollingAudit(params.getCode(),params.getType());

		}catch (Exception e){

			log.error("发送预招商审核邮件异常，异常信息为：{}",e);
		}

	}

	/**
	 *
	 *支付发布的订单
	 */
	private ResponseModel payInfo(BigDecimal payAmount, EnrollingActivity params) {


		//生成支付订单
		UnifiedPayReq payReq = new UnifiedPayReq();
		WxScanPayReq wx = new WxScanPayReq();

		wx.setBody("预招商发布服务费");

		payReq.setPartyId(params.getPartyId());
		payReq.setBusId(String.valueOf(params.getId()));
		payReq.setPayBusCode(PayEnums.PAY_BUS_CODE.ENROLLING_SUBMIT_PAY.getType());
		payReq.setAmount(payAmount);
		payReq.setPayType(PayEnums.PAY_TYPE.SCAN_PAY.getType());
		payReq.setPayParam(wx);

		UnifiedPayResp payResp = new UnifiedPayResp();
		try{
			payResp = payFacade.unifiedPay(payReq);

		}catch (Exception e){

			log.error("请求微信支付异常，异常信息为：{}",e);
		}

		//添加发布支付订单
		EnrollingSubmitOrder order = new EnrollingSubmitOrder();
		order.setActivityId(params.getId());
		order.setAmount(payAmount);
		order.setCreatedAt(new Date());
		order.setOrderNum(payResp.getPayOrder());
		order.setType(Integer.valueOf(params.getType()));
		order.setStatus(EnrollingEnum.ORDER_STATUS.NOT_PAY.getType());
		enrollingSubmitOrderService.saveSubmitOrder(order);


		EnrollingSubmitOrderVo vo = new EnrollingSubmitOrderVo();

		vo.setAmount(String.valueOf(payAmount));
		vo.setUrl(payResp.getUrl());
		vo.setOrderNum(payResp.getPayOrder());
		vo.setActivityName(params.getName());
		vo.setActivityId(String.valueOf(params.getId()));
		return ResponseModel.succ(vo);
	}

	/**
	 * 获取提交预招商所需的金额
	 *
	 */
	private String getPayAmount(String enrollingType) {


		//根据类型获取发布预招商所需的金额
		TServiceConfig tServiceConfig;
		if(EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(enrollingType)){
			tServiceConfig = tServiceConfigService.selectByConfigType(ServiceConfigEnum.ENROLLING_PAWN_PRICE);
			//债权类型
		}else if (EnrollingEnum.ENROLLING_TYPE.PROPERTY_RIGHTS.getType().equals(enrollingType)){
			tServiceConfig = tServiceConfigService.selectByConfigType(ServiceConfigEnum.ENROLLING_CREDITOR_PRICE);
			//物权类型
		}else{
			tServiceConfig = tServiceConfigService.selectByConfigType(ServiceConfigEnum.ENROLLING_REAL_PRICE);
		}

		if(!("0.00").equals(tServiceConfig.getConfigValue())&&!"0".equals(tServiceConfig.getConfigValue())){

			return  tServiceConfig.getConfigValue();

		}

		return null;
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
			log.error("获取不需要支付的partyId 异常，异常信息为{}",e);
		}
		return false;
	}

	private Map<String, Object> getActivityParams(saveActivityReq req,String enrollingType, Integer activityId) {
		//获取对应的模板信息
		System.out.println("fields = " + req.getFields());
		JSONObject jsonObject = JSON.parseObject(req.getFields());
		EnrollingActivity activity ;
		if(activityId == null) {
			activity = new EnrollingActivity();
			//添加的时候设置可见等级
			activity.setVisibilityLevel(EnrollingEnum.VISIBILITY_LEVEL.PLATFORM_DEFAULT.getType());

			//新增设置参数
			activity.setType(enrollingType);
			activity.setCreatedAt(new Date());
			activity.setCode(getCode(EnrollingEnum.MARK_CODE.getCode(enrollingType)));

			Integer partyId = activity.getPartyId();
			activity.setPartyId(partyId == null?req.getPartyId():partyId);
		}else {
			activity = enrollingActivityService.getEnrollingActivityById(String.valueOf(activityId));
		}

		//修改标识，是后台进行修改还是主站进行修改
		String updateType = req.getUpdateType();

		//获取模板传过来的参数
		JSONArray templateDate = jsonObject.getJSONArray("templateDate");

		List<String> busTypeNameList = new ArrayList<>();

		//校验参数数组
		JSONArray errorArrray = new JSONArray();
		//抵押物招商
		if(EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(enrollingType)){
			//预招商活动新增或者修改进行状态设置
			if(activityId == null) {
				//机构上拍 直接到平台审核
				activity.setStatus(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType()==req.getThirdPartyStatus()?EnrollingEnum.STATUS.AGENCY_APPROVED.getType():EnrollingEnum.STATUS.PUBLISHED.getType());
			}else {
				//admin 修改时状态不变
				if(!"1".equals(updateType)){
					activity = setActivityStatus(activity, activityId);
				}
			}

			//将必须存入activity 表的字段提取
			for(EnrollingEnum.GUARANTEE_MUST_KEY e :EnrollingEnum.GUARANTEE_MUST_KEY.values() ){
				//遍历获取模板的所有字段
				for (int i = 0; i < templateDate.size(); i++) {
					AssetDto json = templateDate.getJSONObject(i).toJavaObject(AssetDto.class);


					if(e.getType().equals(formatKey(json.getKey()))){

						Map<String, Object> paramsMap = setParams(json,activity,e,updateType,busTypeNameList);
						AssetDto assetDto = (AssetDto) paramsMap.get("assetDto");
						if(assetDto != null) {
							errorArrray.add(assetDto);
						}
						activity = (EnrollingActivity) paramsMap.put("activity", activity);

                        if(!e.getType().equals(EnrollingEnum.GUARANTEE_MUST_KEY.BUS_TYPE_NAME.getType())){
                            break;
                        }
 					}else {
						//非必填字段校验金额类数据
						if ("1".equals(json.getFmNum())) {

							if (json.getVal().isEmpty()) {
								continue;
							}
							if (StringUtils.isEmpty(json.getVal().get(0))) {
								continue;
							}
							errorArrray = checkNumberField(errorArrray, json);
						}
					}
				}
			}
			//	债权预招商
		}else if (EnrollingEnum.ENROLLING_TYPE.PROPERTY_RIGHTS.getType().equals(enrollingType)){
			//新增时才设置状态
			if(activityId == null) {
				activity.setStatus(EnrollingEnum.STATUS.AGENCY_APPROVED.getType());
			}else {
				//admin 修改时状态不变
				if(!"1".equals(updateType)){
					activity = setActivityStatus(activity, activityId);
				}
 			}

			//将必须存入activity 表的字段提取
			for(EnrollingEnum.ZHAI_QUAN_MUST_KEY e :EnrollingEnum.ZHAI_QUAN_MUST_KEY.values() ){
				//遍历获取模板的所有字段
				for (int i = 0; i < templateDate.size(); i++) {
					AssetDto json = templateDate.getJSONObject(i).toJavaObject(AssetDto.class);
					//提取字段
					if(e.getType().equals(formatKey(json.getKey()))){

						Map<String, Object> paramsMap = setZhaiParams(json,activity,e, updateType,busTypeNameList);
						AssetDto assetDto = (AssetDto) paramsMap.get("assetDto");
						if(assetDto != null) {
							errorArrray.add(assetDto);
						}
						activity = (EnrollingActivity) paramsMap.put("activity", activity);
						//抵押情况要全遍历 其他的取到就遍历结束
						if(!e.getType().equals(EnrollingEnum.ZHAI_QUAN_MUST_KEY.EDYQK.getType())&&!e.getType().equals(EnrollingEnum.ZHAI_QUAN_MUST_KEY.BUS_TYPE_NAME.getType())){
							break;
						}
					}else {
						//非必填字段校验金额类数据
						if ("1".equals(json.getFmNum())) {

							if (json.getVal().isEmpty()) {
								continue;
							}
							if (StringUtils.isEmpty(json.getVal().get(0))) {
								continue;
							}
							errorArrray = checkNumberField(errorArrray, json);
						}
					}
				}

			}
			//物权招商
		}else{
			//新增时采才进行设置状态
			if(activityId == null) {
				activity.setStatus(EnrollingEnum.STATUS.AGENCY_APPROVED.getType());
			}else {
				//admin 修改时状态不变
				if(!"1".equals(updateType)){
					activity = setActivityStatus(activity, activityId);
				}
 			}

			//将必须存入activity 表的字段提取
			for(EnrollingEnum.WU_QUAN_MUST_KEY e :EnrollingEnum.WU_QUAN_MUST_KEY.values() ){
				//遍历获取模板的所有字段
				for (int i = 0; i < templateDate.size(); i++) {
					AssetDto json = templateDate.getJSONObject(i).toJavaObject(AssetDto.class);
					//提取字段
					if(e.getType().equals(formatKey(json.getKey()))){

						Map<String, Object> paramsMap = setWuQuanParams(json,activity,e, updateType,busTypeNameList);
						AssetDto assetDto = (AssetDto) paramsMap.get("assetDto");
						if(assetDto != null) {
							errorArrray.add(assetDto);
						}
						activity = (EnrollingActivity) paramsMap.put("activity", activity);

						//抵押情况要全遍历 其他的取到就遍历结束
						if(!e.getType().equals(EnrollingEnum.WU_QUAN_MUST_KEY.ESFDY.getType())&&!e.getType().equals(EnrollingEnum.WU_QUAN_MUST_KEY.BUS_TYPE_NAME.getType())){
							break;
						}
					}else {
						//非必填字段校验金额类数据
						if ("1".equals(json.getFmNum())) {

							if (json.getVal().isEmpty()) {
								continue;
							}
							if (StringUtils.isEmpty(json.getVal().get(0))) {
								continue;
							}
							errorArrray = checkNumberField(errorArrray, json);
						}
					}
				}
			}
		}
        activity.setBusTypeName(getBusTypeNameInfo(busTypeNameList));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("errorArray", errorArrray);
		resultMap.put("activity", activity);
		return  resultMap;
	}

    private String getBusTypeNameInfo(List<String> busTypeNameList) {

	    String busTypeName="";

	    if(busTypeNameList.size()<1){
            return "";
        }

        for(String name :busTypeNameList){

            busTypeName =busTypeName + name+",";
        }

        return busTypeName;
    }


    private Map<String, Object> getThirdPartyActivityParams(saveActivityThirdPartyReq req,String enrollingType, Integer activityId) {
		//获取对应的模板信息
		System.out.println("fields = " + req.getFields());
		JSONObject jsonObject = JSON.parseObject(req.getFields());
		EnrollingActivity activity ;
		if(activityId == null) {
			activity = new EnrollingActivity();
			//添加的时候设置可见等级
			activity.setVisibilityLevel(EnrollingEnum.VISIBILITY_LEVEL.PLATFORM_DEFAULT.getType());

			//新增设置参数
			activity.setType(enrollingType);
			activity.setCreatedAt(new Date());
			activity.setCode(getCode(EnrollingEnum.MARK_CODE.getCode(enrollingType)));
			activity.setPartyId(req.getPartyId());
		}else {
			activity = enrollingActivityService.getEnrollingActivityById(String.valueOf(activityId));
		}

		//修改标识，是后台进行修改还是主站进行修改
		String updateType = req.getUpdateType();

		//获取模板传过来的参数
		JSONArray templateDate = jsonObject.getJSONArray("templateDate");

		//校验参数数组
		JSONArray errorArrray = new JSONArray();

		List<String> busTypeNameList = new ArrayList<>();

		//	债权预招商
		if (EnrollingEnum.ENROLLING_TYPE.PROPERTY_RIGHTS.getType().equals(enrollingType)){

			//将必须存入activity 表的字段提取
			for(EnrollingEnum.ZHAI_QUAN_MUST_KEY e :EnrollingEnum.ZHAI_QUAN_MUST_KEY.values() ){
				//遍历获取模板的所有字段
				for (int i = 0; i < templateDate.size(); i++) {
					AssetDto json = templateDate.getJSONObject(i).toJavaObject(AssetDto.class);
					//提取字段
					if(("t"+e.getType()).equals(formatKey(json.getKey()))){

						Map<String, Object> paramsMap = setZhaiParams(json,activity,e, updateType,busTypeNameList);
						AssetDto assetDto = (AssetDto) paramsMap.get("assetDto");
						if(assetDto != null) {
							errorArrray.add(assetDto);
						}
						activity = (EnrollingActivity) paramsMap.put("activity", activity);
						//抵押情况要全遍历 其他的取到就遍历结束
						if(!e.getType().equals(EnrollingEnum.ZHAI_QUAN_MUST_KEY.EDYQK.getType())){
							break;
						}
					}else {
						//非必填字段校验金额类数据
						if ("1".equals(json.getFmNum())) {

							if (json.getVal().isEmpty()) {
								continue;
							}
							if (StringUtils.isEmpty(json.getVal().get(0))) {
								continue;
							}
							errorArrray = checkNumberField(errorArrray, json);
						}
					}
				}

			}
			//物权招商
		}else{
			//将必须存入activity 表的字段提取
			for(EnrollingEnum.WU_QUAN_MUST_KEY e :EnrollingEnum.WU_QUAN_MUST_KEY.values() ){
				//遍历获取模板的所有字段
				for (int i = 0; i < templateDate.size(); i++) {
					AssetDto json = templateDate.getJSONObject(i).toJavaObject(AssetDto.class);
					//提取字段
					if(("t"+e.getType()).equals(formatKey(json.getKey()))){

						Map<String, Object> paramsMap = setWuQuanParams(json,activity,e, updateType,busTypeNameList);
						AssetDto assetDto = (AssetDto) paramsMap.get("assetDto");
						if(assetDto != null) {
							errorArrray.add(assetDto);
						}
						activity = (EnrollingActivity) paramsMap.put("activity", activity);

						//抵押情况要全遍历 其他的取到就遍历结束
						if(!e.getType().equals(EnrollingEnum.WU_QUAN_MUST_KEY.ESFDY.getType())){
							break;
						}
					}else {
						//非必填字段校验金额类数据
						if ("1".equals(json.getFmNum())) {

							if (json.getVal().isEmpty()) {
								continue;
							}
							if (StringUtils.isEmpty(json.getVal().get(0))) {
								continue;
							}
							errorArrray = checkNumberField(errorArrray, json);
						}
					}
				}
			}
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("errorArray", errorArrray);
		resultMap.put("activity", activity);
		return  resultMap;
	}

	private JSONArray checkNumberField(JSONArray errorArray, AssetDto json){

		boolean numeric = NumberValidationUtils.isNumeric(json.getVal().get(0));
		if (!numeric) {
			json.setErrorMsg("请输入正确的数值");
			errorArray.add(json);
		}
		return errorArray;
	}

	private EnrollingActivity setActivityStatus(EnrollingActivity activity, Integer activityId) {
		//修改时设置状态
		if(EnrollingEnum.STATUS.AGENCY_REJECT.getType().equals(activity.getStatus())) {
			activity.setStatus(EnrollingEnum.STATUS.PUBLISHED.getType());
		}else if(EnrollingEnum.STATUS.PLATFORM_REJECT.getType().equals(activity.getStatus())) {
			activity.setStatus(EnrollingEnum.STATUS.AGENCY_APPROVED.getType());
		}else if(EnrollingEnum.STATUS.END_PAY.getType().equals(activity.getStatus())) {
            activity.setStatus(EnrollingEnum.STATUS.AGENCY_APPROVED.getType());
        }
		return activity;
	}


	private Map<String, Object> setWuQuanParams(AssetDto json, EnrollingActivity activity, EnrollingEnum.WU_QUAN_MUST_KEY e, String updateType,List<String> busTypeNameList) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		//名称
		if(e.getTypeName().equals(EnrollingEnum.WU_QUAN_MUST_KEY.NAME.getTypeName())){
			//名称校验
			AssetDto jsonObject = fieldCheck(json, ENROLLING_FIELD_TYPE.VARCHAR_255);
			if(jsonObject != null) {
				resultMap.put("assetDto", jsonObject);
				resultMap.put("activity", activity);
				return resultMap;
			}

			//唯一校验预招商
			if(activity.getId() == null) {
				Boolean uniqueFlag = uniqueCheckActivity(json.getVal().get(0));
				if(!uniqueFlag) {
					json.setErrorMsg("已经存在相同的活动名称");
					resultMap.put("assetDto", json);
					resultMap.put("activity", activity);
					return resultMap;
				}
			}

			activity.setName(json.getVal().get(0));
		}
		//城市信息
		if(e.getTypeName().equals(EnrollingEnum.WU_QUAN_MUST_KEY.CITY.getTypeName())){

			activity = formatCity(json.getVal(), activity);
		}

		//市场参考价/债权本金
		if(e.getTypeName().equals(EnrollingEnum.WU_QUAN_MUST_KEY.REF_PRICE.getTypeName())){
			Pattern p = Pattern.compile(EnrollingEnum.FIELD_regExp.PRICE_REGEXP.getRegExpName());
			Matcher m = p.matcher(json.getVal().get(0));
			if(!m.matches()) {
				json.setErrorMsg(EnrollingEnum.FIELD_regExp.PRICE_REGEXP.getRegExpDesc());
				resultMap.put("assetDto", json);
				resultMap.put("activity", activity);
				return resultMap;
			}
			activity.setRefPrice(new BigDecimal(json.getVal().get(0)).setScale(2, BigDecimal.ROUND_HALF_UP));
		}

		//展示时间
		if(e.getTypeName().equals(EnrollingEnum.WU_QUAN_MUST_KEY.BEGIN_AT.getTypeName())){
			if(updateType == null) {
				AssetDto jsonObject = fieldCheck(json, ENROLLING_FIELD_TYPE.BETWENDATE);
				if(jsonObject != null) {
					resultMap.put("assetDto", jsonObject);
					resultMap.put("activity", activity);
					return resultMap;
				}
			}

			activity.setBeginAt(DateUtil.strToDateLong(json.getVal().get(0)));
			activity.setEndAt(DateUtil.strToDateLong(json.getVal().get(1)));
		}


		//展示图片
		if(e.getTypeName().equals(EnrollingEnum.WU_QUAN_MUST_KEY.IMAGES.getTypeName())){

			JSONObject jsonObject1 = getImages(json.getVal());

			activity.setExtra(jsonObject1);
		}
		//资产亮点
		if(e.getTypeName().equals(EnrollingEnum.WU_QUAN_MUST_KEY.DETAIL.getTypeName())){
			AssetDto jsonObject = fieldCheck(json, ENROLLING_FIELD_TYPE.ISBLANK);
			if(jsonObject != null) {
				resultMap.put("assetDto", jsonObject);
				resultMap.put("activity", activity);
				return resultMap;
			}

			activity.setBrightSpot(json.getVal().get(0));

			//activity.setDetail(json.getVal().get(0));
		}

		//项目联系人
		if(e.getTypeName().equals(EnrollingEnum.WU_QUAN_MUST_KEY.CONTACT_NAME.getTypeName())){
			AssetDto jsonObject = fieldCheck(json, ENROLLING_FIELD_TYPE.ISBLANK);
			if(jsonObject != null) {
				resultMap.put("assetDto", jsonObject);
				resultMap.put("activity", activity);
				return resultMap;
			}

			activity.setContactName(json.getVal().get(0));
		}
		//项目联系人号码
		if(e.getTypeName().equals(EnrollingEnum.WU_QUAN_MUST_KEY.CONTACT_PHONE.getTypeName())){
			AssetDto jsonObject = fieldCheck(json, ENROLLING_FIELD_TYPE.ISBLANK);
			if(jsonObject != null) {
				resultMap.put("assetDto", jsonObject);
				resultMap.put("activity", activity);
				return resultMap;
			}

			activity.setContactPhone(json.getVal().get(0));
		}


  		if(e.getTypeName().equals(EnrollingEnum.WU_QUAN_MUST_KEY.ESFDY.getTypeName())){
			//存在抵押情况设置
			if("是".equals(json.getVal().get(0))){
				activity.setGuarantee(1);
			}
		}



		if(e.getTypeName().equals(EnrollingEnum.WU_QUAN_MUST_KEY.EBDWSFYDY.getTypeName())){
			//存在抵押情况设置
			if("是".equals(json.getVal().get(0))){
				activity.setGuarantee(1);
			}
		}

		//标的物情况
        if(e.getTypeName().equals(EnrollingEnum.WU_QUAN_MUST_KEY.BUS_TYPE_NAME.getTypeName())){
            busTypeNameList.add(json.getVal().get(0));
        }


		resultMap.put("activity", activity);

		return  resultMap;
	}


	private Boolean uniqueCheckActivity(String activityName) {
		EnrollingActivityCondition enrollingActivityCondition = new EnrollingActivityCondition();
		EnrollingActivity enrollingActivity = enrollingActivityService.getActivityByName(activityName.trim());
		if(enrollingActivity != null) {
			return false;
		}
		return true;
	}


	private EnrollingActivity formatCity(List<String> json, EnrollingActivity activity) {
		StringBuilder cityNameBuilder = new StringBuilder();
		Set<String> provinces = new HashSet<>();
		Set<String> cities = new HashSet<>();
		Set<String> areas = new HashSet<>();
		for (String val : json) {
			JSONObject jsonObject3 = JSONObject.parseObject(val);
			if (jsonObject3.containsKey("id") && StringUtils.isNotBlank(jsonObject3.getString("id"))) {
				cities.add(jsonObject3.getString("id"));
			}
			if (jsonObject3.containsKey("provinceId") && StringUtils.isNotBlank(jsonObject3.getString("provinceId"))) {
				provinces.add(jsonObject3.getString("provinceId"));
			}
			if (jsonObject3.containsKey("areaId") && StringUtils.isNotBlank(jsonObject3.getString("areaId"))) {
				areas.add(jsonObject3.getString("areaId"));
			}
			if (jsonObject3.containsKey("name") && StringUtils.isNotBlank(jsonObject3.getString("name"))) {
				String cityName = jsonObject3.getString("name");
				cityNameBuilder.append(cityName).append(",");
			}

		}
		activity.setCityId(String.join(",", cities));
		activity.setProvinceId(String.join(",", provinces));
		activity.setAreaId(String.join(",", areas));
		activity.setCityName(cityNameBuilder.toString());
		return activity;
	}

	private JSONObject getImages(List<String> val) {

		JSONArray jsonArray = new JSONArray();
		for (String img : val) {
			JSONObject jsonObject1 = JSONObject.parseObject(img);
			String url = jsonObject1.getString("url");
			jsonArray.add(url);
		}
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("images", jsonArray);

		return jsonObject1;
	}

	private Map<String, Object> setZhaiParams(AssetDto json, EnrollingActivity activity, EnrollingEnum.ZHAI_QUAN_MUST_KEY e, String updateType,List<String> busTypeNameList) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		//名称
		if(e.getTypeName().equals(EnrollingEnum.ZHAI_QUAN_MUST_KEY.NAME.getTypeName())){
			//招商名称校验
			AssetDto jsonObject = fieldCheck(json, ENROLLING_FIELD_TYPE.VARCHAR_255);
			if(jsonObject != null) {
				resultMap.put("assetDto", jsonObject);
				resultMap.put("activity", activity);
				return resultMap;
			}

			//唯一校验预招商
			if(activity.getId() == null) {
				Boolean uniqueFlag = uniqueCheckActivity(json.getVal().get(0));
				if(!uniqueFlag) {
					json.setErrorMsg("已经存在相同的活动名称");
					resultMap.put("assetDto", json);
					resultMap.put("activity", activity);
					return resultMap;
				}
			}

			activity.setName(json.getVal().get(0));

		}
		//城市信息
		if(e.getTypeName().equals(EnrollingEnum.ZHAI_QUAN_MUST_KEY.CITY.getTypeName())){
			activity = formatCity(json.getVal(), activity);
		}

		//市场参考价/债权本金
		if(e.getTypeName().equals(EnrollingEnum.ZHAI_QUAN_MUST_KEY.REF_PRICE.getTypeName())){

			Pattern p = Pattern.compile(EnrollingEnum.FIELD_regExp.PRICE_REGEXP.getRegExpName());
			Matcher m = p.matcher(json.getVal().get(0));
			if(!m.matches()) {
				json.setErrorMsg(EnrollingEnum.FIELD_regExp.PRICE_REGEXP.getRegExpDesc());
				resultMap.put("assetDto", json);
				resultMap.put("activity", activity);
				return resultMap;
			}

			activity.setRefPrice(new BigDecimal(json.getVal().get(0)).setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		//债权利息
		if(e.getTypeName().equals(EnrollingEnum.ZHAI_QUAN_MUST_KEY.DEPOSIT.getTypeName())){
			Pattern p = Pattern.compile(EnrollingEnum.FIELD_regExp.PRICE_REGEXP.getRegExpName());
			Matcher m = p.matcher(json.getVal().get(0));
			if(!m.matches()) {
				json.setErrorMsg(EnrollingEnum.FIELD_regExp.PRICE_REGEXP.getRegExpDesc());
				resultMap.put("assetDto", json);
				resultMap.put("activity", activity);
				return resultMap;
			}

			activity.setDeposit(new BigDecimal(json.getVal().get(0)).setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		//开始展示时间
		if(e.getTypeName().equals(EnrollingEnum.ZHAI_QUAN_MUST_KEY.BEGIN_AT.getTypeName())){

			if(updateType == null) {
				AssetDto jsonObject = fieldCheck(json, ENROLLING_FIELD_TYPE.BETWENDATE);
				if(jsonObject != null) {
					resultMap.put("assetDto", jsonObject);
					resultMap.put("activity", activity);
					return resultMap;
				}
			}

			activity.setBeginAt(DateUtil.strToDateLong(json.getVal().get(0)));
			activity.setEndAt(DateUtil.strToDateLong(json.getVal().get(1)));
		}

		//展示图片
		if(e.getTypeName().equals(EnrollingEnum.ZHAI_QUAN_MUST_KEY.IMAGES.getTypeName())){


			JSONObject jsonObject1 = getImages(json.getVal());

			activity.setExtra(jsonObject1);

		}
		//资产亮点
		if(e.getTypeName().equals(EnrollingEnum.ZHAI_QUAN_MUST_KEY.DETAIL.getTypeName())){

			AssetDto jsonObject = fieldCheck(json, ENROLLING_FIELD_TYPE.ISBLANK);
			if(jsonObject != null) {
				resultMap.put("assetDto", jsonObject);
				resultMap.put("activity", activity);
				return resultMap;
			}
			activity.setBrightSpot(json.getVal().get(0));

			//activity.setDetail(json.getVal().get(0));
		}

		//项目联系人
		if(e.getTypeName().equals(EnrollingEnum.ZHAI_QUAN_MUST_KEY.CONTACT_NAME.getTypeName())){
			AssetDto jsonObject = fieldCheck(json, ENROLLING_FIELD_TYPE.ISBLANK);
			if(jsonObject != null) {
				resultMap.put("assetDto", jsonObject);
				resultMap.put("activity", activity);
				return resultMap;
			}

			activity.setContactName(json.getVal().get(0));
		}
		//项目联系人号码
		if(e.getTypeName().equals(EnrollingEnum.ZHAI_QUAN_MUST_KEY.CONTACT_PHONE.getTypeName())){
			AssetDto jsonObject = fieldCheck(json, ENROLLING_FIELD_TYPE.ISBLANK);
			if(jsonObject != null) {
				resultMap.put("assetDto", jsonObject);
				resultMap.put("activity", activity);
				return resultMap;
			}

			activity.setContactPhone(json.getVal().get(0));
		}



		if(e.getTypeName().equals(EnrollingEnum.ZHAI_QUAN_MUST_KEY.ESFYDZYW.getTypeName())){
			if("是".equals(json.getVal().get(0))){
				activity.setGuarantee(1);
			}
		}

		//抵押物情况
		if(e.getTypeName().equals(EnrollingEnum.ZHAI_QUAN_MUST_KEY.EDYQK.getTypeName())){
			if("有抵押".equals(json.getVal().get(0))){
				activity.setGuarantee(1);
			}
		}

        //标的属性
        if(e.getTypeName().equals(EnrollingEnum.ZHAI_QUAN_MUST_KEY.BUS_TYPE_NAME.getTypeName())){
            busTypeNameList.add(json.getVal().get(0));
        }

		resultMap.put("activity", activity);
		return  resultMap;
	}


	/**
	 * 将字段赋值
	 *
	 */
	private Map<String, Object> setParams(AssetDto json,EnrollingActivity activity,EnrollingEnum.GUARANTEE_MUST_KEY e, String updateType,List<String> busTypeNameList) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		//名称
		if(e.getTypeName().equals(EnrollingEnum.GUARANTEE_MUST_KEY.NAME.getTypeName())){
			AssetDto jsonObject = fieldCheck(json, ENROLLING_FIELD_TYPE.VARCHAR_255);
			if(jsonObject != null) {
				resultMap.put("assetDto", jsonObject);
				resultMap.put("activity", activity);
				return resultMap;
			}

			//唯一校验预招商
			if(activity.getId() == null) {
				Boolean uniqueFlag = uniqueCheckActivity(json.getVal().get(0));
				if(!uniqueFlag) {
					json.setErrorMsg("已经存在相同的活动名称");
					resultMap.put("assetDto", json);
					resultMap.put("activity", activity);
					return resultMap;
				}
			}

			activity.setName(json.getVal().get(0));

		}
		//城市信息
		if(e.getTypeName().equals(EnrollingEnum.GUARANTEE_MUST_KEY.CITY.getTypeName())){
			activity = formatCity(json.getVal(), activity);
		}
		//送拍机构id
		if(e.getTypeName().equals(EnrollingEnum.GUARANTEE_MUST_KEY.AGENCY_ID.getTypeName())){

			//字符长度校验

			JSONObject jsonObject1 = JSONObject.parseObject(json.getVal().get(0));
			String agencyId = jsonObject1.getString("id");
			if(StringUtils.isBlank(agencyId)) {
				json.setErrorMsg("不能为空");
				resultMap.put("assetDto", json);
				resultMap.put("activity", activity);
				return resultMap;
			}
			activity.setAgencyId(Integer.valueOf(agencyId));
		}
		//市场参考价/债权本金
		if(e.getTypeName().equals(EnrollingEnum.GUARANTEE_MUST_KEY.REF_PRICE.getTypeName())){
			Pattern p = Pattern.compile(EnrollingEnum.FIELD_regExp.PRICE_REGEXP.getRegExpName());
			Matcher m = p.matcher(json.getVal().get(0));
			if(!m.matches()) {
				json.setErrorMsg(EnrollingEnum.FIELD_regExp.PRICE_REGEXP.getRegExpDesc());
				resultMap.put("assetDto", json);
				resultMap.put("activity", activity);
				return resultMap;
			}

			activity.setRefPrice(new BigDecimal(json.getVal().get(0)).setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		//保证金金额
		if(e.getTypeName().equals(EnrollingEnum.GUARANTEE_MUST_KEY.DEPOSIT.getTypeName())){
			Pattern p = Pattern.compile(EnrollingEnum.FIELD_regExp.PRICE_REGEXP.getRegExpName());
			Matcher m = p.matcher(json.getVal().get(0));
			if(!m.matches()) {
				json.setErrorMsg(EnrollingEnum.FIELD_regExp.PRICE_REGEXP.getRegExpDesc());
				resultMap.put("assetDto", json);
				resultMap.put("activity", activity);
				return resultMap;
			}

			activity.setDeposit(new BigDecimal(json.getVal().get(0)).setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		//展示时间
		if(e.getTypeName().equals(EnrollingEnum.GUARANTEE_MUST_KEY.BEGIN_AT.getTypeName())){
			//校验时间展示
			if(updateType == null) {
				AssetDto jsonObject = fieldCheck(json, ENROLLING_FIELD_TYPE.BETWENDATE);
				if(jsonObject != null) {
					resultMap.put("assetDto", jsonObject);
					resultMap.put("activity", activity);
					return resultMap;
				}
			}
			activity.setBeginAt(DateUtil.strToDateLong(json.getVal().get(0)));
			activity.setEndAt(DateUtil.strToDateLong(json.getVal().get(1)));
		}

		//期望时间
		if(e.getTypeName().equals(EnrollingEnum.GUARANTEE_MUST_KEY.EYJCZSJ.getTypeName())){
			//校验时间展示
			if(updateType == null) {
				AssetDto jsonObject = fieldCheck(json, ENROLLING_FIELD_TYPE.DATETIME);
				if(jsonObject != null) {
					resultMap.put("assetDto", jsonObject);
					resultMap.put("activity", activity);
					return resultMap;
				}
			}

			activity.setExpireAt(DateUtil.strToDateLong(json.getVal().get(0)));
		}

		//展示图片
		if(e.getTypeName().equals(EnrollingEnum.GUARANTEE_MUST_KEY.IMAGES.getTypeName())){
			JSONObject jsonObject1 = getImages(json.getVal());
			activity.setExtra(jsonObject1);
		}
		//资产亮点
		if(e.getTypeName().equals(EnrollingEnum.GUARANTEE_MUST_KEY.DETAIL.getTypeName())){

			AssetDto jsonObject = fieldCheck(json, ENROLLING_FIELD_TYPE.ISBLANK);
			if(jsonObject != null) {
				resultMap.put("assetDto", jsonObject);
				resultMap.put("activity", activity);
				return resultMap;
			}

			activity.setBrightSpot(json.getVal().get(0));

			//activity.setDetail(json.getVal().get(0));
		}

		//项目联系人
		if(e.getTypeName().equals(EnrollingEnum.GUARANTEE_MUST_KEY.CONTACT_NAME.getTypeName())){
			AssetDto jsonObject = fieldCheck(json, ENROLLING_FIELD_TYPE.ISBLANK);
			if(jsonObject != null) {
				resultMap.put("assetDto", jsonObject);
				resultMap.put("activity", activity);
				return resultMap;
			}

			activity.setContactName(json.getVal().get(0));
		}
		//项目联系人号码
		if(e.getTypeName().equals(EnrollingEnum.GUARANTEE_MUST_KEY.CONTACT_PHONE.getTypeName())){
			AssetDto jsonObject = fieldCheck(json, ENROLLING_FIELD_TYPE.ISBLANK);
			if(jsonObject != null) {
				resultMap.put("assetDto", jsonObject);
				resultMap.put("activity", activity);
				return resultMap;
			}
			activity.setContactPhone(json.getVal().get(0));
		}



        //标的物属性
        if(e.getTypeName().equals(EnrollingEnum.GUARANTEE_MUST_KEY.BUS_TYPE_NAME.getTypeName())){

            busTypeNameList.add(json.getVal().get(0));
        }





        resultMap.put("activity", activity);

		return  resultMap;
	}

	/**
	 * 业务数据表里必填字段校验
	 * @param
	 * @param
	 */
	private AssetDto fieldCheck(AssetDto json, ENROLLING_FIELD_TYPE fieldType) {
		String valueOne = json.getVal().get(0);
		if(FIELD_TYPE.VARCHAR.getTypeName().equals(fieldType.getFieldType())) {
			if(StringUtils.isBlank(valueOne)) {
				json.setErrorMsg("不能为空");
				return json;
			}

			if(valueOne.length() > Integer.parseInt(fieldType.getFieldValue())) {
				json.setErrorMsg(fieldType.getErrorMsg());
				return json;
			}
		}else if(FIELD_TYPE.INT.getTypeName().equals(fieldType.getFieldType())) {
			if(StringUtils.isBlank(valueOne)) {
				json.setErrorMsg("不能为空");
				return json;
			}

			if(Integer.parseInt(valueOne) > Integer.parseInt(fieldType.getFieldValue())) {
				json.setErrorMsg(fieldType.getErrorMsg());
				return json;
			}
		}else if(FIELD_TYPE.DATETIME.getTypeName().equals(fieldType.getFieldType())) {
			if(StringUtils.isNotBlank(valueOne)) {
				if(DateUtil.strToDateLong(valueOne.toString()).getTime()
						< System.currentTimeMillis()) {
					json.setErrorMsg(fieldType.getErrorMsg());
					return json;
				}
			}

		}else if(FIELD_TYPE.BETWENDATE.getTypeName().equals(fieldType.getFieldType())) {
			String valueTwo = json.getVal().get(1);
			if(StringUtils.isBlank(valueOne)) {
				json.setErrorMsg("不能为空");
				return json;
			}

			if(StringUtils.isBlank(valueTwo)) {
				json.setErrorMsg("不能为空");
				return json;
			}
			if(DateUtil.strToDateLong(valueOne.toString()).getTime()
					< System.currentTimeMillis()) {
				json.setErrorMsg(fieldType.getErrorMsg());
				return json;
			}

			if(DateUtil.strToDateLong(valueTwo.toString()).getTime()
					< System.currentTimeMillis()) {
				json.setErrorMsg(fieldType.getErrorMsg());
				return json;
			}
			if(DateUtil.strToDateLong(valueOne.toString()).getTime() > DateUtil.strToDateLong(valueTwo.toString()).getTime()) {
				json.setErrorMsg(fieldType.getErrorMsg());
				return json;
			}
		}else if(FIELD_TYPE.ISBLANK.getTypeName().equals(fieldType.getFieldType())) {
			if(StringUtils.isNotBlank(valueOne)) {
				if(valueOne.length() > Integer.parseInt(fieldType.getFieldValue())) {
					json.setErrorMsg(fieldType.getErrorMsg());
					return json;
				}
			}
		}
//		}else if(FIELD_TYPE.DECIMAL.getTypeName().equals(fieldType.getFieldType())) {
//
//			String regex = "^[1-9][0-9]*(\\.[0-9]{1,"+fieldType.getFieldValue()+"})?$";
////			if() {
////
////			}
//		}
		return null;
	}


	private String getCode(String mark) {

		return RandomNumberGenerator.generatorMarkEnrollingCode(mark,3);
	}

	//暂时以字符串为判断条件，后续根据不同的业务逻辑获取具体的环境
	private List<HomeEnrollingActivityVO> getHomeList(List<HomeEnrollingActivityVO> homeListVO) {
		for (HomeEnrollingActivityVO homeModel : homeListVO) {

			String endAt = homeModel.getEndAt();
			if(endAt != null && endAt.length() > 2) {
				endAt = endAt.substring(0, endAt.length() - 3);
			}

			if (EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(homeModel.getType())) {
				homeModel.setCityLabel(EnrollingEnum.ENROLLING_LABEL.LOCATION.getTypeName());
				homeModel.setDepositLabel(EnrollingEnum.ENROLLING_LABEL.MARGIN.getTypeName());
				homeModel.setMortgageLabel(null);
				homeModel.setMortgageValue(null);
			} else if (EnrollingEnum.ENROLLING_TYPE.PROPERTY_RIGHTS.getType().equals(homeModel.getType())) {
				homeModel.setCityName(null);
				homeModel.setDepositLabel(EnrollingEnum.ENROLLING_LABEL.DEBT_PRINCIPAL.getTypeName());
				homeModel.setDeposit(homeModel.getRefPrice());
				homeModel.setMortgageLabel(EnrollingEnum.ENROLLING_LABEL.MORTGAGE.getTypeName());
			} else if (EnrollingEnum.ENROLLING_TYPE.CREDITOR_RIGHTS.getType().equals(homeModel.getType())) {
				homeModel.setCityName(null);
				homeModel.setCityLabel(EnrollingEnum.ENROLLING_LABEL.LOCATION.getTypeName());
				homeModel.setDepositLabel(EnrollingEnum.ENROLLING_LABEL.REFERENCE_PRICE.getTypeName());
				homeModel.setDeposit(homeModel.getRefPrice());
				homeModel.setMortgageLabel(null);
			}
		}
		return homeListVO;
	}



	private List<PersonalEnrollingActivityVO> getPersionalList(List<HomeEnrollingActivityVO> eaList) {

		List<PersonalEnrollingActivityVO> newPersionalList = new ArrayList<PersonalEnrollingActivityVO>();
		for (HomeEnrollingActivityVO homeEnrollingVO: eaList) {
			PersonalEnrollingActivityVO persioniActivity = new PersonalEnrollingActivityVO();
			String depositNum = homeEnrollingVO.getDepositNum();
			persioniActivity.setDepositNum(depositNum == null ? "0" : depositNum);
			persioniActivity.setImages(homeEnrollingVO.getImages());
			persioniActivity.setName(homeEnrollingVO.getName());
			persioniActivity.setId(homeEnrollingVO.getId());
			persioniActivity.setStatus(homeEnrollingVO.getStatus());
			persioniActivity.setStatusDesc(homeEnrollingVO.getStatusName());
			persioniActivity.setType(homeEnrollingVO.getType());
			persioniActivity.setTypeDesc(homeEnrollingVO.getTypeName());
			persioniActivity.setBeginAt((homeEnrollingVO.getBeginAt()));
			persioniActivity.setEndAt(homeEnrollingVO.getEndAt());
			persioniActivity.setFocusNum(homeEnrollingVO.getFocusNum());
			persioniActivity.setBrowseNum(homeEnrollingVO.getBrowseNum());
			persioniActivity.setCityName(homeEnrollingVO.getCityName());
			persioniActivity.setRejectReason(homeEnrollingVO.getRejectReason());
			persioniActivity.setAmount(EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(homeEnrollingVO.getType())?homeEnrollingVO.getDeposit():homeEnrollingVO.getRefPrice());
			persioniActivity.setAmountDesc(EnrollingEnum.SHOW_AMOUNT.getCode(homeEnrollingVO.getType()));
			persioniActivity.setDepositStatus(homeEnrollingVO.getDepositStatus());
			persioniActivity.setDepositStatusDesc(homeEnrollingVO.getType(), homeEnrollingVO.getDepositStatus());
			persioniActivity.setProof(homeEnrollingVO.getProof());
			persioniActivity.setEndPayTime(homeEnrollingVO.getEndPayTime());
			persioniActivity.setCreatedAt(homeEnrollingVO.getDepositTime());
            persioniActivity.setShopId(homeEnrollingVO.getResourceId());
			persioniActivity.setShopName(StringEscapeUtils.unescapeJava(homeEnrollingVO.getShopName()));

			persioniActivity.setEndTimestamp(DateUtil.strToDateLong(homeEnrollingVO.getEndAt()).getTime());


			persioniActivity.setEnrollingAmount(EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(homeEnrollingVO.getType())?homeEnrollingVO.getEnrollingDeposit():homeEnrollingVO.getEnrollingRefPrice());

			newPersionalList.add(persioniActivity);
		}
		return newPersionalList;
	}

	/**
	 * 取消预招商提醒
	 */
	@Override
	public ResponseModel cancelMyRemind(activityIdTypeReq req) {
//		ActivityPersionDto params = new ActivityPersionDto();
//		BeanUtils.copyProperties(req, params);
		NotifyPartyEnrollingActivity noticeModel = notifyPartyEnrollingActivityService.getFilterModel(req);
		List<NotifyPartyEnrollingActivity> notifyList = notifyPartyEnrollingActivityService.getNotifyListByActivityId(Integer.parseInt(req.getActivityId()));
		if(noticeModel != null ) {
			notifyPartyEnrollingActivityService.deleteModel(String.valueOf(noticeModel.getId()));

			//提醒数量 -1
			EnrollingActivity activity = enrollingActivityService.getEnrollingActivityById(req.getActivityId());
			if(activity!=null){
				activity.setReminderNumber(activity.getReminderNumber()-1);
				enrollingActivityService.updateEnrollingActivityById(activity);

				//当只有一个人设置提醒的时候取消提醒redis缓存
				if(notifyList != null && notifyList.size() == 1) {
					stringRedisTemplate.delete(SystemConstant.ENROLLING_REMIND_KEY + activity.getId());//根据key删除缓存
				}
			}
		}


		return ResponseModel.succ();
	}


	@Override
	@Transactional
	public ResponseModel cancelMyFocus(activityIdTypeReq req) {
		FavoriteEnrollingActivity favoriteModel = favoriteEnrollingActivityService.getFilterModel(req);
		if(favoriteModel != null) {
			favoriteEnrollingActivityService.deleteModel(String.valueOf(favoriteModel.getId()));

			//主表的关注量 -1
			EnrollingActivity activity = enrollingActivityService.getEnrollingActivityById(req.getActivityId());
			if(activity!=null){
				activity.setFocusNumber(activity.getFocusNumber()-1);
				enrollingActivityService.updateEnrollingActivityById(activity);
			}
		}

		return ResponseModel.succ();
	}

	//个人中心获取申请列表
	@Override
	public ResponseModel applyActivityList(activityIdReq req) {

		ActivityIdReqDto dto = new ActivityIdReqDto();
		dto.setActivityId(req.getActivityId());
		dto.setPage(req.getPage());
		dto.setPerPage(req.getPerPage());

		PageInfo info = enrollingDepositService.getEnrollingDepositList(dto);

		List<EnrollingDepositListVo> list =info.getList();

		List<EnrollingApplyActivityListVo> respList = new ArrayList<>();
		PageSerializable resp = new PageSerializable();

		EnrollingActivity activity = enrollingActivityService.getEnrollingActivityById(req.getActivityId());


		int sequence= 1;
		for(EnrollingDepositListVo listVo : list){

			EnrollingApplyActivityListVo vo = new EnrollingApplyActivityListVo();

			vo.setSequence(String.valueOf(sequence));
			vo.setCreateAt(listVo.getCreatedAt());
			vo.setName(listVo.getUseName());
			vo.setAddress(listVo.getUseAddress());
			vo.setUserMobil(listVo.getUseMobile());
			vo.setComMobile(listVo.getComMobile());

			if(StringUtils.isEmpty(listVo.getUseName())){
				vo.setName(listVo.getComName());
				vo.setAddress(listVo.getComAddress());
			}

			sequence++;

			if(!EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(activity.getType())){
				vo.setName(listVo.getShowName());
			}
			respList.add(vo);
		}
		resp.setTotal(info.getTotal());
		resp.setList(respList);
		return ResponseModel.succ(resp);
	}

	@Override
	public ResponseModel saveProgress(saveProgress req) {
		EnrollingActivityProgress progress = new EnrollingActivityProgress();
		progress.setActivityId(Integer.valueOf(req.getActivityId()));
		progress.setTitle(req.getTitle());
		progress.setContent(req.getContent());
		progress.setCreatedAt(new Date());
		enrollingActivityProgressService.saveProgress(progress);
		return ResponseModel.succ();
	}

	@Override
	public ResponseModel saveResult(saveResult req) {

		ActivityIdReqDto reqDto = new ActivityIdReqDto();
		reqDto.setActivityId(req.getActivityId());

		EnrollingActivityResult result1 = enrollingActivityResultService.getResult(reqDto);
		if(result1== null){

			EnrollingActivityResult result = new EnrollingActivityResult();
			result.setActivityId(Integer.valueOf(req.getActivityId()));
			result.setName(req.getBuyer());
			result.setPrice(new BigDecimal(req.getDealPrice()));
			result.setNumber(req.getIdNumber());
			enrollingActivityResultService.saveResult(result);
		}else{
			result1.setActivityId(Integer.valueOf(req.getActivityId()));
			result1.setName(req.getBuyer());
			result1.setPrice(new BigDecimal(req.getDealPrice()));
			result1.setNumber(req.getIdNumber());
			enrollingActivityResultService.updateResult(result1);
		}


		return ResponseModel.succ();
	}

	@Override
	public ResponseModel resultInfo(activityIdTypeReq req) {

		ActivityIdReqDto dto = new ActivityIdReqDto();
		dto.setPage(req.getPage());
		dto.setPerPage(req.getPerPage());
		dto.setActivityId(req.getActivityId());
		EnrollingActivityResult result = enrollingActivityResultService.getResult(dto);

		if(result==null){
			return ResponseModel.succ();
		}
		EnrollingResultInfoVo vo = new EnrollingResultInfoVo();

		EnrollingActivityCommissionOrderCondition condition = new EnrollingActivityCommissionOrderCondition();
		condition.setActivityId(Integer.valueOf(req.getActivityId()));
		condition.setPartyId(Integer.valueOf(req.getPartyId()));
		condition.setSource(req.getSource());

		EnrollingActivityCommissionOrder commissionOrderVo =enrollingActivityCommissionOrderService.getCommissionOrder(condition);

		vo.setPayAmount("0");

		if(commissionOrderVo!=null){
			vo.setId(String.valueOf(commissionOrderVo.getId()));
			vo.setPayStatus(commissionOrderVo.getPaid()?"1":"0");
			vo.setPayAmount(commissionOrderVo.getAmount().toString());
		}

		vo.setBuyer(result.getName());
		vo.setDealPrice(result.getPrice().toString());
		vo.setIdNumber(result.getNumber());

		return ResponseModel.succ(vo);
	}

	@Override
	public ResponseModel payCommission(payCommission req) {
		//获取支付订单
		EnrollingActivityCommissionOrderCondition condition = new EnrollingActivityCommissionOrderCondition();
		condition.setActivityId(Integer.valueOf(req.getActivityId()));
		condition.setPartyId(Integer.valueOf(req.getPartyId()));
		condition.setSource(req.getSource());
		EnrollingActivityCommissionOrder commissionOrderVo =enrollingActivityCommissionOrderService.getCommissionOrder(condition);

		if(commissionOrderVo==null){
			return ResponseModel.fail("佣金订单未生成！");
		}

		if(commissionOrderVo.getPaid()){
			return ResponseModel.fail("佣金订单已支付！");
		}

		LockOrReleaseOrDirectReq payParam = new LockOrReleaseOrDirectReq();
		payParam.setPayMemCode(req.getMemCode());
		payParam.setPayMemName(req.getName());

		UnifiedPayReq unifiedPayReq = new UnifiedPayReq();
		unifiedPayReq.setAmount(commissionOrderVo.getAmount());
		unifiedPayReq.setBusId(String.valueOf(commissionOrderVo.getId()));
		unifiedPayReq.setPartyId(commissionOrderVo.getPartyId());
		unifiedPayReq.setPayType(PayEnums.PAY_TYPE.DIRECT_PAY.getType());
		unifiedPayReq.setPayBusCode(PayEnums.PAY_BUS_CODE.ENROLLING_COMMISSION_PAY.getType());
		unifiedPayReq.setPayTo(PayEnums.PAY_TO.PAY_TO_WEB.getType());
		unifiedPayReq.setLockTag(PayEnums.LOCK_TAG.DIRECT_PAY.getType());
		unifiedPayReq.setPayParam(payParam);
		UnifiedPayResp rep = payFacade.unifiedPay(unifiedPayReq);

		if(!rep.getCode().equals(PayResultEnums.PAY_NOTICE.getCode())){

			return ResponseModel.fail(rep.getDesc());
		}

		EnrollingPayCommissionVo vo = new EnrollingPayCommissionVo();
		vo.setUrl(rep.getUrl());
		vo.setParam(rep.getParam());

		return ResponseModel.succ(vo);
	}

	@Override
	public ResponseModel getProgressList(activityIdReq req) {

		ActivityIdReqDto dto = new ActivityIdReqDto();
		dto.setActivityId(req.getActivityId());
		dto.setPerPage(req.getPerPage());
		dto.setPage(req.getPage());
		PageInfo info = enrollingActivityProgressService.getProgressList(dto);


		List<EnrollingActivityProgress> list = info.getList();

		List<EnrollingProgressListVo> respList = new ArrayList<>();

		for(EnrollingActivityProgress progress :list){
			EnrollingProgressListVo vo = new EnrollingProgressListVo();

			vo.setContent(progress.getContent());
			vo.setTitle(progress.getTitle());
			vo.setCreateAt(DateUtil.dateToStrLong(progress.getCreatedAt()));
			respList.add(vo);
		}

		PageSerializable resp = new PageSerializable();

		resp.setList(respList);
		resp.setTotal(info.getTotal());
		return ResponseModel.succ(resp);
	}

	@Override
	public ResponseModel backOutActivity(activityIdReq req) {
		EnrollingActivity activity = new EnrollingActivity();

		activity.setId(Integer.valueOf(req.getActivityId()));
//		activity.setStatus(EnrollingEnum.STATUS.WITHDRAW.getType());
		enrollingActivityService.updateEnrollingActivityById(activity);
		return ResponseModel.succ();
	}

	//签署模板信息
	@Override
	public ResponseModel signContract(activityIdTypeReq req) {

		//根据合同号id 获取签署合同的id号
		EnrollingActivityContractCondition contractCondition = new EnrollingActivityContractCondition();
		contractCondition.setActivityId(Integer.valueOf(req.getActivityId()));

		EnrollingActivityContract contract = enrollingActivityContractService.getEnrollingActivityContract(contractCondition);

		if(contract==null){

			return ResponseModel.fail("未生成合同，请先生成合同信息");

		}

		//获取需要签章的信息
		EnrollingListReqDto dto = new EnrollingListReqDto();
		dto.setId(req.getActivityId());


		EnrollingSignVO enrollingSign =enrollingActivityService.getSignInfo(dto);


		List<FddSignInfo> sign_list = new ArrayList<>();

		FddSignInfo seller = new FddSignInfo();//委托人的信息
		seller.setSign_role(FddEnums.SING_ROLE_TYPE.SELLER.getType());
		seller.setMem_role(StringUtils.isEmpty(enrollingSign.getComFddId())?"1":"2");
		seller.setFdd_id(StringUtils.isEmpty(enrollingSign.getComFddId())?enrollingSign.getUserFddId():enrollingSign.getComFddId());
		seller.setIs_auto(FddEnums.SING_AUTO.NOT_AUTO.getType());
		seller.setParty_id(req.getPartyId());
		ExtSignContractReq signReq = new ExtSignContractReq();

		if(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType()==enrollingSign.getThirdPartyStatus()){

			seller.setMem_role("2");
			seller.setFdd_id(enrollingSign.getUserAgencyFddId());
		}

		sign_list.add(seller);


		if(EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(enrollingSign.getType())){

			signReq.setType(FddEnums.SING_TYPE.ENROLLING_DELEGATION.getType());//预招商委托协议
			FddSignInfo agency = new FddSignInfo();//送拍机构信息

			agency.setSign_role(FddEnums.SING_ROLE_TYPE.AGENCY.getType());
			agency.setMem_role("2");
			agency.setFdd_id(enrollingSign.getAgencyFddId());
			agency.setIs_auto(FddEnums.SING_AUTO.AUTO.getType());

			sign_list.add(agency);

		}else if(EnrollingEnum.ENROLLING_TYPE.PROPERTY_RIGHTS.getType().equals(enrollingSign.getType())){

			signReq.setType(FddEnums.SING_TYPE.ZHAIQUAN_DELEGATION.getType());//债权

		}else{

			signReq.setType(FddEnums.SING_TYPE.WUQUAN_DELEGATION.getType());//物权预招商

		}

		signReq.setContract_id(contract.getContractId());
		signReq.setActivity_id(req.getActivityId());
		signReq.setSign_list(sign_list);

		ExtSignContractResp rep =fddSignatureFacade.extSignContract(signReq);

		//签章失败返回
		if(!rep.getCode().equals(ApiCallResult.SUCCESS.getCode())){
			ResponseModel.fail(rep.getCode(),rep.getDesc());
		}

		EnrollingSignContractVo vo = new EnrollingSignContractVo();

		vo.setUrl(rep.getUrl());
		vo.setParam(rep.getParam());

		return ResponseModel.succ(vo);
	}



	public String formatKey(String key) {
		Pattern pattern = Pattern.compile("[\\d]");
		Matcher matcher = pattern.matcher(key);
		return (matcher.replaceAll("").trim());
	}


	@Override
	@Transactional
	public ResponseModel cancelMyFocusList(String[] req, Integer partyId, Integer accountId) {

		List<FavoriteEnrollingActivity> focusActivityList = favoriteEnrollingActivityService.getFocusActivityList(partyId, accountId);
		List<String> focusList = new ArrayList<String>();

		//预招商的id
		List<String> activityIdList = new ArrayList<>();


		for (String activityId : req) {
			for (FavoriteEnrollingActivity favoriteEnrollingActivity : focusActivityList) {
				if(activityId.equals(String.valueOf(favoriteEnrollingActivity.getActivityId()))) {
					focusList.add(String.valueOf(favoriteEnrollingActivity.getId()));
					activityIdList.add(String.valueOf(favoriteEnrollingActivity.getActivityId()));
				}
			}
		}

		if(focusList.size() <= 0) {
			return ResponseModel.fail("数据有误");
		}
		favoriteEnrollingActivityService.cancelMyFocusList(focusList);
		//更新取消关注的 标的信息
		enrollingActivityService.batchUpdateFocusNum(activityIdList);

		return ResponseModel.succ();
	}



	/**
	 *
	 *个人中心 预招商详情
	 * @param
	 */
	@Override
	public ResponseModel myActivityInfo(activityIdTypeReq req) {
		EnrollingActivityDataCondition dataCondition = new EnrollingActivityDataCondition();
		dataCondition.setActivityId(Integer.valueOf(req.getActivityId()));
		EnrollingActivityData activityData = enrollingActivityDataService.getActivityData(dataCondition);

		//查询主表信息
		EnrollingActivity activity = enrollingActivityService.getEnrollingActivityById(req.getActivityId());

		if(activityData==null&&activity==null){
			return ResponseModel.fail("未找到该活动信息！");
		}

		if(activity.getThirdPartyStatus()==3||activity.getThirdPartyStatus()==4){
			return ResponseModel.succ();
		}

		Map<String, Object> map = new HashMap<>();
		map.put("id", activity.getId());
		map.put("activityId", activity.getId());

		JSONObject content = activityData.getContent();
		JSONArray templateDate = content.getJSONArray("templateDate");
		JSONArray newData = new JSONArray();

		//过滤分组
		Set<Integer> titles = new HashSet<>();
		Map<Integer, Object> maps = new HashMap<>();

		for (int i = 0; i < templateDate.size(); i++) {
			JSONObject jsonObject = templateDate.getJSONObject(i);
			Integer title = jsonObject.getInteger("title");
			if (titles.add(title)) {
				TAssetFieldGroup tAssetFieldGroup = tAssetFieldGroupDao.selectById(title);
				if (tAssetFieldGroup != null) {
					maps.put(title, tAssetFieldGroup.getName());
				}
			}
		}

		JSONArray groupObject = new JSONArray();
		for (Integer title : titles) {
			JSONArray groupData = new JSONArray();
			for (int i = 0; i < templateDate.size(); i++) {
				JSONObject jsonObject = templateDate.getJSONObject(i);
				Integer tit = jsonObject.getInteger("title");
				if (tit.equals(title)) {
					jsonObject.put("titleName", maps.get(title));
					Integer titleSubset = jsonObject.getInteger("titleSubset");
					JSONArray val = jsonObject.getJSONArray("val");
					if (val.isEmpty()) {
						continue;
					}
					if (StringUtils.isEmpty(val.getString(0))) {
						continue;
					}

					String keyStr = jsonObject.getString("key");
					String key = formatKey(keyStr);


					String type = jsonObject.getString("type");
					boolean skipKey = ("SELECT".equals(type) || "DOWNSELECT".equals(type));
					if (skipKey) {
						String string = val.getString(0);
						if ("请选择".equals(string)) {
							continue;
						}
					}

					if (!skipKey) {
						TAssetField field = tAssetFieldDao.findUnit(key);
						if (field == null) {
							continue;
						}
						Integer fmNum = field.getFmNum();
						//格式化数字
						fmNumber(jsonObject, fmNum);

						String unit = field.getUnit();
						if (unit != null) {
							JSONArray val1 = jsonObject.getJSONArray("val");
							JSONArray val2 = new JSONArray();
							for (int j = 0; j < val1.size(); j++) {
								String string = val1.getString(j);
								val2.add(string + " "+unit);
							}
							jsonObject.put("val", val2);
						}

						jsonObject.put("unit", unit);  //设置单位
					}

					if (titleSubset != null) {
						TAssetFieldModel tAssetFieldModel = assetFieldModelDao.selectById(titleSubset);
						if (tAssetFieldModel != null) {
							jsonObject.put("titleSubsetName", tAssetFieldModel.getModelTitle());
						}
					}
					groupData.add(jsonObject);
				}
			}

			//将大组字段在进行分析是否可扩展
			Set<Integer> grads = new HashSet<>();
			JSONArray gradArray = new JSONArray();
			for (int i = 0; i < groupData.size(); i++) {
				JSONObject jsonObject = groupData.getJSONObject(i);
				Integer grading = jsonObject.getInteger("grading");
				grads.add(grading);
			}

			for (Integer grading : grads) {
				JSONArray gradData = new JSONArray();
				for (int i = 0; i < groupData.size(); i++) {
					JSONObject jsonObject = groupData.getJSONObject(i);
					Integer grad = jsonObject.getInteger("grading");
					if (grading.equals(grad)) {
						gradData.add(jsonObject);
					}
				}
				gradArray.add(gradData);
			}

			JSONArray gradArrayJSON = new JSONArray();
			for (int m = 0; m < gradArray.size(); m++) {
				JSONObject gradJSON = new JSONObject();
				JSONArray gradArrayJSONArray = gradArray.getJSONArray(m);
				JSONArray NoSubArray = new JSONArray();
				Set<Integer> subInt = new HashSet<>();
				for (int i = 0; i < gradArrayJSONArray.size(); i++) {
					JSONObject jsonObject = gradArrayJSONArray.getJSONObject(i);
					Integer titleSubset = jsonObject.getInteger("titleSubset");
					if (titleSubset == null) {
						NoSubArray.add(jsonObject);
					} else {
						subInt.add(titleSubset);
					}
				}
				JSONArray array = new JSONArray();
				for (Integer Subset : subInt) {
//                    JSONObject subJSON = new JSONObject();
					JSONArray subData = new JSONArray();
					for (int i = 0; i < gradArrayJSONArray.size(); i++) {
						JSONObject jsonObject = gradArrayJSONArray.getJSONObject(i);
						Integer titleSubset = jsonObject.getInteger("titleSubset");
						if (Subset.equals(titleSubset)) {
							subData.add(jsonObject);
						}
					}
					Set<Integer> gradSecondInts = new HashSet<>();
					Map<Integer, Object> map1 = new HashMap<>();
					for (int i = 0; i < subData.size(); i++) {
						JSONObject jsonObject = subData.getJSONObject(i);
						Integer gradSecond = jsonObject.getInteger("gradingSecond");
						String titleSubsetName = jsonObject.getString("titleSubsetName");
						gradSecondInts.add(gradSecond);
						map1.put(gradSecond, titleSubsetName);
					}

					for (Integer gradSecondInt : gradSecondInts) {
						JSONObject json = new JSONObject();
						JSONArray jsonArray = new JSONArray();
						for (int i = 0; i < subData.size(); i++) {
							JSONObject jsonObject = subData.getJSONObject(i);
							Integer gradSecond = jsonObject.getInteger("gradingSecond");
							if (gradSecond.equals(gradSecondInt)) {
								jsonArray.add(jsonObject);
							}
						}
						json.put("list", jsonArray);
						if (gradSecondInt == 0) {
							json.put("title", map1.get(gradSecondInt));
						} else {
							json.put("title", map1.get(gradSecondInt) + "" + (gradSecondInt + 1));
						}
						array.add(json);
					}
				}

				if (m == 0) {
					gradJSON.put("flag", true);
				} else {
					gradJSON.put("flag", false);
				}
				gradJSON.put("kong", NoSubArray);
				gradJSON.put("list", array);
				if (m == 0) {
					gradJSON.put("title", maps.get(title));
				} else {
					gradJSON.put("title", maps.get(title) + "" + (m + 1));
				}

				if (gradJSON.getString("title").contains("标的信息")) {
					gradJSON.put("status", true);
				} else {
					gradJSON.put("status", false);
				}

				gradArrayJSON.add(gradJSON);
			}

			if (gradArrayJSON.isEmpty()) {
				continue;
			}
			groupObject.add(gradArrayJSON);
		}

		content.put("templateDate", newData);
		content.put("groups", groupObject);
		map.put("files", content);

		/*List<Map<String ,Object>>  assetList;

		//当data表无数据是 那 activity表的数据组装
		if(activityData==null){
			assetList = getListByActivity(activity);
		}else{
			//获取全部的详情信息
			JSONObject json = activityData.getContent();

			//数据转换
			assetList = (List<Map<String ,Object>>) json.get("templateDate");


		}

		//数据过滤
		assetList = filterList(assetList);

		//数据添加
		if(activity != null &&EnrollingEnum.REQ_TYPE.ADMIN.getType().equals(req.getReqType())&&EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(activity.getType())) {
			assetList = addList(assetList, activity);
		}
*/


		return ResponseModel.succ(map);
	}


	/**
	 *
	 *获取标的信息
	 */
	@Override
	public ResponseModel getMatterInfo(activityIdTypeReq req) {
		EnrollingActivityDataCondition dataCondition = new EnrollingActivityDataCondition();
		dataCondition.setActivityId(Integer.valueOf(req.getActivityId()));
		EnrollingActivityData activityData = enrollingActivityDataService.getActivityData(dataCondition);

		//查询主表信息
		EnrollingActivity activity = enrollingActivityService.getEnrollingActivityById(req.getActivityId());

		if(activityData==null&&activity==null){
			return ResponseModel.fail("未找到该活动信息！");
		}

		Map<String, Object> map = new HashMap<>();
		map.put("seeStatus",req.getSeeStatus());

		//当data表无数据是 那 activity表的数据组装
		if(activityData==null){
			return ResponseModel.succ(map);
		}else{
			//获取全部的详情信息
			JSONObject content = activityData.getContent();

			map.put("id", activity.getId());
			map.put("activityId", activity.getId());

			JSONArray templateDate = content.getJSONArray("templateDate");
			JSONArray newData = new JSONArray();

			//过滤分组
			Set<Integer> titles = new TreeSet<>();
			Map<Integer, Object> maps = new HashMap<>();

			for (int i = 0; i < templateDate.size(); i++) {
				JSONObject jsonObject = templateDate.getJSONObject(i);
				Integer title = jsonObject.getInteger("title");
				if (titles.add(title)) {
					TAssetFieldGroup tAssetFieldGroup = tAssetFieldGroupDao.selectById(title);
					if (tAssetFieldGroup != null) {
						maps.put(title, tAssetFieldGroup.getName());
					}
				}
			}

			JSONArray groupObject = new JSONArray();
			for (Integer title : titles) {
				JSONArray groupData = new JSONArray();
				for (int i = 0; i < templateDate.size(); i++) {
					JSONObject jsonObject = templateDate.getJSONObject(i);
					Integer tit = jsonObject.getInteger("title");
					if (tit.equals(title)) {
						jsonObject.put("titleName", maps.get(title));
						Integer titleSubset = jsonObject.getInteger("titleSubset");
						JSONArray val = jsonObject.getJSONArray("val");
						if (val.isEmpty()) {
							continue;
						}
						if (StringUtils.isEmpty(val.getString(0))) {
							continue;
						}

						String name = jsonObject.getString("name");
						String type = jsonObject.getString("type");
						if (name.contains("地址") && type.contains("TEXT")) {
							jsonObject.put("type", "MAP");
						}
						String keyStr = jsonObject.getString("key");
						String key = formatKey(keyStr);
						boolean skipKey = ("SELECT".equals(type) || "DOWNSELECT".equals(type));
						if (skipKey) {
							String string = val.getString(0);
							if ("请选择".equals(string)) {
								continue;
							}
						}


						if (!skipKey) {
							TAssetField field = tAssetFieldDao.findUnit(key);
							if (field == null) {
								continue;
							}

							//判断前端是否过滤
							if (field.getFrontShow()) {
								continue;
							}
							String unit = field.getUnit();

							Integer fmNum = field.getFmNum();
							//格式化数字
							fmNumber(jsonObject, fmNum);


							if (unit != null) {
								JSONArray val1 = jsonObject.getJSONArray("val");
								JSONArray val2 = new JSONArray();
								for (int j = 0; j < val1.size(); j++) {
									String string = val1.getString(j);
									val2.add(string +" "+unit);
								}
								jsonObject.put("val", val2);
							}

							jsonObject.put("unit", unit);  //设置单位

						}

						if (titleSubset != null) {
							TAssetFieldModel tAssetFieldModel = assetFieldModelDao.selectById(titleSubset);
							if (tAssetFieldModel != null) {
								jsonObject.put("titleSubsetName", tAssetFieldModel.getModelTitle());
							}
						}
						groupData.add(jsonObject);
					}
				}

				//将大组字段在进行分析是否可扩展
				Set<Integer> grads = new HashSet<>();
				JSONArray gradArray = new JSONArray();
				for (int i = 0; i < groupData.size(); i++) {
					JSONObject jsonObject = groupData.getJSONObject(i);
					Integer grading = jsonObject.getInteger("grading");
					grads.add(grading);
				}

				for (Integer grading : grads) {
					JSONArray gradData = new JSONArray();
					for (int i = 0; i < groupData.size(); i++) {
						JSONObject jsonObject = groupData.getJSONObject(i);
						Integer grad = jsonObject.getInteger("grading");
						if (grading.equals(grad)) {
							gradData.add(jsonObject);
						}
					}
					gradArray.add(gradData);
				}

				JSONArray gradArrayJSON = new JSONArray();
				for (int m = 0; m < gradArray.size(); m++) {
					JSONObject gradJSON = new JSONObject();
					JSONArray gradArrayJSONArray = gradArray.getJSONArray(m);
					JSONArray NoSubArray = new JSONArray();
					Set<Integer> subInt = new HashSet<>();
					for (int i = 0; i < gradArrayJSONArray.size(); i++) {
						JSONObject jsonObject = gradArrayJSONArray.getJSONObject(i);
						Integer titleSubset = jsonObject.getInteger("titleSubset");
						if (titleSubset == null) {
							NoSubArray.add(jsonObject);
						} else {
							subInt.add(titleSubset);
						}
					}
					JSONArray array = new JSONArray();
					for (Integer Subset : subInt) {
//                    JSONObject subJSON = new JSONObject();
						JSONArray subData = new JSONArray();
						for (int i = 0; i < gradArrayJSONArray.size(); i++) {
							JSONObject jsonObject = gradArrayJSONArray.getJSONObject(i);
							Integer titleSubset = jsonObject.getInteger("titleSubset");
							if (Subset.equals(titleSubset)) {
								subData.add(jsonObject);
							}
						}
						Set<Integer> gradSecondInts = new HashSet<>();
						Map<Integer, Object> map1 = new HashMap<>();
						for (int i = 0; i < subData.size(); i++) {
							JSONObject jsonObject = subData.getJSONObject(i);
							Integer gradSecond = jsonObject.getInteger("gradingSecond");
							String titleSubsetName = jsonObject.getString("titleSubsetName");
							gradSecondInts.add(gradSecond);
							map1.put(gradSecond, titleSubsetName);
						}

						for (Integer gradSecondInt : gradSecondInts) {
							JSONObject json = new JSONObject();
							JSONArray jsonArray = new JSONArray();
							for (int i = 0; i < subData.size(); i++) {
								JSONObject jsonObject = subData.getJSONObject(i);
								Integer gradSecond = jsonObject.getInteger("gradingSecond");
								if (gradSecond.equals(gradSecondInt)) {
									jsonArray.add(jsonObject);
								}
							}
							json.put("list", jsonArray);
							if (gradSecondInt == 0) {
								json.put("title", map1.get(gradSecondInt));
							} else {
								json.put("title", map1.get(gradSecondInt) + "" + (gradSecondInt + 1));
							}
							array.add(json);
						}
					}

					if (m == 0) {
						gradJSON.put("flag", true);
					} else {
						gradJSON.put("flag", false);
					}
					gradJSON.put("kong", NoSubArray);
					gradJSON.put("list", array);
					if (m == 0) {
						gradJSON.put("title", maps.get(title));
					} else {
						gradJSON.put("title", maps.get(title) + "" + (m + 1));
					}

					if (gradJSON.getString("title").contains("招商信息")||gradJSON.getString("title").contains("标的信息")||gradJSON.getString("title").contains("招商活动信息")||gradJSON.getString("title").contains("文件上传")) {
						gradArrayJSON.add(gradJSON);
					}
				}

				if (gradArrayJSON.isEmpty()) {
					continue;
				}
				groupObject.add(gradArrayJSON);
			}

			content.put("templateDate", newData);
			content.put("groups", groupObject);
			map.put("files", content);

		}

		return ResponseModel.succ(map);
	}








	public void fmNumber(JSONObject jsonObject, Integer fmNum) {
		if (fmNum != null && fmNum == 1) {
			JSONArray val1 = jsonObject.getJSONArray("val");
			JSONArray val2 = new JSONArray();
			for (int j = 0; j < val1.size(); j++) {
				String string = val1.getString(j);
				if (NumberValidationUtils.isNumeric(string)) {
					val2.add(NumberValidationUtils.formatPrice(string));
				} else {
					val2.add(string);
				}
			}
			jsonObject.put("val", val2);
		}
	}



	/**
	 *
	 *签章回调信息
	 */
	@Override
	@Transactional
	public ResponseModel signCallBack(signCallBack req) {

		if(ApiCallResult.SUCCESS.getCode().equals(req.getStatus())){
			//获取签章信息
			EnrollingActivity activity = enrollingActivityService.getEnrollingActivityById(req.getActivityId());

			activity.setStatus(EnrollingEnum.STATUS.ONLINE.getType());
			String amount = getPayAmount(activity.getType());

			//获取是否需要支付
			if(!checkNotPay(activity.getPartyId())&&StringUtils.isNotEmpty(amount)){
				activity.setStatus(EnrollingEnum.STATUS.WAIT_PAY.getType());
				activity.setReleaseAmount(new BigDecimal(amount));
				activity.setEndPayTime(DateUtil.nextMinute(gatewayProperties.getEnrollingPayMinute()==null?24*60:gatewayProperties.getEnrollingPayMinute()));//生成发布支付时间
				//设置支付过期时间
				setPayOverDue(activity);
			}

			enrollingActivityService.updateEnrollingActivityById(activity);


			//修改签章状态
			EnrollingActivityContractCondition contractCondition = new EnrollingActivityContractCondition();
			contractCondition.setActivityId(Integer.valueOf(req.getActivityId()));
			EnrollingActivityContract contract = enrollingActivityContractService.getEnrollingActivityContract(contractCondition);

			if(contract==null){
				return  ResponseModel.fail("合同为空！");
			}
			contract.setSigned(true);
			contract.setAllSigned(true);

			enrollingActivityContractService.updateEnrollingActivityContract(contract);


		}

		return ResponseModel.succ();
	}

	private void setPayOverDue(EnrollingActivity activity) {

		try {
			//支付过期时间为一天
			long timeout = (activity.getEndPayTime().getTime() - System.currentTimeMillis()) / 1000;

			//stringRedisTemplate.opsForValue().set(SystemConstant.ENROLLING_SIGN_UP_KEY + params.getId(), "finish", timeout, TimeUnit.SECONDS);
			mqSender.enrollingEndPayEnqueue(activity.getId() + "", timeout);
			log.info("已经开始设置支付超时时间：" + timeout);

		}catch (Exception e){

			exceptionEmailService.sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.ENROLLING,"设置支付超时时间",JSON.toJSONString(activity),exceptionEmailService.exceptionToStr(e));
			log.error("预招商设置过期时间异常，异常信息为：{}",e);
		}
	}

	private ArrayList<Map<String,Object>> filterMatterInfo(List<Map<String,Object>> assetList) {

		ArrayList<Map<String,Object>> matterInfo = new ArrayList<>();

		for(Map<String,Object> dto :assetList){
			if("24".equals(dto.get("title").toString())){
				dto.put("title","标的信息");
				matterInfo.add(dto);
			}

		}

		return matterInfo;
	}

	private List<Map<String ,Object>> getListByActivity(EnrollingActivity activity) {
		List<Map<String ,Object>>  assetList = new ArrayList<>();
		if(EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(activity.getType())){
			assetList = setAssetListParams("招商名名称",activity.getName(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("活动编号",activity.getCode(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("活动状态",activity.getStatus(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("委托方","上海百昌",EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("委托方联系方式",activity.getContactPhone(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("委托方联系人",activity.getContactName(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("送拍时间",DateUtil.dateToStrLong(activity.getCreatedAt()),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("送拍机构","上海百昌",EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("所属城市",activity.getCityId(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("市场参考价",activity.getRefPrice().toString(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("保证金",activity.getDeposit().toString(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setImgListParams("资产图片",activity.getExtra().toString(),EnrollingEnum.INPUT_TYPE.FILEIMG.getType(),assetList);
			assetList = setAssetListParams("资产亮点",activity.getDetail(),EnrollingEnum.INPUT_TYPE.TEXTAREA.getType(),assetList);

		}else if (EnrollingEnum.ENROLLING_TYPE.PROPERTY_RIGHTS.getType().equals(activity.getType())){

			assetList = setAssetListParams("招商名名称",activity.getName(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("活动编号",activity.getCode(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("活动状态",activity.getStatus(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("委托方联系方式",activity.getContactPhone(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("委托方联系人",activity.getContactName(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("送拍时间",DateUtil.dateToStrLong(activity.getCreatedAt()),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("所属城市",activity.getCityId(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("债权本金",activity.getRefPrice().toString(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("债权利息",activity.getDeposit().toString(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setImgListParams("资产图片",activity.getExtra().toString(),EnrollingEnum.INPUT_TYPE.FILEIMG.getType(),assetList);
			assetList = setAssetListParams("资产亮点",activity.getDetail(),EnrollingEnum.INPUT_TYPE.TEXTAREA.getType(),assetList);
		}else{
			assetList = setAssetListParams("招商名名称",activity.getName(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("活动编号",activity.getCode(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("活动状态",activity.getStatus(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("委托方联系方式",activity.getContactPhone(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("委托方联系人",activity.getContactName(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("送拍时间",DateUtil.dateToStrLong(activity.getCreatedAt()),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("所属城市",activity.getCityId(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setAssetListParams("市场参考价",activity.getRefPrice().toString(),EnrollingEnum.INPUT_TYPE.TEXT.getType(),assetList);
			assetList = setImgListParams("资产图片",activity.getExtra().toString(),EnrollingEnum.INPUT_TYPE.FILEIMG.getType(),assetList);
			assetList = setAssetListParams("资产亮点",activity.getDetail(),EnrollingEnum.INPUT_TYPE.TEXTAREA.getType(),assetList);
		}



		return assetList;
	}

	private List<Map<String ,Object>> setImgListParams(String key, String img, String type, List<Map<String ,Object>> assetList) {

		AssetDto dto = new AssetDto();
		dto.setType(type);
		dto.setName(key);
		JSONObject obj =JSON.parseObject(img) ;
		List<String> valList = new ArrayList<>();

		List<String> imgList = ((List)obj.get("images"));

		for(String i :imgList){
			JSONObject json = new JSONObject();
			json.put("name","详情图片");
			json.put("url",i);
			valList.add(json.toJSONString());
		}
		dto.setVal(valList);
		return assetList;
	}

	/**
	 *
	 *添加参数
	 */
	private List<Map<String ,Object>> setAssetListParams(String key, String val,String type, List<Map<String ,Object>> assetList) {

		List<String> valList = new ArrayList<>();
		valList.add(val);
		Map<String ,Object> dto = new HashMap<>();


		dto.put("name",key);
		dto.put("val",valList);
		dto.put("type",type);
		assetList.add(dto);
		return assetList;
	}

	/**
	 *
	 *添加详情展示的信息
	 */
	private List<Map<String ,Object>> addList(List<Map<String ,Object>> assetList, EnrollingActivity activity) {
		//机构后台加上佣金比例字段
		BigDecimal commissionPercent = activity.getCommissionPercent();
		assetList = setAssetListParams("佣金比例", commissionPercent == null? null:commissionPercent.toString(), EnrollingEnum.INPUT_TYPE.TEXT.getType(), assetList);
		return assetList;
	}

	/**
	 *
	 *过滤详情列表
	 */
	private List<Map<String ,Object>> filterList(List<Map<String ,Object>> assetList) {

		List<Map<String ,Object>> newList = new ArrayList<>();

		//最后显示的数据
		List<Map<String ,Object>> lastList = new ArrayList<>();

		for(int i=0;i<assetList.size();i++){

			//过滤掉标的信息 值为空的数据
			if(!"24".equals(assetList.get(i).get("title"))&&!EnrollingEnum.INPUT_TYPE.FILEIMG.getType().equals(assetList.get(i).get("type"))&&!EnrollingEnum.INPUT_TYPE.TEXTAREA.getType().equals(assetList.get(i).get("type"))&&valNotNull(assetList.get(i))){
				newList.add(assetList.get(i));
			}
			if((EnrollingEnum.INPUT_TYPE.FILEIMG.getType().equals(assetList.get(i).get("type"))||EnrollingEnum.INPUT_TYPE.TEXTAREA.getType().equals(assetList.get(i).get("type")))&&!"24".equals(assetList.get(i).get("title"))){

				lastList.add(assetList.get(i));
			}

		}
		newList.addAll(lastList);


		return newList;

	}

	//判断值是否为空
	private boolean valNotNull(Map<String,Object> stringObjectMap) {

		List val = (List)stringObjectMap.get("val");

		if(val==null||val.size()==0||StringUtils.isEmpty(val.get(0).toString())){
			return false;
		}

		return true;
	}


	@Override
	public void updateActivityStatus(Integer activityId) {

		try {
			EnrollingActivity enrollingActivity = enrollingActivityService.getEnrollingActivityById(String.valueOf(activityId));
			//当订单不为空时
            if(enrollingActivity != null&&!EnrollingEnum.STATUS.NOT_PAY.getType().equals(enrollingActivity.getStatus())){
                //正常状态下订单结束
                if(EnrollingEnum.STATUS.ONLINE.getType().equals(enrollingActivity.getStatus())){
                    enrollingActivity.setStatus(EnrollingEnum.STATUS.FINISHED.getType());
                    enrollingActivityService.updateEnrollingActivityById(enrollingActivity);
                    //发送短信
                    if(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.WEB.getType()==enrollingActivity.getThirdPartyStatus()){
                        sendEndSms(enrollingActivity);
                    }
                    //非正常订单下 直接弄为 平台不可见
                }else{
                    enrollingActivity.setVisibilityLevel(EnrollingEnum.VISIBILITY_LEVEL.PLATFORM_INVISIBLE.getType());
                    enrollingActivityService.updateEnrollingActivityById(enrollingActivity);
                }
            }

		}catch (Exception e){

			log.error("结束异常预招商活动异常，异常信息为:{}",e);
		}

	}

	private void sendEndSms(EnrollingActivity enrollingActivity) {
		FAliSmsSendReq req = new FAliSmsSendReq();

		try {

			AccountBaseDto baseDto = accountService.getAccountBaseByPartyId(enrollingActivity.getPartyId());
			req.setTemplateCode(AliSmsTemplateEnums.ACTIVITY_END.getCode());
			req.setPhoneNumber(baseDto.getMobile());
			Map<String, Object> params = new HashMap<>();
			params.put("enrolling_code",enrollingActivity.getCode());
			req.setTemplateParam(JSON.toJSONString(params));
			gatewayMqSender.sendSms(req);

		}catch (Exception e){

			exceptionEmailService.sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.ENROLLING,"预招商活动结束发送短信",JSON.toJSONString(enrollingActivity),exceptionEmailService.exceptionToStr(e));

			log.error("招商结束发送短信异常，异常信息为：{}",e);
		}

	}

	@Override
	public ResponseModel getSubmitOrderStatus(submitOrder req) {

		EnrollingOrderStatusVo vo = new EnrollingOrderStatusVo();
		vo.setPayStatus(PayResultEnums.NOTPAY.getCode());


		//查询订单看看是否已经主动回调了
		EnrollingSubmitOrderCondition condition = new EnrollingSubmitOrderCondition();
		condition.setOrderNum(req.getOrderNum());

		EnrollingSubmitOrder order = enrollingSubmitOrderService.getSubmitOrder(condition);
		if(order==null){
			return ResponseModel.fail();
		}

		if(EnrollingEnum.ORDER_STATUS.PAY.getType().equals(order.getStatus())){

			vo.setPayStatus(PayResultEnums.PAY_SUCCESS.getCode());


			return ResponseModel.succ(vo);
		}

		//调用支付统一查询接口
		UnifiedQueryReq queryReq = new UnifiedQueryReq();

		queryReq.setPayType(PayEnums.PAY_TYPE.SCAN_PAY.getType());
		queryReq.setPayOrder(req.getOrderNum());

		UnifiedPayResp payResp = payFacade.unifiedPayQuery(queryReq);
		//支付成功更新订单表 以及 状态
		if(PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode())){
			order.setStatus(EnrollingEnum.ORDER_STATUS.PAY.getType());
			order.setUpdateAt(new Date());
			vo.setPayStatus(PayResultEnums.PAY_SUCCESS.getCode());

			enrollingSubmitOrderService.updateSubmitOrderAndActivity(order);



		}

		return ResponseModel.succ(vo);
	}

	//微信支付回调
	@Override
	public void submitOrderCallBack(submitOrder req) {

		if(PayResultEnums.PAY_SUCCESS.getCode().equals(req.getPayStatus())){
			//查询订单看看是否已经主动回调了
			EnrollingSubmitOrderCondition condition = new EnrollingSubmitOrderCondition();
			condition.setOrderNum(req.getOrderNum());

			EnrollingSubmitOrder order = enrollingSubmitOrderService.getSubmitOrder(condition);
			if(order==null||EnrollingEnum.ORDER_STATUS.PAY.getType().equals(order.getStatus())){

				return;
			}

			order.setStatus(EnrollingEnum.ORDER_STATUS.PAY.getType());
			order.setUpdateAt(new Date());

			enrollingSubmitOrderService.updateSubmitOrderAndActivity(order);


		}


	}

	@Override
	public ResponseModel getEnrollingDetail(activityIdReq req) {

		//获取修改的详情信息
		EnrollingActivityDataCondition dataCondition = new EnrollingActivityDataCondition();
		dataCondition.setActivityId(Integer.valueOf(req.getActivityId()));
		EnrollingActivityData data = enrollingActivityDataService.getActivityData(dataCondition);
		if(data==null){
			return ResponseModel.fail(ApiCallResult.EMPTY.getCode(),ApiCallResult.EMPTY.getDesc());
		}

		Map<String, Object> map = new HashMap<>();
		map.put("id", data.getId());
		map.put("activityID", data.getActivityId());

		JSONObject content = data.getContent();
		Integer templateId = content.getInteger("templateId");
		JSONArray templateDate = content.getJSONArray("templateDate");
		JSONArray newData = new JSONArray();
		//返回前端时要在json数据中加入标题和模块名称
		for (int i = 0; i < templateDate.size(); i++) {
			JSONObject jsonObject = templateDate.getJSONObject(i);
//            AssetDto student = JSON.parseObject(jsonObject.toJSONString(), new TypeReference<AssetDto>() {});
			Integer title = jsonObject.getInteger("title");
			TAssetFieldGroup tAssetFieldGroup = tAssetFieldGroupDao.selectById(title);
			if (tAssetFieldGroup != null) {
				jsonObject.put("titleName", tAssetFieldGroup.getName());
			}
			Integer titleSubset = jsonObject.getInteger("titleSubset");
			if (titleSubset != null) {
				TAssetFieldModel tAssetFieldModel = assetFieldModelDao.selectById(titleSubset);
				if (tAssetFieldModel != null) {
					jsonObject.put("titleSubsetName", tAssetFieldModel.getModelTitle());
				}
			}

			String keyStr = jsonObject.getString("key");
			String key = formatKey(keyStr);
			String type = jsonObject.getString("type");
			if (!"SELECT".equals(type)) {
				TAssetField field = tAssetFieldDao.findUnit(key);
				if (field == null) {
					continue;
				}
				String unit = field.getUnit();
				//设置单位
				jsonObject.put("unit", unit);
			}

			if ("SEDATE".equals(type) || "DATE".equals(type)) {
				JSONArray val = jsonObject.getJSONArray("val");
				JSONArray newVal = new JSONArray();
				if (!val.isEmpty()) {
					for (int j = 0; j < val.size(); j++) {
						String dateStr = val.getString(j);
						if (StringUtils.isNotEmpty(dateStr)) {
							boolean validDate = DateUtil.isValidDate(dateStr);
							if (validDate) {
								String format = DateUtil.format(DateUtil.parse(dateStr, DateUtil.NORM_DATETIME_PATTERN), DateUtil.NORM_DATETIME_PATTERN);
								newVal.add(format);
							}
						}
					}
					jsonObject.put("val", newVal);
				}
			}
			newData.add(jsonObject);
		}
		content.put("templateDate", newData);
		map.put("files", content);
		TAssetTemplateCategory tAssetTemplateCategory = tAssetTemplateCategoryDao.selectById(templateId);
		if (tAssetTemplateCategory == null) {
			log.info("查询的模板ID为：{}，原始数据为：{}", templateDate, content);
			throw new BusinessException(ApiCallResult.FAILURE.getCode(), "查询失败");
		}
		map.put("categoryId", tAssetTemplateCategory.getCategoryId());
		map.put("categoryOptionId", tAssetTemplateCategory.getCategoryOptionId());

        map.putAll(getExtraInfo(Integer.valueOf(req.getActivityId())));
		return ResponseModel.succ(map);
	}

	private Map<String,Object> getExtraInfo(Integer activityId){
        Map<String,Object> result = Maps.newHashMap();
	    if (activityId!=null){
            EnrollingActivity enrollingActivity = enrollingActivityService.getEnrollingActivityById(activityId.toString());
			result.put("thirdPartyStatus", enrollingActivity.getThirdPartyStatus());
			result.put("thirdPartyTitle", enrollingActivity.getThirdPartyTitle());
			result.put("thirdPartyUrl", enrollingActivity.getThirdPartyUrl());
			result.put("options", enrollingActivity.getOptions());
		}
        return result;
    }


    @Override
    public ResponseModel getThirdExtraInfo(String activityId){
        Map<String,Object> result = Maps.newHashMap();
        if (StringUtils.isNotBlank(activityId)){
            EnrollingActivity enrollingActivity = enrollingActivityService.getEnrollingActivityById(activityId);
            result.put("thirdPartyStatus", enrollingActivity.getThirdPartyStatus());
            result.put("thirdPartyTitle", enrollingActivity.getThirdPartyTitle());
            result.put("thirdPartyUrl", enrollingActivity.getThirdPartyUrl());
            result.put("options", enrollingActivity.getOptions());
        }
        return ResponseModel.succ(result);
    }

	@Override
	public ResponseModel getShareInfo(ActivityReq.ActivityId req) {
		return ResponseModel.succ(enrollingActivityService.getShareInfo(req));
	}

	@Override
	@Transactional
	public ResponseModel updateActivity(saveActivityReq req) {


		JSONObject jsonObject = JSON.parseObject(req.getFields());
		Integer categoryId = jsonObject.getInteger("templateId"); //获取当前的模板id
		Integer activityId = req.getActivityId() == null?null:Integer.valueOf(req.getActivityId());
		//获取模板对应的类型
		String enrollingType = EnrollingEnum.TEMPLATE_TYPE.getDesc(String.valueOf(categoryId));

		//根据请求参数挑选组装必须的参数
		Map<String, Object> result = getActivityParams(req, enrollingType, activityId);
		JSONArray errorArray = (JSONArray) result.get("errorArray");
		if(!errorArray.isEmpty()) {
			return ResponseModel.fail(ApiCallResult.EMPTY, errorArray);
		}

		EnrollingActivity params = (EnrollingActivity) result.get("activity");

		params.setType(enrollingType);
		params.setId(activityId);
		enrollingActivityService.updateActivity(params);

		//保存预招商债权信息表
		EnrollingActivityData data = enrollingActivityDataService.getActivityDataByActivityId(activityId);
		data.setContent(JSONObject.parseObject(req.getFields()));

		enrollingActivityDataService.updateActivityData(data);

		return ResponseModel.succ();
	}


	@Override
	public ResponseModel updateActivityThirdParty(saveActivityThirdPartyReq req) {

		JSONObject jsonObject = JSON.parseObject(req.getFields());
		Integer categoryId = jsonObject.getInteger("templateId"); //获取当前的模板id
		Integer activityId = req.getActivityId() == null?null:Integer.valueOf(req.getActivityId());
		//获取模板对应的类型
		String enrollingType = EnrollingEnum.TEMPLATE_TYPE.getDesc(String.valueOf(categoryId));

		//根据请求参数挑选组装必须的参数
		Map<String, Object> result = getThirdPartyActivityParams(req, enrollingType, activityId);
		JSONArray errorArray = (JSONArray) result.get("errorArray");
		if(!errorArray.isEmpty()) {
			return ResponseModel.fail(ApiCallResult.EMPTY, errorArray);
		}

		EnrollingActivity params = (EnrollingActivity) result.get("activity");

		params.setType(enrollingType);
		params.setId(activityId);

		params.setThirdPartyStatus(1);
		params.setThirdPartyTitle(req.getThirdPartyTitle());
		params.setThirdPartyUrl(req.getThirdPartyUrl());

		params.setOperatorAt(new Date());
        params.setOperatorId(Integer.valueOf(req.getOperatorId()));

        // 保存项目经理
		params.setOptions(req.getOptions());

		enrollingActivityService.updateActivity(params);

		//保存预招商债权信息表
		EnrollingActivityData data = enrollingActivityDataService.getActivityDataByActivityId(activityId);
		data.setContent(JSONObject.parseObject(req.getFields()));

		enrollingActivityDataService.updateActivityData(data);

		return ResponseModel.succ();
	}

	@Override
	public ResponseModel getActivityModelInfo(activityIdReq req) {

		EnrollingAnnouncementVO enrollingModelVO =
				enrollingActivityService.getActivityModelInfo(req.getActivityId());
		return ResponseModel.succ(enrollingModelVO);
	}


	@Override
	public ResponseModel getFoucseCount(Integer partyPrimaryId, Integer accountId) {
		Integer foucseCount = enrollingActivityService.getFoucseCount(partyPrimaryId, accountId);
		return ResponseModel.succ(foucseCount);
	}

	@Override
	public ResponseModel saveShareInfo(activityIdTypeReq req) {

		try {
			EnrollingActivityShareStats share = new EnrollingActivityShareStats();
			share.setAccountId(Integer.valueOf(req.getAccountId()));
			share.setPartyId(req.getPartyId()==null?null: Integer.valueOf(req.getPartyId()));
			share.setActivityId(Integer.valueOf(req.getActivityId()));
			share.setCreatedAt(new Date());

			enrollingActivityShareStatsService.saveEnrollingActivityShare(share);
		}catch (Exception e){

			log.error("保存预招商分享信息异常，异常信息为：{}",e);
		}

		return ResponseModel.succ();
	}

	@Override
	public ResponseModel projectManagerOperation(projectManagerOperation req) {

		EnrollingActivity activity = new EnrollingActivity();
		activity.setId(Integer.valueOf(req.getActivityId()));
		activity.setOptions(req.getStaffId());
		//取消直接赋值 -1
		if("2".equals(req.getType())){
			activity.setOptions("-1");
		}

		enrollingActivityService.updateEnrollingActivityById(activity);

		return ResponseModel.succ();
	}

	@Override
	public ResponseModel getProjectManager(activityIdReq req) {

		EnrollingProjectInfoVo vo = new EnrollingProjectInfoVo();

		EnrollingDetailInfoVo base = enrollingActivityService.getEnrollingActivityBaseInfo(req.getActivityId());

		if(base==null){
			return ResponseModel.fail("未找到详情信息！");
		}
        List<TDisposeProvider> list = activityServiceProviderService.getEnrollingDisposeProvider(Integer.parseInt(req.getActivityId()));
        if (list.size() > 0) {
			TDisposeProvider disposeProvider = list.get(0);
			vo.setDisposalService(disposeProvider.getCompanyName());
			vo.setDisposeProviderId(disposeProvider.getId());
        }
		vo.setProjectName(base.getProjectName());
		vo.setProjectPhone(base.getProjectPhone());
		vo.setProjectQq(base.getProjectQq());
        vo.setFundProvider(base.getFundProvider());
		return ResponseModel.succ(vo);
	}


	@Override
	@Transactional
	public void sentNoticeMessage(Integer noticeModelId) {
		List<NotifyPartyEnrollingActivity> notifyList = notifyPartyEnrollingActivityService.getNotifyListByActivityId(noticeModelId);

		String notifierMobile = "";
		Map<String, Object> params = new HashMap<String, Object>();

		for (NotifyPartyEnrollingActivity notifyPartyEnrollingActivity : notifyList) {

			notifierMobile = accountService.getNotifierMobile(notifyPartyEnrollingActivity.getPartyId());

			EnrollingActivity enrollingActivity =
					enrollingActivityService.getEnrollingActivityById(String.valueOf(notifyPartyEnrollingActivity.getActivityId()));
			params.put("enrolling_name", enrollingActivity.getName());

			sendEnrollingMessage(notifierMobile, AliSmsTemplateEnums.ENROLLING_REMINDE_END.getCode(), params, null);
		}
	}

	@Override
	public ResponseModel saveProof(EnrollingReq.saveProofReq req) {


		EnrollingDepositCondition condition = new EnrollingDepositCondition();

		condition.setActivityId(Integer.valueOf(req.getActivityId()));
		condition.setPartyId(Integer.valueOf(req.getPartyId()));
		List<EnrollingDeposit> list = enrollingDepositService.getEnrollingDepositList(condition);

		if(list.size()==0){
			return ResponseModel.fail("未找到报名信息");
		}
		EnrollingDeposit deposit = list.get(0);
		deposit.setParticipateProof(req.getImgUrl());
		deposit.setUploadAt(new Date());

		enrollingDepositService.updateEnrollingDeposit(deposit);

		return ResponseModel.succ();
	}

	@Override
	public ResponseModel initEnrollingActivityEndTime(EnrollingReq.activityEndTime req) {
		enrollingActivityService.initEnrollingActivityEndTime(req);
		return ResponseModel.succ();
	}

	@Override
	public ResponseModel wxPay(activityIdTypeReq req) {

		EnrollingActivity activity = enrollingActivityService.getEnrollingActivityById(req.getActivityId());
		if(activity==null||!String.valueOf(activity.getPartyId()).equals(req.getPartyId())){

			return ResponseModel.fail("该活动不存在,或者该活动不是您创建的！");
		}

		if(!EnrollingEnum.STATUS.WAIT_PAY.getType().equals(activity.getStatus())){

			return ResponseModel.fail("该订单不为支付状态！");
		}




		return payInfo(activity.getReleaseAmount(),activity);




	}


	@Override
	public ResponseModel oldDataAddBusTypeName() {

		enrollingActivityDataService.oldDataAddBusTypeName();
		return ResponseModel.succ();
	}

    @Override
    public ResponseModel fundDisposalOperation(fundDisposalOperation req) {

        EnrollingActivity activity = enrollingActivityService.getEnrollingActivityById(req.getActivityId());

        if(activity==null){
            return ResponseModel.fail(ApiCallResult.DATA_EMPTY);
        }
        activity.setFundProvider(req.getFundProvider());
        activity.setDisposalService(req.getDisposalService());
        enrollingActivityService.updateEnrollingActivityById(activity);

        //当为第三方招商时 更新data表
        if(EnrollingEnum.THIRTY_PARTY_STATUS.CHANG_CHENG.getStatus().equals(activity.getThirdPartyStatus())){

            EnrollingActivityImportData data = new EnrollingActivityImportData();
            data.setActivityId(Integer.valueOf(req.getActivityId()));
            data.setFundProvider(req.getFundProvider());
            data.setDisposalService(req.getDisposalService());

            enrollingActivityImportService.updateImportActivityByActivityId(data);

        }

        return ResponseModel.succ();
    }
}
