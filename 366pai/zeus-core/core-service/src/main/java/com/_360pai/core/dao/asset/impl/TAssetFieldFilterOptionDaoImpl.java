
package com._360pai.core.dao.asset.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetFieldFilterOptionCondition;
import com._360pai.core.dao.asset.TAssetFieldFilterOptionDao;
import com._360pai.core.dao.asset.mapper.TAssetFieldFilterOptionMapper;
import com._360pai.core.model.asset.TAssetFieldFilterOption;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TAssetFieldFilterOptionDaoImpl extends AbstractDaoImpl<TAssetFieldFilterOption, TAssetFieldFilterOptionCondition, BaseMapper<TAssetFieldFilterOption,TAssetFieldFilterOptionCondition>> implements TAssetFieldFilterOptionDao {
	
	@Resource
	private TAssetFieldFilterOptionMapper tAssetFieldFilterOptionMapper;
	
	@Override
	protected BaseMapper<TAssetFieldFilterOption, TAssetFieldFilterOptionCondition> daoSupport() {
		return tAssetFieldFilterOptionMapper;
	}

	@Override
	protected TAssetFieldFilterOptionCondition blankCondition() {
		return new TAssetFieldFilterOptionCondition();
	}

    @Override
    public int deleteAssetTemplateFieldOption(Integer id) {
        return tAssetFieldFilterOptionMapper.deleteAssetTemplateFieldOption(id);
    }
}
