
package com._360pai.core.dao.activity.impl;

import javax.annotation.Resource;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.exception.BusinessException;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.activity.AuctionActivityShareStatsCondition;
import com._360pai.core.dao.activity.mapper.AuctionActivityShareStatsMapper;
import com._360pai.core.model.activity.AuctionActivityShareStats;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.activity.AuctionActivityShareStatsDao;

import java.util.Date;
import java.util.List;

@Service
public class AuctionActivityShareStatsDaoImpl extends AbstractDaoImpl<AuctionActivityShareStats, AuctionActivityShareStatsCondition, BaseMapper<AuctionActivityShareStats,AuctionActivityShareStatsCondition>> implements AuctionActivityShareStatsDao{
	
	@Resource
	private AuctionActivityShareStatsMapper auctionActivityShareStatsMapper;
	
	@Override
	protected BaseMapper<AuctionActivityShareStats, AuctionActivityShareStatsCondition> daoSupport() {
		return auctionActivityShareStatsMapper;
	}

	@Override
	protected AuctionActivityShareStatsCondition blankCondition() {
		return new AuctionActivityShareStatsCondition();
	}

	@Override
	public void createIfNotExist(Integer activityId, Integer accountId, Integer partyId) {
		AuctionActivityShareStatsCondition condition = new AuctionActivityShareStatsCondition();
		condition.setActivityId(activityId);
		condition.setAccountId(accountId);
		condition.setPartyId(partyId);
		List<AuctionActivityShareStats> list = auctionActivityShareStatsMapper.selectByCondition(condition);
		if (list.isEmpty()) {
			AuctionActivityShareStats model = new AuctionActivityShareStats();
			model.setActivityId(activityId);
			model.setAccountId(accountId);
			model.setPartyId(partyId);
			model.setCreatedAt(new Date());
			int result = auctionActivityShareStatsMapper.insert(model);
			if (result == 0) {
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		}
	}

	@Override
	public int activityShareCount(Integer activityId) {
		AuctionActivityShareStatsCondition condition = new AuctionActivityShareStatsCondition();
		condition.setActivityId(activityId);
		List<AuctionActivityShareStats> list = auctionActivityShareStatsMapper.selectByCondition(condition);
		return list.size();
	}
}
