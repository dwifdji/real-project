
package com._360pai.core.dao.asset.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.asset.AssetDataCopyCondition;
import com._360pai.core.dao.asset.mapper.AssetDataCopyMapper;
import com._360pai.core.model.asset.AssetDataCopy;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.asset.AssetDataCopyDao;

@Service
public class AssetDataCopyDaoImpl extends AbstractDaoImpl<AssetDataCopy, AssetDataCopyCondition, BaseMapper<AssetDataCopy,AssetDataCopyCondition>> implements AssetDataCopyDao{
	
	@Resource
	private AssetDataCopyMapper assetDataCopyMapper;
	
	@Override
	protected BaseMapper<AssetDataCopy, AssetDataCopyCondition> daoSupport() {
		return assetDataCopyMapper;
	}

	@Override
	protected AssetDataCopyCondition blankCondition() {
		return new AssetDataCopyCondition();
	}

}
