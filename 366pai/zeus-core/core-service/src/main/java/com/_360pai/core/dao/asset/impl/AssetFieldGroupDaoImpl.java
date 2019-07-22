
package com._360pai.core.dao.asset.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.asset.AssetFieldGroupCondition;
import com._360pai.core.dao.asset.mapper.AssetFieldGroupMapper;
import com._360pai.core.model.asset.AssetFieldGroup;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.asset.AssetFieldGroupDao;

@Service
public class AssetFieldGroupDaoImpl extends AbstractDaoImpl<AssetFieldGroup, AssetFieldGroupCondition, BaseMapper<AssetFieldGroup,AssetFieldGroupCondition>> implements AssetFieldGroupDao{
	
	@Resource
	private AssetFieldGroupMapper assetFieldGroupMapper;
	
	@Override
	protected BaseMapper<AssetFieldGroup, AssetFieldGroupCondition> daoSupport() {
		return assetFieldGroupMapper;
	}

	@Override
	protected AssetFieldGroupCondition blankCondition() {
		return new AssetFieldGroupCondition();
	}

}
