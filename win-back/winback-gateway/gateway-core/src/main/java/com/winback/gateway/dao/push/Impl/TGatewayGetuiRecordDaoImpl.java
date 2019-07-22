
package com.winback.gateway.dao.push.Impl;

import javax.annotation.Resource;

import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.gateway.condition.push.TGatewayGetuiRecordCondition;
import com.winback.gateway.model.push.TGatewayGetuiRecord;
import org.springframework.stereotype.Service;

import com.winback.gateway.condition.push.TGatewayGetuiRecordCondition;
import com.winback.gateway.dao.push.mapper.TGatewayGetuiRecordMapper;
import com.winback.gateway.model.push.TGatewayGetuiRecord;

import com.winback.gateway.dao.push.TGatewayGetuiRecordDao;

@Service
public class TGatewayGetuiRecordDaoImpl extends AbstractDaoImpl<TGatewayGetuiRecord, TGatewayGetuiRecordCondition, BaseMapper<TGatewayGetuiRecord,TGatewayGetuiRecordCondition>> implements TGatewayGetuiRecordDao{
	
	@Resource
	private TGatewayGetuiRecordMapper tGatewayGetuiRecordMapper;
	
	@Override
	protected BaseMapper<TGatewayGetuiRecord, TGatewayGetuiRecordCondition> daoSupport() {
		return tGatewayGetuiRecordMapper;
	}

	@Override
	protected TGatewayGetuiRecordCondition blankCondition() {
		return new TGatewayGetuiRecordCondition();
	}

}
