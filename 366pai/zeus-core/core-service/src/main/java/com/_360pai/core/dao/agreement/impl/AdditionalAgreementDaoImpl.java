
package com._360pai.core.dao.agreement.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.agreement.AdditionalAgreementCondition;
import com._360pai.core.dao.agreement.mapper.AdditionalAgreementMapper;
import com._360pai.core.model.agreement.AdditionalAgreement;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.agreement.AdditionalAgreementDao;

import java.util.List;

@Service
public class AdditionalAgreementDaoImpl extends AbstractDaoImpl<AdditionalAgreement, AdditionalAgreementCondition, BaseMapper<AdditionalAgreement,AdditionalAgreementCondition>> implements AdditionalAgreementDao{
	
	@Resource
	private AdditionalAgreementMapper additionalAgreementMapper;
	
	@Override
	protected BaseMapper<AdditionalAgreement, AdditionalAgreementCondition> daoSupport() {
		return additionalAgreementMapper;
	}

	@Override
	protected AdditionalAgreementCondition blankCondition() {
		return new AdditionalAgreementCondition();
	}

	@Override
	public AdditionalAgreement getByActivityId(Integer activityId) {
		AdditionalAgreementCondition condition = new AdditionalAgreementCondition();
		condition.setActivityId(activityId);
		List<AdditionalAgreement> list = additionalAgreementMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public AdditionalAgreement getByContractId(String contractId) {
		AdditionalAgreementCondition condition = new AdditionalAgreementCondition();
		condition.setContractId(contractId);
		List<AdditionalAgreement> list = additionalAgreementMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
