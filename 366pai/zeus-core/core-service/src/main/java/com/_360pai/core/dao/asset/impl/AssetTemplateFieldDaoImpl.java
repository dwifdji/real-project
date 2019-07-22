
package com._360pai.core.dao.asset.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.asset.AssetTemplateFieldCondition;
import com._360pai.core.dao.asset.mapper.AssetTemplateFieldMapper;
import com._360pai.core.model.asset.AssetTemplateField;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.asset.AssetTemplateFieldDao;

@Service
public class AssetTemplateFieldDaoImpl extends AbstractDaoImpl<AssetTemplateField, AssetTemplateFieldCondition, BaseMapper<AssetTemplateField,AssetTemplateFieldCondition>> implements AssetTemplateFieldDao{
	
	@Resource
	private AssetTemplateFieldMapper assetTemplateFieldMapper;
	
	@Override
	protected BaseMapper<AssetTemplateField, AssetTemplateFieldCondition> daoSupport() {
		return assetTemplateFieldMapper;
	}

	@Override
	protected AssetTemplateFieldCondition blankCondition() {
		return new AssetTemplateFieldCondition();
	}

}
