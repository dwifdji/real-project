
package com._360pai.core.dao.comprador.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.comprador.TCompradorRequirementCondition;
import com._360pai.core.dao.comprador.mapper.TCompradorRequirementMapper;
import com._360pai.core.model.comprador.TCompradorRequirement;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.comprador.TCompradorRequirementDao;

import java.util.List;

@Service
public class TCompradorRequirementDaoImpl extends AbstractDaoImpl<TCompradorRequirement, TCompradorRequirementCondition, BaseMapper<TCompradorRequirement,TCompradorRequirementCondition>> implements TCompradorRequirementDao{
	
	@Resource
	private TCompradorRequirementMapper tCompradorRequirementMapper;
	
	@Override
	protected BaseMapper<TCompradorRequirement, TCompradorRequirementCondition> daoSupport() {
		return tCompradorRequirementMapper;
	}

	@Override
	protected TCompradorRequirementCondition blankCondition() {
		return new TCompradorRequirementCondition();
	}

	@Override
	public TCompradorRequirement selectByIdWithoutPay(Integer id) {
		return tCompradorRequirementMapper.selectByIdWithoutPay(id);
	}

    @Override
    public List<TCompradorRequirement> selectListForPlatform(TCompradorRequirementCondition condition) {
        return tCompradorRequirementMapper.selectListForPlatform(condition);
    }

	@Override
	public List<TCompradorRequirement> myRequirementList(TCompradorRequirementCondition condition) {
		return tCompradorRequirementMapper.myRequirementList(condition);
	}
}
