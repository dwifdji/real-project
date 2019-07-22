
package com._360pai.core.dao.fdd.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.fdd.GatewayFddCallBackRecordCondition;
import com._360pai.core.dao.fdd.GatewayFddCallBackRecordDao;
import com._360pai.core.dao.fdd.mapper.GatewayFddCallBackRecordMapper;
import com._360pai.core.model.fdd.GatewayFddCallBackRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GatewayFddCallBackRecordDaoImpl extends AbstractDaoImpl<GatewayFddCallBackRecord, GatewayFddCallBackRecordCondition, BaseMapper<GatewayFddCallBackRecord,GatewayFddCallBackRecordCondition>> implements GatewayFddCallBackRecordDao{
	
	@Resource
	private GatewayFddCallBackRecordMapper gatewayFddCallBackRecordMapper;
	
	@Override
	protected BaseMapper<GatewayFddCallBackRecord, GatewayFddCallBackRecordCondition> daoSupport() {
		return gatewayFddCallBackRecordMapper;
	}

	@Override
	protected GatewayFddCallBackRecordCondition blankCondition() {
		return new GatewayFddCallBackRecordCondition();
	}


	@Override
	public List<GatewayFddCallBackRecord> notifyFailureList() {
		return gatewayFddCallBackRecordMapper.notifyFailureList();
	}
}
