
package com.winback.gateway.dao.sms.impl;

import javax.annotation.Resource;

import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.gateway.condition.sms.TGatewaySmsRecordCondition;
import com.winback.gateway.dao.sms.TGatewaySmsRecordDao;
import com.winback.gateway.model.sms.TGatewaySmsRecord;
import org.springframework.stereotype.Service;

import com.winback.gateway.condition.sms.TGatewaySmsRecordCondition;
import com.winback.gateway.dao.sms.mapper.TGatewaySmsRecordMapper;
import com.winback.gateway.model.sms.TGatewaySmsRecord;
import com.winback.gateway.dao.sms.TGatewaySmsRecordDao;

@Service
public class TGatewaySmsRecordDaoImpl extends AbstractDaoImpl<TGatewaySmsRecord, TGatewaySmsRecordCondition, BaseMapper<TGatewaySmsRecord,TGatewaySmsRecordCondition>> implements TGatewaySmsRecordDao {
	
	@Resource
	private TGatewaySmsRecordMapper tGatewaySmsRecordMapper;
	
	@Override
	protected BaseMapper<TGatewaySmsRecord, TGatewaySmsRecordCondition> daoSupport() {
		return tGatewaySmsRecordMapper;
	}

	@Override
	protected TGatewaySmsRecordCondition blankCondition() {
		return new TGatewaySmsRecordCondition();
	}

}
