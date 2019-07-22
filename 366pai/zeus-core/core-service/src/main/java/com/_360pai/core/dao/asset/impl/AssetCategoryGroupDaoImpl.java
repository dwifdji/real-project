
package com._360pai.core.dao.asset.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.asset.AssetCategoryGroupCondition;
import com._360pai.core.dao.asset.mapper.AssetCategoryGroupMapper;
import com._360pai.core.model.asset.AssetCategoryGroup;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.asset.AssetCategoryGroupDao;

@Service
public class AssetCategoryGroupDaoImpl extends AbstractDaoImpl<AssetCategoryGroup, AssetCategoryGroupCondition, BaseMapper<AssetCategoryGroup,AssetCategoryGroupCondition>> implements AssetCategoryGroupDao{
	
	@Resource
	private AssetCategoryGroupMapper assetCategoryGroupMapper;
	
	@Override
	protected BaseMapper<AssetCategoryGroup, AssetCategoryGroupCondition> daoSupport() {
		return assetCategoryGroupMapper;
	}

	@Override
	protected AssetCategoryGroupCondition blankCondition() {
		return new AssetCategoryGroupCondition();
	}

}
