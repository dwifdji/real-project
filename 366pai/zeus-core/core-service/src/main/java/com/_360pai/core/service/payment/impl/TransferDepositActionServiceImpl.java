package com._360pai.core.service.payment.impl;

import com._360pai.core.dao.payment.TransferDepositActionDao;
import com._360pai.core.service.payment.TransferDepositActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferDepositActionServiceImpl	implements TransferDepositActionService{

	@Autowired
	private TransferDepositActionDao transferDepositActionDao;


}