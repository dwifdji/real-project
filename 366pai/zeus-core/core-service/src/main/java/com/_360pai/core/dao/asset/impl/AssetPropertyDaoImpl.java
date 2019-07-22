
package com._360pai.core.dao.asset.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.asset.AssetPropertyCondition;
import com._360pai.core.dao.asset.mapper.AssetPropertyMapper;
import com._360pai.core.model.asset.AssetProperty;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.asset.AssetPropertyDao;

@Service
public class AssetPropertyDaoImpl extends AbstractDaoImpl<AssetProperty, AssetPropertyCondition, BaseMapper<AssetProperty,AssetPropertyCondition>> implements AssetPropertyDao{
	
	@Resource
	private AssetPropertyMapper assetPropertyMapper;
	
	@Override
	protected BaseMapper<AssetProperty, AssetPropertyCondition> daoSupport() {
		return assetPropertyMapper;
	}

	@Override
	protected AssetPropertyCondition blankCondition() {
		return new AssetPropertyCondition();
	}

    @Override
    public Object getProperties() {
        return assetPropertyMapper.getProperties();
    }
}
