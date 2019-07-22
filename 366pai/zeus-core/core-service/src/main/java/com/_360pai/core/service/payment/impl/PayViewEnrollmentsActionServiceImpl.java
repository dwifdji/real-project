package com._360pai.core.service.payment.impl;

import com._360pai.core.dao.payment.PayViewEnrollmentsActionDao;
import com._360pai.core.service.payment.PayViewEnrollmentsActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayViewEnrollmentsActionServiceImpl	implements PayViewEnrollmentsActionService{

	@Autowired
	private PayViewEnrollmentsActionDao payViewEnrollmentsActionDao;


}