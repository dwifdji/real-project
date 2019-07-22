package com._360pai.core.service.payment.impl;

import com._360pai.core.dao.payment.ReleaseShareCommissionActionDao;
import com._360pai.core.service.payment.ReleaseShareCommissionActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReleaseShareCommissionActionServiceImpl	implements ReleaseShareCommissionActionService{

	@Autowired
	private ReleaseShareCommissionActionDao releaseShareCommissionActionDao;


}