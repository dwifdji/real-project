
package com._360pai.core.dao.asset.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.asset.AssetCategoryGroupSelfMappingCondition;
import com._360pai.core.dao.asset.mapper.AssetCategoryGroupSelfMappingMapper;
import com._360pai.core.model.asset.AssetCategoryGroupSelfMapping;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.asset.AssetCategoryGroupSelfMappingDao;

@Service
public class AssetCategoryGroupSelfMappingDaoImpl extends AbstractDaoImpl<AssetCategoryGroupSelfMapping, AssetCategoryGroupSelfMappingCondition, BaseMapper<AssetCategoryGroupSelfMapping,AssetCategoryGroupSelfMappingCondition>> implements AssetCategoryGroupSelfMappingDao{
	
	@Resource
	private AssetCategoryGroupSelfMappingMapper assetCategoryGroupSelfMappingMapper;
	
	@Override
	protected BaseMapper<AssetCategoryGroupSelfMapping, AssetCategoryGroupSelfMappingCondition> daoSupport() {
		return assetCategoryGroupSelfMappingMapper;
	}

	@Override
	protected AssetCategoryGroupSelfMappingCondition blankCondition() {
		return new AssetCategoryGroupSelfMappingCondition();
	}

}
