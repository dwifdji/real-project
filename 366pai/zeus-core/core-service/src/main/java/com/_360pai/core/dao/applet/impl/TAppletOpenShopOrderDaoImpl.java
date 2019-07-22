
package com._360pai.core.dao.applet.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.applet.TAppletOpenShopOrderCondition;
import com._360pai.core.dao.applet.TAppletOpenShopOrderDao;
import com._360pai.core.dao.applet.mapper.TAppletOpenShopOrderMapper;
import com._360pai.core.model.applet.TAppletOpenShopOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TAppletOpenShopOrderDaoImpl extends AbstractDaoImpl<TAppletOpenShopOrder, TAppletOpenShopOrderCondition, BaseMapper<TAppletOpenShopOrder,TAppletOpenShopOrderCondition>> implements TAppletOpenShopOrderDao{
	
	@Resource
	private TAppletOpenShopOrderMapper tAppletOpenShopOrderMapper;
	
	@Override
	protected BaseMapper<TAppletOpenShopOrder, TAppletOpenShopOrderCondition> daoSupport() {
		return tAppletOpenShopOrderMapper;
	}

	@Override
	protected TAppletOpenShopOrderCondition blankCondition() {
		return new TAppletOpenShopOrderCondition();
	}


	@Override
	public List<TAppletOpenShopOrder> getAppletNotPayOrderList() {
		return tAppletOpenShopOrderMapper.getAppletNotPayOrderList();
	}
}
