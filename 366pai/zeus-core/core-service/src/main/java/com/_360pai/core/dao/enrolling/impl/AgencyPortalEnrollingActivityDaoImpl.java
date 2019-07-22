
package com._360pai.core.dao.enrolling.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.enrolling.AgencyPortalEnrollingActivityCondition;
import com._360pai.core.dao.enrolling.mapper.AgencyPortalEnrollingActivityMapper;
import com._360pai.core.model.enrolling.AgencyPortalEnrollingActivity;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.enrolling.AgencyPortalEnrollingActivityDao;

@Service
public class AgencyPortalEnrollingActivityDaoImpl extends AbstractDaoImpl<AgencyPortalEnrollingActivity, AgencyPortalEnrollingActivityCondition, BaseMapper<AgencyPortalEnrollingActivity,AgencyPortalEnrollingActivityCondition>> implements AgencyPortalEnrollingActivityDao{
	
	@Resource
	private AgencyPortalEnrollingActivityMapper agencyPortalEnrollingActivityMapper;
	
	@Override
	protected BaseMapper<AgencyPortalEnrollingActivity, AgencyPortalEnrollingActivityCondition> daoSupport() {
		return agencyPortalEnrollingActivityMapper;
	}

	@Override
	protected AgencyPortalEnrollingActivityCondition blankCondition() {
		return new AgencyPortalEnrollingActivityCondition();
	}

}
