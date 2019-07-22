
package com._360pai.core.dao.asset.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.asset.AssetCopyCondition;
import com._360pai.core.dao.asset.mapper.AssetCopyMapper;
import com._360pai.core.model.asset.AssetCopy;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.asset.AssetCopyDao;

@Service
public class AssetCopyDaoImpl extends AbstractDaoImpl<AssetCopy, AssetCopyCondition, BaseMapper<AssetCopy,AssetCopyCondition>> implements AssetCopyDao{
	
	@Resource
	private AssetCopyMapper assetCopyMapper;
	
	@Override
	protected BaseMapper<AssetCopy, AssetCopyCondition> daoSupport() {
		return assetCopyMapper;
	}

	@Override
	protected AssetCopyCondition blankCondition() {
		return new AssetCopyCondition();
	}

}
