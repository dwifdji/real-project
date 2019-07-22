package com._360pai.core.service.payment.impl;

import com._360pai.core.dao.payment.LockShareCommissionActionDao;
import com._360pai.core.service.payment.LockShareCommissionActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LockShareCommissionActionServiceImpl	implements LockShareCommissionActionService{

	@Autowired
	private LockShareCommissionActionDao lockShareCommissionActionDao;


}