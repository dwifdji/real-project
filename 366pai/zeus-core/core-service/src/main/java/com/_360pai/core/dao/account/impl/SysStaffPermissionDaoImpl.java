
package com._360pai.core.dao.account.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.SysStaffPermissionCondition;
import com._360pai.core.dao.account.SysStaffPermissionDao;
import com._360pai.core.dao.account.mapper.SysStaffPermissionMapper;
import com._360pai.core.facade.account.vo.PermissionVo;
import com._360pai.core.model.account.SysStaffPermission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysStaffPermissionDaoImpl extends AbstractDaoImpl<SysStaffPermission, SysStaffPermissionCondition, BaseMapper<SysStaffPermission,SysStaffPermissionCondition>> implements SysStaffPermissionDao{
	
	@Resource
	private SysStaffPermissionMapper sysStaffPermissionMapper;
	
	@Override
	protected BaseMapper<SysStaffPermission, SysStaffPermissionCondition> daoSupport() {
		return sysStaffPermissionMapper;
	}

	@Override
	protected SysStaffPermissionCondition blankCondition() {
		return new SysStaffPermissionCondition();
	}

	@Override
	public List<PermissionVo> getStaffSpecialPermissionList(Integer staffId) {
		return sysStaffPermissionMapper.getStaffSpecialPermissionList(staffId);
	}

	@Override
	public List<SysStaffPermission> getStaffPermissionList(Integer staffId) {
		SysStaffPermissionCondition condition = new SysStaffPermissionCondition();
		condition.setStaffId(staffId);
		condition.setIsDelete(false);
		return sysStaffPermissionMapper.selectByCondition(condition);
	}

	@Override
	public List<SysStaffPermission> getAllStaffPermissionList(Integer staffId) {
		SysStaffPermissionCondition condition = new SysStaffPermissionCondition();
		condition.setStaffId(staffId);
		return sysStaffPermissionMapper.selectByCondition(condition);
	}
}
