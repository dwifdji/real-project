package com._360pai.core.service.activity.impl;

import com._360pai.core.dao.activity.DealAnnouncementDao;
import com._360pai.core.service.activity.DealAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DealAnnouncementServiceImpl	implements DealAnnouncementService{

	@Autowired
	private DealAnnouncementDao dealAnnouncementDao;


}