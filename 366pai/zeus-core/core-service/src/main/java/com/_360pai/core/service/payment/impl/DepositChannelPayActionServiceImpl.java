package com._360pai.core.service.payment.impl;

import com._360pai.core.dao.payment.DepositChannelPayActionDao;
import com._360pai.core.service.payment.DepositChannelPayActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepositChannelPayActionServiceImpl	implements DepositChannelPayActionService{

	@Autowired
	private DepositChannelPayActionDao depositChannelPayActionDao;


}