
package com._360pai.core.dao.account.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TPersonaAssetPartyCondition;
import com._360pai.core.dao.account.TPersonaAssetPartyDao;
import com._360pai.core.dao.account.mapper.TPersonaAssetPartyMapper;
import com._360pai.core.model.account.TPersonaAssetParty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TPersonaAssetPartyDaoImpl extends AbstractDaoImpl<TPersonaAssetParty, TPersonaAssetPartyCondition, BaseMapper<TPersonaAssetParty,TPersonaAssetPartyCondition>> implements TPersonaAssetPartyDao {
	
	@Resource
	private TPersonaAssetPartyMapper tPersonaAssetPartyMapper;
	
	@Override
	protected BaseMapper<TPersonaAssetParty, TPersonaAssetPartyCondition> daoSupport() {
		return tPersonaAssetPartyMapper;
	}

	@Override
	protected TPersonaAssetPartyCondition blankCondition() {
		return new TPersonaAssetPartyCondition();
	}

	@Override
	public TPersonaAssetParty getByPersonaId(Integer personaId) {
		TPersonaAssetPartyCondition condition = new TPersonaAssetPartyCondition();
		condition.setPersonaId(personaId);
		List<TPersonaAssetParty> list = tPersonaAssetPartyMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
