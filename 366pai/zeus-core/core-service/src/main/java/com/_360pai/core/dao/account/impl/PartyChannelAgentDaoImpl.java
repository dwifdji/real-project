
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.PartyChannelAgentCondition;
import com._360pai.core.dao.account.mapper.PartyChannelAgentMapper;
import com._360pai.core.model.account.PartyChannelAgent;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.PartyChannelAgentDao;

import java.util.List;

@Service
public class PartyChannelAgentDaoImpl extends AbstractDaoImpl<PartyChannelAgent, PartyChannelAgentCondition, BaseMapper<PartyChannelAgent,PartyChannelAgentCondition>> implements PartyChannelAgentDao{
	
	@Resource
	private PartyChannelAgentMapper partyChannelAgentMapper;
	
	@Override
	protected BaseMapper<PartyChannelAgent, PartyChannelAgentCondition> daoSupport() {
		return partyChannelAgentMapper;
	}

	@Override
	protected PartyChannelAgentCondition blankCondition() {
		return new PartyChannelAgentCondition();
	}

	@Override
	public PartyChannelAgent getByPartyId(Integer partyId) {
		PartyChannelAgentCondition condition = new PartyChannelAgentCondition();
		condition.setPartyId(partyId);
		List<PartyChannelAgent> list = partyChannelAgentMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<PartyChannelAgent> getByChannelAgentPartyId(Integer channelAgentPartyId) {
		PartyChannelAgentCondition condition = new PartyChannelAgentCondition();
		condition.setChannelAgentPartyId(channelAgentPartyId);
		return partyChannelAgentMapper.selectByCondition(condition);
	}

	@Override
	public int deleteById(Integer id) {
		return partyChannelAgentMapper.deleteById(id);
	}
}
