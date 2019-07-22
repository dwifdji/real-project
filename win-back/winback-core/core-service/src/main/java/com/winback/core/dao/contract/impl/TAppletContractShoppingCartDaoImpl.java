
package com.winback.core.dao.contract.impl;

import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.condition.contract.TAppletContractShoppingCartCondition;
import com.winback.core.dao.contract.TAppletContractShoppingCartDao;
import com.winback.core.dao.contract.mapper.TAppletContractShoppingCartMapper;
import com.winback.core.exception.BusinessException;
import com.winback.core.model.contract.TAppletContractShoppingCart;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TAppletContractShoppingCartDaoImpl extends AbstractDaoImpl<TAppletContractShoppingCart, TAppletContractShoppingCartCondition, BaseMapper<TAppletContractShoppingCart,TAppletContractShoppingCartCondition>> implements TAppletContractShoppingCartDao{
	
	@Resource
	private TAppletContractShoppingCartMapper tAppletContractShoppingCartMapper;
	
	@Override
	protected BaseMapper<TAppletContractShoppingCart, TAppletContractShoppingCartCondition> daoSupport() {
		return tAppletContractShoppingCartMapper;
	}

	@Override
	protected TAppletContractShoppingCartCondition blankCondition() {
		return new TAppletContractShoppingCartCondition();
	}

	@Override
	public TAppletContractShoppingCart findBy(Integer extBindId) {
		TAppletContractShoppingCartCondition condition = new TAppletContractShoppingCartCondition();
		condition.setExtBindId(extBindId);
		List<TAppletContractShoppingCart> list = tAppletContractShoppingCartMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Integer> getContractIdList(Integer extBindId) {
		return tAppletContractShoppingCartMapper.getContractIdList(extBindId);
	}

	@Override
	public boolean isInShoppingCart(Integer extBindId, Integer contractId) {
		Integer id = tAppletContractShoppingCartMapper.isInShoppingCart(extBindId, contractId);
		return id != null;
	}

	@Override
	public TAppletContractShoppingCart create(Integer extBindId) {
		TAppletContractShoppingCart shoppingCart = new TAppletContractShoppingCart();
		shoppingCart.setExtBindId(extBindId);
		int result = tAppletContractShoppingCartMapper.insert(shoppingCart);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return shoppingCart;
	}
}
