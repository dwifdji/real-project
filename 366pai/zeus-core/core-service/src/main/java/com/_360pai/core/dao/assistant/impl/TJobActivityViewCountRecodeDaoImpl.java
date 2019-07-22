
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import com._360pai.core.condition.assistant.TJobActivityViewCountRecodeCondition;
import org.springframework.stereotype.Service;

import com._360pai.core.dao.assistant.mapper.TJobActivityViewCountRecodeMapper;
import com._360pai.core.model.assistant.TJobActivityViewCountRecode;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.TJobActivityViewCountRecodeDao;

@Service
public class TJobActivityViewCountRecodeDaoImpl extends AbstractDaoImpl<TJobActivityViewCountRecode, TJobActivityViewCountRecodeCondition, BaseMapper<TJobActivityViewCountRecode,TJobActivityViewCountRecodeCondition>> implements TJobActivityViewCountRecodeDao{
	
	@Resource
	private TJobActivityViewCountRecodeMapper tJobActivityViewCountRecodeMapper;
	
	@Override
	protected BaseMapper<TJobActivityViewCountRecode, TJobActivityViewCountRecodeCondition> daoSupport() {
		return tJobActivityViewCountRecodeMapper;
	}

	@Override
	protected TJobActivityViewCountRecodeCondition blankCondition() {
		return new TJobActivityViewCountRecodeCondition();
	}

	@Override
	public int getTotalActivityViewCount(Integer activityType, Integer activityId) {
		return tJobActivityViewCountRecodeMapper.getTotalActivityViewCount(activityType,activityId);
	}
}
