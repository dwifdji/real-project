
package com.tzCloud.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.account.TAccountMembershipCardCondition;
import com.tzCloud.core.dao.account.mapper.TAccountMembershipCardMapper;
import com.tzCloud.core.model.account.TAccountMembershipCard;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.account.TAccountMembershipCardDao;

import java.util.List;

@Service
public class TAccountMembershipCardDaoImpl extends AbstractDaoImpl<TAccountMembershipCard, TAccountMembershipCardCondition, BaseMapper<TAccountMembershipCard,TAccountMembershipCardCondition>> implements TAccountMembershipCardDao{
	
	@Resource
	private TAccountMembershipCardMapper tAccountMembershipCardMapper;
	
	@Override
	protected BaseMapper<TAccountMembershipCard, TAccountMembershipCardCondition> daoSupport() {
		return tAccountMembershipCardMapper;
	}

	@Override
	protected TAccountMembershipCardCondition blankCondition() {
		return new TAccountMembershipCardCondition();
	}

	@Override
	public List<TAccountMembershipCard> findAvailableCard(Integer id) {
		return tAccountMembershipCardMapper.findAvailableCard(id);
	}
}
