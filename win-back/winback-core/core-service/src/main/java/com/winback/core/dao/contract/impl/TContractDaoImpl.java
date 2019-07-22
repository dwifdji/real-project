
package com.winback.core.dao.contract.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.condition.contract.TContractCondition;
import com.winback.core.dao.contract.TContractDao;
import com.winback.core.dao.contract.mapper.TContractMapper;
import com.winback.core.model.contract.TContract;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TContractDaoImpl extends AbstractDaoImpl<TContract, TContractCondition, BaseMapper<TContract,TContractCondition>> implements TContractDao{

	@Resource
	private TContractMapper tContractMapper;
	
	@Override
	protected BaseMapper<TContract, TContractCondition> daoSupport() {
		return tContractMapper;
	}

	@Override
	protected TContractCondition blankCondition() {
		return new TContractCondition();
	}

	@Override
	public PageInfo<TContract> getFrontList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TContract> list = tContractMapper.getFrontList(params);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<TContract> getPossessedContractList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TContract> list = tContractMapper.getPossessedContractList(params);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<TContract> getBackgroundList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TContract> list = tContractMapper.getBackgroundList(params);
		return new PageInfo<>(list);
	}

	@Override
	public int addDownloadCount(Integer id) {
		return tContractMapper.addDownloadCount(id);
	}

	@Override
	public int addFavoriteCount(Integer id) {
		return tContractMapper.addFavoriteCount(id);
	}

	@Override
	public int subFavoriteCount(Integer id) {
		return tContractMapper.subFavoriteCount(id);
	}

	@Override
	public int addViewCount(Integer id) {
		return tContractMapper.addViewCount(id);
	}

	@Override
	public int addPurchaseCount(Integer id) {
		return tContractMapper.addPurchaseCount(id);
	}

	@Override
	public boolean isExists(String name) {
		TContractCondition condition = new TContractCondition();
		condition.setName(name);
		condition.setDeleteFlag(false);
		List<TContract> list = tContractMapper.selectByCondition(condition);
		return list.size() > 0;
	}

	@Override
	public TContract findBy(String name, Integer contractTypeId) {
		List<TContract> list = tContractMapper.findByNameContractTypeId(name, contractTypeId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageInfo<TContract> getWrongList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TContract> list = tContractMapper.getWrongList(params);
		return new PageInfo<>(list);
	}
}
