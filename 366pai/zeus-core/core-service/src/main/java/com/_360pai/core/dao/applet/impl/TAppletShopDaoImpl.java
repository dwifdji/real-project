
package com._360pai.core.dao.applet.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.condition.applet.TAppletShopCondition;
import com._360pai.core.dao.applet.TAppletShopDao;
import com._360pai.core.dao.applet.mapper.TAppletShopMapper;
import com._360pai.core.model.applet.TAppletShop;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TAppletShopDaoImpl extends AbstractDaoImpl<TAppletShop, TAppletShopCondition, BaseMapper<TAppletShop,TAppletShopCondition>> implements TAppletShopDao{
	
	@Resource
	private TAppletShopMapper tAppletShopMapper;

	@Override
	protected BaseMapper<TAppletShop, TAppletShopCondition> daoSupport() {
		return tAppletShopMapper;
	}

	@Override
	protected TAppletShopCondition blankCondition() {
		return new TAppletShopCondition();
	}

	@Override
	public int addFavoriteCount(Integer id) {
		return tAppletShopMapper.addFavoriteCount(id);
	}

	@Override
	public int subFavoriteCount(Integer id) {
		return tAppletShopMapper.subFavoriteCount(id);
	}

	@Override
	public PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TAppletShop> list = tAppletShopMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public int countShopInviteNum(Integer shopId) {
		return tAppletShopMapper.countInviteNum(AccountEnum.InviteType.DP.getKey(), shopId);
	}

	@Override
	public TAppletShop getByPartyId(Integer partyId) {
		TAppletShopCondition condition = new TAppletShopCondition();
		condition.setPartyId(partyId);
		List<TAppletShop> list = tAppletShopMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> getSummaryInfo(Map<String, Object> params) {
		return tAppletShopMapper.getSummaryInfo(params);
	}

	@Override
	public PageInfo<TAppletShop> getInvitedList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TAppletShop> list = tAppletShopMapper.getInvitedList(params);
		return new PageInfo<>(list);
	}
}
