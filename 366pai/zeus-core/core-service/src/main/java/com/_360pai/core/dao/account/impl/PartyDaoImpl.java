
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.PartyCondition;
import com._360pai.core.dao.account.mapper.PartyMapper;
import com._360pai.core.model.account.Party;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.PartyDao;

@Service
public class PartyDaoImpl extends AbstractDaoImpl<Party, PartyCondition, BaseMapper<Party,PartyCondition>> implements PartyDao{
	
	@Resource
	private PartyMapper partyMapper;
	
	@Override
	protected BaseMapper<Party, PartyCondition> daoSupport() {
		return partyMapper;
	}

	@Override
	protected PartyCondition blankCondition() {
		return new PartyCondition();
	}

}
