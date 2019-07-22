
package com._360pai.core.dao.pay.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.pay.GatewayPayBackRecordCondition;
import com._360pai.core.dao.pay.mapper.GatewayPayBackRecordMapper;
import com._360pai.core.model.pay.GatewayPayBackRecord;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.pay.GatewayPayBackRecordDao;

@Service
public class GatewayPayBackRecordDaoImpl extends AbstractDaoImpl<GatewayPayBackRecord, GatewayPayBackRecordCondition, BaseMapper<GatewayPayBackRecord,GatewayPayBackRecordCondition>> implements GatewayPayBackRecordDao{
	
	@Resource
	private GatewayPayBackRecordMapper gatewayPayBackRecordMapper;
	
	@Override
	protected BaseMapper<GatewayPayBackRecord, GatewayPayBackRecordCondition> daoSupport() {
		return gatewayPayBackRecordMapper;
	}

	@Override
	protected GatewayPayBackRecordCondition blankCondition() {
		return new GatewayPayBackRecordCondition();
	}

}
