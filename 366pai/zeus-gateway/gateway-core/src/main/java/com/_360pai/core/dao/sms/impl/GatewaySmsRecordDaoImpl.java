
package com._360pai.core.dao.sms.impl;

import javax.annotation.Resource;

import com._360pai.core.condition.sms.GatewaySmsRecordCondition;
import com._360pai.core.dao.sms.GatewaySmsRecordDao;
import com._360pai.core.dao.sms.mapper.GatewaySmsRecordMapper;
import com._360pai.core.model.sms.GatewaySmsRecord;
import org.springframework.stereotype.Service;


import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;

@Service
public class GatewaySmsRecordDaoImpl extends AbstractDaoImpl<GatewaySmsRecord, GatewaySmsRecordCondition, BaseMapper<GatewaySmsRecord,GatewaySmsRecordCondition>> implements GatewaySmsRecordDao {
	
	@Resource
	private GatewaySmsRecordMapper gatewaySmsRecordMapper;
	
	@Override
	protected BaseMapper<GatewaySmsRecord, GatewaySmsRecordCondition> daoSupport() {
		return gatewaySmsRecordMapper;
	}

	@Override
	protected GatewaySmsRecordCondition blankCondition() {
		return new GatewaySmsRecordCondition();
	}

}
