
package com.winback.core.dao.account.impl;

import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.condition.account.TSysPermissionCondition;
import com.winback.core.dao.account.TSysPermissionDao;
import com.winback.core.dao.account.mapper.TSysPermissionMapper;
import com.winback.core.model.account.TSysPermission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TSysPermissionDaoImpl extends AbstractDaoImpl<TSysPermission, TSysPermissionCondition, BaseMapper<TSysPermission,TSysPermissionCondition>> implements TSysPermissionDao{
	
	@Resource
	private TSysPermissionMapper tSysPermissionMapper;
	
	@Override
	protected BaseMapper<TSysPermission, TSysPermissionCondition> daoSupport() {
		return tSysPermissionMapper;
	}

	@Override
	protected TSysPermissionCondition blankCondition() {
		return new TSysPermissionCondition();
	}

	@Override
	public List<TSysPermission> getSpecialPermissionList() {
		TSysPermissionCondition condition = new TSysPermissionCondition();
		condition.setPermissionType("1");
		condition.setDeleteFlag(false);
		return tSysPermissionMapper.selectByCondition(condition);
	}

	@Override
	public List<TSysPermission> getNormalPermissionList() {
		TSysPermissionCondition condition = new TSysPermissionCondition();
		condition.setPermissionType("0");
		condition.setDeleteFlag(false);
		return tSysPermissionMapper.selectByCondition(condition);
	}
}
