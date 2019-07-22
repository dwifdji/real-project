
package com._360pai.core.dao.fastway.impl;

import javax.annotation.Resource;

import com._360pai.core.model.account.TCompany;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.fastway.TFastwayAgencyApplyCondition;
import com._360pai.core.dao.fastway.mapper.TFastwayAgencyApplyMapper;
import com._360pai.core.model.fastway.TFastwayAgencyApply;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.fastway.TFastwayAgencyApplyDao;

import java.util.List;
import java.util.Map;

@Service
public class TFastwayAgencyApplyDaoImpl extends AbstractDaoImpl<TFastwayAgencyApply, TFastwayAgencyApplyCondition, BaseMapper<TFastwayAgencyApply,TFastwayAgencyApplyCondition>> implements TFastwayAgencyApplyDao{
	
	@Resource
	private TFastwayAgencyApplyMapper tFastwayAgencyApplyMapper;
	
	@Override
	protected BaseMapper<TFastwayAgencyApply, TFastwayAgencyApplyCondition> daoSupport() {
		return tFastwayAgencyApplyMapper;
	}

	@Override
	protected TFastwayAgencyApplyCondition blankCondition() {
		return new TFastwayAgencyApplyCondition();
	}

	@Override
	public List<TFastwayAgencyApply> findByParam(Map<String, Object> query) {
		return tFastwayAgencyApplyMapper.findByParam(query);
	}

	@Override
	public List<TCompany> findByAccountId(Integer accountId) {
		return tFastwayAgencyApplyMapper.findByAccountId(accountId);
	}
}
