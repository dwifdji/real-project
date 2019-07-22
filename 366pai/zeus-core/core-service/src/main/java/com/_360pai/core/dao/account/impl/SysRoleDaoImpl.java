
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import com._360pai.core.model.account.TAgencyApplyRecord;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.SysRoleCondition;
import com._360pai.core.dao.account.mapper.SysRoleMapper;
import com._360pai.core.model.account.SysRole;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.SysRoleDao;

import java.util.List;
import java.util.Map;

@Service
public class SysRoleDaoImpl extends AbstractDaoImpl<SysRole, SysRoleCondition, BaseMapper<SysRole,SysRoleCondition>> implements SysRoleDao{
	
	@Resource
	private SysRoleMapper sysRoleMapper;
	
	@Override
	protected BaseMapper<SysRole, SysRoleCondition> daoSupport() {
		return sysRoleMapper;
	}

	@Override
	protected SysRoleCondition blankCondition() {
		return new SysRoleCondition();
	}

	@Override
	public PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<SysRole> list = sysRoleMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public boolean isExistName(String name) {
		SysRoleCondition condition = new SysRoleCondition();
		condition.setName(name);
		List<SysRole> list = sysRoleMapper.selectByCondition(condition);
		return list.size() > 0;
	}
}
