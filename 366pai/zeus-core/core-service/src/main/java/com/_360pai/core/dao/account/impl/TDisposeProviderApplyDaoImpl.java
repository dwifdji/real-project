
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.model.account.TCompany;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.TDisposeProviderApplyCondition;
import com._360pai.core.dao.account.mapper.TDisposeProviderApplyMapper;
import com._360pai.core.model.account.TDisposeProviderApply;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.TDisposeProviderApplyDao;

import java.util.List;
import java.util.Map;

@Service
public class TDisposeProviderApplyDaoImpl extends AbstractDaoImpl<TDisposeProviderApply, TDisposeProviderApplyCondition, BaseMapper<TDisposeProviderApply,TDisposeProviderApplyCondition>> implements TDisposeProviderApplyDao{
	
	@Resource
	private TDisposeProviderApplyMapper tDisposeProviderApplyMapper;
	
	@Override
	protected BaseMapper<TDisposeProviderApply, TDisposeProviderApplyCondition> daoSupport() {
		return tDisposeProviderApplyMapper;
	}

	@Override
	protected TDisposeProviderApplyCondition blankCondition() {
		return new TDisposeProviderApplyCondition();
	}

	@Override
	public PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TDisposeProviderApply> list = tDisposeProviderApplyMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public boolean hasPendingApply(Integer partyId) {
		TDisposeProviderApplyCondition condition = new TDisposeProviderApplyCondition();
		condition.setPartyId(partyId);
		condition.setStatus(AccountEnum.ApplyStatus.PENDING.getKey());
		List<TDisposeProviderApply> list = tDisposeProviderApplyMapper.selectByCondition(condition);
		return list.size() > 0 ? true : false;
	}
}
