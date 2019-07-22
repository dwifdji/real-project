
package com.winback.core.dao.contract.impl;

import javax.annotation.Resource;

import com.winback.core.condition.contract.TContractShoppingCartItemCondition;
import com.winback.core.model.contract.TContractShoppingCartItem;
import org.springframework.stereotype.Service;

import com.winback.core.condition.contract.TAppletContractShoppingCartItemCondition;
import com.winback.core.dao.contract.mapper.TAppletContractShoppingCartItemMapper;
import com.winback.core.model.contract.TAppletContractShoppingCartItem;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.contract.TAppletContractShoppingCartItemDao;

import java.util.List;

@Service
public class TAppletContractShoppingCartItemDaoImpl extends AbstractDaoImpl<TAppletContractShoppingCartItem, TAppletContractShoppingCartItemCondition, BaseMapper<TAppletContractShoppingCartItem,TAppletContractShoppingCartItemCondition>> implements TAppletContractShoppingCartItemDao{
	
	@Resource
	private TAppletContractShoppingCartItemMapper tAppletContractShoppingCartItemMapper;
	
	@Override
	protected BaseMapper<TAppletContractShoppingCartItem, TAppletContractShoppingCartItemCondition> daoSupport() {
		return tAppletContractShoppingCartItemMapper;
	}

	@Override
	protected TAppletContractShoppingCartItemCondition blankCondition() {
		return new TAppletContractShoppingCartItemCondition();
	}

	@Override
	public List<TAppletContractShoppingCartItem> findBy(Integer shoppingCartId) {
		TAppletContractShoppingCartItemCondition condition = new TAppletContractShoppingCartItemCondition();
		condition.setShoppingCartId(shoppingCartId);
		condition.setDeleteFlag(false);
		return tAppletContractShoppingCartItemMapper.selectByCondition(condition);
	}

	@Override
	public TAppletContractShoppingCartItem findBy(Integer shoppingCartId, Integer contractId) {
		TAppletContractShoppingCartItemCondition condition = new TAppletContractShoppingCartItemCondition();
		condition.setShoppingCartId(shoppingCartId);
		condition.setContractId(contractId);
		condition.setDeleteFlag(false);
		List<TAppletContractShoppingCartItem> list = tAppletContractShoppingCartItemMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int clearShoppingCart(Integer shoppingCartId) {
		return tAppletContractShoppingCartItemMapper.clearShoppingCart(shoppingCartId);
	}
}
