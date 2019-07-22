package com._360pai.core.service.enrolling;


import com._360pai.core.condition.enrolling.AgencyPortalEnrollingActivityCondition;
import com._360pai.core.model.enrolling.AgencyPortalEnrollingActivity;

public interface AgencyPortalEnrollingActivityService{


    void saveOrUpdate(AgencyPortalEnrollingActivity req,Integer viewCount);



    AgencyPortalEnrollingActivity getAgencyPortalEnrollingActivity(AgencyPortalEnrollingActivityCondition req);

}