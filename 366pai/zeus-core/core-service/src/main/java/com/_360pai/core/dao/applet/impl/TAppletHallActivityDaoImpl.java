
package com._360pai.core.dao.applet.impl;

import javax.annotation.Resource;

import com._360pai.core.facade.assistant.vo.BackAppletHallActivity;
import com._360pai.core.facade.shop.vo.AppletHallActivityVO;
import com._360pai.core.model.applet.TAppletShop;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.applet.TAppletHallActivityCondition;
import com._360pai.core.dao.applet.mapper.TAppletHallActivityMapper;
import com._360pai.core.model.applet.TAppletHallActivity;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.applet.TAppletHallActivityDao;

import java.util.List;
import java.util.Map;

@Service
public class TAppletHallActivityDaoImpl extends AbstractDaoImpl<TAppletHallActivity, TAppletHallActivityCondition, BaseMapper<TAppletHallActivity,TAppletHallActivityCondition>> implements TAppletHallActivityDao{
	
	@Resource
	private TAppletHallActivityMapper tAppletHallActivityMapper;
	
	@Override
	protected BaseMapper<TAppletHallActivity, TAppletHallActivityCondition> daoSupport() {
		return tAppletHallActivityMapper;
	}

	@Override
	protected TAppletHallActivityCondition blankCondition() {
		return new TAppletHallActivityCondition();
	}

	@Override
	public PageInfo<AppletHallActivityVO> getFrontList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<AppletHallActivityVO> list = tAppletHallActivityMapper.getFrontList(params);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<BackAppletHallActivity> getBackList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<BackAppletHallActivity> list = tAppletHallActivityMapper.getBackList(params);
		return new PageInfo<>(list);
	}

	@Override
	public TAppletHallActivity findBy(String type, Integer activityId) {
		TAppletHallActivityCondition condition = new TAppletHallActivityCondition();
		condition.setType(Integer.parseInt(type));
		condition.setActivityId(activityId);
		condition.setDeleteFlag(false);
		List<TAppletHallActivity> list = tAppletHallActivityMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
