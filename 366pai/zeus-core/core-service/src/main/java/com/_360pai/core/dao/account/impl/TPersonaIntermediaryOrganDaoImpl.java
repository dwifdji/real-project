
package com._360pai.core.dao.account.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TPersonaIntermediaryOrganCondition;
import com._360pai.core.dao.account.TPersonaIntermediaryOrganDao;
import com._360pai.core.dao.account.mapper.TPersonaIntermediaryOrganMapper;
import com._360pai.core.model.account.TPersonaIntermediaryOrgan;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TPersonaIntermediaryOrganDaoImpl extends AbstractDaoImpl<TPersonaIntermediaryOrgan, TPersonaIntermediaryOrganCondition, BaseMapper<TPersonaIntermediaryOrgan,TPersonaIntermediaryOrganCondition>> implements TPersonaIntermediaryOrganDao {
	
	@Resource
	private TPersonaIntermediaryOrganMapper tPersonaIntermediaryOrganMapper;
	
	@Override
	protected BaseMapper<TPersonaIntermediaryOrgan, TPersonaIntermediaryOrganCondition> daoSupport() {
		return tPersonaIntermediaryOrganMapper;
	}

	@Override
	protected TPersonaIntermediaryOrganCondition blankCondition() {
		return new TPersonaIntermediaryOrganCondition();
	}

	@Override
	public TPersonaIntermediaryOrgan getByPersonaId(Integer personaId) {
		TPersonaIntermediaryOrganCondition condition = new TPersonaIntermediaryOrganCondition();
		condition.setPersonaId(personaId);
		List<TPersonaIntermediaryOrgan> list = tPersonaIntermediaryOrganMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
