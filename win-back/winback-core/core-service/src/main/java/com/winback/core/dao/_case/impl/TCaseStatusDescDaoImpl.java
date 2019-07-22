
package com.winback.core.dao._case.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition._case.TCaseStatusDescCondition;
import com.winback.core.dao._case.mapper.TCaseStatusDescMapper;
import com.winback.core.model._case.TCaseStatusDesc;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao._case.TCaseStatusDescDao;

import java.util.List;

@Service
public class TCaseStatusDescDaoImpl extends AbstractDaoImpl<TCaseStatusDesc, TCaseStatusDescCondition, BaseMapper<TCaseStatusDesc,TCaseStatusDescCondition>> implements TCaseStatusDescDao{
	
	@Resource
	private TCaseStatusDescMapper tCaseStatusDescMapper;
	
	@Override
	protected BaseMapper<TCaseStatusDesc, TCaseStatusDescCondition> daoSupport() {
		return tCaseStatusDescMapper;
	}

	@Override
	protected TCaseStatusDescCondition blankCondition() {
		return new TCaseStatusDescCondition();
	}

	@Override
	public TCaseStatusDesc getStatusDescByStatus(String caseStatus) {
		TCaseStatusDescCondition condition = new TCaseStatusDescCondition();
		condition.setStatus(caseStatus);
		condition.setDeleteFlag(false);
		List<TCaseStatusDesc> descList = tCaseStatusDescMapper.selectByCondition(condition);
		if(descList != null && !descList.isEmpty()){
			return descList.get(0);
		}else{
			return null;
		}
	}
}
