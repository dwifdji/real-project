
package com.winback.core.dao.assistant.impl;

import javax.annotation.Resource;

import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.exception.BusinessException;
import org.springframework.stereotype.Service;

import com.winback.core.condition.assistant.TAppReadMessageMapCondition;
import com.winback.core.dao.assistant.mapper.TAppReadMessageMapMapper;
import com.winback.core.model.assistant.TAppReadMessageMap;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.assistant.TAppReadMessageMapDao;

@Service
public class TAppReadMessageMapDaoImpl extends AbstractDaoImpl<TAppReadMessageMap, TAppReadMessageMapCondition, BaseMapper<TAppReadMessageMap,TAppReadMessageMapCondition>> implements TAppReadMessageMapDao{
	
	@Resource
	private TAppReadMessageMapMapper tAppReadMessageMapMapper;
	
	@Override
	protected BaseMapper<TAppReadMessageMap, TAppReadMessageMapCondition> daoSupport() {
		return tAppReadMessageMapMapper;
	}

	@Override
	protected TAppReadMessageMapCondition blankCondition() {
		return new TAppReadMessageMapCondition();
	}

	@Override
	public void readMessage(Integer accountId, Integer messageId) {
		TAppReadMessageMap messageMap = new TAppReadMessageMap();
		messageMap.setAccountId(accountId);
		messageMap.setMessageId(messageId);
		int result = tAppReadMessageMapMapper.insert(messageMap);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
	}
}
