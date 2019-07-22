
package com._360pai.core.dao.asset.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetTemplateCategoryCondition;
import com._360pai.core.dao.asset.TAssetTemplateCategoryDao;
import com._360pai.core.dao.asset.mapper.TAssetTemplateCategoryMapper;
import com._360pai.core.model.asset.TAssetTemplateCategory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TAssetTemplateCategoryDaoImpl extends AbstractDaoImpl<TAssetTemplateCategory, TAssetTemplateCategoryCondition, BaseMapper<TAssetTemplateCategory,TAssetTemplateCategoryCondition>> implements TAssetTemplateCategoryDao {
	
	@Resource
	private TAssetTemplateCategoryMapper tAssetTemplateCategoryMapper;
	
	@Override
	protected BaseMapper<TAssetTemplateCategory, TAssetTemplateCategoryCondition> daoSupport() {
		return tAssetTemplateCategoryMapper;
	}

	@Override
	protected TAssetTemplateCategoryCondition blankCondition() {
		return new TAssetTemplateCategoryCondition();
	}

}
