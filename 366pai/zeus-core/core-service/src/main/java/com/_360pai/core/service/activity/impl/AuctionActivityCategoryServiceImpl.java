package com._360pai.core.service.activity.impl;

import com._360pai.core.dao.activity.AuctionActivityCategoryDao;
import com._360pai.core.service.activity.AuctionActivityCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionActivityCategoryServiceImpl	implements AuctionActivityCategoryService{

	@Autowired
	private AuctionActivityCategoryDao auctionActivityCategoryDao;


}