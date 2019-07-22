
package com.winback.core.dao._case.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition._case.TCaseProjectManagerMapCondition;
import com.winback.core.dao._case.mapper.TCaseProjectManagerMapMapper;
import com.winback.core.model._case.TCaseProjectManagerMap;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao._case.TCaseProjectManagerMapDao;

import java.util.List;

@Service
public class TCaseProjectManagerMapDaoImpl extends AbstractDaoImpl<TCaseProjectManagerMap, TCaseProjectManagerMapCondition, BaseMapper<TCaseProjectManagerMap,TCaseProjectManagerMapCondition>> implements TCaseProjectManagerMapDao{
	
	@Resource
	private TCaseProjectManagerMapMapper tCaseProjectManagerMapMapper;
	
	@Override
	protected BaseMapper<TCaseProjectManagerMap, TCaseProjectManagerMapCondition> daoSupport() {
		return tCaseProjectManagerMapMapper;
	}

	@Override
	protected TCaseProjectManagerMapCondition blankCondition() {
		return new TCaseProjectManagerMapCondition();
	}

	@Override
	public int countCaseNum(Integer accountId) {
		return tCaseProjectManagerMapMapper.countCaseNum(accountId);
	}

	@Override
	public int countSuccessCaseNum(Integer accountId) {
		return tCaseProjectManagerMapMapper.countSuccessCaseNum(accountId);
	}

	@Override
	public TCaseProjectManagerMap findBy(String caseId) {
		TCaseProjectManagerMapCondition condition = new TCaseProjectManagerMapCondition();
		condition.setCaseId(caseId);
		condition.setDeleteFlag(false);
		List<TCaseProjectManagerMap> list = tCaseProjectManagerMapMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<TCaseProjectManagerMap> findBy(Integer accountId) {
		TCaseProjectManagerMapCondition condition = new TCaseProjectManagerMapCondition();
		condition.setAccountId(accountId);
		condition.setDeleteFlag(false);
		return tCaseProjectManagerMapMapper.selectByCondition(condition);
	}

	@Override
	public boolean isCaseAllocatedToThisAccount(String caseId, Integer accountId) {
		TCaseProjectManagerMapCondition condition = new TCaseProjectManagerMapCondition();
		condition.setCaseId(caseId);
		condition.setAccountId(accountId);
		condition.setDeleteFlag(false);
		List<TCaseProjectManagerMap> list = tCaseProjectManagerMapMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}
}
