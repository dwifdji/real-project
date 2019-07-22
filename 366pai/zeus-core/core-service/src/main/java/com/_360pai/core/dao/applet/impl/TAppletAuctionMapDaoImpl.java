
package com._360pai.core.dao.applet.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.applet.TAppletAuctionMapCondition;
import com._360pai.core.dao.applet.TAppletAuctionMapDao;
import com._360pai.core.dao.applet.mapper.TAppletAuctionMapMapper;
import com._360pai.core.facade.applet.vo.AppletSearchAuctionActivityVo;
import com._360pai.core.facade.applet.vo.AuctionDetailVo;
import com._360pai.core.facade.shop.dto.ShopAuctionSearchDto;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.applet.TAppletAuctionMap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TAppletAuctionMapDaoImpl extends AbstractDaoImpl<TAppletAuctionMap, TAppletAuctionMapCondition, BaseMapper<TAppletAuctionMap,TAppletAuctionMapCondition>> implements TAppletAuctionMapDao{
	
	@Resource
	private TAppletAuctionMapMapper tAppletAuctionMapMapper;
	
	@Override
	protected BaseMapper<TAppletAuctionMap, TAppletAuctionMapCondition> daoSupport() {
		return tAppletAuctionMapMapper;
	}

	@Override
	protected TAppletAuctionMapCondition blankCondition() {
		return new TAppletAuctionMapCondition();
	}

	@Override
	public List<Integer> getFilterIds(TAppletAuctionMapCondition tAppletAuctionMapCondition) {
		return tAppletAuctionMapMapper.getFilterIds(tAppletAuctionMapCondition);

	}

	@Override
	public void updateAllAuciton(String shopId) {
		tAppletAuctionMapMapper.updateAllAuciton(shopId);
	}

	@Override
	public void batchDeleteStocks(Map<String, Object> params) {
		tAppletAuctionMapMapper.batchDeleteStocks(params);
	}

	@Override
	public void batchUpOfAuction(List<TAppletAuctionMap> tAppletAuctionMaps) {
		tAppletAuctionMapMapper.batchUpOfAuction(tAppletAuctionMaps);
	}

	@Override
	public PageInfo getActivityListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<AuctionActivity> list = tAppletAuctionMapMapper.getActivityListByPage(params);
		return new PageInfo<>(list);
	}

	@Override
	public List<AppletSearchAuctionActivityVo> searchShopAuctionList(ShopAuctionSearchDto req) {

		return tAppletAuctionMapMapper.searchShopAuctionList(req);
	}

	@Override
	public void batchUpdateAuctionMap(List<TAppletAuctionMap> tAppletAuctionMaps) {

		tAppletAuctionMapMapper.batchUpdateAuctionMap(tAppletAuctionMaps);
	}

	@Override
	public void batchUpdateStocks(Map<String, Object> params) {
		tAppletAuctionMapMapper.batchUpdateStocks(params);
	}

	@Override
	public List<String> getHotPushIds(ShopAuctionSearchDto params) {

		return tAppletAuctionMapMapper.getHotPushIds(params);
	}

	@Override
	public AuctionDetailVo getAppletAuctionDetail(Map<String, Object> params) {
		return tAppletAuctionMapMapper.getAppletAuctionDetail(params);
	}

	@Override
	public List<AppletSearchAuctionActivityVo> searchWebShopAuctionList(ShopAuctionSearchDto req) {
		return tAppletAuctionMapMapper.searchWebShopAuctionList(req);
	}

	@Override
	public void batchSetDeleteStocks(Map<String, Object> params) {
		tAppletAuctionMapMapper.batchSetDeleteStocks(params);
	}

	@Override
	public int countProduct(Integer shopId) {
		return tAppletAuctionMapMapper.countProduct(shopId);
	}


}
