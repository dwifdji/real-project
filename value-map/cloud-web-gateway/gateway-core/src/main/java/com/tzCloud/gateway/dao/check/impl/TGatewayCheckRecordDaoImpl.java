
package com.tzCloud.gateway.dao.check.impl;

import javax.annotation.Resource;

import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.gateway.condition.check.TGatewayCheckRecordCondition;
import com.tzCloud.gateway.dao.check.mapper.TGatewayCheckRecordMapper;
import com.tzCloud.gateway.model.check.TGatewayCheckRecord;
import org.springframework.stereotype.Service;

import com.tzCloud.gateway.condition.check.TGatewayCheckRecordCondition;
import com.tzCloud.gateway.dao.check.mapper.TGatewayCheckRecordMapper;
import com.tzCloud.gateway.model.check.TGatewayCheckRecord;

import com.tzCloud.gateway.dao.check.TGatewayCheckRecordDao;

@Service
public class TGatewayCheckRecordDaoImpl extends AbstractDaoImpl<TGatewayCheckRecord, TGatewayCheckRecordCondition, BaseMapper<TGatewayCheckRecord,TGatewayCheckRecordCondition>> implements TGatewayCheckRecordDao{
	
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
