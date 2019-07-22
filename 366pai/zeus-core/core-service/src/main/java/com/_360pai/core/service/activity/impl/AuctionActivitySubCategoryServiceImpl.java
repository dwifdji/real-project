package com._360pai.core.service.activity.impl;

import com._360pai.core.dao.activity.AuctionActivitySubCategoryDao;
import com._360pai.core.service.activity.AuctionActivitySubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionActivitySubCategoryServiceImpl	implements AuctionActivitySubCategoryService{

	@Autowired
	private AuctionActivitySubCategoryDao auctionActivitySubCategoryDao;


}