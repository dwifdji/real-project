
package com._360pai.core.dao.lease.impl;

import javax.annotation.Resource;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.vo.lease.LeaseApplyRecordVo;
import com._360pai.core.vo.lease.LeaseAuditRecordVo;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.lease.TLeaseAuditRecordCondition;
import com._360pai.core.dao.lease.mapper.TLeaseAuditRecordMapper;
import com._360pai.core.model.lease.TLeaseAuditRecord;
import com._360pai.core.dao.lease.TLeaseAuditRecordDao;

import java.util.List;
import java.util.Map;

@Service
public class TLeaseAuditRecordDaoImpl extends AbstractDaoImpl<TLeaseAuditRecord, TLeaseAuditRecordCondition, BaseMapper<TLeaseAuditRecord,TLeaseAuditRecordCondition>> implements TLeaseAuditRecordDao{
	
	@Resource
	private TLeaseAuditRecordMapper tLeaseAuditRecordMapper;
	
	@Override
	protected BaseMapper<TLeaseAuditRecord, TLeaseAuditRecordCondition> daoSupport() {
		return tLeaseAuditRecordMapper;
	}

	@Override
	protected TLeaseAuditRecordCondition blankCondition() {
		return new TLeaseAuditRecordCondition();
	}

	@Override
	public List<LeaseAuditRecordVo> getAuditRecordList(Map<String, Object> params) {
		return tLeaseAuditRecordMapper.getAuditRecordList(params);
	}

	@Override
	public List<LeaseApplyRecordVo> myApplyLeaseRecord(String name, String partyId) {
		return tLeaseAuditRecordMapper.myApplyLeaseRecord(name, partyId);
	}
}
