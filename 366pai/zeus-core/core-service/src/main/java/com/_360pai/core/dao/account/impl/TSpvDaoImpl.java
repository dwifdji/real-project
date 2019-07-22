
package com._360pai.core.dao.account.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TSpvCondition;
import com._360pai.core.dao.account.TSpvDao;
import com._360pai.core.dao.account.mapper.TSpvMapper;
import com._360pai.core.model.account.TSpv;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TSpvDaoImpl extends AbstractDaoImpl<TSpv, TSpvCondition, BaseMapper<TSpv,TSpvCondition>> implements TSpvDao{
	
	@Resource
	private TSpvMapper tSpvMapper;
	
	@Override
	protected BaseMapper<TSpv, TSpvCondition> daoSupport() {
		return tSpvMapper;
	}

	@Override
	protected TSpvCondition blankCondition() {
		return new TSpvCondition();
	}

}
