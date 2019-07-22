
package com._360pai.core.dao.pay.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.pay.GatewayPayOrderCondition;
import com._360pai.core.dao.pay.GatewayPayOrderDao;
import com._360pai.core.dao.pay.mapper.GatewayPayOrderMapper;
import com._360pai.core.model.pay.GatewayPayOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GatewayPayOrderDaoImpl extends AbstractDaoImpl<GatewayPayOrder, GatewayPayOrderCondition, BaseMapper<GatewayPayOrder,GatewayPayOrderCondition>> implements GatewayPayOrderDao{
	
	@Resource
	private GatewayPayOrderMapper gatewayPayOrderMapper;
	
	@Override
	protected BaseMapper<GatewayPayOrder, GatewayPayOrderCondition> daoSupport() {
		return gatewayPayOrderMapper;
	}

	@Override
	protected GatewayPayOrderCondition blankCondition() {
		return new GatewayPayOrderCondition();
	}

	@Override
	public List<GatewayPayOrder> getNoticeList() {
		return gatewayPayOrderMapper.getNoticeList();
	}
}
