
package com.winback.core.dao.contract.impl;

import javax.annotation.Resource;

import com.winback.core.condition.contract.TContractShoppingCartCondition;
import com.winback.core.model.contract.TContractShoppingCart;
import org.springframework.stereotype.Service;

import com.winback.core.condition.contract.TContractShoppingCartItemCondition;
import com.winback.core.dao.contract.mapper.TContractShoppingCartItemMapper;
import com.winback.core.model.contract.TContractShoppingCartItem;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.contract.TContractShoppingCartItemDao;

import java.util.List;

@Service
public class TContractShoppingCartItemDaoImpl extends AbstractDaoImpl<TContractShoppingCartItem, TContractShoppingCartItemCondition, BaseMapper<TContractShoppingCartItem,TContractShoppingCartItemCondition>> implements TContractShoppingCartItemDao{
	
	@Resource
	private TContractShoppingCartItemMapper tContractShoppingCartItemMapper;
	
	@Override
	protected BaseMapper<TContractShoppingCartItem, TContractShoppingCartItemCondition> daoSupport() {
		return tContractShoppingCartItemMapper;
	}

	@Override
	protected TContractShoppingCartItemCondition blankCondition() {
		return new TContractShoppingCartItemCondition();
	}

	@Override
	public List<TContractShoppingCartItem> findBy(Integer shoppingCartId) {
		TContractShoppingCartItemCondition condition = new TContractShoppingCartItemCondition();
		condition.setShoppingCartId(shoppingCartId);
		condition.setDeleteFlag(false);
		return tContractShoppingCartItemMapper.selectByCondition(condition);
	}

	@Override
	public TContractShoppingCartItem findBy(Integer shoppingCartId, Integer contractId) {
		TContractShoppingCartItemCondition condition = new TContractShoppingCartItemCondition();
		condition.setShoppingCartId(shoppingCartId);
		condition.setContractId(contractId);
		condition.setDeleteFlag(false);
		List<TContractShoppingCartItem> list = tContractShoppingCartItemMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int clearShoppingCart(Integer shoppingCartId) {
		return tContractShoppingCartItemMapper.clearShoppingCart(shoppingCartId);
	}
}
