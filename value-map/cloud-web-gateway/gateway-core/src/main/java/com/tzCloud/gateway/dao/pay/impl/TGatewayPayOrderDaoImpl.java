
package com.tzCloud.gateway.dao.pay.impl;

import javax.annotation.Resource;

import com.tzCloud.gateway.dao.pay.TGatewayPayOrderDao;
import com.tzCloud.gateway.dao.pay.mapper.TGatewayPayOrderMapper;
import org.springframework.stereotype.Service;

import com.tzCloud.gateway.condition.pay.TGatewayPayOrderCondition;
import com.tzCloud.gateway.dao.pay.mapper.TGatewayPayOrderMapper;
import com.tzCloud.gateway.model.pay.TGatewayPayOrder;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.gateway.dao.pay.TGatewayPayOrderDao;

@Service
public class TGatewayPayOrderDaoImpl extends AbstractDaoImpl<TGatewayPayOrder, TGatewayPayOrderCondition, BaseMapper<TGatewayPayOrder,TGatewayPayOrderCondition>> implements TGatewayPayOrderDao {
	
	@Resource
	private TGatewayPayOrderMapper tGatewayPayOrderMapper;
	
	@Override
	protected BaseMapper<TGatewayPayOrder, TGatewayPayOrderCondition> daoSupport() {
		return tGatewayPayOrderMapper;
	}

	@Override
	protected TGatewayPayOrderCondition blankCondition() {
		return new TGatewayPayOrderCondition();
	}

}
