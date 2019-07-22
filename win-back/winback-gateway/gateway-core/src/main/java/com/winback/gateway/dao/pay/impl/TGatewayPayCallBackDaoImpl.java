
package com.winback.gateway.dao.pay.impl;

import javax.annotation.Resource;

import com.winback.gateway.condition.pay.TGatewayPayCallBackCondition;
import com.winback.gateway.dao.pay.TGatewayPayCallBackDao;
import org.springframework.stereotype.Service;

import com.winback.gateway.condition.pay.TGatewayPayCallBackCondition;
import com.winback.gateway.dao.pay.mapper.TGatewayPayCallBackMapper;
import com.winback.gateway.model.pay.TGatewayPayCallBack;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.gateway.dao.pay.TGatewayPayCallBackDao;

@Service
public class TGatewayPayCallBackDaoImpl extends AbstractDaoImpl<TGatewayPayCallBack, TGatewayPayCallBackCondition, BaseMapper<TGatewayPayCallBack,TGatewayPayCallBackCondition>> implements TGatewayPayCallBackDao {
	
	@Resource
	private TGatewayPayCallBackMapper tGatewayPayCallBackMapper;
	
	@Override
	protected BaseMapper<TGatewayPayCallBack, TGatewayPayCallBackCondition> daoSupport() {
		return tGatewayPayCallBackMapper;
	}

	@Override
	protected TGatewayPayCallBackCondition blankCondition() {
		return new TGatewayPayCallBackCondition();
	}

}
