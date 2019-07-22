
package com._360pai.core.dao.order.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.order.TServiceBusinessRecordCondition;
import com._360pai.core.dao.order.mapper.TServiceBusinessRecordMapper;
import com._360pai.core.model.order.TServiceBusinessRecord;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.order.TServiceBusinessRecordDao;

import java.util.List;

@Service
public class TServiceBusinessRecordDaoImpl extends AbstractDaoImpl<TServiceBusinessRecord, TServiceBusinessRecordCondition, BaseMapper<TServiceBusinessRecord,TServiceBusinessRecordCondition>> implements TServiceBusinessRecordDao{
	
	@Resource
	private TServiceBusinessRecordMapper tServiceBusinessRecordMapper;
	
	@Override
	protected BaseMapper<TServiceBusinessRecord, TServiceBusinessRecordCondition> daoSupport() {
		return tServiceBusinessRecordMapper;
	}

	@Override
	protected TServiceBusinessRecordCondition blankCondition() {
		return new TServiceBusinessRecordCondition();
	}

	@Override
	public List<TServiceBusinessRecord> getBusinessRecordByReportType(TServiceBusinessRecordCondition condition) {
		return tServiceBusinessRecordMapper.getBusinessRecordByReportType(condition);
	}
}
