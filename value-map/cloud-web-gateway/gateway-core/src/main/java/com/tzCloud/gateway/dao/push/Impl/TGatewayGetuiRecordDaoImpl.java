
package com.tzCloud.gateway.dao.push.Impl;

import javax.annotation.Resource;

import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.gateway.condition.push.TGatewayGetuiRecordCondition;
import com.tzCloud.gateway.dao.push.TGatewayGetuiRecordDao;
import com.tzCloud.gateway.dao.push.mapper.TGatewayGetuiRecordMapper;
import com.tzCloud.gateway.model.push.TGatewayGetuiRecord;
import org.springframework.stereotype.Service;

import com.tzCloud.gateway.condition.push.TGatewayGetuiRecordCondition;
import com.tzCloud.gateway.dao.push.mapper.TGatewayGetuiRecordMapper;
import com.tzCloud.gateway.model.push.TGatewayGetuiRecord;

import com.tzCloud.gateway.dao.push.TGatewayGetuiRecordDao;

@Service
public class TGatewayGetuiRecordDaoImpl extends AbstractDaoImpl<TGatewayGetuiRecord, TGatewayGetuiRecordCondition, BaseMapper<TGatewayGetuiRecord,TGatewayGetuiRecordCondition>> implements TGatewayGetuiRecordDao {
	
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
