
package com._360pai.core.dao.applet.impl;

import javax.annotation.Resource;

import com._360pai.core.facade.shop.vo.ShopGuideVO;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.applet.TAppletLeadCondition;
import com._360pai.core.dao.applet.mapper.TAppletLeadMapper;
import com._360pai.core.model.applet.TAppletLead;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.applet.TAppletLeadDao;

import java.util.List;

@Service
public class TAppletLeadDaoImpl extends AbstractDaoImpl<TAppletLead, TAppletLeadCondition, BaseMapper<TAppletLead,TAppletLeadCondition>> implements TAppletLeadDao{
	
	@Resource
	private TAppletLeadMapper tAppletLeadMapper;
	
	@Override
	protected BaseMapper<TAppletLead, TAppletLeadCondition> daoSupport() {
		return tAppletLeadMapper;
	}

	@Override
	protected TAppletLeadCondition blankCondition() {
		return new TAppletLeadCondition();
	}

	@Override
	public List<ShopGuideVO> getRemainingGuides(String openId) {
		return tAppletLeadMapper.getRemainingGuides(openId);
	}
}
