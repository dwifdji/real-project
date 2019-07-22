package com._360pai.core.service.payment.impl;

import com._360pai.core.dao.payment.PlatformChannelPayActionDao;
import com._360pai.core.service.payment.PlatformChannelPayActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlatformChannelPayActionServiceImpl	implements PlatformChannelPayActionService{

	@Autowired
	private PlatformChannelPayActionDao platformChannelPayActionDao;


}