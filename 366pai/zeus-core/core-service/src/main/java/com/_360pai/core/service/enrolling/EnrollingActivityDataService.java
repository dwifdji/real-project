package com._360pai.core.service.enrolling;


import com._360pai.core.condition.enrolling.EnrollingActivityDataCondition;
import com._360pai.core.model.enrolling.EnrollingActivityData;

public interface EnrollingActivityDataService{

    int saveActivityData(EnrollingActivityData req);



    EnrollingActivityData getActivityData(EnrollingActivityDataCondition req);



	Integer updateActivityData(EnrollingActivityData data);



	EnrollingActivityData getActivityDataByActivityId(Integer activityId);


	//老数据迁徙
	void oldDataMigration(String ids,String templateId);



	void oldDataAddBusTypeName();


}