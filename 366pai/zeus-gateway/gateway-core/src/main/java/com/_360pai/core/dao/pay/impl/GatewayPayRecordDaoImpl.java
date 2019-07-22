
package com._360pai.core.dao.pay.impl;

import javax.annotation.Resource;

import com._360pai.core.condition.pay.GatewayPayRecordCondition;
import com._360pai.core.dao.pay.GatewayPayRecordDao;
import com._360pai.core.dao.pay.mapper.GatewayPayRecordMapper;
import com._360pai.core.model.pay.GatewayPayRecord;
import org.springframework.stereotype.Service;


import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;

@Service
public class GatewayPayRecordDaoImpl extends AbstractDaoImpl<GatewayPayRecord, GatewayPayRecordCondition, BaseMapper<GatewayPayRecord,GatewayPayRecordCondition>> implements GatewayPayRecordDao {
	
	@Resource
	private GatewayPayRecordMapper gatewayPayRecordMapper;
	
	@Override
	protected BaseMapper<GatewayPayRecord, GatewayPayRecordCondition> daoSupport() {
		return gatewayPayRecordMapper;
	}

	@Override
	protected GatewayPayRecordCondition blankCondition() {
		return new GatewayPayRecordCondition();
	}

}
