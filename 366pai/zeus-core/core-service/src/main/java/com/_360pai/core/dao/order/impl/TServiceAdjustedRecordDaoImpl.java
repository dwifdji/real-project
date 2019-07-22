
package com._360pai.core.dao.order.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.order.TServiceAdjustedRecordCondition;
import com._360pai.core.dao.order.mapper.TServiceAdjustedRecordMapper;
import com._360pai.core.model.order.TServiceAdjustedRecord;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.order.TServiceAdjustedRecordDao;

import java.util.List;

@Service
public class TServiceAdjustedRecordDaoImpl extends AbstractDaoImpl<TServiceAdjustedRecord, TServiceAdjustedRecordCondition, BaseMapper<TServiceAdjustedRecord,TServiceAdjustedRecordCondition>> implements TServiceAdjustedRecordDao{
	
	@Resource
	private TServiceAdjustedRecordMapper tServiceAdjustedRecordMapper;
	
	@Override
	protected BaseMapper<TServiceAdjustedRecord, TServiceAdjustedRecordCondition> daoSupport() {
		return tServiceAdjustedRecordMapper;
	}

	@Override
	protected TServiceAdjustedRecordCondition blankCondition() {
		return new TServiceAdjustedRecordCondition();
	}

	@Override
	public List<TServiceAdjustedRecord> selectAdjustedRecordByIds(Integer[] adjustedIds) {
		return tServiceAdjustedRecordMapper.selectAdjustedRecordByIds(adjustedIds);
	}
}
