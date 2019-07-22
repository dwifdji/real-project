package com._360pai.core.service.payment.impl;

import com._360pai.core.dao.payment.LockEnrollingDepositActionDao;
import com._360pai.core.service.payment.LockEnrollingDepositActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LockEnrollingDepositActionServiceImpl	implements LockEnrollingDepositActionService{

	@Autowired
	private LockEnrollingDepositActionDao lockEnrollingDepositActionDao;


}