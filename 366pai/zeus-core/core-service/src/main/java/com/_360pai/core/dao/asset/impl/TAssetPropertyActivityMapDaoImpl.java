
package com._360pai.core.dao.asset.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetPropertyActivityMapCondition;
import com._360pai.core.dao.asset.TAssetPropertyActivityMapDao;
import com._360pai.core.dao.asset.mapper.TAssetPropertyActivityMapMapper;
import com._360pai.core.model.asset.TAssetPropertyActivityMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TAssetPropertyActivityMapDaoImpl extends AbstractDaoImpl<TAssetPropertyActivityMap, TAssetPropertyActivityMapCondition, BaseMapper<TAssetPropertyActivityMap,TAssetPropertyActivityMapCondition>> implements TAssetPropertyActivityMapDao {
	
	@Resource
	private TAssetPropertyActivityMapMapper tAssetPropertyActivityMapMapper;
	
	@Override
	protected BaseMapper<TAssetPropertyActivityMap, TAssetPropertyActivityMapCondition> daoSupport() {
		return tAssetPropertyActivityMapMapper;
	}

	@Override
	protected TAssetPropertyActivityMapCondition blankCondition() {
		return new TAssetPropertyActivityMapCondition();
	}

}
