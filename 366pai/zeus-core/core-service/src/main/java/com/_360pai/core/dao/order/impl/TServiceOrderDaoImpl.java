
package com._360pai.core.dao.order.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.order.TServiceOrderCondition;
import com._360pai.core.dao.order.mapper.TServiceOrderMapper;
import com._360pai.core.model.order.TServiceOrder;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.order.TServiceOrderDao;

@Service
public class TServiceOrderDaoImpl extends AbstractDaoImpl<TServiceOrder, TServiceOrderCondition, BaseMapper<TServiceOrder,TServiceOrderCondition>> implements TServiceOrderDao{
	
	@Resource
	private TServiceOrderMapper tServiceOrderMapper;
	
	@Override
	protected BaseMapper<TServiceOrder, TServiceOrderCondition> daoSupport() {
		return tServiceOrderMapper;
	}

	@Override
	protected TServiceOrderCondition blankCondition() {
		return new TServiceOrderCondition();
	}

}
