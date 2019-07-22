
package com.winback.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition.account.TSysSubModuleCondition;
import com.winback.core.dao.account.mapper.TSysSubModuleMapper;
import com.winback.core.model.account.TSysSubModule;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.account.TSysSubModuleDao;

import java.util.List;

@Service
public class TSysSubModuleDaoImpl extends AbstractDaoImpl<TSysSubModule, TSysSubModuleCondition, BaseMapper<TSysSubModule,TSysSubModuleCondition>> implements TSysSubModuleDao{
	
	@Resource
	private TSysSubModuleMapper tSysSubModuleMapper;
	
	@Override
	protected BaseMapper<TSysSubModule, TSysSubModuleCondition> daoSupport() {
		return tSysSubModuleMapper;
	}

	@Override
	protected TSysSubModuleCondition blankCondition() {
		return new TSysSubModuleCondition();
	}

	@Override
	public List<TSysSubModule> findBy(Integer moduleId) {
		TSysSubModuleCondition condition = new TSysSubModuleCondition();
		condition.setModuleId(moduleId);
		condition.setDeleteFlag(false);
		return tSysSubModuleMapper.selectByCondition(condition);
	}
}
