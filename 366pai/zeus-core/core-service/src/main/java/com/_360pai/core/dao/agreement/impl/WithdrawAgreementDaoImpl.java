
package com._360pai.core.dao.agreement.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.agreement.WithdrawAgreementCondition;
import com._360pai.core.dao.agreement.mapper.WithdrawAgreementMapper;
import com._360pai.core.model.agreement.WithdrawAgreement;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.agreement.WithdrawAgreementDao;

import java.util.List;

@Service
public class WithdrawAgreementDaoImpl extends AbstractDaoImpl<WithdrawAgreement, WithdrawAgreementCondition, BaseMapper<WithdrawAgreement,WithdrawAgreementCondition>> implements WithdrawAgreementDao{
	
	@Resource
	private WithdrawAgreementMapper withdrawAgreementMapper;
	
	@Override
	protected BaseMapper<WithdrawAgreement, WithdrawAgreementCondition> daoSupport() {
		return withdrawAgreementMapper;
	}

	@Override
	protected WithdrawAgreementCondition blankCondition() {
		return new WithdrawAgreementCondition();
	}

	@Override
	public WithdrawAgreement getByAcctDetailId(Long acctDetailId) {
		WithdrawAgreementCondition condition = new WithdrawAgreementCondition();
		condition.setAcctDetailId(acctDetailId);
		List<WithdrawAgreement> list = withdrawAgreementMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
