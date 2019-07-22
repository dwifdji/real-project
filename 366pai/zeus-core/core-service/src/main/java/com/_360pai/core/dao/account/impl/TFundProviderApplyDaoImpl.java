
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.model.account.TDisposeProvider;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.TFundProviderApplyCondition;
import com._360pai.core.dao.account.mapper.TFundProviderApplyMapper;
import com._360pai.core.model.account.TFundProviderApply;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.TFundProviderApplyDao;

import java.util.List;
import java.util.Map;

@Service
public class TFundProviderApplyDaoImpl extends AbstractDaoImpl<TFundProviderApply, TFundProviderApplyCondition, BaseMapper<TFundProviderApply,TFundProviderApplyCondition>> implements TFundProviderApplyDao{
	
	@Resource
	private TFundProviderApplyMapper tFundProviderApplyMapper;
	
	@Override
	protected BaseMapper<TFundProviderApply, TFundProviderApplyCondition> daoSupport() {
		return tFundProviderApplyMapper;
	}

	@Override
	protected TFundProviderApplyCondition blankCondition() {
		return new TFundProviderApplyCondition();
	}

	@Override
	public PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TFundProviderApply> list = tFundProviderApplyMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public boolean hasPendingApply(Integer partyId) {
		TFundProviderApplyCondition condition = new TFundProviderApplyCondition();
		condition.setPartyId(partyId);
		condition.setStatus(AccountEnum.ApplyStatus.PENDING.getKey());
		List<TFundProviderApply> list = tFundProviderApplyMapper.selectByCondition(condition);
		return list.size() > 0 ? true : false;
	}
}
