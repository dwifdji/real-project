
package com.winback.core.dao.account.impl;

import javax.annotation.Resource;

import com.winback.core.model.account.TSysPermission;
import org.springframework.stereotype.Service;

import com.winback.core.condition.account.TSysStaffPermissionMapCondition;
import com.winback.core.dao.account.mapper.TSysStaffPermissionMapMapper;
import com.winback.core.model.account.TSysStaffPermissionMap;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.account.TSysStaffPermissionMapDao;

import java.util.List;

@Service
public class TSysStaffPermissionMapDaoImpl extends AbstractDaoImpl<TSysStaffPermissionMap, TSysStaffPermissionMapCondition, BaseMapper<TSysStaffPermissionMap,TSysStaffPermissionMapCondition>> implements TSysStaffPermissionMapDao{
	
	@Resource
	private TSysStaffPermissionMapMapper tSysStaffPermissionMapMapper;
	
	@Override
	protected BaseMapper<TSysStaffPermissionMap, TSysStaffPermissionMapCondition> daoSupport() {
		return tSysStaffPermissionMapMapper;
	}

	@Override
	protected TSysStaffPermissionMapCondition blankCondition() {
		return new TSysStaffPermissionMapCondition();
	}

	@Override
	public List<TSysStaffPermissionMap> findBy(Integer staffId) {
		TSysStaffPermissionMapCondition condition = new TSysStaffPermissionMapCondition();
		condition.setStaffId(staffId);
		return tSysStaffPermissionMapMapper.selectByCondition(condition);
	}

	@Override
	public TSysStaffPermissionMap findBy(Integer staffId, Integer permissionId) {
		TSysStaffPermissionMapCondition condition = new TSysStaffPermissionMapCondition();
		condition.setStaffId(staffId);
		condition.setPermissionId(permissionId);
		List<TSysStaffPermissionMap> list = tSysStaffPermissionMapMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<TSysPermission> findPermissionBy(Integer staffId) {
		return tSysStaffPermissionMapMapper.findPermissionBy(staffId);
	}
}
