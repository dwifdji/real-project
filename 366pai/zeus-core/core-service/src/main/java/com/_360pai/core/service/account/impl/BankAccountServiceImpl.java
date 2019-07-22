package com._360pai.core.service.account.impl;

import com._360pai.core.dao.account.BankAccountDao;
import com._360pai.core.service.account.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountServiceImpl	implements BankAccountService{

	@Autowired
	private BankAccountDao bankAccountDao;


}