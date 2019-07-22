
package com.winback.core.dao.risk.impl;

import javax.annotation.Resource;

import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import org.springframework.stereotype.Service;

import com.winback.core.condition.risk.TRiskComInfoCondition;
import com.winback.core.dao.risk.mapper.TRiskComInfoMapper;
import com.winback.core.model.risk.TRiskComInfo;

import com.winback.core.dao.risk.TRiskComInfoDao;

@Service
public class TRiskComInfoDaoImpl extends AbstractDaoImpl<TRiskComInfo, TRiskComInfoCondition, BaseMapper<TRiskComInfo,TRiskComInfoCondition>> implements TRiskComInfoDao{
	
	@Resource
	private TRiskComInfoMapper tRiskComInfoMapper;
	
	@Override
	protected BaseMapper<TRiskComInfo, TRiskComInfoCondition> daoSupport() {
		return tRiskComInfoMapper;
	}

	@Override
	protected TRiskComInfoCondition blankCondition() {
		return new TRiskComInfoCondition();
	}

}
