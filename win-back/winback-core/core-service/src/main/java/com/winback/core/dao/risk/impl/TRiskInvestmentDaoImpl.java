
package com.winback.core.dao.risk.impl;

import javax.annotation.Resource;

import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import org.springframework.stereotype.Service;

import com.winback.core.condition.risk.TRiskInvestmentCondition;
import com.winback.core.dao.risk.mapper.TRiskInvestmentMapper;
import com.winback.core.model.risk.TRiskInvestment;
import com.winback.core.dao.risk.TRiskInvestmentDao;

import java.util.List;

@Service
public class TRiskInvestmentDaoImpl extends AbstractDaoImpl<TRiskInvestment, TRiskInvestmentCondition, BaseMapper<TRiskInvestment,TRiskInvestmentCondition>> implements TRiskInvestmentDao{
	
	@Resource
	private TRiskInvestmentMapper tRiskInvestmentMapper;
	
	@Override
	protected BaseMapper<TRiskInvestment, TRiskInvestmentCondition> daoSupport() {
		return tRiskInvestmentMapper;
	}

	@Override
	protected TRiskInvestmentCondition blankCondition() {
		return new TRiskInvestmentCondition();
	}

	@Override
	public void batchSaveRiskInvestment(List<TRiskInvestment> batchList) {
		tRiskInvestmentMapper.batchSaveRiskInvestment(batchList);
	}
}
