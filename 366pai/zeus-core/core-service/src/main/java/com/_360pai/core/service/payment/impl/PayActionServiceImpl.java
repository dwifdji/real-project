package com._360pai.core.service.payment.impl;

import com._360pai.core.dao.payment.PayActionDao;
import com._360pai.core.service.payment.PayActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayActionServiceImpl	implements PayActionService{

	@Autowired
	private PayActionDao payActionDao;


}