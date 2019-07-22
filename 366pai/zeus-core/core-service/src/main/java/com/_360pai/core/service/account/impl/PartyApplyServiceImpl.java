package com._360pai.core.service.account.impl;

import com._360pai.core.dao.account.PartyApplyDao;
import com._360pai.core.service.account.PartyApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartyApplyServiceImpl	implements PartyApplyService{

	@Autowired
	private PartyApplyDao partyApplyDao;


}