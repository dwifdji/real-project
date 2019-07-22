
package com.winback.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition.assistant.TCourtCondition;
import com.winback.core.dao.assistant.mapper.TCourtMapper;
import com.winback.core.model.assistant.TCourt;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.assistant.TCourtDao;

@Service
public class TCourtDaoImpl extends AbstractDaoImpl<TCourt, TCourtCondition, BaseMapper<TCourt,TCourtCondition>> implements TCourtDao{
	
	@Resource
	private TCourtMapper tCourtMapper;
	
	@Override
	protected BaseMapper<TCourt, TCourtCondition> daoSupport() {
		return tCourtMapper;
	}

	@Override
	protected TCourtCondition blankCondition() {
		return new TCourtCondition();
	}

}
