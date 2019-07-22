
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import com._360pai.core.condition.account.SysPermissionCondition;
import com._360pai.core.model.account.SysPermission;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.SysRolePermissionCondition;
import com._360pai.core.dao.account.mapper.SysRolePermissionMapper;
import com._360pai.core.model.account.SysRolePermission;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.SysRolePermissionDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SysRolePermissionDaoImpl extends AbstractDaoImpl<SysRolePermission, SysRolePermissionCondition, BaseMapper<SysRolePermission,SysRolePermissionCondition>> implements SysRolePermissionDao{
	
	@Resource
	private SysRolePermissionMapper sysRolePermissionMapper;
	
	@Override
	protected BaseMapper<SysRolePermission, SysRolePermissionCondition> daoSupport() {
		return sysRolePermissionMapper;
	}

	@Override
	protected SysRolePermissionCondition blankCondition() {
		return new SysRolePermissionCondition();
	}

	@Override
	public List<SysRolePermission> getByRoleId(Integer roleId) {
		SysRolePermissionCondition condition = new SysRolePermissionCondition();
		condition.setRoleId(roleId);
		condition.setIsDelete(false);
		return sysRolePermissionMapper.selectByCondition(condition);
	}

	@Override
	public List<SysRolePermission> getAllByRoleId(Integer roleId) {
		SysRolePermissionCondition condition = new SysRolePermissionCondition();
		condition.setRoleId(roleId);
		return sysRolePermissionMapper.selectByCondition(condition);
	}

	@Override
	public List<Integer> getByPermissionIdRoleId(Integer roleId) {
		List<SysRolePermission> list = getByRoleId(roleId);
		List<Integer> resultList = new ArrayList<>();
		for (SysRolePermission sysRolePermission : list) {
			resultList.add(sysRolePermission.getPermissionId());
		}
		return resultList;
	}

	@Override
	public Set<String> getRolePermissionCodeList(Integer roleId) {
		return sysRolePermissionMapper.getRolePermissionCodeList(roleId);
	}
}
