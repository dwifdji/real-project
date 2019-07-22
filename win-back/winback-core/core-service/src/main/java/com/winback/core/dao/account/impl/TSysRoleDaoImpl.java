
package com.winback.core.dao.account.impl;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.core.condition.account.TSysStaffCondition;
import com.winback.core.model.account.TSysStaff;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.winback.core.condition.account.TSysRoleCondition;
import com.winback.core.dao.account.mapper.TSysRoleMapper;
import com.winback.core.model.account.TSysRole;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.account.TSysRoleDao;

import java.util.List;
import java.util.Map;

@Service
public class TSysRoleDaoImpl extends AbstractDaoImpl<TSysRole, TSysRoleCondition, BaseMapper<TSysRole,TSysRoleCondition>> implements TSysRoleDao{
	
	@Resource
	private TSysRoleMapper tSysRoleMapper;
	
	@Override
	protected BaseMapper<TSysRole, TSysRoleCondition> daoSupport() {
		return tSysRoleMapper;
	}

	@Override
	protected TSysRoleCondition blankCondition() {
		return new TSysRoleCondition();
	}

	@Override
	public PageInfo<TSysRole> getList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TSysRole> list = tSysRoleMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public TSysRole findBy(String name) {
		TSysRoleCondition condition = new TSysRoleCondition();
		condition.setName(name);
		List<TSysRole> list = tSysRoleMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
