
package com._360pai.core.dao.asset.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetTemplateRecodeCondition;
import com._360pai.core.dao.asset.TAssetTemplateRecodeDao;
import com._360pai.core.dao.asset.mapper.TAssetTemplateRecodeMapper;
import com._360pai.core.model.asset.TAssetTemplateRecode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TAssetTemplateRecodeDaoImpl extends AbstractDaoImpl<TAssetTemplateRecode, TAssetTemplateRecodeCondition, BaseMapper<TAssetTemplateRecode,TAssetTemplateRecodeCondition>> implements TAssetTemplateRecodeDao {
	
	@Resource
	private TAssetTemplateRecodeMapper tAssetTemplateRecodeMapper;
	
	@Override
	protected BaseMapper<TAssetTemplateRecode, TAssetTemplateRecodeCondition> daoSupport() {
		return tAssetTemplateRecodeMapper;
	}

	@Override
	protected TAssetTemplateRecodeCondition blankCondition() {
		return new TAssetTemplateRecodeCondition();
	}

}
