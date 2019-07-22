
package com._360pai.core.dao.asset.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.AssetFieldItemCondition;
import com._360pai.core.dao.asset.AssetFieldItemDao;
import com._360pai.core.dao.asset.mapper.AssetFieldItemMapper;
import com._360pai.core.model.asset.AssetFieldItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AssetFieldItemDaoImpl extends AbstractDaoImpl<AssetFieldItem, AssetFieldItemCondition, BaseMapper<AssetFieldItem,AssetFieldItemCondition>> implements AssetFieldItemDao{
	
	@Resource
	private AssetFieldItemMapper assetFieldItemMapper;
	
	@Override
	protected BaseMapper<AssetFieldItem, AssetFieldItemCondition> daoSupport() {
		return assetFieldItemMapper;
	}

	@Override
	protected AssetFieldItemCondition blankCondition() {
		return new AssetFieldItemCondition();
	}

    @Override
    public int deleteItem(Integer id) {
        return assetFieldItemMapper.deleteItem(id);
    }
}
