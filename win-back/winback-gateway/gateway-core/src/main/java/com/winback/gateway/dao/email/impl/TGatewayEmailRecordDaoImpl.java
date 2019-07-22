
package com.winback.gateway.dao.email.impl;

import javax.annotation.Resource;

import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.gateway.condition.email.TGatewayEmailRecordCondition;
import com.winback.gateway.model.email.TGatewayEmailRecord;
import org.springframework.stereotype.Service;

import com.winback.gateway.condition.email.TGatewayEmailRecordCondition;
import com.winback.gateway.dao.email.mapper.TGatewayEmailRecordMapper;
import com.winback.gateway.model.email.TGatewayEmailRecord;
import com.winback.gateway.dao.email.TGatewayEmailRecordDao;

@Service
public class TGatewayEmailRecordDaoImpl extends AbstractDaoImpl<TGatewayEmailRecord, TGatewayEmailRecordCondition, BaseMapper<TGatewayEmailRecord,TGatewayEmailRecordCondition>> implements TGatewayEmailRecordDao{
	
	@Resource
	private TGatewayEmailRecordMapper tGatewayEmailRecordMapper;
	
	@Override
	protected BaseMapper<TGatewayEmailRecord, TGatewayEmailRecordCondition> daoSupport() {
		return tGatewayEmailRecordMapper;
	}

	@Override
	protected TGatewayEmailRecordCondition blankCondition() {
		return new TGatewayEmailRecordCondition();
	}

}
