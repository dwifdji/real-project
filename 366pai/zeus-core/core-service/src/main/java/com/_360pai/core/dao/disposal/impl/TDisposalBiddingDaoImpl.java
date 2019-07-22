
package com._360pai.core.dao.disposal.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.disposal.TDisposalBiddingCondition;
import com._360pai.core.dao.disposal.mapper.TDisposalBiddingMapper;
import com._360pai.core.model.disposal.TDisposalBidding;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.disposal.TDisposalBiddingDao;

import java.util.List;

@Service
public class TDisposalBiddingDaoImpl extends AbstractDaoImpl<TDisposalBidding, TDisposalBiddingCondition, BaseMapper<TDisposalBidding,TDisposalBiddingCondition>> implements TDisposalBiddingDao{
	
	@Resource
	private TDisposalBiddingMapper tDisposalBiddingMapper;
	
	@Override
	protected BaseMapper<TDisposalBidding, TDisposalBiddingCondition> daoSupport() {
		return tDisposalBiddingMapper;
	}

	@Override
	protected TDisposalBiddingCondition blankCondition() {
		return new TDisposalBiddingCondition();
	}

	@Override
	public List<TDisposalBidding> findBiddingInfoList(Integer partyId) {
		return tDisposalBiddingMapper.findBiddingInfoList(partyId);
	}

	@Override
	public List<TDisposalBidding> findInvestmentInfo() {
		return tDisposalBiddingMapper.findInvestmentInfo();
	}

	@Override
	public int updateManuallyFinished(TDisposalBidding condition) {
		return tDisposalBiddingMapper.updateManuallyFinished(condition);
	}

	@Override
	public List<TDisposalBidding> findBiddingInfoListByCondition(TDisposalBiddingCondition condition) {
		return tDisposalBiddingMapper.findBiddingInfoListByCondition(condition);
	}
}
