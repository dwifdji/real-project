
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.PartyApplyCondition;
import com._360pai.core.dao.account.mapper.PartyApplyMapper;
import com._360pai.core.model.account.PartyApply;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.PartyApplyDao;

@Service
public class PartyApplyDaoImpl extends AbstractDaoImpl<PartyApply, PartyApplyCondition, BaseMapper<PartyApply,PartyApplyCondition>> implements PartyApplyDao{
	
	@Resource
	private PartyApplyMapper partyApplyMapper;
	
	@Override
	protected BaseMapper<PartyApply, PartyApplyCondition> daoSupport() {
		return partyApplyMapper;
	}

	@Override
	protected PartyApplyCondition blankCondition() {
		return new PartyApplyCondition();
	}

}
