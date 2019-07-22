
package com._360pai.core.dao.activity.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.activity.AuctionActivityCategoryCondition;
import com._360pai.core.dao.activity.mapper.AuctionActivityCategoryMapper;
import com._360pai.core.model.activity.AuctionActivityCategory;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.activity.AuctionActivityCategoryDao;

@Service
public class AuctionActivityCategoryDaoImpl extends AbstractDaoImpl<AuctionActivityCategory, AuctionActivityCategoryCondition, BaseMapper<AuctionActivityCategory,AuctionActivityCategoryCondition>> implements AuctionActivityCategoryDao{
	
	@Resource
	private AuctionActivityCategoryMapper auctionActivityCategoryMapper;
	
	@Override
	protected BaseMapper<AuctionActivityCategory, AuctionActivityCategoryCondition> daoSupport() {
		return auctionActivityCategoryMapper;
	}

	@Override
	protected AuctionActivityCategoryCondition blankCondition() {
		return new AuctionActivityCategoryCondition();
	}

}
