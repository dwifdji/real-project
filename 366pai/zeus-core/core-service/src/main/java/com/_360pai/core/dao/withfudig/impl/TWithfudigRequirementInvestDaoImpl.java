
package com._360pai.core.dao.withfudig.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.withfudig.TWithfudigRequirementInvestCondition;
import com._360pai.core.dao.withfudig.mapper.TWithfudigRequirementInvestMapper;
import com._360pai.core.model.withfudig.TWithfudigRequirementInvest;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.withfudig.TWithfudigRequirementInvestDao;

@Service
public class TWithfudigRequirementInvestDaoImpl extends AbstractDaoImpl<TWithfudigRequirementInvest, TWithfudigRequirementInvestCondition, BaseMapper<TWithfudigRequirementInvest,TWithfudigRequirementInvestCondition>> implements TWithfudigRequirementInvestDao{
	
	@Resource
	private TWithfudigRequirementInvestMapper tWithfudigRequirementInvestMapper;
	
	@Override
	protected BaseMapper<TWithfudigRequirementInvest, TWithfudigRequirementInvestCondition> daoSupport() {
		return tWithfudigRequirementInvestMapper;
	}

	@Override
	protected TWithfudigRequirementInvestCondition blankCondition() {
		return new TWithfudigRequirementInvestCondition();
	}

    @Override
    public Integer getCountByRequirementId(Integer requirementId) {
        return tWithfudigRequirementInvestMapper.getCountByRequirementId(requirementId);
    }
}
