
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.AgencyPortalRegisteredAccountCondition;
import com._360pai.core.dao.account.mapper.AgencyPortalRegisteredAccountMapper;
import com._360pai.core.model.account.AgencyPortalRegisteredAccount;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.AgencyPortalRegisteredAccountDao;

@Service
public class AgencyPortalRegisteredAccountDaoImpl extends AbstractDaoImpl<AgencyPortalRegisteredAccount, AgencyPortalRegisteredAccountCondition, BaseMapper<AgencyPortalRegisteredAccount,AgencyPortalRegisteredAccountCondition>> implements AgencyPortalRegisteredAccountDao{
	
	@Resource
	private AgencyPortalRegisteredAccountMapper agencyPortalRegisteredAccountMapper;
	
	@Override
	protected BaseMapper<AgencyPortalRegisteredAccount, AgencyPortalRegisteredAccountCondition> daoSupport() {
		return agencyPortalRegisteredAccountMapper;
	}

	@Override
	protected AgencyPortalRegisteredAccountCondition blankCondition() {
		return new AgencyPortalRegisteredAccountCondition();
	}

}
