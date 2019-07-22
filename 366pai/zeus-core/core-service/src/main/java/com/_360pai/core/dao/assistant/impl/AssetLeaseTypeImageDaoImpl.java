
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.AssetLeaseTypeImageCondition;
import com._360pai.core.dao.assistant.mapper.AssetLeaseTypeImageMapper;
import com._360pai.core.model.assistant.AssetLeaseTypeImage;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.AssetLeaseTypeImageDao;

@Service
public class AssetLeaseTypeImageDaoImpl extends AbstractDaoImpl<AssetLeaseTypeImage, AssetLeaseTypeImageCondition, BaseMapper<AssetLeaseTypeImage,AssetLeaseTypeImageCondition>> implements AssetLeaseTypeImageDao{
	
	@Resource
	private AssetLeaseTypeImageMapper assetLeaseTypeImageMapper;
	
	@Override
	protected BaseMapper<AssetLeaseTypeImage, AssetLeaseTypeImageCondition> daoSupport() {
		return assetLeaseTypeImageMapper;
	}

	@Override
	protected AssetLeaseTypeImageCondition blankCondition() {
		return new AssetLeaseTypeImageCondition();
	}

}
