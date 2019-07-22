
package com._360pai.core.dao.numberJump.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.numberJump.TDebtCalculatorCondition;
import com._360pai.core.dao.numberJump.TDebtCalculatorDao;
import com._360pai.core.dao.numberJump.mapper.TDebtCalculatorMapper;
import com._360pai.core.facade.applet.vo.CalculatorBroadcast;
import com._360pai.core.facade.applet.vo.CalculatorHistory;
import com._360pai.core.model.numberJump.TDebtCalculator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TDebtCalculatorDaoImpl extends AbstractDaoImpl<TDebtCalculator, TDebtCalculatorCondition, BaseMapper<TDebtCalculator,TDebtCalculatorCondition>> implements TDebtCalculatorDao{
	
	@Resource
	private TDebtCalculatorMapper tDebtCalculatorMapper;
	
	@Override
	protected BaseMapper<TDebtCalculator, TDebtCalculatorCondition> daoSupport() {
		return tDebtCalculatorMapper;
	}

	@Override
	protected TDebtCalculatorCondition blankCondition() {
		return new TDebtCalculatorCondition();
	}

	@Override
	public PageInfo<CalculatorHistory> getHistoryListV2(int page, int perPage, Map<String, Object> params) {
		PageHelper.startPage(page, perPage);
		List<CalculatorHistory> list = tDebtCalculatorMapper.getHistoryListV2(params);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<CalculatorHistory> getHistoryList(int page, int perPage, Map<String, Object> params) {
		PageHelper.startPage(page, perPage);
		List<CalculatorHistory> list = tDebtCalculatorMapper.getHistoryList(params);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<TDebtCalculator> getNeedToBroadcastList(int page, int perPage, Map<String, Object> params) {
		PageHelper.startPage(page, perPage);
		List<TDebtCalculator> list = tDebtCalculatorMapper.getNeedToBroadcastList(params);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<CalculatorBroadcast> getMyBroadcastListV2(int page, int perPage, Map<String, Object> params) {
		PageHelper.startPage(page, perPage);
		List<CalculatorBroadcast> list = tDebtCalculatorMapper.getMyBroadcastListV2(params);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<CalculatorBroadcast> getMyBroadcastList(int page, int perPage, Map<String, Object> params) {
		PageHelper.startPage(page, perPage);
		List<CalculatorBroadcast> list = tDebtCalculatorMapper.getMyBroadcastList(params);
		return new PageInfo<>(list);
	}

	@Override
	public int getUnreadBroadcastCount(Integer extBindId) {
		return tDebtCalculatorMapper.getUnreadBroadcastCount(extBindId);
	}
}
