
package com._360pai.core.dao.asset.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetPropertyEnrollingCondition;
import com._360pai.core.dao.asset.TAssetPropertyEnrollingDao;
import com._360pai.core.dao.asset.mapper.TAssetPropertyEnrollingMapper;
import com._360pai.core.model.asset.TAssetPropertyEnrolling;

@Service
public class TAssetPropertyEnrollingDaoImpl extends AbstractDaoImpl<TAssetPropertyEnrolling, TAssetPropertyEnrollingCondition, BaseMapper<TAssetPropertyEnrolling,TAssetPropertyEnrollingCondition>> implements TAssetPropertyEnrollingDao{
	
	@Resource
	private TAssetPropertyEnrollingMapper tAssetPropertyEnrollingMapper;
	
	@Override
	protected BaseMapper<TAssetPropertyEnrolling, TAssetPropertyEnrollingCondition> daoSupport() {
		return tAssetPropertyEnrollingMapper;
	}

	@Override
	protected TAssetPropertyEnrollingCondition blankCondition() {
		return new TAssetPropertyEnrollingCondition();
	}

}
