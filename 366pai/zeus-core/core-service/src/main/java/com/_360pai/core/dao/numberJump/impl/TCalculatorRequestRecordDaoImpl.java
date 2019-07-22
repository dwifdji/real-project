
package com._360pai.core.dao.numberJump.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.numberJump.TCalculatorRequestRecordCondition;
import com._360pai.core.dao.numberJump.mapper.TCalculatorRequestRecordMapper;
import com._360pai.core.model.numberJump.TCalculatorRequestRecord;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.numberJump.TCalculatorRequestRecordDao;

@Service
public class TCalculatorRequestRecordDaoImpl extends AbstractDaoImpl<TCalculatorRequestRecord, TCalculatorRequestRecordCondition, BaseMapper<TCalculatorRequestRecord,TCalculatorRequestRecordCondition>> implements TCalculatorRequestRecordDao{
	
	@Resource
	private TCalculatorRequestRecordMapper tCalculatorRequestRecordMapper;
	
	@Override
	protected BaseMapper<TCalculatorRequestRecord, TCalculatorRequestRecordCondition> daoSupport() {
		return tCalculatorRequestRecordMapper;
	}

	@Override
	protected TCalculatorRequestRecordCondition blankCondition() {
		return new TCalculatorRequestRecordCondition();
	}

}
