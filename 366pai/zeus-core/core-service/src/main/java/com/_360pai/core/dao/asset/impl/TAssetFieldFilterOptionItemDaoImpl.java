
package com._360pai.core.dao.asset.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetFieldFilterOptionItemCondition;
import com._360pai.core.dao.asset.TAssetFieldFilterOptionItemDao;
import com._360pai.core.dao.asset.mapper.TAssetFieldFilterOptionItemMapper;
import com._360pai.core.model.asset.TAssetFieldFilterOptionItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TAssetFieldFilterOptionItemDaoImpl extends AbstractDaoImpl<TAssetFieldFilterOptionItem, TAssetFieldFilterOptionItemCondition, BaseMapper<TAssetFieldFilterOptionItem,TAssetFieldFilterOptionItemCondition>> implements TAssetFieldFilterOptionItemDao {
	
	@Resource
	private TAssetFieldFilterOptionItemMapper tAssetFieldFilterOptionItemMapper;
	
	@Override
	protected BaseMapper<TAssetFieldFilterOptionItem, TAssetFieldFilterOptionItemCondition> daoSupport() {
		return tAssetFieldFilterOptionItemMapper;
	}

	@Override
	protected TAssetFieldFilterOptionItemCondition blankCondition() {
		return new TAssetFieldFilterOptionItemCondition();
	}

    @Override
    public int deleteTAssetFieldFilterOptionItem(Integer id) {
        return tAssetFieldFilterOptionItemMapper.deleteTAssetFieldFilterOptionItem(id);
    }
}
