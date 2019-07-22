
package com._360pai.core.dao.numberJump.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.numberJump.TPrincipalInterestCalculatorCondition;
import com._360pai.core.dao.numberJump.TPrincipalInterestCalculatorDao;
import com._360pai.core.dao.numberJump.mapper.TPrincipalInterestCalculatorMapper;
import com._360pai.core.facade.applet.vo.CalculatorBroadcast;
import com._360pai.core.facade.applet.vo.CalculatorHistory;
import com._360pai.core.model.numberJump.TPrincipalInterestCalculator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TPrincipalInterestCalculatorDaoImpl extends AbstractDaoImpl<TPrincipalInterestCalculator, TPrincipalInterestCalculatorCondition, BaseMapper<TPrincipalInterestCalculator,TPrincipalInterestCalculatorCondition>> implements TPrincipalInterestCalculatorDao{
	
	@Resource
	private TPrincipalInterestCalculatorMapper tPrincipalInterestCalculatorMapper;
	
	@Override
	protected BaseMapper<TPrincipalInterestCalculator, TPrincipalInterestCalculatorCondition> daoSupport() {
		return tPrincipalInterestCalculatorMapper;
	}

	@Override
	protected TPrincipalInterestCalculatorCondition blankCondition() {
		return new TPrincipalInterestCalculatorCondition();
	}

	@Override
	public PageInfo<TPrincipalInterestCalculator> getNeedToBroadcastList(int page, int perPage, Map<String, Object> params) {
		PageHelper.startPage(page, perPage);
		List<TPrincipalInterestCalculator> list = tPrincipalInterestCalculatorMapper.getNeedToBroadcastList(params);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<CalculatorHistory> getHistoryListV2(int page, int perPage, Map<String, Object> params) {
		PageHelper.startPage(page, perPage);
		List<CalculatorHistory> list = tPrincipalInterestCalculatorMapper.getHistoryList(params);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<CalculatorBroadcast> getMyBroadcastList(int page, int perPage, Map<String, Object> stringObjectMap) {
		PageHelper.startPage(page, perPage);
		List<CalculatorBroadcast> list = tPrincipalInterestCalculatorMapper.getMyBroadcastList(stringObjectMap);
		return new PageInfo<>(list);
	}
}
