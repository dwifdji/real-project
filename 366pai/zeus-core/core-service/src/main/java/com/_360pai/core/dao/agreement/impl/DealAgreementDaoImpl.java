
package com._360pai.core.dao.agreement.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.agreement.DealAgreementCondition;
import com._360pai.core.dao.agreement.DealAgreementDao;
import com._360pai.core.dao.agreement.mapper.DealAgreementMapper;
import com._360pai.core.model.agreement.DealAgreement;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DealAgreementDaoImpl extends AbstractDaoImpl<DealAgreement, DealAgreementCondition, BaseMapper<DealAgreement,DealAgreementCondition>> implements DealAgreementDao{
	
	@Resource
	private DealAgreementMapper dealAgreementMapper;
	
	@Override
	protected BaseMapper<DealAgreement, DealAgreementCondition> daoSupport() {
		return dealAgreementMapper;
	}

	@Override
	protected DealAgreementCondition blankCondition() {
		return new DealAgreementCondition();
	}

	@Override
	public DealAgreement getByOrderId(Long orderId,String contractType) {
		DealAgreementCondition condition = new DealAgreementCondition();
		condition.setOrderId(orderId);
		condition.setContractType(contractType);
		List<DealAgreement> list = dealAgreementMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
