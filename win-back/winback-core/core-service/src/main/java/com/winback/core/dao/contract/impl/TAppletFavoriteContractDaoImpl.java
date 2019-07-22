
package com.winback.core.dao.contract.impl;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.core.model.contract.TContract;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.winback.core.condition.contract.TAppletFavoriteContractCondition;
import com.winback.core.dao.contract.mapper.TAppletFavoriteContractMapper;
import com.winback.core.model.contract.TAppletFavoriteContract;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.contract.TAppletFavoriteContractDao;

import java.util.List;
import java.util.Map;

@Service
public class TAppletFavoriteContractDaoImpl extends AbstractDaoImpl<TAppletFavoriteContract, TAppletFavoriteContractCondition, BaseMapper<TAppletFavoriteContract,TAppletFavoriteContractCondition>> implements TAppletFavoriteContractDao{
	
	@Resource
	private TAppletFavoriteContractMapper tAppletFavoriteContractMapper;
	
	@Override
	protected BaseMapper<TAppletFavoriteContract, TAppletFavoriteContractCondition> daoSupport() {
		return tAppletFavoriteContractMapper;
	}

	@Override
	protected TAppletFavoriteContractCondition blankCondition() {
		return new TAppletFavoriteContractCondition();
	}

	@Override
	public TAppletFavoriteContract findBy(Integer extBindId, Integer contractId) {
		TAppletFavoriteContractCondition condition = new TAppletFavoriteContractCondition();
		condition.setExtBindId(extBindId);
		condition.setContractId(contractId);
		condition.setDeleteFlag(false);
		List<TAppletFavoriteContract> list = tAppletFavoriteContractMapper.selectByCondition(condition);
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
		List<TContract> list = tAppletFavoriteContractMapper.getFavoriteContractList(params);
		return new PageInfo<>(list);
	}

	@Override
	public List<Integer> getContractIdList(Integer extBindId) {
		return tAppletFavoriteContractMapper.getContractIdList(extBindId);
	}

	@Override
	public Integer favoriteContractCount(Integer extBindId) {
		return tAppletFavoriteContractMapper.favoriteContractCount(extBindId);
	}
}
