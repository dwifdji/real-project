package com._360pai.core.service.payment.impl;

import com._360pai.core.dao.payment.LockDepositForSendBackOrderActionDao;
import com._360pai.core.service.payment.LockDepositForSendBackOrderActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LockDepositForSendBackOrderActionServiceImpl	implements LockDepositForSendBackOrderActionService{

	@Autowired
	private LockDepositForSendBackOrderActionDao lockDepositForSendBackOrderActionDao;


}