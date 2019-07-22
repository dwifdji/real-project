
package com.tzCloud.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.account.TAccountSearchRecordCondition;
import com.tzCloud.core.dao.account.mapper.TAccountSearchRecordMapper;
import com.tzCloud.core.model.account.TAccountSearchRecord;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.account.TAccountSearchRecordDao;

@Service
public class TAccountSearchRecordDaoImpl extends AbstractDaoImpl<TAccountSearchRecord, TAccountSearchRecordCondition, BaseMapper<TAccountSearchRecord,TAccountSearchRecordCondition>> implements TAccountSearchRecordDao{
	
	@Resource
	private TAccountSearchRecordMapper tAccountSearchRecordMapper;
	
	@Override
	protected BaseMapper<TAccountSearchRecord, TAccountSearchRecordCondition> daoSupport() {
		return tAccountSearchRecordMapper;
	}

	@Override
	protected TAccountSearchRecordCondition blankCondition() {
		return new TAccountSearchRecordCondition();
	}

}
