
package com._360pai.core.dao.applet.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.applet.TAppletViewShopCondition;
import com._360pai.core.dao.applet.TAppletViewShopDao;
import com._360pai.core.dao.applet.mapper.TAppletViewShopMapper;
import com._360pai.core.facade.applet.vo.AppletVisitVo;
import com._360pai.core.model.applet.TAppletViewShop;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TAppletViewShopDaoImpl extends AbstractDaoImpl<TAppletViewShop, TAppletViewShopCondition, BaseMapper<TAppletViewShop,TAppletViewShopCondition>> implements TAppletViewShopDao{
	
	@Resource
	private TAppletViewShopMapper tAppletViewShopMapper;
	
	@Override
	protected BaseMapper<TAppletViewShop, TAppletViewShopCondition> daoSupport() {
		return tAppletViewShopMapper;
	}

	@Override
	protected TAppletViewShopCondition blankCondition() {
		return new TAppletViewShopCondition();
	}


	@Override
	public List<AppletVisitVo> getAppletVisitList(TAppletViewShopCondition req) {
		return tAppletViewShopMapper.getAppletVisitList(req);
	}
}
