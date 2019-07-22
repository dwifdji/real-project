
package com._360pai.core.dao.applet.impl;

import javax.annotation.Resource;

import com._360pai.core.facade.applet.vo.ShopEnrollingAndAuctionListVO;
import com._360pai.core.facade.applet.vo.ShopEnrollingListVO;
import com._360pai.core.facade.shop.dto.ShopEnrollingSearchDto;
import com._360pai.core.facade.shop.req.ShopReq;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.applet.TAppletEnrollingMapCondition;
import com._360pai.core.dao.applet.mapper.TAppletEnrollingMapMapper;
import com._360pai.core.model.applet.TAppletEnrollingMap;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.applet.TAppletEnrollingMapDao;

import java.util.List;

@Service
public class TAppletEnrollingMapDaoImpl extends AbstractDaoImpl<TAppletEnrollingMap, TAppletEnrollingMapCondition, BaseMapper<TAppletEnrollingMap,TAppletEnrollingMapCondition>> implements TAppletEnrollingMapDao{
	
	@Resource
	private TAppletEnrollingMapMapper tAppletEnrollingMapMapper;
	
	@Override
	protected BaseMapper<TAppletEnrollingMap, TAppletEnrollingMapCondition> daoSupport() {
		return tAppletEnrollingMapMapper;
	}

	@Override
	protected TAppletEnrollingMapCondition blankCondition() {
		return new TAppletEnrollingMapCondition();
	}

	@Override
	public List<ShopEnrollingListVO> getShopWebEnrollingList(ShopEnrollingSearchDto params) {
		return tAppletEnrollingMapMapper.getShopWebEnrollingList(params);
	}

	@Override
	public List<ShopEnrollingListVO> getShopEnrollingList(ShopEnrollingSearchDto params) {
		return tAppletEnrollingMapMapper.getShopEnrollingList(params);
	}

	@Override
	public void saveBatchAppletEnrollingList(List<TAppletEnrollingMap> saveTAppletEnrollingMaps) {
		tAppletEnrollingMapMapper.saveBatchAppletEnrollingList(saveTAppletEnrollingMaps);
	}

	@Override
	public void updateBatchAppletEnrollingList(List<TAppletEnrollingMap> updateTAppletEnrollingMaps) {
		tAppletEnrollingMapMapper.updateBatchAppletEnrollingList(updateTAppletEnrollingMaps);
	}

	@Override
	public void deleteEnrollingHomePage(String shopId) {
		tAppletEnrollingMapMapper.deleteEnrollingHomePage(shopId);
	}

	@Override
	public void setEnrollingHomePage(List<TAppletEnrollingMap> tAppletEnrollingMaps) {
		tAppletEnrollingMapMapper.setEnrollingHomePage(tAppletEnrollingMaps);
	}

	@Override
	public List<ShopEnrollingAndAuctionListVO> getSearchAuctionAndEnrollingList(ShopEnrollingSearchDto enrollingParams) {

		return tAppletEnrollingMapMapper.getSearchAuctionAndEnrollingList(enrollingParams);
	}

	@Override
	public List<String> getHotPushIds(ShopEnrollingSearchDto params) {
		return tAppletEnrollingMapMapper.getHotPushIds(params);
	}

	@Override
	public int countProduct(Integer shopId) {
		return tAppletEnrollingMapMapper.countProduct(shopId);
	}
}
