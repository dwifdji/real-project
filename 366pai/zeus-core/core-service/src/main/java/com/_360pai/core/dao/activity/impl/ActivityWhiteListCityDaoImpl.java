
package com._360pai.core.dao.activity.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.activity.ActivityWhiteListCityCondition;
import com._360pai.core.dao.activity.ActivityWhiteListCityDao;
import com._360pai.core.dao.activity.mapper.ActivityWhiteListCityMapper;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.activity.ActivityWhiteListCity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ActivityWhiteListCityDaoImpl extends AbstractDaoImpl<ActivityWhiteListCity, ActivityWhiteListCityCondition, BaseMapper<ActivityWhiteListCity,ActivityWhiteListCityCondition>> implements ActivityWhiteListCityDao{
	
	@Resource
	private ActivityWhiteListCityMapper activityWhiteListCityMapper;
	
	@Override
	protected BaseMapper<ActivityWhiteListCity, ActivityWhiteListCityCondition> daoSupport() {
		return activityWhiteListCityMapper;
	}

	@Override
	protected ActivityWhiteListCityCondition blankCondition() {
		return new ActivityWhiteListCityCondition();
	}

	@Override
	public List<Integer> getCityIdListByActivityId(Integer activityId) {
		ActivityWhiteListCityCondition condition = new ActivityWhiteListCityCondition();
		condition.setActivityId(activityId);
		List<ActivityWhiteListCity> list = activityWhiteListCityMapper.selectByCondition(condition);
		if (list.size() > 0) {
			List<Integer> result = new ArrayList<>();
			for (ActivityWhiteListCity city : list) {
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
			List<Integer> oldCityIdList = activityWhiteListCityMapper.getIdListByActivityId(activityId);
			for (Integer cityId : newCityIdList) {
				if (!oldCityIdList.contains(cityId)) {
					ActivityWhiteListCity item = new ActivityWhiteListCity();
					item.setCityId(cityId);
					item.setActivityId(activityId);
					result = activityWhiteListCityMapper.insert(item);
					if (result == 0) {
						throw new BusinessException(ApiCallResult.FAILURE);
					}
				}
			}
		} else {
			ActivityWhiteListCityCondition condition = new ActivityWhiteListCityCondition();
			condition.setActivityId(activityId);
			List<ActivityWhiteListCity> list = activityWhiteListCityMapper.selectByCondition(condition);
			if (list.size() > 0) {
				for (ActivityWhiteListCity item : list) {
					result = activityWhiteListCityMapper.deleteById(item.getId());
					if (result == 0) {
						throw new BusinessException(ApiCallResult.FAILURE);
					}
				}
			}
		}
		return true;
	}
}
