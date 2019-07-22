package com._360pai.core.service.account.impl;

import com._360pai.core.dao.account.AgencyPortalRegisteredAccountDao;
import com._360pai.core.service.account.AgencyPortalRegisteredAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgencyPortalRegisteredAccountServiceImpl	implements AgencyPortalRegisteredAccountService{

	@Autowired
	private AgencyPortalRegisteredAccountDao agencyPortalRegisteredAccountDao;


}