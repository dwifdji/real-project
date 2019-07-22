package com._360pai.core.service.payment.impl;

import com._360pai.core.dao.payment.PayDepositActionDao;
import com._360pai.core.service.payment.PayDepositActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayDepositActionServiceImpl	implements PayDepositActionService{

	@Autowired
	private PayDepositActionDao payDepositActionDao;


}