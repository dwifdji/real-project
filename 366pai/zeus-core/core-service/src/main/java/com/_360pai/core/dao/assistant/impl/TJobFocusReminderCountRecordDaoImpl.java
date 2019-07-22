
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.TJobFocusReminderCountRecordCondition;
import com._360pai.core.dao.assistant.mapper.TJobFocusReminderCountRecordMapper;
import com._360pai.core.model.assistant.TJobFocusReminderCountRecord;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.TJobFocusReminderCountRecordDao;

@Service
public class TJobFocusReminderCountRecordDaoImpl extends AbstractDaoImpl<TJobFocusReminderCountRecord, TJobFocusReminderCountRecordCondition, BaseMapper<TJobFocusReminderCountRecord,TJobFocusReminderCountRecordCondition>> implements TJobFocusReminderCountRecordDao{
	
	@Resource
	private TJobFocusReminderCountRecordMapper tJobFocusReminderCountRecordMapper;
	
	@Override
	protected BaseMapper<TJobFocusReminderCountRecord, TJobFocusReminderCountRecordCondition> daoSupport() {
		return tJobFocusReminderCountRecordMapper;
	}

	@Override
	protected TJobFocusReminderCountRecordCondition blankCondition() {
		return new TJobFocusReminderCountRecordCondition();
	}

}
