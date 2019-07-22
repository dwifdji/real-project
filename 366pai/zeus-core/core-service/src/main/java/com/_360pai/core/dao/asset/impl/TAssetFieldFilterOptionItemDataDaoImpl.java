
package com._360pai.core.dao.asset.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetFieldFilterOptionItemDataCondition;
import com._360pai.core.dao.asset.TAssetFieldFilterOptionItemDataDao;
import com._360pai.core.dao.asset.mapper.TAssetFieldFilterOptionItemDataMapper;
import com._360pai.core.model.asset.TAssetFieldFilterOptionItemData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TAssetFieldFilterOptionItemDataDaoImpl extends AbstractDaoImpl<TAssetFieldFilterOptionItemData, TAssetFieldFilterOptionItemDataCondition, BaseMapper<TAssetFieldFilterOptionItemData, TAssetFieldFilterOptionItemDataCondition>> implements TAssetFieldFilterOptionItemDataDao {
	
	@Resource
	private TAssetFieldFilterOptionItemDataMapper tAssetFieldFilterOptionItemDataMapper;
	
	@Override
	protected BaseMapper<TAssetFieldFilterOptionItemData, TAssetFieldFilterOptionItemDataCondition> daoSupport() {
		return tAssetFieldFilterOptionItemDataMapper;
	}

	@Override
	protected TAssetFieldFilterOptionItemDataCondition blankCondition() {
		return new TAssetFieldFilterOptionItemDataCondition();
	}

}
