package com._360pai.core.service.payment.impl;

import com._360pai.core.dao.payment.LockPayOrderActionDao;
import com._360pai.core.service.payment.LockPayOrderActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LockPayOrderActionServiceImpl	implements LockPayOrderActionService{

	@Autowired
	private LockPayOrderActionDao lockPayOrderActionDao;


}