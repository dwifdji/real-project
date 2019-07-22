
package com._360pai.core.dao.applet.impl;

import javax.annotation.Resource;

import com._360pai.core.facade.applet.vo.InviteRecord;
import com._360pai.core.model.applet.TAppletShop;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.applet.TAppletFavoriteShopCondition;
import com._360pai.core.dao.applet.mapper.TAppletFavoriteShopMapper;
import com._360pai.core.model.applet.TAppletFavoriteShop;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.applet.TAppletFavoriteShopDao;

import java.util.List;
import java.util.Map;

@Service
public class TAppletFavoriteShopDaoImpl extends AbstractDaoImpl<TAppletFavoriteShop, TAppletFavoriteShopCondition, BaseMapper<TAppletFavoriteShop,TAppletFavoriteShopCondition>> implements TAppletFavoriteShopDao{
	
	@Resource
	private TAppletFavoriteShopMapper tAppletFavoriteShopMapper;
	
	@Override
	protected BaseMapper<TAppletFavoriteShop, TAppletFavoriteShopCondition> daoSupport() {
		return tAppletFavoriteShopMapper;
	}

	@Override
	protected TAppletFavoriteShopCondition blankCondition() {
		return new TAppletFavoriteShopCondition();
	}

	@Override
	public TAppletFavoriteShop getByShopIdPartyId(Integer shopId, String openId) {
		TAppletFavoriteShopCondition condition = new TAppletFavoriteShopCondition();
		condition.setShopId(shopId);
		condition.setOpenId(openId);
		List<TAppletFavoriteShop> list = tAppletFavoriteShopMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TAppletShop> list = tAppletFavoriteShopMapper.getShopListByPage(params);
		return new PageInfo<>(list);
	}

	@Override
	public boolean isFavorShop(String openId, Integer shopId) {
		TAppletFavoriteShopCondition condition = new TAppletFavoriteShopCondition();
		condition.setShopId(shopId);
		condition.setOpenId(openId);
		condition.setIsDelete(false);
		return tAppletFavoriteShopMapper.selectByCondition(condition).size() > 0;
	}
}
