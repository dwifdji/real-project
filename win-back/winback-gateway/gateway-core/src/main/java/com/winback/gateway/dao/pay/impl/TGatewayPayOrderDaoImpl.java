
package com.winback.gateway.dao.pay.impl;

import javax.annotation.Resource;

import com.winback.gateway.condition.pay.TGatewayPayOrderCondition;
import org.springframework.stereotype.Service;

import com.winback.gateway.condition.pay.TGatewayPayOrderCondition;
import com.winback.gateway.dao.pay.mapper.TGatewayPayOrderMapper;
import com.winback.gateway.model.pay.TGatewayPayOrder;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.gateway.dao.pay.TGatewayPayOrderDao;

import java.util.List;

@Service
public class TGatewayPayOrderDaoImpl extends AbstractDaoImpl<TGatewayPayOrder, TGatewayPayOrderCondition, BaseMapper<TGatewayPayOrder,TGatewayPayOrderCondition>> implements TGatewayPayOrderDao{
	
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

	@Override
	public List<TGatewayPayOrder> getNotPayOrder() {
		return tGatewayPayOrderMapper.getNotPayOrder();
	}
}
