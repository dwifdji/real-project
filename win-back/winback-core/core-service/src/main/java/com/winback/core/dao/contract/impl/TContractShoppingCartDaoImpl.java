
package com.winback.core.dao.contract.impl;

import javax.annotation.Resource;

import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.exception.BusinessException;
import org.springframework.stereotype.Service;

import com.winback.core.condition.contract.TContractShoppingCartCondition;
import com.winback.core.dao.contract.mapper.TContractShoppingCartMapper;
import com.winback.core.model.contract.TContractShoppingCart;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.contract.TContractShoppingCartDao;

import java.util.List;

@Service
public class TContractShoppingCartDaoImpl extends AbstractDaoImpl<TContractShoppingCart, TContractShoppingCartCondition, BaseMapper<TContractShoppingCart,TContractShoppingCartCondition>> implements TContractShoppingCartDao{
	
	@Resource
	private TContractShoppingCartMapper tContractShoppingCartMapper;
	
	@Override
	protected BaseMapper<TContractShoppingCart, TContractShoppingCartCondition> daoSupport() {
		return tContractShoppingCartMapper;
	}

	@Override
	protected TContractShoppingCartCondition blankCondition() {
		return new TContractShoppingCartCondition();
	}

	@Override
	public TContractShoppingCart findBy(Integer accountId) {
		TContractShoppingCartCondition condition = new TContractShoppingCartCondition();
		condition.setAccountId(accountId);
		List<TContractShoppingCart> list = tContractShoppingCartMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Integer> getContractIdList(Integer accountId) {
		return tContractShoppingCartMapper.getContractIdList(accountId);
	}

	@Override
	public boolean isInShoppingCart(Integer accountId, Integer contractId) {
		Integer id = tContractShoppingCartMapper.isInShoppingCart(accountId, contractId);
		return id != null;
	}

	@Override
	public void migrateFromAppletToApp(Integer appletShoppingCartId, Integer shoppingCartId) {
		tContractShoppingCartMapper.migrateFromAppletToApp(appletShoppingCartId, shoppingCartId);
	}

	@Override
	public TContractShoppingCart create(Integer accountId) {
		TContractShoppingCart shoppingCart = new TContractShoppingCart();
		shoppingCart.setAccountId(accountId);
		int result = tContractShoppingCartMapper.insert(shoppingCart);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return shoppingCart;
	}
}
