package com._360pai.core.service.payment.impl;

import com._360pai.core.dao.payment.TransferDepositForSendBackOrderActionDao;
import com._360pai.core.service.payment.TransferDepositForSendBackOrderActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferDepositForSendBackOrderActionServiceImpl	implements TransferDepositForSendBackOrderActionService{

	@Autowired
	private TransferDepositForSendBackOrderActionDao transferDepositForSendBackOrderActionDao;


}