
package com.winback.core.dao._case.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition._case.TCaseStepGroupCondition;
import com.winback.core.dao._case.mapper.TCaseStepGroupMapper;
import com.winback.core.model._case.TCaseStepGroup;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao._case.TCaseStepGroupDao;

@Service
public class TCaseStepGroupDaoImpl extends AbstractDaoImpl<TCaseStepGroup, TCaseStepGroupCondition, BaseMapper<TCaseStepGroup,TCaseStepGroupCondition>> implements TCaseStepGroupDao{
	
	@Resource
	private TCaseStepGroupMapper tCaseStepGroupMapper;
	
	@Override
	protected BaseMapper<TCaseStepGroup, TCaseStepGroupCondition> daoSupport() {
		return tCaseStepGroupMapper;
	}

	@Override
	protected TCaseStepGroupCondition blankCondition() {
		return new TCaseStepGroupCondition();
	}

}
