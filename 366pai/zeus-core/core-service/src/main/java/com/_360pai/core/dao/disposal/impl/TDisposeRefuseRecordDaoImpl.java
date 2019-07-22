
package com._360pai.core.dao.disposal.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.disposal.TDisposeRefuseRecordCondition;
import com._360pai.core.dao.disposal.mapper.TDisposeRefuseRecordMapper;
import com._360pai.core.model.disposal.TDisposeRefuseRecord;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.disposal.TDisposeRefuseRecordDao;

@Service
public class TDisposeRefuseRecordDaoImpl extends AbstractDaoImpl<TDisposeRefuseRecord, TDisposeRefuseRecordCondition, BaseMapper<TDisposeRefuseRecord,TDisposeRefuseRecordCondition>> implements TDisposeRefuseRecordDao{
	
	@Resource
	private TDisposeRefuseRecordMapper tDisposeRefuseRecordMapper;
	
	@Override
	protected BaseMapper<TDisposeRefuseRecord, TDisposeRefuseRecordCondition> daoSupport() {
		return tDisposeRefuseRecordMapper;
	}

	@Override
	protected TDisposeRefuseRecordCondition blankCondition() {
		return new TDisposeRefuseRecordCondition();
	}

}
