
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import com._360pai.core.condition.assistant.TJobActivityTotalViewCountCondition;
import org.springframework.stereotype.Service;

import com._360pai.core.dao.assistant.mapper.TJobActivityTotalViewCountMapper;
import com._360pai.core.model.assistant.TJobActivityTotalViewCount;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.TJobActivityTotalViewCountDao;

@Service
public class TJobActivityTotalViewCountDaoImpl extends AbstractDaoImpl<TJobActivityTotalViewCount, TJobActivityTotalViewCountCondition, BaseMapper<TJobActivityTotalViewCount,TJobActivityTotalViewCountCondition>> implements TJobActivityTotalViewCountDao{
	
	@Resource
	private TJobActivityTotalViewCountMapper tJobActivityTotalViewCountMapper;
	
	@Override
	protected BaseMapper<TJobActivityTotalViewCount, TJobActivityTotalViewCountCondition> daoSupport() {
		return tJobActivityTotalViewCountMapper;
	}

	@Override
	protected TJobActivityTotalViewCountCondition blankCondition() {
		return new TJobActivityTotalViewCountCondition();
	}

}
