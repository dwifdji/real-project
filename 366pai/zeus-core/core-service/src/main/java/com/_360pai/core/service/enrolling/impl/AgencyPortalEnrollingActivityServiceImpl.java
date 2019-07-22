package com._360pai.core.service.enrolling.impl;

import com._360pai.core.condition.enrolling.AgencyPortalEnrollingActivityCondition;
import com._360pai.core.dao.enrolling.AgencyPortalEnrollingActivityDao;
import com._360pai.core.model.enrolling.AgencyPortalEnrollingActivity;
import com._360pai.core.service.enrolling.AgencyPortalEnrollingActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgencyPortalEnrollingActivityServiceImpl	implements AgencyPortalEnrollingActivityService{

	@Autowired
	private AgencyPortalEnrollingActivityDao agencyPortalEnrollingActivityDao;


	@Override
	public void saveOrUpdate(AgencyPortalEnrollingActivity req,Integer viewCount) {
		AgencyPortalEnrollingActivityCondition condition = new AgencyPortalEnrollingActivityCondition();
		condition.setAgencyId(req.getAgencyId());
		condition.setEnrollingActivityId(req.getEnrollingActivityId());

		AgencyPortalEnrollingActivity activity = agencyPortalEnrollingActivityDao.selectFirst(condition);
		if(activity == null){
			agencyPortalEnrollingActivityDao.insert(req);
			return;
		}
		activity.setViewCount(activity.getViewCount()+viewCount);
		agencyPortalEnrollingActivityDao.updateById(activity);

	}

	@Override
	public AgencyPortalEnrollingActivity getAgencyPortalEnrollingActivity(AgencyPortalEnrollingActivityCondition req) {
		return agencyPortalEnrollingActivityDao.selectFirst(req);
	}
}