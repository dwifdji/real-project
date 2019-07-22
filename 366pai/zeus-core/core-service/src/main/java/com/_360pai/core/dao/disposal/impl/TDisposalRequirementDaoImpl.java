
package com._360pai.core.dao.disposal.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.disposal.TDisposalBiddingCondition;
import com._360pai.core.condition.disposal.TDisposalRequirementCondition;
import com._360pai.core.dao.disposal.TDisposalRequirementDao;
import com._360pai.core.dao.disposal.mapper.TDisposalRequirementMapper;
import com._360pai.core.facade.account.req.AcctReq;
import com._360pai.core.model.disposal.TDisposalBidding;
import com._360pai.core.model.disposal.TDisposalRequirement;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TDisposalRequirementDaoImpl extends AbstractDaoImpl<TDisposalRequirement, TDisposalRequirementCondition, BaseMapper<TDisposalRequirement,TDisposalRequirementCondition>> implements TDisposalRequirementDao{
	
	@Resource
	private TDisposalRequirementMapper tDisposalRequirementMapper;
	
	@Override
	protected BaseMapper<TDisposalRequirement, TDisposalRequirementCondition> daoSupport() {
		return tDisposalRequirementMapper;
	}

	@Override
	protected TDisposalRequirementCondition blankCondition() {
		return new TDisposalRequirementCondition();
	}

	@Override
	public List<TDisposalRequirement> findBySimilar(TDisposalRequirement requirement) {
		return tDisposalRequirementMapper.findBySimilar(requirement);
	}

	@Override
	public List<TDisposalRequirement> findByHotDisposalList(String disposalType) {
		return tDisposalRequirementMapper.findByHotDisposalList(disposalType);
	}

    @Override
    public List<TDisposalBidding> findBiddingByDisposalId(TDisposalBiddingCondition req) {
        return tDisposalRequirementMapper.findBiddingByDisposalId(req);
    }

	@Override
	public PageInfo findDisposalRequirementListPage(int page, int perPage, TDisposalRequirementCondition req) {
		PageHelper.startPage(page, perPage);
		List<TDisposalRequirement> list = tDisposalRequirementMapper.findDisposalRequirementListPage(req);
		return new PageInfo<>(list);
	}

	@Override
	public List<TDisposalRequirement> findDisposalConditionAdmin(TDisposalRequirementCondition condition) {
		return tDisposalRequirementMapper.findDisposalConditionAdmin(condition);
	}

	@Override
	public int updateRequirementStatusByBiddingId(String status, Integer biddingId, Integer operatorId) {
		return tDisposalRequirementMapper.updateRequirementStatusByBiddingId(status, biddingId, operatorId);
	}

	@Override
	public int updateBiddingStatusByBiddingId(String status, Integer biddingId, Integer operatorId) {
		return tDisposalRequirementMapper.updateBiddingStatusByBiddingId(status, biddingId, operatorId);
	}

    @Override
    public TDisposalRequirement selectByIdWithoutPay(String id) {
        return tDisposalRequirementMapper.selectByIdWithoutPay(id);
    }

	@Override
	public List<TDisposalBidding> findBiddingSituation(Integer disposalId) {
		return tDisposalRequirementMapper.findBiddingSituation(disposalId);
	}

	@Override
	public int updateManuallyFinished(TDisposalRequirement condition) {
		return tDisposalRequirementMapper.updateManuallyFinished(condition);
	}

	@Override
	public TDisposalRequirement findRequirementDetail(Integer disposalId) {
		return tDisposalRequirementMapper.findRequirementDetail(disposalId);
	}

	@Override
	public int updateBiddingNumListByIds(Integer[] disposalIds) {
		return tDisposalRequirementMapper.updateBiddingNumListByIds(disposalIds);
	}

	@Override
	public int updateBiddingNumById(Integer disposalId) {
		return tDisposalRequirementMapper.updateBiddingNumById(disposalId);
	}

	@Override
	public List<TDisposalRequirement> findDisposalFollowListPage(TDisposalRequirementCondition req) {
		return tDisposalRequirementMapper.findDisposalFollowListPage(req);
	}

	@Override
	public int confirmBid(TDisposalRequirement condition) {
		return tDisposalRequirementMapper.confirmBid(condition);
	}

    @Override
    public List<TDisposalRequirement> mySelectList(TDisposalRequirementCondition condition){
		return tDisposalRequirementMapper.mySelectList(condition);
    }

	@Override
	public PageInfo getDisposalActivityByAccountId(AcctReq.ViewRecordRequest viewRecordRequest) {
		PageHelper.startPage(viewRecordRequest.getPage(), viewRecordRequest.getPerPage());
		List<TDisposalRequirement> list = tDisposalRequirementMapper.getDisposalActivityByAccountId(viewRecordRequest.getAccountId(), viewRecordRequest.getPartyId());
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo getListByPage(int page, int perPage) {
		PageHelper.startPage(page, perPage);
		List<TDisposalRequirement> list = tDisposalRequirementMapper.selectAll();
		return new PageInfo<>(list);
	}
}
