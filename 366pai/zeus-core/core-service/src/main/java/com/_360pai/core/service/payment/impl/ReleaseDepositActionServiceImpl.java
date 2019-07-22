package com._360pai.core.service.payment.impl;

import com._360pai.core.dao.payment.ReleaseDepositActionDao;
import com._360pai.core.service.payment.ReleaseDepositActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReleaseDepositActionServiceImpl	implements ReleaseDepositActionService{

	@Autowired
	private ReleaseDepositActionDao releaseDepositActionDao;


}