
package com.winback.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition.account.TSysModuleCondition;
import com.winback.core.dao.account.mapper.TSysModuleMapper;
import com.winback.core.model.account.TSysModule;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.account.TSysModuleDao;

import java.util.List;

@Service
public class TSysModuleDaoImpl extends AbstractDaoImpl<TSysModule, TSysModuleCondition, BaseMapper<TSysModule,TSysModuleCondition>> implements TSysModuleDao{
	
	@Resource
	private TSysModuleMapper tSysModuleMapper;
	
	@Override
	protected BaseMapper<TSysModule, TSysModuleCondition> daoSupport() {
		return tSysModuleMapper;
	}

	@Override
	protected TSysModuleCondition blankCondition() {
		return new TSysModuleCondition();
	}

	@Override
	public List<TSysModule> findAll() {
		TSysModuleCondition condition = new TSysModuleCondition();
		condition.setDeleteFlag(false);
		return tSysModuleMapper.selectByCondition(condition);
	}
}
