package com._360pai.core.service.payment.impl;

import com._360pai.core.dao.payment.ReleasePayCommissionActionDao;
import com._360pai.core.service.payment.ReleasePayCommissionActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReleasePayCommissionActionServiceImpl	implements ReleasePayCommissionActionService{

	@Autowired
	private ReleasePayCommissionActionDao releasePayCommissionActionDao;


}