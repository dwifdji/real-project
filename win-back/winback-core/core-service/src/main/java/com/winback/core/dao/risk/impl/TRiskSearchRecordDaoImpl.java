
package com.winback.core.dao.risk.impl;

import javax.annotation.Resource;

import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import org.springframework.stereotype.Service;

import com.winback.core.condition.risk.TRiskSearchRecordCondition;
import com.winback.core.dao.risk.mapper.TRiskSearchRecordMapper;
import com.winback.core.model.risk.TRiskSearchRecord;

import com.winback.core.dao.risk.TRiskSearchRecordDao;

@Service
public class TRiskSearchRecordDaoImpl extends AbstractDaoImpl<TRiskSearchRecord, TRiskSearchRecordCondition, BaseMapper<TRiskSearchRecord,TRiskSearchRecordCondition>> implements TRiskSearchRecordDao{
	
	@Resource
	private TRiskSearchRecordMapper tRiskSearchRecordMapper;
	
	@Override
	protected BaseMapper<TRiskSearchRecord, TRiskSearchRecordCondition> daoSupport() {
		return tRiskSearchRecordMapper;
	}

	@Override
	protected TRiskSearchRecordCondition blankCondition() {
		return new TRiskSearchRecordCondition();
	}

}
