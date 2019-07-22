
package com.winback.gateway.dao.pay.impl;

import javax.annotation.Resource;

import com.winback.gateway.condition.pay.TGatewayPayRecordCondition;
import com.winback.gateway.dao.pay.TGatewayPayRecordDao;
import com.winback.gateway.dao.pay.mapper.TGatewayPayRecordMapper;
import com.winback.gateway.model.pay.TGatewayPayRecord;
import org.springframework.stereotype.Service;

import com.winback.gateway.condition.pay.TGatewayPayRecordCondition;
import com.winback.gateway.dao.pay.mapper.TGatewayPayRecordMapper;
import com.winback.gateway.model.pay.TGatewayPayRecord;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.gateway.dao.pay.TGatewayPayRecordDao;

@Service
public class TGatewayPayRecordDaoImpl extends AbstractDaoImpl<TGatewayPayRecord, TGatewayPayRecordCondition, BaseMapper<TGatewayPayRecord,TGatewayPayRecordCondition>> implements TGatewayPayRecordDao {
	
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
