
package com._360pai.core.dao.asset.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetFieldGroupCondition;
import com._360pai.core.dao.asset.TAssetFieldGroupDao;
import com._360pai.core.dao.asset.mapper.TAssetFieldGroupMapper;
import com._360pai.core.model.asset.TAssetFieldGroup;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TAssetFieldGroupDaoImpl extends AbstractDaoImpl<TAssetFieldGroup, TAssetFieldGroupCondition, BaseMapper<TAssetFieldGroup,TAssetFieldGroupCondition>> implements TAssetFieldGroupDao {
	
	@Resource
	private TAssetFieldGroupMapper tAssetFieldGroupMapper;
	
	@Override
	protected BaseMapper<TAssetFieldGroup, TAssetFieldGroupCondition> daoSupport() {
		return tAssetFieldGroupMapper;
	}

	@Override
	protected TAssetFieldGroupCondition blankCondition() {
		return new TAssetFieldGroupCondition();
	}

}
