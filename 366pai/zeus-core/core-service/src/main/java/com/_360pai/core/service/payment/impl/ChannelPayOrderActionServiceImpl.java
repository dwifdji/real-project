package com._360pai.core.service.payment.impl;

import com._360pai.core.dao.payment.ChannelPayOrderActionDao;
import com._360pai.core.service.payment.ChannelPayOrderActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelPayOrderActionServiceImpl	implements ChannelPayOrderActionService{

	@Autowired
	private ChannelPayOrderActionDao channelPayOrderActionDao;


}