
package com._360pai.core.dao.asset.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetFieldModelCondition;
import com._360pai.core.dao.asset.TAssetFieldModelDao;
import com._360pai.core.dao.asset.mapper.TAssetFieldModelMapper;
import com._360pai.core.model.asset.TAssetFieldModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TAssetFieldModelDaoImpl extends AbstractDaoImpl<TAssetFieldModel, TAssetFieldModelCondition, BaseMapper<TAssetFieldModel,TAssetFieldModelCondition>> implements TAssetFieldModelDao {
	
	@Resource
	private TAssetFieldModelMapper tAssetFieldModelMapper;
	
	@Override
	protected BaseMapper<TAssetFieldModel, TAssetFieldModelCondition> daoSupport() {
		return tAssetFieldModelMapper;
	}

	@Override
	protected TAssetFieldModelCondition blankCondition() {
		return new TAssetFieldModelCondition();
	}

}
