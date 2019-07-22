
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.TBackstageOperationRecordCondition;
import com._360pai.core.dao.assistant.mapper.TBackstageOperationRecordMapper;
import com._360pai.core.model.assistant.TBackstageOperationRecord;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.TBackstageOperationRecordDao;

@Service
public class TBackstageOperationRecordDaoImpl extends AbstractDaoImpl<TBackstageOperationRecord, TBackstageOperationRecordCondition, BaseMapper<TBackstageOperationRecord,TBackstageOperationRecordCondition>> implements TBackstageOperationRecordDao{
	
	@Resource
	private TBackstageOperationRecordMapper tBackstageOperationRecordMapper;
	
	@Override
	protected BaseMapper<TBackstageOperationRecord, TBackstageOperationRecordCondition> daoSupport() {
		return tBackstageOperationRecordMapper;
	}

	@Override
	protected TBackstageOperationRecordCondition blankCondition() {
		return new TBackstageOperationRecordCondition();
	}

}
