
package com.tzCloud.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.account.TAccountMembershipPermissionCondition;
import com.tzCloud.core.dao.account.mapper.TAccountMembershipPermissionMapper;
import com.tzCloud.core.model.account.TAccountMembershipPermission;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.account.TAccountMembershipPermissionDao;

@Service
public class TAccountMembershipPermissionDaoImpl extends AbstractDaoImpl<TAccountMembershipPermission, TAccountMembershipPermissionCondition, BaseMapper<TAccountMembershipPermission,TAccountMembershipPermissionCondition>> implements TAccountMembershipPermissionDao{
	
	@Resource
	private TAccountMembershipPermissionMapper tAccountMembershipPermissionMapper;
	
	@Override
	protected BaseMapper<TAccountMembershipPermission, TAccountMembershipPermissionCondition> daoSupport() {
		return tAccountMembershipPermissionMapper;
	}

	@Override
	protected TAccountMembershipPermissionCondition blankCondition() {
		return new TAccountMembershipPermissionCondition();
	}

}
