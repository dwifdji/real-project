
package com.tzCloud.core.dao.auction.impl;

import javax.annotation.Resource;


import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.auction.TMapAuctionCondition;
import com.tzCloud.core.dao.auction.TMapAuctionDao;
import com.tzCloud.core.dao.auction.mapper.TMapAuctionMapper;
import com.tzCloud.core.model.auction.TMapAuction;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Map;

@Service
public class TMapAuctionDaoImpl extends AbstractDaoImpl<TMapAuction, TMapAuctionCondition, BaseMapper<TMapAuction,TMapAuctionCondition>> implements TMapAuctionDao {
	
	@Resource
	private TMapAuctionMapper tMapAuctionMapper;
	
	@Override
	protected BaseMapper<TMapAuction, TMapAuctionCondition> daoSupport() {
		return tMapAuctionMapper;
	}

	@Override
	protected TMapAuctionCondition blankCondition() {
		return new TMapAuctionCondition();
	}

	@Override
	public List<TMapAuction> getMapAuctionListByParam(Map<String, String> param) {
		return tMapAuctionMapper.getMapAuctionListByParam(param);
	}
}
