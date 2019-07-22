package com._360pai.core.service.enrolling.impl;

import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.JsonUtil;
import com._360pai.arch.common.utils.NumberToCN;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.core.file.QiNiuUtil;
import com._360pai.arch.core.file.QrUtil;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.aspact.EnrollingEmailService;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.condition.enrolling.EnrollingActivityCondition;
import com._360pai.core.condition.enrolling.EnrollingActivityImportDataCondition;
import com._360pai.core.condition.enrolling.EnrollingActivityImportRealDataCondition;
import com._360pai.core.dao.account.TPartyDao;
import com._360pai.core.dao.assistant.AreaDao;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dao.assistant.ProvinceDao;
import com._360pai.core.dao.assistant.StaffDao;
import com._360pai.core.dao.enrolling.EnrollingActivityDao;
import com._360pai.core.dao.enrolling.EnrollingActivityImportDataDao;
import com._360pai.core.dao.enrolling.EnrollingActivityImportRealDataDao;
import com._360pai.core.dto.enrolling.ActivityPersionDto;
import com._360pai.core.dto.enrolling.ActivityTabDto;
import com._360pai.core.dto.enrolling.EnrollingListReqDto;
import com._360pai.core.dto.enrolling.PageReqDto;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.AcctReq;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.enrolling.req.EnrollingReq;
import com._360pai.core.facade.enrolling.req.EnrollingReq.agencyUpdateReq;
import com._360pai.core.model.account.TParty;
import com._360pai.core.model.assistant.Area;
import com._360pai.core.model.assistant.City;
import com._360pai.core.model.assistant.Province;
import com._360pai.core.model.assistant.Staff;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.EnrollingActivityImportData;
import com._360pai.core.model.enrolling.EnrollingActivityImportRealData;
import com._360pai.core.service.assistant.AssistantService;
import com._360pai.core.service.assistant.CityService;
import com._360pai.core.service.enrolling.EnrollingActivityService;
import com._360pai.core.vo.enrolling.AgencyActivityDetailVO;
import com._360pai.core.vo.enrolling.EnrollingActivityVo;
import com._360pai.core.vo.enrolling.EnrollingContractParamVO;
import com._360pai.core.vo.enrolling.web.*;
import com._360pai.gateway.controller.req.wx.WXACodeUnLimitReq;
import com._360pai.gateway.facade.WxFacade;
import com._360pai.gateway.resp.wxpay.WXACodeUnLimitResp;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class EnrollingActivityServiceImpl implements EnrollingActivityService{


	@Autowired
	private EnrollingActivityDao enrollingActivityDao;
	
	@Resource
	private RedisTemplate redisTemplate;
	
	@Resource
	public StringRedisTemplate stringRedisTemplate;

	@Autowired
	private EnrollingEmailService enrollingEmailService;

	@Autowired
	private GatewayMqSender mqSender;
	@Autowired
	private ProvinceDao provinceDao;
	@Autowired
	private QiNiuUtil qiNiuUtil;
	@Autowired
	private GatewayProperties gatewayProperties;
	@Reference(version = "1.0.0")
	private WxFacade wxFacade;
	@Autowired
	private SystemProperties systemProperties;
	@Autowired
	private EnrollingActivityImportDataDao enrollingActivityImportDataDao;
	@Autowired
	private StaffDao staffDao;
	@Resource
	private RedisCachemanager redisCachemanager;
	@Autowired
	private TPartyDao partyDao;
	@Autowired
	private CityDao cityDao;
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private CityService cityService;

	@Autowired
	private EnrollingActivityImportRealDataDao enrollingActivityImportRealDataDao;
	@Autowired
	private AssistantService assistantService;


	@Override
	public PageInfo getEnrollingActivityList(EnrollingListReqDto params) {

		PageHelper.startPage(params.getPage(), params.getPerPage());
		List<EnrollingActivityVo> list = enrollingActivityDao.getEnrollingActivityList(params);

		return new PageInfo<>(list);

 	}
	
	

	@Override
	public PageInfo myEnrollingActivities(EnrollingListReqDto params) {


		PageHelper.startPage(params.getPage(), params.getPerPage());

		List<HomeEnrollingActivityVO> list = enrollingActivityDao.myEnrollingActivities(params);

		return new PageInfo<>(list);

 	}



	@Override
	public EnrollingDetailInfoVo getEnrollingActivityBaseInfo(String id) {

		EnrollingListReqDto params = new EnrollingListReqDto();
		params.setId(id);

		EnrollingDetailInfoVo detail = enrollingActivityDao.getEnrollingDetailInfoVo(params);
		if (detail != null) {
			detail.setCityName(cityService.getCityName(detail));
		}
		return detail;
	}

	@Override
	public EnrollingActivity getEnrollingActivityById(String id) {

		EnrollingActivityCondition condition = new EnrollingActivityCondition();

		condition.setId(Integer.valueOf(id));


		return enrollingActivityDao.selectFirst(condition);
	}

	@Override
	public int updateEnrollingActivityById(EnrollingActivity activity) {

		 if(StringUtils.isNotEmpty(activity.getOptions())){
			 //更新importData 信息
			 updateImportInfo(activity);
		 }


		return enrollingActivityDao.updateById(activity);
	}

	private void updateImportInfo(EnrollingActivity activity) {

		try {
			EnrollingActivityImportDataCondition condition = new EnrollingActivityImportDataCondition();

			condition.setActivityId(activity.getId());

			EnrollingActivityImportData data = enrollingActivityImportDataDao.selectFirst(condition);


			updateEnrollingActivityImportData(data,activity);

			//更新real_data  表信息
			updateRealImportData(activity);


		}catch (Exception e){
			log.error("更新信息ImportData异常",e);
		}

	}

	private void updateRealImportData(EnrollingActivity activity) {
		EnrollingActivityImportRealDataCondition condition = new EnrollingActivityImportRealDataCondition();
		condition.setActivityId(activity.getId());
		condition.setDeleteFlag(false);
		EnrollingActivityImportRealData data = enrollingActivityImportRealDataDao.selectFirst(condition);

		if(data==null){
			return;
		}

		data.setContactName("");
		data.setContactPhone("");
		if(Integer.valueOf(activity.getOptions())>0){
			Staff staff = staffDao.selectById(activity.getOptions());
			data.setContactName(staff.getName());
			data.setContactPhone(staff.getMobile());
		}

		enrollingActivityImportRealDataDao.updateById(data);


	}

	private void updateEnrollingActivityImportData(EnrollingActivityImportData data,EnrollingActivity activity) {

		if(data==null){
			return;
		}

		data.setContactPerson("");
		data.setContactPhone("");
		if(Integer.valueOf(activity.getOptions())>0){
			Staff staff = staffDao.selectById(activity.getOptions());

			data.setContactPerson(staff.getName());
			data.setContactPhone(staff.getMobile());
		}
		enrollingActivityImportDataDao.updateById(data);


	}


	@Override
	public PageInfo getAvailableEnrollingActivities(EnrollingListReqDto params) {
		PageHelper.startPage(params.getPage(), params.getPerPage());
		List<EnrollingActivityVo> list = enrollingActivityDao.getAvailableEnrollingActivities(params);
		return new PageInfo<>(list);
	}


	@Override
	public PageInfo focusList(EnrollingListReqDto params) {
		PageHelper.startPage(params.getPage(), params.getPerPage());
		params.setFocusInfo(params.getFocusType()==null?null:String.valueOf(params.getFocusType()));
		List<HomeEnrollingActivityVO> list = enrollingActivityDao.focusList(params);
		return new PageInfo<>(list);
	}



	@Override
	public PageInfo remindList(ActivityPersionDto params) {
		PageHelper.startPage(params.getPage(), params.getPerPage());
		List<HomeEnrollingActivityVO> list = enrollingActivityDao.remindList(params);
		return new PageInfo<>(list);
	}



	@Override
	public PageInfo getActivityByTabId(String tabId) {
		PageHelper.offsetPage(1, 6);

		List<EnrollingHomeVo> list =enrollingActivityDao.getActivityByTabId(tabId);

		return new PageInfo<>(list);
	}



	@Override
	public PageInfo myApplyActivity(EnrollingListReqDto params) {
		PageHelper.startPage(params.getPage(), params.getPerPage());
		List<HomeEnrollingActivityVO> list = enrollingActivityDao.myApplyActivity(params);
		return new PageInfo<>(list);
	}



	@Override
	public PageInfo getAllTypeActivity(PageReqDto params) {
		PageHelper.startPage(params.getPage(), params.getPerPage());
		List<HomeEnrollingActivityVO> list = enrollingActivityDao.getAllTypeActivity(params);
		return new PageInfo<>(list);
	}



	@Override
	public PageInfo searchHomeActivity(EnrollingListReqDto params) {
		PageHelper.startPage(params.getPage(), params.getPerPage());

		//if(StringUtils.isNotBlank(params.getOrderBy()) && params.getOrderBy().contains("_")) {
		//	int len = params.getOrderBy().lastIndexOf("_");
		//	params.setOrderVar1(params.getOrderBy().substring(0, len));
		//	params.setOrderVar2(params.getOrderBy().substring(len + 1, params.getOrderBy().length()));
		//}

		List<MyEnrollingActivityVO> list = enrollingActivityDao.searchHomeActivity(params);
		return new PageInfo<>(list);
	}



	@Override
	public List<EnrollingStatusVO> getAllActivityStatus() {
		return enrollingActivityDao.getAllActivityStatus();
	}


	/**
	 *
	 * 获取首页查询信息
	 * @
	 */
	@Override
	public PageInfo getWebSearchActivity(EnrollingListReqDto params) {
		return null;
	}



	@Override
	public List<HomeEnrollingActivityVO> getActivityByTab(ActivityTabDto params) {
		return enrollingActivityDao.getActivityByTab(params);
	}



	@Override
	public PageInfo leaderboardEnrolling(EnrollingListReqDto params) {
		
		PageHelper.startPage(params.getPage(), params.getPerPage());

		List<HomeEnrollingActivityVO> list = enrollingActivityDao.leaderboardEnrolling(params);

		return new PageInfo<>(list);

		 
	}

	/**
	 *
	 *保存预招商信息
	 */
	@Override
	@Transactional
	public int saveActivity(EnrollingActivity params) {

		int insertCount = enrollingActivityDao.insert(params);
		assistantService.setEnrollingActivityExpireTime(params);
		return insertCount;
	}


	@Override
	public PageInfo getActivityJoint(String activityId,Integer page ,Integer perPage) {

		PageHelper.startPage(page, perPage);

		List<LinkageEnrollingActivityVO> joinList = enrollingActivityDao.getActivityJoint(activityId);
		return new PageInfo<>(joinList);
	}

	@Override
	public EnrollingSignVO getSignInfo(EnrollingListReqDto dto) {
		return enrollingActivityDao.getSignInfo(dto);
	}



	@Override
	public Integer updateAgencyActivity(agencyUpdateReq agencyUpdateReq) {
		EnrollingActivityCondition enrollingActivityCondition = new EnrollingActivityCondition();
		enrollingActivityCondition.setId(Integer.parseInt(agencyUpdateReq.getId()));
		
		EnrollingActivity enrollingActivity = enrollingActivityDao.selectOneResult(enrollingActivityCondition);
		
		enrollingActivity.setName(agencyUpdateReq.getName());
		enrollingActivity.setRejectReason(agencyUpdateReq.getRejectReasion());
		if(agencyUpdateReq.getDeposit() != null) {
			enrollingActivity.setCommissionPercent(new BigDecimal(agencyUpdateReq.getDeposit()));
		}
		enrollingActivity.setStatus(EnrollingEnum.QUERY_STATUS.getDesc(agencyUpdateReq.getStatus()));
		
		Integer updateCount = enrollingActivityDao.updateById(enrollingActivity);
		//审核通过 发送邮件
		if(EnrollingEnum.STATUS.AGENCY_APPROVED.getType().equals(enrollingActivity.getStatus())){
			new Thread(()->sendAuditEmail(enrollingActivity)).start();
		}
		return updateCount;
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

	@Override
	public AgencyActivityDetailVO getAgencyActivityInfo(String activityId) {
		
		return enrollingActivityDao.getAgencyActivityInfo(activityId);
	}

	@Override
	public EnrollingContractParamVO getEnrollingContractParam(String activityId) {
		return enrollingActivityDao.getEnrollingContractParam(activityId);
	}



	@Override
	public EnrollingActivity getActivityByName(String name) {
		
		return enrollingActivityDao.getActivityByName(name);
	}



	@Override
    @Transactional
	public Integer updateActivity(EnrollingActivity params) {
		
		int updateCount = enrollingActivityDao.updateById(params);
		//设置活动过期时间当时间过期报名活动结束
        setActivityEndTimer(params);

		return updateCount;
	}


	private void setActivityEndTimer(EnrollingActivity enrollingActivity){
        String status = enrollingActivity.getStatus();
        if(EnrollingEnum.STATUS.PUBLISHED.getType().equals(status)
                || EnrollingEnum.STATUS.AGENCY_APPROVED.getType().equals(status)
                || EnrollingEnum.STATUS.PLATFORM_APPROVED.getType().equals(status)
                || EnrollingEnum.STATUS.ONLINE.getType().equals(status)) {
            if((enrollingActivity.getEndAt().getTime() - System.currentTimeMillis()) / 1000 > 0) {
				assistantService.setEnrollingActivityExpireTime(enrollingActivity);
            }
        }
    }



	@Override
	public EnrollingAnnouncementVO getActivityModelInfo(String activityId) {
		return enrollingActivityDao.getActivityModelInfo(activityId);
	}



	@Override
	public Integer getFoucseCount(Integer partyId, Integer accountId) {

		return enrollingActivityDao.getFoucseCount(partyId, accountId);
	}

	@Override
	public void batchUpdateFocusNum(List<String> activityIdList) {

		enrollingActivityDao.batchUpdateFocusNum(activityIdList);

	}

    @Override
    @Transactional
    public void initEnrollingActivityEndTime(EnrollingReq.activityEndTime req) {

        List<EnrollingActivity> enrollingActivities =getEnrollingActivity(req);

        for (EnrollingActivity enrollingActivity:enrollingActivities ) {
            setActivityEndTimer(enrollingActivity);
        }
    }

	private List<EnrollingActivity> getEnrollingActivity(EnrollingReq.activityEndTime req) {
		if(req.getActivityIds()==null){
			return 	enrollingActivityDao.selectAll();

		}
		List<EnrollingActivity> list = new ArrayList<>();

		for(String id :req.getActivityIds()){
			EnrollingActivity activity = enrollingActivityDao.selectById(id);

			if(activity !=null){
				list.add(activity);
			}

		}

		return list;

	}

	@Override
	public List<EnrollingActivityVo> getExportEnrollingActivities(EnrollingListReqDto req) {
		PageHelper.startPage(1, 100);
		List<EnrollingActivityVo> enrollingActivityList = enrollingActivityDao.getEnrollingActivityList(req);

		List<EnrollingActivityVo> totalActivityList = new ArrayList<>();
		totalActivityList.addAll(enrollingActivityList);

		PageInfo<EnrollingActivityVo> pageInfo = new PageInfo<>(enrollingActivityList);
		if(pageInfo.getTotal() > 100){
			for (int i = 2; i < pageInfo.getTotal()/ 100 + 2; i++) {
				PageHelper.startPage(i, 100);
				enrollingActivityList = enrollingActivityDao.getEnrollingActivityList(req);
				totalActivityList.addAll(enrollingActivityList);
			}
		}

		return totalActivityList;
	}

	@Override
	public PageInfo getActivitysByAccountId(AcctReq.ViewRecordRequest viewRecordRequest) {
		PageHelper.startPage(viewRecordRequest.getPage(), viewRecordRequest.getPerPage());
		List<MyEnrollingActivityVO> list = enrollingActivityDao.getActivitysByAccountId(viewRecordRequest.getAccountId(), viewRecordRequest.getPartyId());
		return new PageInfo<>(list);
	}

	@Override
	public Map<String, Object> getShareInfo(ActivityReq.ActivityId req) {
		EnrollingActivity activity = enrollingActivityDao.selectById(req.getActivityId());
		if (activity == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		Map<String, Object> data = new LinkedHashMap<>();
		String category = "";
		String amount = "";
		String amountDesc = "";
		String imageUrl = "";
		if ("1".equals(activity.getType())) {
			category = "抵押物预招商";
			amount = NumberToCN.simpleFormatAmount(activity.getRefPrice());
			amountDesc = "市场参考价";
		} else if ("2".equals(activity.getType())) {
			category = "债权招商";
			amount = NumberToCN.simpleFormatAmount(activity.getRefPrice());
			amountDesc = "债权本金";
		}  else if ("3".equals(activity.getType())) {
			category = "物权招商";
			amount = NumberToCN.simpleFormatAmount(activity.getRefPrice());
			amountDesc = "市场参考价";
		}
		JSONObject extra = activity.getExtra();
		if (extra != null) {
			JSONArray images = extra.getJSONArray("images");
			if (images != null && images.size() > 0) {
				imageUrl = images.getString(0);
			}
		}
		data.put("imageUrl", imageUrl);
		data.put("amount", amount);
		data.putAll(NumberToCN.simpleFormatAmountMap(activity.getRefPrice()));
		data.put("amountDesc", amountDesc);
		data.put("category", category);
		data.put("name", activity.getName());
		if (EnrollingEnum.THIRTY_PARTY_STATUS.TAOBAO_INVESTMENT.getStatus().equals(activity.getThirdPartyStatus())) {
			data.put("partyCategoryName", "一般公司");
		} else if (EnrollingEnum.THIRTY_PARTY_STATUS.AGENCY_INVESTMENT.getStatus().equals(activity.getThirdPartyStatus())) {
			data.put("partyCategoryName", "拍卖公司");
		} else {
			TParty party = partyDao.selectById(activity.getPartyId());
			if (party != null) {
				data.put("partyCategoryName", PartyEnum.Category.getFrontNameByKey(party.getCategory()));
			} else {
				data.put("partyCategoryName", "");
			}
		}
		if (req.isAppletFlag()) {
			data.put("qrCodeUrl", getAppletQrCode(req.getActivityId(), req.getShopId()));
		} else {
			//data.put("qrCodeUrl", getQrUrl(req.getUrl()));
			data.put("qrCodeUrl", getAppletQrCode(req.getActivityId(), SystemConstant.DEFAULT_APPLET_SHOP));
		}
		return data;
	}

	private String getQrUrl(String content) {
		File uploadFile = new File(QrUtil.createQrCode(content));
		if (!uploadFile.exists()) {
			return "";
		}
		String returnUrl = "";
		try {
			returnUrl = qiNiuUtil.uploadToPublic(uploadFile, RandomNumberGenerator.wordGenerator(30));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (StringUtils.isEmpty(returnUrl)) {
			return "";
		}
		return "https://" + gatewayProperties.getDomain() + "/" + returnUrl;
	}

	private String getAppletQrCode(Integer activityId, Integer shopId) {
		String cache = (String) redisCachemanager.hGet(RedisKeyConstant.APPLET_ENROLLING_ACTIVITY_SHARE_IMAGE, getScene(activityId, shopId));
		if (StringUtils.isNotEmpty(cache)) {
			return cache;
		}
		WXACodeUnLimitReq wxaCodeUnLimitReq = new WXACodeUnLimitReq();
		wxaCodeUnLimitReq.setPage("pages/enrolling/enrolling");
		wxaCodeUnLimitReq.setScene(getScene(activityId, shopId));
		log.info("获取店铺小程序二维码，入参={}", JSON.toJSONString(wxaCodeUnLimitReq));
		WXACodeUnLimitResp wxaCodeUnLimitResp = wxFacade.getWXACodeUnLimit(wxaCodeUnLimitReq);
		if (wxaCodeUnLimitResp == null || !wxaCodeUnLimitResp.getCode().equals("000")) {
			log.error("获取店铺招商详情小程序二维码失败，入参={}，出参={}", JSON.toJSONString(wxaCodeUnLimitReq), JSON.toJSONString(wxaCodeUnLimitResp));
			return "";
		}
		redisCachemanager.hSet(RedisKeyConstant.APPLET_ENROLLING_ACTIVITY_SHARE_IMAGE, getScene(activityId, shopId), wxaCodeUnLimitResp.getImgUrl());
		return wxaCodeUnLimitResp.getImgUrl();
	}

	private String getScene(Integer activityId, Integer shopId) {
		if (shopId != null) {
			return activityId + "-" + shopId;
		}
		return activityId + "-";
	}

	@Override
	public PageInfo getExportEnrollingActivitiesInfo(EnrollingListReqDto req) {
		PageHelper.startPage(req.getPage(), req.getPerPage());
		List<EnrollingActivityVo> enrollingActivityList = enrollingActivityDao.getEnrollingActivityList(req);
		for (EnrollingActivityVo item : enrollingActivityList) {
			try {
				String cityName = "";
				String provinceName = "";
				EnrollingActivity activity = enrollingActivityDao.selectById(item.getId());
				if (StringUtils.isNotBlank(activity.getCityId())) {
					City city = cityDao.selectById(activity.getCityId().split(",")[0]);
					if (city != null) {
						cityName = city.getName();
						provinceName = provinceDao.getName(city.getProvinceId());
					}
				} else if (StringUtils.isNotBlank(activity.getProvinceId())) {
					Province province = provinceDao.selectById(activity.getProvinceId().split(",")[0]);
					if (province != null) {
						provinceName = province.getName();
					}
				}
				item.setCityName(cityName);
				item.setProvinceName(provinceName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new PageInfo<>(enrollingActivityList);
	}
}