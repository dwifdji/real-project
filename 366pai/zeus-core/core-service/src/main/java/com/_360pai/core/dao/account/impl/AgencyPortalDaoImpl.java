
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.AgencyPortalCondition;
import com._360pai.core.dao.account.mapper.AgencyPortalMapper;
import com._360pai.core.model.account.AgencyPortal;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.AgencyPortalDao;

import java.util.List;

@Service
public class AgencyPortalDaoImpl extends AbstractDaoImpl<AgencyPortal, AgencyPortalCondition, BaseMapper<AgencyPortal,AgencyPortalCondition>> implements AgencyPortalDao{
	
	@Resource
	private AgencyPortalMapper agencyPortalMapper;
	
	@Override
	protected BaseMapper<AgencyPortal, AgencyPortalCondition> daoSupport() {
		return agencyPortalMapper;
	}

	@Override
	protected AgencyPortalCondition blankCondition() {
		return new AgencyPortalCondition();
	}

	@Override
	public AgencyPortal getByAgencyId(Integer agencyId) {
		AgencyPortalCondition condition = new AgencyPortalCondition();
		condition.setAgencyId(agencyId);
		List<AgencyPortal> list = agencyPortalMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
