
package com._360pai.core.dao.numberJump.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.numberJump.TBankBenchmarkInterestRateCondition;
import com._360pai.core.dao.numberJump.mapper.TBankBenchmarkInterestRateMapper;
import com._360pai.core.model.numberJump.TBankBenchmarkInterestRate;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.numberJump.TBankBenchmarkInterestRateDao;

import java.util.Date;
import java.util.List;

@Service
public class TBankBenchmarkInterestRateDaoImpl extends AbstractDaoImpl<TBankBenchmarkInterestRate, TBankBenchmarkInterestRateCondition, BaseMapper<TBankBenchmarkInterestRate,TBankBenchmarkInterestRateCondition>> implements TBankBenchmarkInterestRateDao{
	
	@Resource
	private TBankBenchmarkInterestRateMapper tBankBenchmarkInterestRateMapper;
	
	@Override
	protected BaseMapper<TBankBenchmarkInterestRate, TBankBenchmarkInterestRateCondition> daoSupport() {
		return tBankBenchmarkInterestRateMapper;
	}

	@Override
	protected TBankBenchmarkInterestRateCondition blankCondition() {
		return new TBankBenchmarkInterestRateCondition();
	}

	@Override
	public List<TBankBenchmarkInterestRate> findAll() {
		TBankBenchmarkInterestRateCondition condition = new TBankBenchmarkInterestRateCondition();
		condition.setIsDelete(false);
		return tBankBenchmarkInterestRateMapper.selectByCondition(condition);
	}

	@Override
	public List<TBankBenchmarkInterestRate> getByDateDuration(Date startDate, Date endDate) {
		return tBankBenchmarkInterestRateMapper.getByDateDuration(startDate,endDate);
	}

	@Override
	public TBankBenchmarkInterestRate getByDate(Date date) {
		return tBankBenchmarkInterestRateMapper.getByDate(date);
	}
}
