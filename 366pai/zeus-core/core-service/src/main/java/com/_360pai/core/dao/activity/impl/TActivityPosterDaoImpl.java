
package com._360pai.core.dao.activity.impl;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.activity.TActivityPosterCondition;
import com._360pai.core.dao.activity.mapper.TActivityPosterMapper;
import com._360pai.core.model.activity.TActivityPoster;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.activity.TActivityPosterDao;

import java.util.List;
import java.util.Map;

@Service
public class TActivityPosterDaoImpl extends AbstractDaoImpl<TActivityPoster, TActivityPosterCondition, BaseMapper<TActivityPoster,TActivityPosterCondition>> implements TActivityPosterDao{
	
	@Resource
	private TActivityPosterMapper tActivityPosterMapper;
	
	@Override
	protected BaseMapper<TActivityPoster, TActivityPosterCondition> daoSupport() {
		return tActivityPosterMapper;
	}

	@Override
	protected TActivityPosterCondition blankCondition() {
		return new TActivityPosterCondition();
	}

	@Override
	public PageInfo<TActivityPoster> getBackendList(int page, int perPage, Map<String, Object> params) {
		PageHelper.startPage(page, perPage);
		TActivityPosterCondition condition = new TActivityPosterCondition();
		condition.setDeleteFlag(false);
		List<TActivityPoster> list = tActivityPosterMapper.selectByCondition(condition);
		return new PageInfo<>(list);
	}

	@Override
	public List<TActivityPoster> getFrontList(Map<String, Object> params) {
		return tActivityPosterMapper.getFrontList(params);
	}

	@Override
	public List<Map> getManualAuctionActivityList(List<Integer> activityIdList) {
		return tActivityPosterMapper.getManualAuctionActivityList(activityIdList);
	}

	@Override
	public List<Map> getManualEnrollingActivityList(List<Integer> activityIdList) {
		return tActivityPosterMapper.getManualEnrollingActivityList(activityIdList);
	}
}
