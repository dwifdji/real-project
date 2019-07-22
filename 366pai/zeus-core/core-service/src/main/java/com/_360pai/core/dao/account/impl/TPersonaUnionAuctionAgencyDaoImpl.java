
package com._360pai.core.dao.account.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TPersonaUnionAuctionAgencyCondition;
import com._360pai.core.dao.account.TPersonaUnionAuctionAgencyDao;
import com._360pai.core.dao.account.mapper.TPersonaUnionAuctionAgencyMapper;
import com._360pai.core.model.account.TPersonaUnionAuctionAgency;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TPersonaUnionAuctionAgencyDaoImpl extends AbstractDaoImpl<TPersonaUnionAuctionAgency, TPersonaUnionAuctionAgencyCondition, BaseMapper<TPersonaUnionAuctionAgency,TPersonaUnionAuctionAgencyCondition>> implements TPersonaUnionAuctionAgencyDao {
	
	@Resource
	private TPersonaUnionAuctionAgencyMapper tPersonaUnionAuctionAgencyMapper;
	
	@Override
	protected BaseMapper<TPersonaUnionAuctionAgency, TPersonaUnionAuctionAgencyCondition> daoSupport() {
		return tPersonaUnionAuctionAgencyMapper;
	}

	@Override
	protected TPersonaUnionAuctionAgencyCondition blankCondition() {
		return new TPersonaUnionAuctionAgencyCondition();
	}

	@Override
	public TPersonaUnionAuctionAgency getByPersonaId(Integer personaId) {
		TPersonaUnionAuctionAgencyCondition condition = new TPersonaUnionAuctionAgencyCondition();
		condition.setPersonaId(personaId);
		List<TPersonaUnionAuctionAgency> list = tPersonaUnionAuctionAgencyMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
