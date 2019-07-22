
package com._360pai.core.dao.account.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TPersonaFundingAgencyCondition;
import com._360pai.core.dao.account.TPersonaFundingAgencyDao;
import com._360pai.core.dao.account.mapper.TPersonaFundingAgencyMapper;
import com._360pai.core.model.account.TPersonaFundingAgency;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TPersonaFundingAgencyDaoImpl extends AbstractDaoImpl<TPersonaFundingAgency, TPersonaFundingAgencyCondition, BaseMapper<TPersonaFundingAgency,TPersonaFundingAgencyCondition>> implements TPersonaFundingAgencyDao {
	
	@Resource
	private TPersonaFundingAgencyMapper tPersonaFundingAgencyMapper;
	
	@Override
	protected BaseMapper<TPersonaFundingAgency, TPersonaFundingAgencyCondition> daoSupport() {
		return tPersonaFundingAgencyMapper;
	}

	@Override
	protected TPersonaFundingAgencyCondition blankCondition() {
		return new TPersonaFundingAgencyCondition();
	}

	@Override
	public TPersonaFundingAgency getByPersonaId(Integer personaId) {
		TPersonaFundingAgencyCondition condition = new TPersonaFundingAgencyCondition();
		condition.setPersonaId(personaId);
		List<TPersonaFundingAgency> list = tPersonaFundingAgencyMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
