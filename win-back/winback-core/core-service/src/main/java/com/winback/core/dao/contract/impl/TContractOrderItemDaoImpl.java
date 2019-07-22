
package com.winback.core.dao.contract.impl;

import javax.annotation.Resource;

import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.exception.BusinessException;
import org.springframework.stereotype.Service;

import com.winback.core.condition.contract.TContractOrderItemCondition;
import com.winback.core.dao.contract.mapper.TContractOrderItemMapper;
import com.winback.core.model.contract.TContractOrderItem;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.contract.TContractOrderItemDao;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TContractOrderItemDaoImpl extends AbstractDaoImpl<TContractOrderItem, TContractOrderItemCondition, BaseMapper<TContractOrderItem,TContractOrderItemCondition>> implements TContractOrderItemDao{
	
	@Resource
	private TContractOrderItemMapper tContractOrderItemMapper;
	
	@Override
	protected BaseMapper<TContractOrderItem, TContractOrderItemCondition> daoSupport() {
		return tContractOrderItemMapper;
	}

	@Override
	protected TContractOrderItemCondition blankCondition() {
		return new TContractOrderItemCondition();
	}

	@Override
	public TContractOrderItem createOrderItem(Integer orderId, Integer contractId, BigDecimal amount) {
		TContractOrderItem orderItem = new TContractOrderItem();
		orderItem.setAmount(amount);
		orderItem.setContractId(contractId);
		orderItem.setOrderId(orderId);
		int result = tContractOrderItemMapper.insert(orderItem);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return orderItem;
	}

	@Override
	public List<TContractOrderItem> findBy(Integer orderId) {
		TContractOrderItemCondition condition = new TContractOrderItemCondition();
		condition.setOrderId(orderId);
		return tContractOrderItemMapper.selectByCondition(condition);
	}
}
