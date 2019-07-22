
package com._360pai.core.dao.asset.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetFieldItemCondition;
import com._360pai.core.dao.asset.TAssetFieldItemDao;
import com._360pai.core.dao.asset.mapper.TAssetFieldItemMapper;
import com._360pai.core.model.asset.TAssetFieldItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TAssetFieldItemDaoImpl extends AbstractDaoImpl<TAssetFieldItem, TAssetFieldItemCondition, BaseMapper<TAssetFieldItem, TAssetFieldItemCondition>> implements TAssetFieldItemDao {

    @Resource
    private TAssetFieldItemMapper tAssetFieldItemMapper;

    @Override
    protected BaseMapper<TAssetFieldItem, TAssetFieldItemCondition> daoSupport() {
        return tAssetFieldItemMapper;
    }

    @Override
    protected TAssetFieldItemCondition blankCondition() {
        return new TAssetFieldItemCondition();
    }

    @Override
    public List<Map> findFields(Integer templateCategoryId, Integer assetFieldGroupId, Integer filterOptionId, Integer filterOptionItemId,Integer filterOptionItemDataId) {
        return tAssetFieldItemMapper.findFields(templateCategoryId, assetFieldGroupId,filterOptionId,filterOptionItemId,filterOptionItemDataId);

    }

    @Override
    public int deleteTemplateGroupField(Integer id) {
        return tAssetFieldItemMapper.deleteTemplateGroupField(id);
    }

    @Override
    public List<Map> findFieldsNotHaveOption(Integer templateCategoryId, int fieldGroupId) {
        return tAssetFieldItemMapper.findFieldsNotHaveOption(templateCategoryId,fieldGroupId);
    }

    @Override
    public List<Map> findDisplayField(Integer templateCategoryId, int fieldGroupId) {
        return tAssetFieldItemMapper.findDisplayField(templateCategoryId,fieldGroupId);
    }
}
