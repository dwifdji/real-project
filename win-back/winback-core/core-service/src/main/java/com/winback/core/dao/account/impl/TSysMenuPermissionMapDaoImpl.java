
package com.winback.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition.account.TSysMenuPermissionMapCondition;
import com.winback.core.dao.account.mapper.TSysMenuPermissionMapMapper;
import com.winback.core.model.account.TSysMenuPermissionMap;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.account.TSysMenuPermissionMapDao;

@Service
public class TSysMenuPermissionMapDaoImpl extends AbstractDaoImpl<TSysMenuPermissionMap, TSysMenuPermissionMapCondition, BaseMapper<TSysMenuPermissionMap,TSysMenuPermissionMapCondition>> implements TSysMenuPermissionMapDao{
	
	@Resource
	private TSysMenuPermissionMapMapper tSysMenuPermissionMapMapper;
	
	@Override
	protected BaseMapper<TSysMenuPermissionMap, TSysMenuPermissionMapCondition> daoSupport() {
		return tSysMenuPermissionMapMapper;
	}

	@Override
	protected TSysMenuPermissionMapCondition blankCondition() {
		return new TSysMenuPermissionMapCondition();
	}

}
