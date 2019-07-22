
package com._360pai.core.dao.asset.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetFieldGroupFilterMapCondition;
import com._360pai.core.dao.asset.TAssetFieldGroupFilterMapDao;
import com._360pai.core.dao.asset.mapper.TAssetFieldGroupFilterMapMapper;
import com._360pai.core.model.asset.TAssetFieldGroupFilterMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TAssetFieldGroupFilterMapDaoImpl extends AbstractDaoImpl<TAssetFieldGroupFilterMap, TAssetFieldGroupFilterMapCondition, BaseMapper<TAssetFieldGroupFilterMap,TAssetFieldGroupFilterMapCondition>> implements TAssetFieldGroupFilterMapDao {
	
	@Resource
	private TAssetFieldGroupFilterMapMapper tAssetFieldGroupFilterMapMapper;
	
	@Override
	protected BaseMapper<TAssetFieldGroupFilterMap, TAssetFieldGroupFilterMapCondition> daoSupport() {
		return tAssetFieldGroupFilterMapMapper;
	}

	@Override
	protected TAssetFieldGroupFilterMapCondition blankCondition() {
		return new TAssetFieldGroupFilterMapCondition();
	}

}
