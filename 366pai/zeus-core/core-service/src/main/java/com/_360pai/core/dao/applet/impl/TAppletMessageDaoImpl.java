
package com._360pai.core.dao.applet.impl;

import javax.annotation.Resource;

import com._360pai.core.facade.shop.vo.AppletMessageVO;
import com._360pai.core.facade.shop.vo.ShopMessageTypeVO;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.applet.TAppletMessageCondition;
import com._360pai.core.dao.applet.mapper.TAppletMessageMapper;
import com._360pai.core.model.applet.TAppletMessage;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.applet.TAppletMessageDao;

import java.util.List;
import java.util.Map;

@Service
public class TAppletMessageDaoImpl extends AbstractDaoImpl<TAppletMessage, TAppletMessageCondition, BaseMapper<TAppletMessage,TAppletMessageCondition>> implements TAppletMessageDao{
	
	@Resource
	private TAppletMessageMapper tAppletMessageMapper;
	
	@Override
	protected BaseMapper<TAppletMessage, TAppletMessageCondition> daoSupport() {
		return tAppletMessageMapper;
	}

	@Override
	protected TAppletMessageCondition blankCondition() {
		return new TAppletMessageCondition();
	}

	@Override
	public List<AppletMessageVO> getShopMessageList(Map<String, Object> params) {
		return tAppletMessageMapper.getShopMessageList(params);
	}

	@Override
	public List<ShopMessageTypeVO> getMyShopMessage(Map<String, Object> params) {

		return tAppletMessageMapper.getMyShopMessage(params);
	}

	@Override
	public List<AppletMessageVO> getAnnoucementList(Map<String, Object> params) {
		return tAppletMessageMapper.getAnnoucementList(params);
	}

	@Override
	public String getRecentMessageAt(Map<String, Object> params) {
		return tAppletMessageMapper.getRecentMessageAt(params);
	}

	@Override
	public String getRecentAnnouncementAt(Map<String, Object> params) {
		return tAppletMessageMapper.getRecentAnnouncementAt(params);
	}
}
