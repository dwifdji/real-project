
package com._360pai.core.dao.email.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.email.GatewayEmailRecordCondition;
import com._360pai.core.dao.email.mapper.GatewayEmailRecordMapper;
import com._360pai.core.model.email.GatewayEmailRecord;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.email.GatewayEmailRecordDao;

@Service
public class GatewayEmailRecordDaoImpl extends AbstractDaoImpl<GatewayEmailRecord, GatewayEmailRecordCondition, BaseMapper<GatewayEmailRecord,GatewayEmailRecordCondition>> implements GatewayEmailRecordDao{
	
	@Resource
	private GatewayEmailRecordMapper gatewayEmailRecordMapper;
	
	@Override
	protected BaseMapper<GatewayEmailRecord, GatewayEmailRecordCondition> daoSupport() {
		return gatewayEmailRecordMapper;
	}

	@Override
	protected GatewayEmailRecordCondition blankCondition() {
		return new GatewayEmailRecordCondition();
	}

}
