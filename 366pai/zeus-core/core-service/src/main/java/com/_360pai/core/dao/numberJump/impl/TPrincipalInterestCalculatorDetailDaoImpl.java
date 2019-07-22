
package com._360pai.core.dao.numberJump.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.numberJump.TPrincipalInterestCalculatorDetailCondition;
import com._360pai.core.dao.numberJump.mapper.TPrincipalInterestCalculatorDetailMapper;
import com._360pai.core.model.numberJump.TPrincipalInterestCalculatorDetail;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.numberJump.TPrincipalInterestCalculatorDetailDao;

import java.util.List;

@Service
public class TPrincipalInterestCalculatorDetailDaoImpl extends AbstractDaoImpl<TPrincipalInterestCalculatorDetail, TPrincipalInterestCalculatorDetailCondition, BaseMapper<TPrincipalInterestCalculatorDetail,TPrincipalInterestCalculatorDetailCondition>> implements TPrincipalInterestCalculatorDetailDao{
	
	@Resource
	private TPrincipalInterestCalculatorDetailMapper tPrincipalInterestCalculatorDetailMapper;
	
	@Override
	protected BaseMapper<TPrincipalInterestCalculatorDetail, TPrincipalInterestCalculatorDetailCondition> daoSupport() {
		return tPrincipalInterestCalculatorDetailMapper;
	}

	@Override
	protected TPrincipalInterestCalculatorDetailCondition blankCondition() {
		return new TPrincipalInterestCalculatorDetailCondition();
	}

	@Override
	public List<TPrincipalInterestCalculatorDetail> findOverdueListByCalculatorId(Integer calculatorId) {
		TPrincipalInterestCalculatorDetailCondition condition = new TPrincipalInterestCalculatorDetailCondition();
		condition.setIsDelete(false);
		condition.setCalculatorId(calculatorId);
		condition.setType("0");
		return tPrincipalInterestCalculatorDetailMapper.selectByCondition(condition);
	}
}
