
package com.winback.gateway.dao.check.impl;

import javax.annotation.Resource;

import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.gateway.condition.check.TGatewayCheckRecordCondition;
import com.winback.gateway.dao.check.TGatewayCheckRecordDao;
import com.winback.gateway.model.check.TGatewayCheckRecord;
import org.springframework.stereotype.Service;

import com.winback.gateway.condition.check.TGatewayCheckRecordCondition;
import com.winback.gateway.dao.check.mapper.TGatewayCheckRecordMapper;
import com.winback.gateway.model.check.TGatewayCheckRecord;

import com.winback.gateway.dao.check.TGatewayCheckRecordDao;

@Service
public class TGatewayCheckRecordDaoImpl extends AbstractDaoImpl<TGatewayCheckRecord, TGatewayCheckRecordCondition, BaseMapper<TGatewayCheckRecord,TGatewayCheckRecordCondition>> implements TGatewayCheckRecordDao {
	
	@Resource
	private TGatewayCheckRecordMapper tGatewayCheckRecordMapper;
	
	@Override
	protected BaseMapper<TGatewayCheckRecord, TGatewayCheckRecordCondition> daoSupport() {
		return tGatewayCheckRecordMapper;
	}

	@Override
	protected TGatewayCheckRecordCondition blankCondition() {
		return new TGatewayCheckRecordCondition();
	}

}
