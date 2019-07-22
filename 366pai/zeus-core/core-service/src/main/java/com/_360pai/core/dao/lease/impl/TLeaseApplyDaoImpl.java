
package com._360pai.core.dao.lease.impl;

import javax.annotation.Resource;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.vo.lease.LeaseAuditVo;
import com._360pai.core.vo.lease.LeaseCompeteApplyVo;
import com._360pai.core.vo.lease.LeaseLeadAuditVo;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.lease.TLeaseApplyCondition;
import com._360pai.core.dao.lease.mapper.TLeaseApplyMapper;
import com._360pai.core.model.lease.TLeaseApply;

import com._360pai.core.dao.lease.TLeaseApplyDao;

import java.util.List;
import java.util.Map;

@Service
public class TLeaseApplyDaoImpl extends AbstractDaoImpl<TLeaseApply, TLeaseApplyCondition, BaseMapper<TLeaseApply,TLeaseApplyCondition>> implements TLeaseApplyDao{
	
	@Resource
	private TLeaseApplyMapper tLeaseApplyMapper;
	
	@Override
	protected BaseMapper<TLeaseApply, TLeaseApplyCondition> daoSupport() {
		return tLeaseApplyMapper;
	}

	@Override
	protected TLeaseApplyCondition blankCondition() {
		return new TLeaseApplyCondition();
	}


	@Override
	public List<LeaseAuditVo> myLeaseAuditList(Map<String, Object> params) {
		return tLeaseApplyMapper.myLeaseAuditList(params);
	}

	@Override
	public List<LeaseCompeteApplyVo> getCompeteApplyList(Map<String, Object> params) {
		return tLeaseApplyMapper.getCompeteApplyList(params);
	}

	@Override
	public List<LeaseLeadAuditVo> getLeadAuditList(Map<String, Object> params) {
		return tLeaseApplyMapper.getLeadAuditList(params);
	}

	@Override
	public List<LeaseCompeteApplyVo> getLeaseCompeteApply(TLeaseApplyCondition condition) {
		return tLeaseApplyMapper.getLeaseCompeteApply(condition);
	}



}
