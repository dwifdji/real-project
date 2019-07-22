package com._360pai.core.service.account.impl;

import com._360pai.core.dao.account.AgencyChannelAgentDao;
import com._360pai.core.service.account.AgencyChannelAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgencyChannelAgentServiceImpl	implements AgencyChannelAgentService{

	@Autowired
	private AgencyChannelAgentDao agencyChannelAgentDao;


}