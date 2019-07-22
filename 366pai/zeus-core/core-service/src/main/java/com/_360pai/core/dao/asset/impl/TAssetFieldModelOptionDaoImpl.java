
package com._360pai.core.dao.asset.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetFieldModelOptionCondition;
import com._360pai.core.dao.asset.TAssetFieldModelOptionDao;
import com._360pai.core.dao.asset.mapper.TAssetFieldModelOptionMapper;
import com._360pai.core.model.asset.TAssetFieldModelOption;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TAssetFieldModelOptionDaoImpl extends AbstractDaoImpl<TAssetFieldModelOption, TAssetFieldModelOptionCondition, BaseMapper<TAssetFieldModelOption,TAssetFieldModelOptionCondition>> implements TAssetFieldModelOptionDao {
	
	@Resource
	private TAssetFieldModelOptionMapper tAssetFieldModelOptionMapper;
	
	@Override
	protected BaseMapper<TAssetFieldModelOption, TAssetFieldModelOptionCondition> daoSupport() {
		return tAssetFieldModelOptionMapper;
	}

	@Override
	protected TAssetFieldModelOptionCondition blankCondition() {
		return new TAssetFieldModelOptionCondition();
	}

}
