
package com.tzCloud.core.dao.legalEngine.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.legalEngine.TLawtimeCourtCondition;
import com.tzCloud.core.dao.legalEngine.mapper.TLawtimeCourtMapper;
import com.tzCloud.core.model.legalEngine.TLawtimeCourt;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.legalEngine.TLawtimeCourtDao;

@Service
public class TLawtimeCourtDaoImpl extends AbstractDaoImpl<TLawtimeCourt, TLawtimeCourtCondition, BaseMapper<TLawtimeCourt,TLawtimeCourtCondition>> implements TLawtimeCourtDao{
	
	@Resource
	private TLawtimeCourtMapper tLawtimeCourtMapper;
	
	@Override
	protected BaseMapper<TLawtimeCourt, TLawtimeCourtCondition> daoSupport() {
		return tLawtimeCourtMapper;
	}

	@Override
	protected TLawtimeCourtCondition blankCondition() {
		return new TLawtimeCourtCondition();
	}

}
