
package com.winback.core.dao.account.impl;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.core.model.account.TFranchiseeApplyRecord;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.winback.core.condition.account.TSysStaffCondition;
import com.winback.core.dao.account.mapper.TSysStaffMapper;
import com.winback.core.model.account.TSysStaff;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.account.TSysStaffDao;

import java.util.List;
import java.util.Map;

@Service
public class TSysStaffDaoImpl extends AbstractDaoImpl<TSysStaff, TSysStaffCondition, BaseMapper<TSysStaff,TSysStaffCondition>> implements TSysStaffDao{
	
	@Resource
	private TSysStaffMapper tSysStaffMapper;
	
	@Override
	protected BaseMapper<TSysStaff, TSysStaffCondition> daoSupport() {
		return tSysStaffMapper;
	}

	@Override
	protected TSysStaffCondition blankCondition() {
		return new TSysStaffCondition();
	}

	@Override
	public TSysStaff findByMobile(String mobile) {
		TSysStaffCondition condition = new TSysStaffCondition();
		condition.setMobile(mobile);
		List<TSysStaff> list = tSysStaffMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageInfo<TSysStaff> getList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TSysStaff> list = tSysStaffMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public String getName(Integer staffId) {
		if (staffId == null) {
			return "";
		}
		TSysStaff staff = super.selectById(staffId);
		if (staff != null) {
			return staff.getName();
		}
		return "";
	}

	@Override
	public String getNameAndMobile(Integer staffId) {
		if (staffId == null) {
			return "";
		}
		TSysStaff staff = super.selectById(staffId);
		if (staff != null) {
			return staff.getName() + staff.getMobile();
		}
		return "";
	}
}
