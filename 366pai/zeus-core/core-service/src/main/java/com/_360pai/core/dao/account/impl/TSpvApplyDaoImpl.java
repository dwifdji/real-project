
package com._360pai.core.dao.account.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TSpvApplyCondition;
import com._360pai.core.dao.account.TSpvApplyDao;
import com._360pai.core.dao.account.mapper.TSpvApplyMapper;
import com._360pai.core.model.account.TSpvApply;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TSpvApplyDaoImpl extends AbstractDaoImpl<TSpvApply, TSpvApplyCondition, BaseMapper<TSpvApply,TSpvApplyCondition>> implements TSpvApplyDao{
	
	@Resource
	private TSpvApplyMapper tSpvApplyMapper;
	
	@Override
	protected BaseMapper<TSpvApply, TSpvApplyCondition> daoSupport() {
		return tSpvApplyMapper;
	}

	@Override
	protected TSpvApplyCondition blankCondition() {
		return new TSpvApplyCondition();
	}

}
