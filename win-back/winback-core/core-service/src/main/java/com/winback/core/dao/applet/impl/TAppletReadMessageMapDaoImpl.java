
package com.winback.core.dao.applet.impl;

import javax.annotation.Resource;

import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.exception.BusinessException;
import org.springframework.stereotype.Service;

import com.winback.core.condition.applet.TAppletReadMessageMapCondition;
import com.winback.core.dao.applet.mapper.TAppletReadMessageMapMapper;
import com.winback.core.model.applet.TAppletReadMessageMap;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.applet.TAppletReadMessageMapDao;

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

	@Override
	public void readMessage(Integer extBindId, Integer messageId) {
		TAppletReadMessageMap messageMap = new TAppletReadMessageMap();
		messageMap.setExtBindId(extBindId);
		messageMap.setMessageId(messageId);
		int result = tAppletReadMessageMapMapper.insert(messageMap);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
	}
}
