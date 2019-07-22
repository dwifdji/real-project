
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import com._360pai.core.model.account.AgencyCopy;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.AgencyCondition;
import com._360pai.core.dao.account.mapper.AgencyMapper;
import com._360pai.core.model.account.Agency;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.AgencyDao;

import java.util.List;

@Service
public class AgencyDaoImpl extends AbstractDaoImpl<Agency, AgencyCondition, BaseMapper<Agency,AgencyCondition>> implements AgencyDao{
	
	@Resource
	private AgencyMapper agencyMapper;
	
	@Override
	protected BaseMapper<Agency, AgencyCondition> daoSupport() {
		return agencyMapper;
	}

	@Override
	protected AgencyCondition blankCondition() {
		return new AgencyCondition();
	}

	@Override
	public Agency getByLicense(String license) {
		AgencyCondition condition = new AgencyCondition();
		condition.setLicense(license);
		List<Agency> list = agencyMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
