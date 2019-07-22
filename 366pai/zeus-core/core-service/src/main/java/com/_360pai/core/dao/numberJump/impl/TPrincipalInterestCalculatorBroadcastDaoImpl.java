
package com._360pai.core.dao.numberJump.impl;

import javax.annotation.Resource;

import com._360pai.core.model.numberJump.TDebtCalculatorBroadcast;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.numberJump.TPrincipalInterestCalculatorBroadcastCondition;
import com._360pai.core.dao.numberJump.mapper.TPrincipalInterestCalculatorBroadcastMapper;
import com._360pai.core.model.numberJump.TPrincipalInterestCalculatorBroadcast;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.numberJump.TPrincipalInterestCalculatorBroadcastDao;

import java.util.List;

@Service
public class TPrincipalInterestCalculatorBroadcastDaoImpl extends AbstractDaoImpl<TPrincipalInterestCalculatorBroadcast, TPrincipalInterestCalculatorBroadcastCondition, BaseMapper<TPrincipalInterestCalculatorBroadcast,TPrincipalInterestCalculatorBroadcastCondition>> implements TPrincipalInterestCalculatorBroadcastDao{
	
	@Resource
	private TPrincipalInterestCalculatorBroadcastMapper tPrincipalInterestCalculatorBroadcastMapper;
	
	@Override
	protected BaseMapper<TPrincipalInterestCalculatorBroadcast, TPrincipalInterestCalculatorBroadcastCondition> daoSupport() {
		return tPrincipalInterestCalculatorBroadcastMapper;
	}

	@Override
	protected TPrincipalInterestCalculatorBroadcastCondition blankCondition() {
		return new TPrincipalInterestCalculatorBroadcastCondition();
	}

	@Override
	public TPrincipalInterestCalculatorBroadcast findLatestByCalculatorId(Integer calculatorId) {
		return tPrincipalInterestCalculatorBroadcastMapper.findLatestByCalculatorId(calculatorId);
	}

	@Override
	public PageInfo<TPrincipalInterestCalculatorBroadcast> findByCalculatorId(int page, int perPage, Integer calculatorId) {
		PageHelper.startPage(page, perPage);
		List<TPrincipalInterestCalculatorBroadcast> list = tPrincipalInterestCalculatorBroadcastMapper.findByCalculatorId(calculatorId);
		return new PageInfo<>(list);
	}

    @Override
    public Integer getUnreadBroadcastCount(Integer extBindId) {

		return tPrincipalInterestCalculatorBroadcastMapper.getUnreadBroadcastCount(extBindId);
    }

}
