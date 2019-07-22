
package com._360pai.core.dao.activity.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.activity.AuctionActivitySubCategoryCondition;
import com._360pai.core.dao.activity.mapper.AuctionActivitySubCategoryMapper;
import com._360pai.core.model.activity.AuctionActivitySubCategory;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.activity.AuctionActivitySubCategoryDao;

@Service
public class AuctionActivitySubCategoryDaoImpl extends AbstractDaoImpl<AuctionActivitySubCategory, AuctionActivitySubCategoryCondition, BaseMapper<AuctionActivitySubCategory,AuctionActivitySubCategoryCondition>> implements AuctionActivitySubCategoryDao{
	
	@Resource
	private AuctionActivitySubCategoryMapper auctionActivitySubCategoryMapper;
	
	@Override
	protected BaseMapper<AuctionActivitySubCategory, AuctionActivitySubCategoryCondition> daoSupport() {
		return auctionActivitySubCategoryMapper;
	}

	@Override
	protected AuctionActivitySubCategoryCondition blankCondition() {
		return new AuctionActivitySubCategoryCondition();
	}

}
