
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.TServiceFollowCondition;
import com._360pai.core.dao.assistant.mapper.TServiceFollowMapper;
import com._360pai.core.model.assistant.TServiceFollow;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.TServiceFollowDao;

import java.util.List;

@Service
public class TServiceFollowDaoImpl extends AbstractDaoImpl<TServiceFollow, TServiceFollowCondition, BaseMapper<TServiceFollow,TServiceFollowCondition>> implements TServiceFollowDao{
	
	@Resource
	private TServiceFollowMapper tServiceFollowMapper;
	
	@Override
	protected BaseMapper<TServiceFollow, TServiceFollowCondition> daoSupport() {
		return tServiceFollowMapper;
	}

	@Override
	protected TServiceFollowCondition blankCondition() {
		return new TServiceFollowCondition();
	}

	@Override
	public int removeFollowList(List<TServiceFollow> list) {
		return list.size() == 0 ? 0 : tServiceFollowMapper.removeFollowList(list);
	}
}
