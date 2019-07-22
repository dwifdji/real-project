
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.AgencyChannelAgentCondition;
import com._360pai.core.dao.account.mapper.AgencyChannelAgentMapper;
import com._360pai.core.model.account.AgencyChannelAgent;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.AgencyChannelAgentDao;

import java.util.List;

@Service
public class AgencyChannelAgentDaoImpl extends AbstractDaoImpl<AgencyChannelAgent, AgencyChannelAgentCondition, BaseMapper<AgencyChannelAgent,AgencyChannelAgentCondition>> implements AgencyChannelAgentDao{
	
	@Resource
	private AgencyChannelAgentMapper agencyChannelAgentMapper;
	
	@Override
	protected BaseMapper<AgencyChannelAgent, AgencyChannelAgentCondition> daoSupport() {
		return agencyChannelAgentMapper;
	}

	@Override
	protected AgencyChannelAgentCondition blankCondition() {
		return new AgencyChannelAgentCondition();
	}

	@Override
	public AgencyChannelAgent getByAgencyId(Integer agencyId) {
		AgencyChannelAgentCondition condition = new AgencyChannelAgentCondition();
		condition.setAgencyId(agencyId);
		List<AgencyChannelAgent> list = agencyChannelAgentMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<AgencyChannelAgent> getByChannelAgentAgencyId(Integer channelAgentAgencyId) {
		AgencyChannelAgentCondition condition = new AgencyChannelAgentCondition();
		condition.setChannelAgentAgencyId(channelAgentAgencyId);
		return agencyChannelAgentMapper.selectByCondition(condition);
	}

	@Override
	public int deleteById(Integer id) {
		return agencyChannelAgentMapper.deleteById(id);
	}
}
