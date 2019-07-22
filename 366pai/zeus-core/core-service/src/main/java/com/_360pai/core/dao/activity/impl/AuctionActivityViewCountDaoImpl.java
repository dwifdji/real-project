
package com._360pai.core.dao.activity.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.activity.AuctionActivityViewCountCondition;
import com._360pai.core.dao.activity.mapper.AuctionActivityViewCountMapper;
import com._360pai.core.model.activity.AuctionActivityViewCount;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.activity.AuctionActivityViewCountDao;

import java.util.List;

@Service
public class AuctionActivityViewCountDaoImpl extends AbstractDaoImpl<AuctionActivityViewCount, AuctionActivityViewCountCondition, BaseMapper<AuctionActivityViewCount,AuctionActivityViewCountCondition>> implements AuctionActivityViewCountDao{
	
	@Resource
	private AuctionActivityViewCountMapper auctionActivityViewCountMapper;
	
	@Override
	protected BaseMapper<AuctionActivityViewCount, AuctionActivityViewCountCondition> daoSupport() {
		return auctionActivityViewCountMapper;
	}

	@Override
	protected AuctionActivityViewCountCondition blankCondition() {
		return new AuctionActivityViewCountCondition();
	}

	@Override
	public int viewCount(Integer activityId) {
		AuctionActivityViewCountCondition condition = new AuctionActivityViewCountCondition();
		condition.setActivityId(activityId);
		List<AuctionActivityViewCount> list = auctionActivityViewCountMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0).getViewCount();
		}
		return 0;
	}
}
