
package com._360pai.core.dao.activity.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.activity.ActivityBlackListCityCondition;
import com._360pai.core.dao.activity.ActivityBlackListCityDao;
import com._360pai.core.dao.activity.mapper.ActivityBlackListCityMapper;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.activity.ActivityBlackListCity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ActivityBlackListCityDaoImpl extends AbstractDaoImpl<ActivityBlackListCity, ActivityBlackListCityCondition, BaseMapper<ActivityBlackListCity,ActivityBlackListCityCondition>> implements ActivityBlackListCityDao{
	
	@Resource
	private ActivityBlackListCityMapper activityBlackListCityMapper;
	
	@Override
	protected BaseMapper<ActivityBlackListCity, ActivityBlackListCityCondition> daoSupport() {
		return activityBlackListCityMapper;
	}

	@Override
	protected ActivityBlackListCityCondition blankCondition() {
		return new ActivityBlackListCityCondition();
	}

	@Override
	public List<Integer> getCityIdListByActivityId(Integer activityId) {
		ActivityBlackListCityCondition condition = new ActivityBlackListCityCondition();
		condition.setActivityId(activityId);
		List<ActivityBlackListCity> list = activityBlackListCityMapper.selectByCondition(condition);
		if (list.size() > 0) {
			List<Integer> result = new ArrayList<>();
			for (ActivityBlackListCity city : list) {
				result.add(city.getCityId());
			}
			return result;
		}
		return Collections.EMPTY_LIST;
	}

	@Override
	public boolean syncCityIdListByActivityId(Integer activityId, List<Integer> newCityIdList) {
		int result;
		if (newCityIdList != null && newCityIdList.size() > 0) {
			List<Integer> oldCityIdList = activityBlackListCityMapper.getIdListByActivityId(activityId);
			for (Integer cityId : newCityIdList) {
				if (!oldCityIdList.contains(cityId)) {
					ActivityBlackListCity item = new ActivityBlackListCity();
					item.setCityId(cityId);
					item.setActivityId(activityId);
					result = activityBlackListCityMapper.insert(item);
					if (result == 0) {
						throw new BusinessException(ApiCallResult.FAILURE);
					}
				}
			}
		} else {
			ActivityBlackListCityCondition condition = new ActivityBlackListCityCondition();
			condition.setActivityId(activityId);
			List<ActivityBlackListCity> list = activityBlackListCityMapper.selectByCondition(condition);
			if (list.size() > 0) {
				for (ActivityBlackListCity item : list) {
					result = activityBlackListCityMapper.deleteById(item.getId());
					if (result == 0) {
						throw new BusinessException(ApiCallResult.FAILURE);
					}
				}
			}
		}
		return true;
	}
}
