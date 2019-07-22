package com._360pai.core.service.activity.impl;

import com._360pai.core.dao.activity.ActivityWhiteListCityDao;
import com._360pai.core.service.activity.ActivityWhiteListCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityWhiteListCityServiceImpl	implements ActivityWhiteListCityService{

	@Autowired
	private ActivityWhiteListCityDao activityWhiteListCityDao;


}