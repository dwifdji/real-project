
package com._360pai.core.dao.fdd.impl;

import javax.annotation.Resource;

import com._360pai.core.condition.fdd.GatewayFddRecordCondition;
import com._360pai.core.dao.fdd.GatewayFddRecordDao;
import com._360pai.core.dao.fdd.mapper.GatewayFddRecordMapper;
import com._360pai.core.model.fdd.GatewayFddRecord;
import org.springframework.stereotype.Service;


import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;

@Service
public class GatewayFddRecordDaoImpl extends AbstractDaoImpl<GatewayFddRecord, GatewayFddRecordCondition, BaseMapper<GatewayFddRecord,GatewayFddRecordCondition>> implements GatewayFddRecordDao {
	
	@Resource
	private GatewayFddRecordMapper gatewayFddRecordMapper;
	
	@Override
	protected BaseMapper<GatewayFddRecord, GatewayFddRecordCondition> daoSupport() {
		return gatewayFddRecordMapper;
	}

	@Override
	protected GatewayFddRecordCondition blankCondition() {
		return new GatewayFddRecordCondition();
	}

}
