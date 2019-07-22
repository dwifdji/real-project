package com._360pai.core.service.activity.impl;

import com._360pai.core.condition.account.AgencyPortalCondition;
import com._360pai.core.dao.account.AgencyPortalDao;
import com._360pai.core.model.account.AgencyPortal;
import com._360pai.core.service.activity.AgencyPortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgencyPortalServiceImpl	implements AgencyPortalService{

	@Autowired
	private AgencyPortalDao agencyPortalDao;


	@Override
	public AgencyPortal getByAgencyId(Integer agencyId) {
		AgencyPortalCondition condition = new AgencyPortalCondition();
		condition.setAgencyId(agencyId);
		return agencyPortalDao.selectFirst(condition);
	}
}