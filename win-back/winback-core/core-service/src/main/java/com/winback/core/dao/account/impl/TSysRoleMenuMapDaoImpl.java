
package com.winback.core.dao.account.impl;

import javax.annotation.Resource;

import com.winback.core.condition.account.TSysStaffPermissionMapCondition;
import com.winback.core.model.account.TSysPermission;
import org.springframework.stereotype.Service;

import com.winback.core.condition.account.TSysRoleMenuMapCondition;
import com.winback.core.dao.account.mapper.TSysRoleMenuMapMapper;
import com.winback.core.model.account.TSysRoleMenuMap;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.account.TSysRoleMenuMapDao;

import java.util.List;

@Service
public class TSysRoleMenuMapDaoImpl extends AbstractDaoImpl<TSysRoleMenuMap, TSysRoleMenuMapCondition, BaseMapper<TSysRoleMenuMap,TSysRoleMenuMapCondition>> implements TSysRoleMenuMapDao{
	
	@Resource
	private TSysRoleMenuMapMapper tSysRoleMenuMapMapper;
	
	@Override
	protected BaseMapper<TSysRoleMenuMap, TSysRoleMenuMapCondition> daoSupport() {
		return tSysRoleMenuMapMapper;
	}

	@Override
	protected TSysRoleMenuMapCondition blankCondition() {
		return new TSysRoleMenuMapCondition();
	}

	@Override
	public List<TSysRoleMenuMap> findBy(Integer roleId) {
		TSysRoleMenuMapCondition condition = new TSysRoleMenuMapCondition();
		condition.setRoleId(roleId);
		return tSysRoleMenuMapMapper.selectByCondition(condition);
	}

	@Override
	public List<TSysPermission> findPermissionBy(Integer roleId) {
		return tSysRoleMenuMapMapper.findPermissionBy(roleId);
	}
}
