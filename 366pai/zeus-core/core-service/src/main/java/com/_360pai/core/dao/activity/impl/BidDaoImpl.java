
package com._360pai.core.dao.activity.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.activity.BidCondition;
import com._360pai.core.dao.activity.mapper.BidMapper;
import com._360pai.core.model.activity.Bid;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.activity.BidDao;

@Service
public class BidDaoImpl extends AbstractDaoImpl<Bid, BidCondition, BaseMapper<Bid,BidCondition>> implements BidDao{
	
	@Resource
	private BidMapper bidMapper;
	
	@Override
	protected BaseMapper<Bid, BidCondition> daoSupport() {
		return bidMapper;
	}

	@Override
	protected BidCondition blankCondition() {
		return new BidCondition();
	}

}
