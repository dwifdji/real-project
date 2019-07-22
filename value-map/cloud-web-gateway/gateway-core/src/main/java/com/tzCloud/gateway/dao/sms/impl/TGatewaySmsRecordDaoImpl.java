
package com.tzCloud.gateway.dao.sms.impl;

import javax.annotation.Resource;

import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.gateway.condition.sms.TGatewaySmsRecordCondition;
import com.tzCloud.gateway.dao.sms.mapper.TGatewaySmsRecordMapper;
import com.tzCloud.gateway.model.sms.TGatewaySmsRecord;
import org.springframework.stereotype.Service;

import com.tzCloud.gateway.condition.sms.TGatewaySmsRecordCondition;
import com.tzCloud.gateway.dao.sms.mapper.TGatewaySmsRecordMapper;
import com.tzCloud.gateway.model.sms.TGatewaySmsRecord;
import com.tzCloud.gateway.dao.sms.TGatewaySmsRecordDao;

@Service
public class TGatewaySmsRecordDaoImpl extends AbstractDaoImpl<TGatewaySmsRecord, TGatewaySmsRecordCondition, BaseMapper<TGatewaySmsRecord,TGatewaySmsRecordCondition>> implements TGatewaySmsRecordDao{
	
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
