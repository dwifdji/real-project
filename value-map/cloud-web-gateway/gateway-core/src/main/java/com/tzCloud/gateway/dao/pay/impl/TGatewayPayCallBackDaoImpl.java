
package com.tzCloud.gateway.dao.pay.impl;

import javax.annotation.Resource;

import com.tzCloud.gateway.dao.pay.TGatewayPayCallBackDao;
import org.springframework.stereotype.Service;

import com.tzCloud.gateway.condition.pay.TGatewayPayCallBackCondition;
import com.tzCloud.gateway.dao.pay.mapper.TGatewayPayCallBackMapper;
import com.tzCloud.gateway.model.pay.TGatewayPayCallBack;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.gateway.dao.pay.TGatewayPayCallBackDao;

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
