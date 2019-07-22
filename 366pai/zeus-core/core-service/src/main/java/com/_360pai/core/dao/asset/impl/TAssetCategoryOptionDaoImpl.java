
package com._360pai.core.dao.asset.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.asset.TAssetCategoryOptionCondition;
import com._360pai.core.dao.asset.mapper.TAssetCategoryOptionMapper;
import com._360pai.core.model.asset.TAssetCategoryOption;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.asset.TAssetCategoryOptionDao;

@Service
public class TAssetCategoryOptionDaoImpl extends AbstractDaoImpl<TAssetCategoryOption, TAssetCategoryOptionCondition, BaseMapper<TAssetCategoryOption,TAssetCategoryOptionCondition>> implements TAssetCategoryOptionDao{
	
	@Resource
	private TAssetCategoryOptionMapper tAssetCategoryOptionMapper;
	
	@Override
	protected BaseMapper<TAssetCategoryOption, TAssetCategoryOptionCondition> daoSupport() {
		return tAssetCategoryOptionMapper;
	}

	@Override
	protected TAssetCategoryOptionCondition blankCondition() {
		return new TAssetCategoryOptionCondition();
	}

}
