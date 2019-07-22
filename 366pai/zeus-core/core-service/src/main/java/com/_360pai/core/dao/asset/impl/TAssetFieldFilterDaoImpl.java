
package com._360pai.core.dao.asset.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetFieldFilterCondition;
import com._360pai.core.dao.asset.TAssetFieldFilterDao;
import com._360pai.core.dao.asset.mapper.TAssetFieldFilterMapper;
import com._360pai.core.model.asset.TAssetFieldFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TAssetFieldFilterDaoImpl extends AbstractDaoImpl<TAssetFieldFilter, TAssetFieldFilterCondition, BaseMapper<TAssetFieldFilter,TAssetFieldFilterCondition>> implements TAssetFieldFilterDao {
	
	@Resource
	private TAssetFieldFilterMapper tAssetFieldFilterMapper;
	
	@Override
	protected BaseMapper<TAssetFieldFilter, TAssetFieldFilterCondition> daoSupport() {
		return tAssetFieldFilterMapper;
	}

	@Override
	protected TAssetFieldFilterCondition blankCondition() {
		return new TAssetFieldFilterCondition();
	}

}
