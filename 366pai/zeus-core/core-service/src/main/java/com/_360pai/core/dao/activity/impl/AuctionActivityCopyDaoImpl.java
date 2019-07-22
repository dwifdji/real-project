
package com._360pai.core.dao.activity.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.activity.AuctionActivityCopyCondition;
import com._360pai.core.dao.activity.mapper.AuctionActivityCopyMapper;
import com._360pai.core.model.activity.AuctionActivityCopy;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.activity.AuctionActivityCopyDao;

@Service
public class AuctionActivityCopyDaoImpl extends AbstractDaoImpl<AuctionActivityCopy, AuctionActivityCopyCondition, BaseMapper<AuctionActivityCopy,AuctionActivityCopyCondition>> implements AuctionActivityCopyDao{
	
	@Resource
	private AuctionActivityCopyMapper auctionActivityCopyMapper;
	
	@Override
	protected BaseMapper<AuctionActivityCopy, AuctionActivityCopyCondition> daoSupport() {
		return auctionActivityCopyMapper;
	}

	@Override
	protected AuctionActivityCopyCondition blankCondition() {
		return new AuctionActivityCopyCondition();
	}

}
