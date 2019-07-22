
package com._360pai.core.dao.activity.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.activity.TAuctionPayOrderCondition;
import com._360pai.core.dao.activity.TAuctionPayOrderDao;
import com._360pai.core.dao.activity.mapper.TAuctionPayOrderMapper;
import com._360pai.core.model.activity.TAuctionPayOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TAuctionPayOrderDaoImpl extends AbstractDaoImpl<TAuctionPayOrder, TAuctionPayOrderCondition, BaseMapper<TAuctionPayOrder,TAuctionPayOrderCondition>> implements TAuctionPayOrderDao {
	
	@Resource
	private TAuctionPayOrderMapper tAuctionPayOrderMapper;
	
	@Override
	protected BaseMapper<TAuctionPayOrder, TAuctionPayOrderCondition> daoSupport() {
		return tAuctionPayOrderMapper;
	}

	@Override
	protected TAuctionPayOrderCondition blankCondition() {
		return new TAuctionPayOrderCondition();
	}

}
