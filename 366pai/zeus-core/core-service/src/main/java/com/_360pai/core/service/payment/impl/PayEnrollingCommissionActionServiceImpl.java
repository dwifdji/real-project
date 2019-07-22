package com._360pai.core.service.payment.impl;

import com._360pai.core.dao.payment.PayEnrollingCommissionActionDao;
import com._360pai.core.service.payment.PayEnrollingCommissionActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayEnrollingCommissionActionServiceImpl	implements PayEnrollingCommissionActionService{

	@Autowired
	private PayEnrollingCommissionActionDao payEnrollingCommissionActionDao;


}