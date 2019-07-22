
package com.winback.core.dao.risk.impl;

import javax.annotation.Resource;

import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import org.springframework.stereotype.Service;

import com.winback.core.condition.risk.TRiskStockInfoCondition;
import com.winback.core.dao.risk.mapper.TRiskStockInfoMapper;
import com.winback.core.model.risk.TRiskStockInfo;
import com.winback.core.dao.risk.TRiskStockInfoDao;

@Service
public class TRiskStockInfoDaoImpl extends AbstractDaoImpl<TRiskStockInfo, TRiskStockInfoCondition, BaseMapper<TRiskStockInfo,TRiskStockInfoCondition>> implements TRiskStockInfoDao{
	
	@Resource
	private TRiskStockInfoMapper tRiskStockInfoMapper;
	
	@Override
	protected BaseMapper<TRiskStockInfo, TRiskStockInfoCondition> daoSupport() {
		return tRiskStockInfoMapper;
	}

	@Override
	protected TRiskStockInfoCondition blankCondition() {
		return new TRiskStockInfoCondition();
	}

}
