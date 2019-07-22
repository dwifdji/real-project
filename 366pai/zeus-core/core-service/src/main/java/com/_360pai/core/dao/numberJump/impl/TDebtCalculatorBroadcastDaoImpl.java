
package com._360pai.core.dao.numberJump.impl;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.numberJump.TDebtCalculatorBroadcastCondition;
import com._360pai.core.dao.numberJump.mapper.TDebtCalculatorBroadcastMapper;
import com._360pai.core.model.numberJump.TDebtCalculatorBroadcast;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.numberJump.TDebtCalculatorBroadcastDao;

import java.util.Date;
import java.util.List;

@Service
public class TDebtCalculatorBroadcastDaoImpl extends AbstractDaoImpl<TDebtCalculatorBroadcast, TDebtCalculatorBroadcastCondition, BaseMapper<TDebtCalculatorBroadcast,TDebtCalculatorBroadcastCondition>> implements TDebtCalculatorBroadcastDao{
	
	@Resource
	private TDebtCalculatorBroadcastMapper tDebtCalculatorBroadcastMapper;
	
	@Override
	protected BaseMapper<TDebtCalculatorBroadcast, TDebtCalculatorBroadcastCondition> daoSupport() {
		return tDebtCalculatorBroadcastMapper;
	}

	@Override
	protected TDebtCalculatorBroadcastCondition blankCondition() {
		return new TDebtCalculatorBroadcastCondition();
	}

	@Override
	public TDebtCalculatorBroadcast findLatestByCalculatorId(Integer calculatorId) {
		return tDebtCalculatorBroadcastMapper.findLatestByCalculatorId(calculatorId);
	}

	@Override
	public PageInfo<TDebtCalculatorBroadcast> findByCalculatorId(int page, int perPage, Integer calculatorId) {
		PageHelper.startPage(page, perPage);
		List<TDebtCalculatorBroadcast> list = tDebtCalculatorBroadcastMapper.findByCalculatorId(calculatorId);
		return new PageInfo<>(list);
	}

    @Override
    public TDebtCalculatorBroadcast findLatestByCalculatorIdAndDate(Integer calculatorId, Date date) {
        return tDebtCalculatorBroadcastMapper.findLatestByCalculatorIdAndDate(calculatorId,date);
    }

    @Override
    public Integer getUnreadBroadcastCount(Integer extBindId) {
		return tDebtCalculatorBroadcastMapper.getUnreadBroadcastCount(extBindId);

    }
}
