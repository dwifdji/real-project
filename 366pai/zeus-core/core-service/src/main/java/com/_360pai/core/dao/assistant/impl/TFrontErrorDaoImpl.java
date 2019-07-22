
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.TFrontErrorCondition;
import com._360pai.core.dao.assistant.mapper.TFrontErrorMapper;
import com._360pai.core.model.assistant.TFrontError;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.TFrontErrorDao;

@Service
public class TFrontErrorDaoImpl extends AbstractDaoImpl<TFrontError, TFrontErrorCondition, BaseMapper<TFrontError,TFrontErrorCondition>> implements TFrontErrorDao{
	
	@Resource
	private TFrontErrorMapper tFrontErrorMapper;
	
	@Override
	protected BaseMapper<TFrontError, TFrontErrorCondition> daoSupport() {
		return tFrontErrorMapper;
	}

	@Override
	protected TFrontErrorCondition blankCondition() {
		return new TFrontErrorCondition();
	}

}
