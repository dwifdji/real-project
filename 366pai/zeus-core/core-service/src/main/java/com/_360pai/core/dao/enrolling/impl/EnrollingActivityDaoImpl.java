
package com._360pai.core.dao.enrolling.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.enrolling.EnrollingActivityCondition;
import com._360pai.core.dao.enrolling.EnrollingActivityDao;
import com._360pai.core.dao.enrolling.mapper.EnrollingActivityMapper;
import com._360pai.core.dto.enrolling.ActivityPersionDto;
import com._360pai.core.dto.enrolling.ActivityTabDto;
import com._360pai.core.dto.enrolling.EnrollingListReqDto;
import com._360pai.core.dto.enrolling.PageReqDto;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.vo.enrolling.AgencyActivityDetailVO;
import com._360pai.core.vo.enrolling.EnrollingActivityVo;
import com._360pai.core.vo.enrolling.EnrollingContractParamVO;
import com._360pai.core.vo.enrolling.web.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class EnrollingActivityDaoImpl extends AbstractDaoImpl<EnrollingActivity, EnrollingActivityCondition, BaseMapper<EnrollingActivity,EnrollingActivityCondition>> implements EnrollingActivityDao{
	
	@Resource
	private EnrollingActivityMapper enrollingActivityMapper;
	
	@Override
	protected BaseMapper<EnrollingActivity, EnrollingActivityCondition> daoSupport() {
		return enrollingActivityMapper;
	}

	@Override
	protected EnrollingActivityCondition blankCondition() {
		return new EnrollingActivityCondition();
	}

	@Override
	public List<EnrollingActivityVo> getEnrollingActivityList(EnrollingListReqDto params) {
		return enrollingActivityMapper.getEnrollingActivityList(params);
	}

	@Override
	public List<EnrollingActivityVo> getAvailableEnrollingActivities(EnrollingListReqDto params) {
		return enrollingActivityMapper.getAvailableEnrollingActivities(params);
	}

	@Override
	public List<HomeEnrollingActivityVO> focusList(EnrollingListReqDto params) {
		return enrollingActivityMapper.focusList(params);
	}

	@Override
	public List<HomeEnrollingActivityVO> remindList(ActivityPersionDto params) {
		return enrollingActivityMapper.remindList(params);
	}

	@Override
	public List<EnrollingHomeVo> getActivityByTabId(String tabId) {
		return null;
	}

	@Override
	public List<HomeEnrollingActivityVO> myApplyActivity(EnrollingListReqDto params) {
		return enrollingActivityMapper.myApplyActivity(params);
	}

	@Override
	public List<HomeEnrollingActivityVO> getAllTypeActivity(PageReqDto params) {
		return enrollingActivityMapper.getAllTypeActivity(params);
	}

	@Override
	public List<MyEnrollingActivityVO> searchHomeActivity(EnrollingListReqDto params) {
		List<MyEnrollingActivityVO> list = enrollingActivityMapper.searchHomeActivity(params);
		for (MyEnrollingActivityVO item : list) {
			//if (StringUtils.isNotBlank(item.getAreaName())) {
			//	item.setCityName(item.getAreaName());
			//} else if (StringUtils.isNotBlank(item.getCityName())) {
			//	item.setCityName(item.getCityName());
			//} else if (StringUtils.isNotBlank(item.getProvinceName())) {
			//	item.setCityName(item.getProvinceName());
			//} else {
			//	item.setCityName("");
			//}
			if (StringUtils.isBlank(item.getCityName())) {
				if (StringUtils.isNotBlank(item.getProvinceName())) {
					item.setCityName(item.getProvinceName());
				} else {
					item.setCityName("");
				}
			}
		}
		return list;
	}

	@Override
	public List<EnrollingStatusVO> getAllActivityStatus() {
		return enrollingActivityMapper.getAllActivityStatus();
	}

	@Override
	public List<HomeEnrollingActivityVO> getActivityByTab(ActivityTabDto params) {
		return enrollingActivityMapper.getActivityByTab(params);
	}

	@Override
	public List<HomeEnrollingActivityVO> leaderboardEnrolling(EnrollingListReqDto params) {
		return enrollingActivityMapper.leaderboardEnrolling(params);
	}

	@Override
	public EnrollingDetailInfoVo getEnrollingDetailInfoVo(EnrollingListReqDto params) {
		return enrollingActivityMapper.getEnrollingDetailInfoVo(params);
	}

	@Override
	public List<LinkageEnrollingActivityVO> getActivityJoint(String activityId) {
		return enrollingActivityMapper.getActivityJoint(activityId);
	}

	@Override
	public EnrollingSignVO getSignInfo(EnrollingListReqDto params) {
		return enrollingActivityMapper.getSignInfo(params);
	}


	@Override
	public List<HomeEnrollingActivityVO> myEnrollingActivities(EnrollingListReqDto params) {
		return enrollingActivityMapper.myEnrollingActivities(params);
	}

	@Override
	public AgencyActivityDetailVO getAgencyActivityInfo(String activityId) {
		return enrollingActivityMapper.getAgencyActivityInfo(activityId);
	}

	@Override
	public EnrollingContractParamVO getEnrollingContractParam(String activityId) {
		return enrollingActivityMapper.getEnrollingContractParam(activityId);
	}

	@Override
	public EnrollingActivity getActivityByName(String name) {
		return enrollingActivityMapper.getActivityByName(name);
	}

	@Override
	public EnrollingAnnouncementVO getActivityModelInfo(String activityId) {
		return enrollingActivityMapper.getActivityModelInfo(activityId);
	}

	@Override
	public Integer getFoucseCount(Integer partyId, Integer accountId) {
		return enrollingActivityMapper.getFoucseCount(partyId, accountId);
	}

	@Override
	public void batchUpdateFocusNum(List<String> activityIdList) {

		enrollingActivityMapper.batchUpdateFocusNum(activityIdList);
	}

	@Override
	public List<MyEnrollingActivityVO> getActivitysByAccountId(Integer accountId, Integer partyId ) {
		return enrollingActivityMapper.getActivitysByAccountId(accountId, partyId);
	}

	@Override
	public PageInfo getListByPage(int page, int perPage) {
		PageHelper.startPage(page, perPage);
		List<EnrollingActivity> list = enrollingActivityMapper.selectAll();
		return new PageInfo<>(list);
	}

	@Override
	public int countPlatformActivityNum() {
		return enrollingActivityMapper.countPlatformActivityNum();
	}

	@Override
	public PageInfo<EnrollingActivity> getListWillEndAt2Days(int page, int perPage, Map<String, Object> params) {
		PageHelper.startPage(page, perPage);
		List<EnrollingActivity> list = enrollingActivityMapper.getListWillEndAt2Days(params);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<EnrollingActivity> getListWillEndAtOver2Days(int page, int perPage, Map<String, Object> params) {
		PageHelper.startPage(page, perPage);
		List<EnrollingActivity> list = enrollingActivityMapper.getListWillEndAtOver2Days(params);
		return new PageInfo<>(list);
	}


}
