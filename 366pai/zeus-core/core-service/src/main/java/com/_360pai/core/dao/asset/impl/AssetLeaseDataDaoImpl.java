
package com._360pai.core.dao.asset.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.asset.AssetLeaseDataCondition;
import com._360pai.core.dao.asset.mapper.AssetLeaseDataMapper;
import com._360pai.core.model.asset.AssetLeaseData;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.asset.AssetLeaseDataDao;

@Service
public class AssetLeaseDataDaoImpl extends AbstractDaoImpl<AssetLeaseData, AssetLeaseDataCondition, BaseMapper<AssetLeaseData,AssetLeaseDataCondition>> implements AssetLeaseDataDao{
	
	@Resource
	private AssetLeaseDataMapper assetLeaseDataMapper;
	
	@Override
	protected BaseMapper<AssetLeaseData, AssetLeaseDataCondition> daoSupport() {
		return assetLeaseDataMapper;
	}

	@Override
	protected AssetLeaseDataCondition blankCondition() {
		return new AssetLeaseDataCondition();
	}

}
