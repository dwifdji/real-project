package com._360pai.core.service.payment.impl;

import com._360pai.core.dao.payment.ReleasePayOrderActionDao;
import com._360pai.core.service.payment.ReleasePayOrderActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReleasePayOrderActionServiceImpl	implements ReleasePayOrderActionService{

	@Autowired
	private ReleasePayOrderActionDao releasePayOrderActionDao;


}