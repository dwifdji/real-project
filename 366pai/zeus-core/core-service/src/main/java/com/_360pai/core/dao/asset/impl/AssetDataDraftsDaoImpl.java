
package com._360pai.core.dao.asset.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.asset.AssetDataDraftsCondition;
import com._360pai.core.dao.asset.mapper.AssetDataDraftsMapper;
import com._360pai.core.model.asset.AssetDataDrafts;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.asset.AssetDataDraftsDao;

@Service
public class AssetDataDraftsDaoImpl extends AbstractDaoImpl<AssetDataDrafts, AssetDataDraftsCondition, BaseMapper<AssetDataDrafts,AssetDataDraftsCondition>> implements AssetDataDraftsDao{
	
	@Resource
	private AssetDataDraftsMapper assetDataDraftsMapper;
	
	@Override
	protected BaseMapper<AssetDataDrafts, AssetDataDraftsCondition> daoSupport() {
		return assetDataDraftsMapper;
	}

	@Override
	protected AssetDataDraftsCondition blankCondition() {
		return new AssetDataDraftsCondition();
	}

}
