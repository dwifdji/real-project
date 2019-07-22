package com._360pai.core.service.payment.impl;

import com._360pai.core.dao.payment.ReleaseTransferredDepositActionDao;
import com._360pai.core.service.payment.ReleaseTransferredDepositActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReleaseTransferredDepositActionServiceImpl	implements ReleaseTransferredDepositActionService{

	@Autowired
	private ReleaseTransferredDepositActionDao releaseTransferredDepositActionDao;


}