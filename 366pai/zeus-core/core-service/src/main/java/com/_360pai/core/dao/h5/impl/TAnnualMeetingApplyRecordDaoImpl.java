
package com._360pai.core.dao.h5.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.h5.TAnnualMeetingApplyRecordCondition;
import com._360pai.core.dao.h5.mapper.TAnnualMeetingApplyRecordMapper;
import com._360pai.core.model.h5.TAnnualMeetingApplyRecord;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.h5.TAnnualMeetingApplyRecordDao;

import java.util.List;

@Service
public class TAnnualMeetingApplyRecordDaoImpl extends AbstractDaoImpl<TAnnualMeetingApplyRecord, TAnnualMeetingApplyRecordCondition, BaseMapper<TAnnualMeetingApplyRecord,TAnnualMeetingApplyRecordCondition>> implements TAnnualMeetingApplyRecordDao{
	
	@Resource
	private TAnnualMeetingApplyRecordMapper tAnnualMeetingApplyRecordMapper;
	
	@Override
	protected BaseMapper<TAnnualMeetingApplyRecord, TAnnualMeetingApplyRecordCondition> daoSupport() {
		return tAnnualMeetingApplyRecordMapper;
	}

	@Override
	protected TAnnualMeetingApplyRecordCondition blankCondition() {
		return new TAnnualMeetingApplyRecordCondition();
	}

	@Override
	public boolean isAlreadyApply(String mobile) {
		TAnnualMeetingApplyRecordCondition condition = new TAnnualMeetingApplyRecordCondition();
		condition.setMobile(mobile);
		List<TAnnualMeetingApplyRecord> list = tAnnualMeetingApplyRecordMapper.selectByCondition(condition);
		return list.size() > 0;
	}
}
