
package com.tzCloud.gateway.dao.pay.impl;

import javax.annotation.Resource;

import com.tzCloud.gateway.dao.pay.mapper.TGatewayPayRecordMapper;
import org.springframework.stereotype.Service;

import com.tzCloud.gateway.condition.pay.TGatewayPayRecordCondition;
import com.tzCloud.gateway.dao.pay.mapper.TGatewayPayRecordMapper;
import com.tzCloud.gateway.model.pay.TGatewayPayRecord;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.gateway.dao.pay.TGatewayPayRecordDao;

@Service
public class TGatewayPayRecordDaoImpl extends AbstractDaoImpl<TGatewayPayRecord, TGatewayPayRecordCondition, BaseMapper<TGatewayPayRecord,TGatewayPayRecordCondition>> implements TGatewayPayRecordDao{
	
	@Resource
	private TGatewayPayRecordMapper tGatewayPayRecordMapper;
	
	@Override
	protected BaseMapper<TGatewayPayRecord, TGatewayPayRecordCondition> daoSupport() {
		return tGatewayPayRecordMapper;
	}

	@Override
	protected TGatewayPayRecordCondition blankCondition() {
		return new TGatewayPayRecordCondition();
	}

}
