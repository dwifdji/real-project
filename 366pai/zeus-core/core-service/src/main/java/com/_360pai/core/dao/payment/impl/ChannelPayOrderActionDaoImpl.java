
package com._360pai.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.payment.ChannelPayOrderActionCondition;
import com._360pai.core.dao.payment.mapper.ChannelPayOrderActionMapper;
import com._360pai.core.model.payment.ChannelPayOrderAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.payment.ChannelPayOrderActionDao;

@Service
public class ChannelPayOrderActionDaoImpl extends AbstractDaoImpl<ChannelPayOrderAction, ChannelPayOrderActionCondition, BaseMapper<ChannelPayOrderAction,ChannelPayOrderActionCondition>> implements ChannelPayOrderActionDao{
	
	@Resource
	private ChannelPayOrderActionMapper channelPayOrderActionMapper;
	
	@Override
	protected BaseMapper<ChannelPayOrderAction, ChannelPayOrderActionCondition> daoSupport() {
		return channelPayOrderActionMapper;
	}

	@Override
	protected ChannelPayOrderActionCondition blankCondition() {
		return new ChannelPayOrderActionCondition();
	}

}
