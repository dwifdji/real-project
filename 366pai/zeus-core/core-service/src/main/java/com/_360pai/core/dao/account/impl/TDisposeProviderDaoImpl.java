
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import com._360pai.core.model.account.TDisposeProviderApply;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.TDisposeProviderCondition;
import com._360pai.core.dao.account.mapper.TDisposeProviderMapper;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.TDisposeProviderDao;

import java.util.List;
import java.util.Map;

@Service
public class TDisposeProviderDaoImpl extends AbstractDaoImpl<TDisposeProvider, TDisposeProviderCondition, BaseMapper<TDisposeProvider,TDisposeProviderCondition>> implements TDisposeProviderDao{
	
	@Resource
	private TDisposeProviderMapper tDisposeProviderMapper;
	
	@Override
	protected BaseMapper<TDisposeProvider, TDisposeProviderCondition> daoSupport() {
		return tDisposeProviderMapper;
	}

	@Override
	protected TDisposeProviderCondition blankCondition() {
		return new TDisposeProviderCondition();
	}

	@Override
	public TDisposeProvider getByAccountId(Integer accountId) {
		TDisposeProviderCondition condition = new TDisposeProviderCondition();
		condition.setAccountId(accountId);
		List<TDisposeProvider> list = tDisposeProviderMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TDisposeProvider getByPartyId(Integer partyId) {
		TDisposeProviderCondition condition = new TDisposeProviderCondition();
		condition.setPartyId(partyId);
		List<TDisposeProvider> list = tDisposeProviderMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TDisposeProvider> list = tDisposeProviderMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<TDisposeProvider> getRecommendList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TDisposeProvider> list = tDisposeProviderMapper.getRecommendList(params);
		return new PageInfo<>(list);
	}

	@Override
	public TDisposeProvider findByName(String name) {
		List<TDisposeProvider> list = tDisposeProviderMapper.findByName(name);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
