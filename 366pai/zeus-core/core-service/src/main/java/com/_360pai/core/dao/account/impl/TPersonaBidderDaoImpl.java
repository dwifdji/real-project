
package com._360pai.core.dao.account.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TPersonaBidderCondition;
import com._360pai.core.dao.account.TPersonaBidderDao;
import com._360pai.core.dao.account.mapper.TPersonaBidderMapper;
import com._360pai.core.model.account.TPersonaBidder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TPersonaBidderDaoImpl extends AbstractDaoImpl<TPersonaBidder, TPersonaBidderCondition, BaseMapper<TPersonaBidder,TPersonaBidderCondition>> implements TPersonaBidderDao {
	
	@Resource
	private TPersonaBidderMapper tPersonaBidderMapper;
	
	@Override
	protected BaseMapper<TPersonaBidder, TPersonaBidderCondition> daoSupport() {
		return tPersonaBidderMapper;
	}

	@Override
	protected TPersonaBidderCondition blankCondition() {
		return new TPersonaBidderCondition();
	}

	@Override
	public TPersonaBidder getByPersonaId(Integer personaId) {
		TPersonaBidderCondition condition = new TPersonaBidderCondition();
		condition.setPersonaId(personaId);
		List<TPersonaBidder> list = tPersonaBidderMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
