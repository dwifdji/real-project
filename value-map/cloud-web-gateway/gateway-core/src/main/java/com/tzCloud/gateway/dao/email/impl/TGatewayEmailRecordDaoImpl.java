
package com.tzCloud.gateway.dao.email.impl;

import javax.annotation.Resource;

import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.gateway.condition.email.TGatewayEmailRecordCondition;
import com.tzCloud.gateway.dao.email.mapper.TGatewayEmailRecordMapper;
import com.tzCloud.gateway.model.email.TGatewayEmailRecord;
import org.springframework.stereotype.Service;

import com.tzCloud.gateway.condition.email.TGatewayEmailRecordCondition;
import com.tzCloud.gateway.dao.email.mapper.TGatewayEmailRecordMapper;
import com.tzCloud.gateway.model.email.TGatewayEmailRecord;
import com.tzCloud.gateway.dao.email.TGatewayEmailRecordDao;

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
