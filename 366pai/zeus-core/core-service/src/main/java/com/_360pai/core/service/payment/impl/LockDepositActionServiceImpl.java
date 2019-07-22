package com._360pai.core.service.payment.impl;

import com._360pai.core.dao.payment.LockDepositActionDao;
import com._360pai.core.service.payment.LockDepositActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LockDepositActionServiceImpl	implements LockDepositActionService{

	@Autowired
	private LockDepositActionDao lockDepositActionDao;


}