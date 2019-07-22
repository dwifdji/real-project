
package com.winback.core.dao.operate.impl;

import javax.annotation.Resource;

import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import org.springframework.stereotype.Service;

import com.winback.core.condition.operate.TOperateCaseMapCondition;
import com.winback.core.dao.operate.mapper.TOperateCaseMapMapper;
import com.winback.core.model.operate.TOperateCaseMap;
import com.winback.core.dao.operate.TOperateCaseMapDao;

@Service
public class TOperateCaseMapDaoImpl extends AbstractDaoImpl<TOperateCaseMap, TOperateCaseMapCondition, BaseMapper<TOperateCaseMap,TOperateCaseMapCondition>> implements TOperateCaseMapDao{
	
	@Resource
	private TOperateCaseMapMapper tOperateCaseMapMapper;
	
	@Override
	protected BaseMapper<TOperateCaseMap, TOperateCaseMapCondition> daoSupport() {
		return tOperateCaseMapMapper;
	}

	@Override
	protected TOperateCaseMapCondition blankCondition() {
		return new TOperateCaseMapCondition();
	}

}
