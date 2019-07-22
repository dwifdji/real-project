
package com._360pai.core.dao.lease.impl;

import javax.annotation.Resource;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.lease.TLeaseStaffCondition;
import com._360pai.core.dao.lease.mapper.TLeaseStaffMapper;
import com._360pai.core.model.lease.TLeaseStaff;

import com._360pai.core.dao.lease.TLeaseStaffDao;

@Service
public class TLeaseStaffDaoImpl extends AbstractDaoImpl<TLeaseStaff, TLeaseStaffCondition, BaseMapper<TLeaseStaff,TLeaseStaffCondition>> implements TLeaseStaffDao{
	
	@Resource
	private TLeaseStaffMapper tLeaseStaffMapper;
	
	@Override
	protected BaseMapper<TLeaseStaff, TLeaseStaffCondition> daoSupport() {
		return tLeaseStaffMapper;
	}

	@Override
	protected TLeaseStaffCondition blankCondition() {
		return new TLeaseStaffCondition();
	}

}
