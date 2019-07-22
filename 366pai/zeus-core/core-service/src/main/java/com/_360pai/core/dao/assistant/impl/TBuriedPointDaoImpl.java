
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.TBuriedPointCondition;
import com._360pai.core.dao.assistant.mapper.TBuriedPointMapper;
import com._360pai.core.model.assistant.TBuriedPoint;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.TBuriedPointDao;

@Service
public class TBuriedPointDaoImpl extends AbstractDaoImpl<TBuriedPoint, TBuriedPointCondition, BaseMapper<TBuriedPoint,TBuriedPointCondition>> implements TBuriedPointDao{
	
	@Resource
	private TBuriedPointMapper tBuriedPointMapper;
	
	@Override
	protected BaseMapper<TBuriedPoint, TBuriedPointCondition> daoSupport() {
		return tBuriedPointMapper;
	}

	@Override
	protected TBuriedPointCondition blankCondition() {
		return new TBuriedPointCondition();
	}

}
