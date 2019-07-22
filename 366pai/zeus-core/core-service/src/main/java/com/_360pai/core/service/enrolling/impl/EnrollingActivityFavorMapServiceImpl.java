package com._360pai.core.service.enrolling.impl;

import com._360pai.core.dao.enrolling.EnrollingActivityFavorMapDao;
import com._360pai.core.service.enrolling.EnrollingActivityFavorMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollingActivityFavorMapServiceImpl	implements EnrollingActivityFavorMapService{

	@Autowired
	private EnrollingActivityFavorMapDao enrollingActivityFavorMapDao;


}