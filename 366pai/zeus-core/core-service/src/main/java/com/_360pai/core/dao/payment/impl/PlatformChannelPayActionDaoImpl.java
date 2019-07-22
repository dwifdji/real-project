
package com._360pai.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.payment.PlatformChannelPayActionCondition;
import com._360pai.core.dao.payment.mapper.PlatformChannelPayActionMapper;
import com._360pai.core.model.payment.PlatformChannelPayAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.payment.PlatformChannelPayActionDao;

@Service
public class PlatformChannelPayActionDaoImpl extends AbstractDaoImpl<PlatformChannelPayAction, PlatformChannelPayActionCondition, BaseMapper<PlatformChannelPayAction,PlatformChannelPayActionCondition>> implements PlatformChannelPayActionDao{
	
	@Resource
	private PlatformChannelPayActionMapper platformChannelPayActionMapper;
	
	@Override
	protected BaseMapper<PlatformChannelPayAction, PlatformChannelPayActionCondition> daoSupport() {
		return platformChannelPayActionMapper;
	}

	@Override
	protected PlatformChannelPayActionCondition blankCondition() {
		return new PlatformChannelPayActionCondition();
	}

}
