
package com._360pai.core.dao.asset.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.asset.AssetTemplateOptionsResultCondition;
import com._360pai.core.dao.asset.mapper.AssetTemplateOptionsResultMapper;
import com._360pai.core.model.asset.AssetTemplateOptionsResult;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.asset.AssetTemplateOptionsResultDao;

@Service
public class AssetTemplateOptionsResultDaoImpl extends AbstractDaoImpl<AssetTemplateOptionsResult, AssetTemplateOptionsResultCondition, BaseMapper<AssetTemplateOptionsResult,AssetTemplateOptionsResultCondition>> implements AssetTemplateOptionsResultDao{
	
	@Resource
	private AssetTemplateOptionsResultMapper assetTemplateOptionsResultMapper;
	
	@Override
	protected BaseMapper<AssetTemplateOptionsResult, AssetTemplateOptionsResultCondition> daoSupport() {
		return assetTemplateOptionsResultMapper;
	}

	@Override
	protected AssetTemplateOptionsResultCondition blankCondition() {
		return new AssetTemplateOptionsResultCondition();
	}

	@Override
	public int deleteForGroupId(Integer groupId) {
		return assetTemplateOptionsResultMapper.deleteForGroupId(groupId);
	}
}
