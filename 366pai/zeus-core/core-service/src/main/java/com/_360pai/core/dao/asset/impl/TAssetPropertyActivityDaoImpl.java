
package com._360pai.core.dao.asset.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.asset.TAssetPropertyActivityCondition;
import com._360pai.core.dao.asset.mapper.TAssetPropertyActivityMapper;
import com._360pai.core.model.asset.TAssetPropertyActivity;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.asset.TAssetPropertyActivityDao;

import java.util.List;
import java.util.Map;

@Service
public class TAssetPropertyActivityDaoImpl extends AbstractDaoImpl<TAssetPropertyActivity, TAssetPropertyActivityCondition, BaseMapper<TAssetPropertyActivity,TAssetPropertyActivityCondition>> implements TAssetPropertyActivityDao{
	
	@Resource
	private TAssetPropertyActivityMapper tAssetPropertyActivityMapper;
	
	@Override
	protected BaseMapper<TAssetPropertyActivity, TAssetPropertyActivityCondition> daoSupport() {
		return tAssetPropertyActivityMapper;
	}

	@Override
	protected TAssetPropertyActivityCondition blankCondition() {
		return new TAssetPropertyActivityCondition();
	}

    @Override
    public List<Map> propertySearch(Integer assetPropertyId) {
        return tAssetPropertyActivityMapper.propertySearch(assetPropertyId);
    }
}
