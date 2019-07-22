
package com._360pai.core.dao.fastway.impl;

import javax.annotation.Resource;

import com._360pai.core.model.account.TCompany;
import com._360pai.core.model.fastway.TFastwayAgencyApply;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.fastway.TFastwayFundApplyCondition;
import com._360pai.core.dao.fastway.mapper.TFastwayFundApplyMapper;
import com._360pai.core.model.fastway.TFastwayFundApply;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.fastway.TFastwayFundApplyDao;

import java.util.List;
import java.util.Map;

@Service
public class TFastwayFundApplyDaoImpl extends AbstractDaoImpl<TFastwayFundApply, TFastwayFundApplyCondition, BaseMapper<TFastwayFundApply,TFastwayFundApplyCondition>> implements TFastwayFundApplyDao{
	
	@Resource
	private TFastwayFundApplyMapper tFastwayFundApplyMapper;
	
	@Override
	protected BaseMapper<TFastwayFundApply, TFastwayFundApplyCondition> daoSupport() {
		return tFastwayFundApplyMapper;
	}

	@Override
	protected TFastwayFundApplyCondition blankCondition() {
		return new TFastwayFundApplyCondition();
	}

	@Override
	public List<TFastwayAgencyApply> findByParam(Map<String, Object> query) {
		return tFastwayFundApplyMapper.findByParam(query);
	}

	@Override
	public List<TCompany> findApplyStatusByAccountId(Integer accountId) {
		return tFastwayFundApplyMapper.findApplyStatusByAccountId(accountId);
	}
}
