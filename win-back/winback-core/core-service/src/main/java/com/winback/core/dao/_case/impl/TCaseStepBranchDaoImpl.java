
package com.winback.core.dao._case.impl;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.winback.core.condition._case.TCaseStepBranchCondition;
import com.winback.core.dao._case.mapper.TCaseStepBranchMapper;
import com.winback.core.model._case.TCaseStepBranch;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao._case.TCaseStepBranchDao;

import java.util.List;

@Service
public class TCaseStepBranchDaoImpl extends AbstractDaoImpl<TCaseStepBranch, TCaseStepBranchCondition, BaseMapper<TCaseStepBranch,TCaseStepBranchCondition>> implements TCaseStepBranchDao{
	
	@Resource
	private TCaseStepBranchMapper tCaseStepBranchMapper;
	
	@Override
	protected BaseMapper<TCaseStepBranch, TCaseStepBranchCondition> daoSupport() {
		return tCaseStepBranchMapper;
	}

	@Override
	protected TCaseStepBranchCondition blankCondition() {
		return new TCaseStepBranchCondition();
	}

	@Override
	public TCaseStepBranch getMaxCaseStepBranch(String stepId) {
		return tCaseStepBranchMapper.getMaxCaseStepBranch( stepId);
	}

	@Override
	public void saveBatchCaseBranch(List<TCaseStepBranch> tCaseStepBranches) {
		tCaseStepBranchMapper.saveBatchCaseBranch(tCaseStepBranches);
	}

	@Override
	public void deleteBatchCaseStepBranch(Integer id) {
		tCaseStepBranchMapper.deleteBatchCaseStepBranch(id);
	}
}
