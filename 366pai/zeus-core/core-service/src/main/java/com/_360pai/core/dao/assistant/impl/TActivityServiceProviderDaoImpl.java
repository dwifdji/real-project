
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import com._360pai.core.common.constants.ActivityServiceProviderEnum;
import com._360pai.core.model.account.TDisposeProvider;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.TActivityServiceProviderCondition;
import com._360pai.core.dao.assistant.mapper.TActivityServiceProviderMapper;
import com._360pai.core.model.assistant.TActivityServiceProvider;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.TActivityServiceProviderDao;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class TActivityServiceProviderDaoImpl extends AbstractDaoImpl<TActivityServiceProvider, TActivityServiceProviderCondition, BaseMapper<TActivityServiceProvider,TActivityServiceProviderCondition>> implements TActivityServiceProviderDao {

	@Resource
	private TActivityServiceProviderMapper tActivityServiceProviderMapper;

	@Override
	protected BaseMapper<TActivityServiceProvider, TActivityServiceProviderCondition> daoSupport() {
		return tActivityServiceProviderMapper;
	}

	@Override
	protected TActivityServiceProviderCondition blankCondition() {
		return new TActivityServiceProviderCondition();
	}

	@Override
	public List<TDisposeProvider> getFirstLevelAuctionDisposeProvider(Integer activityId, Integer cityId) {
		List<TDisposeProvider> list = new LinkedList<>();
		TDisposeProvider disposeProvider = tActivityServiceProviderMapper.getFirstLevelDisposeProvider(cityId);
		if (disposeProvider != null) {
			list.add(disposeProvider);
		}
		list.addAll(tActivityServiceProviderMapper.getAuctionDisposeProvider(activityId));
		return list;
	}

	@Override
	public List<TDisposeProvider> getAuctionDisposeProvider(Integer activityId) {
		return tActivityServiceProviderMapper.getAuctionDisposeProvider(activityId);
	}

	@Override
	public List<TDisposeProvider> getEnrollingDisposeProvider(Integer activityId) {
		return tActivityServiceProviderMapper.getEnrollingDisposeProvider(activityId);
	}

	@Override
	public List<TDisposeProvider> getDisposeProvider(Integer activityId, String activityType) {
		return tActivityServiceProviderMapper.getDisposeProvider(activityId, activityType);
	}

	@Override
	public TActivityServiceProvider findDisposeBy(Integer activityId, String activityType, Integer providerId) {
		TActivityServiceProviderCondition condition = new TActivityServiceProviderCondition();
		condition.setActivityId(activityId);
		condition.setActivityType(activityType);
		condition.setProviderType(ActivityServiceProviderEnum.ProviderType.Dispose.getKey());
		condition.setProviderId(providerId);
		condition.setDeleteFlag(false);
		List<TActivityServiceProvider> list = tActivityServiceProviderMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TActivityServiceProvider findFundBy(Integer activityId, String activityType, Integer providerId) {
		TActivityServiceProviderCondition condition = new TActivityServiceProviderCondition();
		condition.setActivityId(activityId);
		condition.setActivityType(activityType);
		condition.setProviderType(ActivityServiceProviderEnum.ProviderType.Fund.getKey());
		condition.setProviderId(providerId);
		condition.setDeleteFlag(false);
		List<TActivityServiceProvider> list = tActivityServiceProviderMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageInfo<TActivityServiceProvider> getActivityServiceDisposeProviderEnrollRecord(int page, int perPage, Map<String, Object> params) {
		PageHelper.startPage(page, perPage);
		List<TActivityServiceProvider> list = tActivityServiceProviderMapper.getActivityServiceDisposeProviderEnrollRecord(params);
		return new PageInfo<>(list);
	}

	@Override
	public int countFundBy(Integer activityId, String activityType) {
		TActivityServiceProviderCondition condition = new TActivityServiceProviderCondition();
		condition.setActivityId(activityId);
		condition.setActivityType(activityType);
		condition.setProviderType(ActivityServiceProviderEnum.ProviderType.Fund.getKey());
		condition.setDeleteFlag(false);
		List<TActivityServiceProvider> list = tActivityServiceProviderMapper.selectByCondition(condition);
		return list.size();
	}

	@Override
	public PageInfo<TActivityServiceProvider> getActivityServiceFundProviderEnrollRecord(int page, int perPage, Map<String, Object> params) {
		PageHelper.startPage(page, perPage);
		List<TActivityServiceProvider> list = tActivityServiceProviderMapper.getActivityServiceFundProviderEnrollRecord(params);
		return new PageInfo<>(list);
	}

	@Override
	public int deleteBackDisposeProvider(Integer activityId, String activityType) {
		TActivityServiceProviderCondition condition = new TActivityServiceProviderCondition();
		condition.setActivityId(activityId);
		condition.setActivityType(activityType);
		condition.setProviderType(ActivityServiceProviderEnum.ProviderType.Dispose.getKey());
		condition.setDeleteFlag(false);
		condition.setSource(ActivityServiceProviderEnum.Source.Admin.getKey());
		List<TActivityServiceProvider> list = tActivityServiceProviderMapper.selectByCondition(condition);
		if (list.size() > 0) {
			for (TActivityServiceProvider item : list) {
				item.setDeleteFlag(true);
				tActivityServiceProviderMapper.updateById(item);
			}
		}
		return list.size();
	}

	@Override
	public TDisposeProvider getFirstLevelDisposeProvider(Integer cityId) {
		return tActivityServiceProviderMapper.getFirstLevelDisposeProvider(cityId);
	}

	@Override
	public TActivityServiceProvider getAuctionDisposeProvider(Integer activityId, Integer providerId) {
		TActivityServiceProviderCondition condition = new TActivityServiceProviderCondition();
		condition.setActivityId(activityId);
		condition.setActivityType(ActivityServiceProviderEnum.ActivityType.Auction.getKey());
		condition.setProviderType(ActivityServiceProviderEnum.ProviderType.Dispose.getKey());
		condition.setProviderId(providerId);
		condition.setDeleteFlag(false);
		List<TActivityServiceProvider> list = tActivityServiceProviderMapper.selectByCondition(condition);
		if (list.size() > 0) {
			if (list.size() > 1) {
				for (int i = 1; i < list.size(); i ++) {
					TActivityServiceProvider item = list.get(i);
					item.setDeleteFlag(true);
					tActivityServiceProviderMapper.updateById(item);
				}
			}
			return list.get(0);
		}
		return null;
	}

	@Override
	public int removeFirstLevelAuctionDisposeProviderByProviderId(Integer providerId) {
		TActivityServiceProviderCondition condition = new TActivityServiceProviderCondition();
		condition.setProviderId(providerId);
		condition.setActivityType(ActivityServiceProviderEnum.ActivityType.Auction.getKey());
		condition.setProviderType(ActivityServiceProviderEnum.ProviderType.Dispose.getKey());
		condition.setDeleteFlag(false);
		condition.setCityPartnerFlag(true);
		List<TActivityServiceProvider> list = tActivityServiceProviderMapper.selectByCondition(condition);
		for (TActivityServiceProvider item : list) {
			item.setDeleteFlag(true);
			tActivityServiceProviderMapper.updateById(item);
		}
		return 0;
	}
}


