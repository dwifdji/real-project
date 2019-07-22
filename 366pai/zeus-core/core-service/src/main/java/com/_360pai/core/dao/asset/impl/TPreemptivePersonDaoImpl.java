
package com._360pai.core.dao.asset.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TPreemptivePersonCondition;
import com._360pai.core.dao.asset.TPreemptivePersonDao;
import com._360pai.core.dao.asset.mapper.TPreemptivePersonMapper;
import com._360pai.core.model.asset.TPreemptivePerson;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TPreemptivePersonDaoImpl extends AbstractDaoImpl<TPreemptivePerson, TPreemptivePersonCondition, BaseMapper<TPreemptivePerson,TPreemptivePersonCondition>> implements TPreemptivePersonDao {
	
	@Resource
	private TPreemptivePersonMapper tPreemptivePersonMapper;
	
	@Override
	protected BaseMapper<TPreemptivePerson, TPreemptivePersonCondition> daoSupport() {
		return tPreemptivePersonMapper;
	}

	@Override
	protected TPreemptivePersonCondition blankCondition() {
		return new TPreemptivePersonCondition();
	}

}
