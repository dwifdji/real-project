
package com._360pai.core.dao.applet.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.applet.TAppletReadMessageMapCondition;
import com._360pai.core.dao.applet.mapper.TAppletReadMessageMapMapper;
import com._360pai.core.model.applet.TAppletReadMessageMap;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.applet.TAppletReadMessageMapDao;

@Service
public class TAppletReadMessageMapDaoImpl extends AbstractDaoImpl<TAppletReadMessageMap, TAppletReadMessageMapCondition, BaseMapper<TAppletReadMessageMap,TAppletReadMessageMapCondition>> implements TAppletReadMessageMapDao{
	
	@Resource
	private TAppletReadMessageMapMapper tAppletReadMessageMapMapper;
	
	@Override
	protected BaseMapper<TAppletReadMessageMap, TAppletReadMessageMapCondition> daoSupport() {
		return tAppletReadMessageMapMapper;
	}

	@Override
	protected TAppletReadMessageMapCondition blankCondition() {
		return new TAppletReadMessageMapCondition();
	}

}
