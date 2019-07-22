
package com._360pai.core.dao.agreement.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.agreement.DelegationAgreementCondition;
import com._360pai.core.dao.agreement.mapper.DelegationAgreementMapper;
import com._360pai.core.model.agreement.DelegationAgreement;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.agreement.DelegationAgreementDao;

import java.util.List;

@Service
public class DelegationAgreementDaoImpl extends AbstractDaoImpl<DelegationAgreement, DelegationAgreementCondition, BaseMapper<DelegationAgreement,DelegationAgreementCondition>> implements DelegationAgreementDao{
	
	@Resource
	private DelegationAgreementMapper delegationAgreementMapper;
	
	@Override
	protected BaseMapper<DelegationAgreement, DelegationAgreementCondition> daoSupport() {
		return delegationAgreementMapper;
	}

	@Override
	protected DelegationAgreementCondition blankCondition() {
		return new DelegationAgreementCondition();
	}

	@Override
	public DelegationAgreement getByActivityId(Integer activityId) {
		DelegationAgreementCondition condition = new DelegationAgreementCondition();
		condition.setActivityId(activityId);
		List<DelegationAgreement> list = delegationAgreementMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public DelegationAgreement getByContractId(String contractId) {
		DelegationAgreementCondition condition = new DelegationAgreementCondition();
		condition.setContractId(contractId);
		List<DelegationAgreement> list = delegationAgreementMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Integer> getAllSignedTimeout() {
		return delegationAgreementMapper.getAllSignedTimeout();
	}
}
