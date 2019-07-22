
package com.winback.core.dao.account.impl;

import javax.annotation.Resource;

import com.winback.core.model.account.TSysRole;
import org.springframework.stereotype.Service;

import com.winback.core.condition.account.TSysStaffRoleMapCondition;
import com.winback.core.dao.account.mapper.TSysStaffRoleMapMapper;
import com.winback.core.model.account.TSysStaffRoleMap;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.account.TSysStaffRoleMapDao;

import java.util.List;

@Service
public class TSysStaffRoleMapDaoImpl extends AbstractDaoImpl<TSysStaffRoleMap, TSysStaffRoleMapCondition, BaseMapper<TSysStaffRoleMap,TSysStaffRoleMapCondition>> implements TSysStaffRoleMapDao{
	
	@Resource
	private TSysStaffRoleMapMapper tSysStaffRoleMapMapper;
	
	@Override
	protected BaseMapper<TSysStaffRoleMap, TSysStaffRoleMapCondition> daoSupport() {
		return tSysStaffRoleMapMapper;
	}

	@Override
	protected TSysStaffRoleMapCondition blankCondition() {
		return new TSysStaffRoleMapCondition();
	}

	@Override
	public TSysStaffRoleMap findBy(Integer staffId, Integer roleId) {
		TSysStaffRoleMapCondition condition = new TSysStaffRoleMapCondition();
		condition.setStaffId(staffId);
		condition.setRoleId(roleId);
		List<TSysStaffRoleMap> list = tSysStaffRoleMapMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<TSysStaffRoleMap> findBy(Integer staffId) {
		TSysStaffRoleMapCondition condition = new TSysStaffRoleMapCondition();
		condition.setStaffId(staffId);
		condition.setDeleteFlag(false);
		return tSysStaffRoleMapMapper.selectByCondition(condition);
	}

	@Override
	public List<TSysStaffRoleMap> findAllBy(Integer staffId) {
		TSysStaffRoleMapCondition condition = new TSysStaffRoleMapCondition();
		condition.setStaffId(staffId);
		return tSysStaffRoleMapMapper.selectByCondition(condition);
	}

	@Override
	public List<TSysRole> findRoleBy(Integer staffId) {
		return tSysStaffRoleMapMapper.findRoleBy(staffId);
	}


}
