
package com.tzCloud.core.dao.order.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.order.TServiceOrderCondition;
import com.tzCloud.core.dao.order.mapper.TServiceOrderMapper;
import com.tzCloud.core.model.order.TServiceOrder;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.order.TServiceOrderDao;

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
