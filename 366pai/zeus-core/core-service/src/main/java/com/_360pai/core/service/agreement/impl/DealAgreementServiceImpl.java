package com._360pai.core.service.agreement.impl;

import com._360pai.core.condition.agreement.DealAgreementCondition;
import com._360pai.core.dao.agreement.DealAgreementDao;
import com._360pai.core.model.agreement.DealAgreement;
import com._360pai.core.service.agreement.DealAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DealAgreementServiceImpl implements DealAgreementService{

	@Autowired
	private DealAgreementDao dealAgreementDao;


	@Override
	public DealAgreement getByOrderId(Long orderId,String contractType) {
		DealAgreementCondition condition = new DealAgreementCondition();
		condition.setOrderId(orderId);
		condition.setContractType(contractType);
		return dealAgreementDao.selectFirst(condition);
	}

	@Override
	public DealAgreement getByContractId(String contractId) {
		DealAgreementCondition condition = new DealAgreementCondition();
		condition.setContractId(contractId);
		return dealAgreementDao.selectFirst(condition);
	}

	@Override
	public boolean saveDealAgreement(DealAgreement dealAgreement) {
		return dealAgreementDao.insert(dealAgreement) == 1;
	}

	@Override
	public boolean updateDealAgreement(DealAgreement dealAgreement) {
		return dealAgreementDao.updateById(dealAgreement) == 1;
	}
}