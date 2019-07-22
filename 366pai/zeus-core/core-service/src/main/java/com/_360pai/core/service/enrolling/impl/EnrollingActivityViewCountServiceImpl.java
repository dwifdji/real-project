package com._360pai.core.service.enrolling.impl;

import com._360pai.core.dao.enrolling.EnrollingActivityViewCountDao;
import com._360pai.core.service.enrolling.EnrollingActivityViewCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollingActivityViewCountServiceImpl	implements EnrollingActivityViewCountService{

	@Autowired
	private EnrollingActivityViewCountDao enrollingActivityViewCountDao;


}