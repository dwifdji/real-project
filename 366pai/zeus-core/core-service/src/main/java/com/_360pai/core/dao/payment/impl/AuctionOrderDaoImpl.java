
package com._360pai.core.dao.payment.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.payment.AuctionOrderCondition;
import com._360pai.core.dao.payment.AuctionOrderDao;
import com._360pai.core.dao.payment.mapper.AuctionOrderMapper;
import com._360pai.core.facade.payment.vo.ShopAuctionOrderVo;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.payment.AuctionOrder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class AuctionOrderDaoImpl extends AbstractDaoImpl<AuctionOrder, AuctionOrderCondition, BaseMapper<AuctionOrder,AuctionOrderCondition>> implements AuctionOrderDao{
	
	@Resource
	private AuctionOrderMapper auctionOrderMapper;
	
	@Override
	protected BaseMapper<AuctionOrder, AuctionOrderCondition> daoSupport() {
		return auctionOrderMapper;
	}

	@Override
	protected AuctionOrderCondition blankCondition() {
		return new AuctionOrderCondition();
	}

	@Override
	public PageInfo getAuctionOrderList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<AuctionOrder> list = auctionOrderMapper.getAuctionOrderList(params);
		return new PageInfo<>(list);
	}

	@Override
	public List<Map<String,Object>> getAuctionOrders(Map<String, Object> params) {
		return auctionOrderMapper.getAuctionOrders(params);
	}

	@Override
	public PageInfo getShopDealCommissionListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<ShopAuctionOrderVo> list = auctionOrderMapper.getShopDealCommissionListByPage(params);
		return new PageInfo<>(list);
	}

	@Override
	public List<AuctionOrder> getBuyerNeedToPaidList(Integer partyId) {
		return auctionOrderMapper.getBuyerNeedToPaidList(partyId);
	}

	@Override
	public String getBuyerNameByActivityId(Integer assetId) {

		return auctionOrderMapper.getBuyerNameByActivityId(assetId);
	}
}
