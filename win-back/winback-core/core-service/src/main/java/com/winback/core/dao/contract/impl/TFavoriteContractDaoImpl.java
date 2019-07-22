
package com.winback.core.dao.contract.impl;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.core.model.contract.TContract;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.winback.core.condition.contract.TFavoriteContractCondition;
import com.winback.core.dao.contract.mapper.TFavoriteContractMapper;
import com.winback.core.model.contract.TFavoriteContract;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.contract.TFavoriteContractDao;

import java.util.List;
import java.util.Map;

@Service
public class TFavoriteContractDaoImpl extends AbstractDaoImpl<TFavoriteContract, TFavoriteContractCondition, BaseMapper<TFavoriteContract,TFavoriteContractCondition>> implements TFavoriteContractDao{
	
	@Resource
	private TFavoriteContractMapper tFavoriteContractMapper;
	
	@Override
	protected BaseMapper<TFavoriteContract, TFavoriteContractCondition> daoSupport() {
		return tFavoriteContractMapper;
	}

	@Override
	protected TFavoriteContractCondition blankCondition() {
		return new TFavoriteContractCondition();
	}

	@Override
	public TFavoriteContract findBy(Integer accountId, Integer contractId) {
		TFavoriteContractCondition condition = new TFavoriteContractCondition();
		condition.setAccountId(accountId);
		condition.setContractId(contractId);
		condition.setDeleteFlag(false);
		List<TFavoriteContract> list = tFavoriteContractMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageInfo<TContract> getFavoriteContractList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TContract> list = tFavoriteContractMapper.getFavoriteContractList(params);
		return new PageInfo<>(list);
	}

	@Override
	public List<Integer> getContractIdList(Integer accountId) {
		return tFavoriteContractMapper.getContractIdList(accountId);
	}

	@Override
	public void migrateFromAppletToApp(Integer extBindId, Integer accountId) {
		tFavoriteContractMapper.migrateFromAppletToApp(extBindId, accountId);
	}

	@Override
	public Integer favoriteContractCount(Integer accountId) {
		return tFavoriteContractMapper.favoriteContractCount(accountId);
	}
}
