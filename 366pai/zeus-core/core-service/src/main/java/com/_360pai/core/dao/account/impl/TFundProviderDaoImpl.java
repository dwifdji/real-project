
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import com._360pai.core.model.account.TDisposeProvider;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.TFundProviderCondition;
import com._360pai.core.dao.account.mapper.TFundProviderMapper;
import com._360pai.core.model.account.TFundProvider;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.TFundProviderDao;

import java.util.List;
import java.util.Map;

@Service
public class TFundProviderDaoImpl extends AbstractDaoImpl<TFundProvider, TFundProviderCondition, BaseMapper<TFundProvider,TFundProviderCondition>> implements TFundProviderDao{
	
	@Resource
	private TFundProviderMapper tFundProviderMapper;
	
	@Override
	protected BaseMapper<TFundProvider, TFundProviderCondition> daoSupport() {
		return tFundProviderMapper;
	}

	@Override
	protected TFundProviderCondition blankCondition() {
		return new TFundProviderCondition();
	}

	@Override
	public TFundProvider getByAccountId(Integer accountId) {
		TFundProviderCondition condition = new TFundProviderCondition();
		condition.setAccountId(accountId);
		List<TFundProvider> list = tFundProviderMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TFundProvider getByPartyId(Integer partyId) {
		TFundProviderCondition condition = new TFundProviderCondition();
		condition.setPartyId(partyId);
		List<TFundProvider> list = tFundProviderMapper.selectByCondition(condition);
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
		List<TFundProvider> list = tFundProviderMapper.getList(params);
		return new PageInfo<>(list);
	}
}
