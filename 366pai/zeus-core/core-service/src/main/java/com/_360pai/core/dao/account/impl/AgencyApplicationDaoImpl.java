
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import com._360pai.core.common.constants.AccountEnum;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.AgencyApplicationCondition;
import com._360pai.core.dao.account.mapper.AgencyApplicationMapper;
import com._360pai.core.model.account.AgencyApplication;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.AgencyApplicationDao;

import java.util.List;

@Service
public class AgencyApplicationDaoImpl extends AbstractDaoImpl<AgencyApplication, AgencyApplicationCondition, BaseMapper<AgencyApplication,AgencyApplicationCondition>> implements AgencyApplicationDao{
	
	@Resource
	private AgencyApplicationMapper agencyApplicationMapper;
	
	@Override
	protected BaseMapper<AgencyApplication, AgencyApplicationCondition> daoSupport() {
		return agencyApplicationMapper;
	}

	@Override
	protected AgencyApplicationCondition blankCondition() {
		return new AgencyApplicationCondition();
	}

	@Override
	public AgencyApplication getApprovedByLicense(String license) {
		AgencyApplicationCondition condition = new AgencyApplicationCondition();
		condition.setLicense(license);
		condition.setStatus(AccountEnum.ApplyStatus.APPROVED.getKey());
		List<AgencyApplication> list = agencyApplicationMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
