package com._360pai.core.service.activity.impl;

import com._360pai.core.dao.activity.ActivityBlackListCityDao;
import com._360pai.core.service.activity.ActivityBlackListCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityBlackListCityServiceImpl	implements ActivityBlackListCityService{

	@Autowired
	private ActivityBlackListCityDao activityBlackListCityDao;


}