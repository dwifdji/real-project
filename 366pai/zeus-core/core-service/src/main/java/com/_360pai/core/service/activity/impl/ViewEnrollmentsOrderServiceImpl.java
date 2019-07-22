package com._360pai.core.service.activity.impl;

import com._360pai.core.dao.activity.ViewEnrollmentsOrderDao;
import com._360pai.core.service.activity.ViewEnrollmentsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewEnrollmentsOrderServiceImpl	implements ViewEnrollmentsOrderService {

	@Autowired
	private ViewEnrollmentsOrderDao viewEnrollmentsOrderDao;


}