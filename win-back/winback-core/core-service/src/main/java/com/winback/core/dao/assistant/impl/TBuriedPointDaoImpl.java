
package com.winback.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition.assistant.TBuriedPointCondition;
import com.winback.core.dao.assistant.mapper.TBuriedPointMapper;
import com.winback.core.model.assistant.TBuriedPoint;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.assistant.TBuriedPointDao;

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
