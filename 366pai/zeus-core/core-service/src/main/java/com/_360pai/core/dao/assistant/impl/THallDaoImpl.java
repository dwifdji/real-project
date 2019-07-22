
package com._360pai.core.dao.assistant.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.assistant.THallCondition;
import com._360pai.core.dao.assistant.THallDao;
import com._360pai.core.dao.assistant.mapper.THallMapper;
import com._360pai.core.model.assistant.THall;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class THallDaoImpl extends AbstractDaoImpl<THall, THallCondition, BaseMapper<THall,THallCondition>> implements THallDao {
	
	@Resource
	private THallMapper tHallMapper;
	
	@Override
	protected BaseMapper<THall, THallCondition> daoSupport() {
		return tHallMapper;
	}

	@Override
	protected THallCondition blankCondition() {
		return new THallCondition();
	}

}
