package com._360pai.core.service.payment.impl;

import com._360pai.core.dao.payment.LockPayCommissionActionDao;
import com._360pai.core.service.payment.LockPayCommissionActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LockPayCommissionActionServiceImpl	implements LockPayCommissionActionService{

	@Autowired
	private LockPayCommissionActionDao lockPayCommissionActionDao;


}