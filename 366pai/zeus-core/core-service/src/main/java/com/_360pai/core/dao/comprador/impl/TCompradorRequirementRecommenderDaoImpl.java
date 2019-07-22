
package com._360pai.core.dao.comprador.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.comprador.TCompradorRequirementRecommenderCondition;
import com._360pai.core.dao.comprador.mapper.TCompradorRequirementRecommenderMapper;
import com._360pai.core.model.comprador.TCompradorRequirementRecommender;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.comprador.TCompradorRequirementRecommenderDao;

@Service
public class TCompradorRequirementRecommenderDaoImpl extends AbstractDaoImpl<TCompradorRequirementRecommender, TCompradorRequirementRecommenderCondition, BaseMapper<TCompradorRequirementRecommender,TCompradorRequirementRecommenderCondition>> implements TCompradorRequirementRecommenderDao{
	
	@Resource
	private TCompradorRequirementRecommenderMapper tCompradorRequirementRecommenderMapper;
	
	@Override
	protected BaseMapper<TCompradorRequirementRecommender, TCompradorRequirementRecommenderCondition> daoSupport() {
		return tCompradorRequirementRecommenderMapper;
	}

	@Override
	protected TCompradorRequirementRecommenderCondition blankCondition() {
		return new TCompradorRequirementRecommenderCondition();
	}

    @Override
    public int getCountByRequirementId(Integer requirementId) {
		return tCompradorRequirementRecommenderMapper.getCountByRequirementId(requirementId);
    }
}
