
package com.tzCloud.core.dao.legalEngine.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.legalEngine.TCollectCondition;
import com.tzCloud.core.dao.legalEngine.mapper.TCollectMapper;
import com.tzCloud.core.model.legalEngine.TCollect;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.legalEngine.TCollectDao;

@Service
public class TCollectDaoImpl extends AbstractDaoImpl<TCollect, TCollectCondition, BaseMapper<TCollect,TCollectCondition>> implements TCollectDao{
	
	@Resource
	private TCollectMapper tCollectMapper;
	
	@Override
	protected BaseMapper<TCollect, TCollectCondition> daoSupport() {
		return tCollectMapper;
	}

	@Override
	protected TCollectCondition blankCondition() {
		return new TCollectCondition();
	}

}
