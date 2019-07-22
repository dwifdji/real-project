
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import com._360pai.core.model.account.SysRole;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.SysStaffRoleCondition;
import com._360pai.core.dao.account.mapper.SysStaffRoleMapper;
import com._360pai.core.model.account.SysStaffRole;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.SysStaffRoleDao;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysStaffRoleDaoImpl extends AbstractDaoImpl<SysStaffRole, SysStaffRoleCondition, BaseMapper<SysStaffRole,SysStaffRoleCondition>> implements SysStaffRoleDao{
	
	@Resource
	private SysStaffRoleMapper sysStaffRoleMapper;
	
	@Override
	protected BaseMapper<SysStaffRole, SysStaffRoleCondition> daoSupport() {
		return sysStaffRoleMapper;
	}

	@Override
	protected SysStaffRoleCondition blankCondition() {
		return new SysStaffRoleCondition();
	}

	@Override
	public List<SysStaffRole> getByStaffId(Integer staffId) {
		SysStaffRoleCondition condition = new SysStaffRoleCondition();
		condition.setStaffId(staffId);
		condition.setIsDelete(false);
		return sysStaffRoleMapper.selectByCondition(condition);
	}

	@Override
	public List<SysStaffRole> getAllByStaffId(Integer staffId) {
		SysStaffRoleCondition condition = new SysStaffRoleCondition();
		condition.setStaffId(staffId);
		return sysStaffRoleMapper.selectByCondition(condition);
	}

	@Override
	public Set<String> getRoleIdList(Integer staffId) {
		List<SysStaffRole> list = getByStaffId(staffId);
		if (list.size() > 0) {
			Set<String> result = new HashSet<>();
			for (SysStaffRole item : list) {
				result.add(item.getRoleId() + "");
			}
			return result;
		}
		return Collections.EMPTY_SET;
	}

	@Override
	public List<SysRole> getRoleList(Integer staffId) {
		return sysStaffRoleMapper.getRoleList(staffId);
	}
}
