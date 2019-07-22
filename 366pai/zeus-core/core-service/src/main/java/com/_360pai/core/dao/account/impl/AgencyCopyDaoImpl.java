
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.AgencyCopyCondition;
import com._360pai.core.dao.account.mapper.AgencyCopyMapper;
import com._360pai.core.model.account.AgencyCopy;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.AgencyCopyDao;

@Service
public class AgencyCopyDaoImpl extends AbstractDaoImpl<AgencyCopy, AgencyCopyCondition, BaseMapper<AgencyCopy,AgencyCopyCondition>> implements AgencyCopyDao{
	
	@Resource
	private AgencyCopyMapper agencyCopyMapper;
	
	@Override
	protected BaseMapper<AgencyCopy, AgencyCopyCondition> daoSupport() {
		return agencyCopyMapper;
	}

	@Override
	protected AgencyCopyCondition blankCondition() {
		return new AgencyCopyCondition();
	}

}
