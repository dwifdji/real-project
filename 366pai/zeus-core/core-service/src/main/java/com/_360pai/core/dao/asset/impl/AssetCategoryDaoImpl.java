
package com._360pai.core.dao.asset.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.asset.AssetCategoryCondition;
import com._360pai.core.dao.asset.mapper.AssetCategoryMapper;
import com._360pai.core.model.asset.AssetCategory;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.asset.AssetCategoryDao;

@Service
public class AssetCategoryDaoImpl extends AbstractDaoImpl<AssetCategory, AssetCategoryCondition, BaseMapper<AssetCategory,AssetCategoryCondition>> implements AssetCategoryDao{
	
	@Resource
	private AssetCategoryMapper assetCategoryMapper;
	
	@Override
	protected BaseMapper<AssetCategory, AssetCategoryCondition> daoSupport() {
		return assetCategoryMapper;
	}

	@Override
	protected AssetCategoryCondition blankCondition() {
		return new AssetCategoryCondition();
	}

	@Override
	public Integer selectDefault(Integer id) {
		return assetCategoryMapper.selectDefault(id);
	}
}
