
package com._360pai.core.dao.asset.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetCategoryCondition;
import com._360pai.core.dao.asset.TAssetCategoryDao;
import com._360pai.core.dao.asset.mapper.TAssetCategoryMapper;
import com._360pai.core.model.asset.TAssetCategory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TAssetCategoryDaoImpl extends AbstractDaoImpl<TAssetCategory, TAssetCategoryCondition, BaseMapper<TAssetCategory,TAssetCategoryCondition>> implements TAssetCategoryDao {
	
	@Resource
	private TAssetCategoryMapper tAssetCategoryMapper;
	
	@Override
	protected BaseMapper<TAssetCategory, TAssetCategoryCondition> daoSupport() {
		return tAssetCategoryMapper;
	}

	@Override
	protected TAssetCategoryCondition blankCondition() {
		return new TAssetCategoryCondition();
	}

}
