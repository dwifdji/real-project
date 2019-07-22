package com._360pai.core.service.asset.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.asset.AssetFieldItemCondition;
import com._360pai.core.dao.asset.AssetFieldItemDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.asset.AssetFieldItem;
import com._360pai.core.service.asset.AssetFieldItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetFieldItemServiceImpl implements AssetFieldItemService {

    @Autowired
    private AssetFieldItemDao assetFieldItemDao;


    @Override
    public int insert(AssetFieldItem params) {
        //先判断是否已添加
        AssetFieldItem item = findByIds(params);
        if (null != item) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "该字段已添加过");
        }
        return assetFieldItemDao.insert(params);
    }

    @Override
    public int delete(AssetFieldItem params) {
        AssetFieldItem item = findById(params);
        if (null == item) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "删除的字段不存在");
        }
        return assetFieldItemDao.deleteItem(item.getId());
    }

    @Override
    public Object update(AssetFieldItem params) {
        AssetFieldItem assetFieldItem = findById(params);
        if (null == assetFieldItem) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "更改的字段不存在");
        }
        return assetFieldItemDao.updateById(params);
    }

    private AssetFieldItem findByIds(AssetFieldItem params) {
        AssetFieldItemCondition condition = new AssetFieldItemCondition();
        condition.setGroupId(params.getGroupId());
        condition.setFieldId(params.getFieldId());
        condition.setCategoryId(params.getCategoryId());
        return assetFieldItemDao.selectFirst(condition);
    }

    private AssetFieldItem findById(AssetFieldItem params) {
        AssetFieldItemCondition condition = new AssetFieldItemCondition();
        condition.setId(params.getId());
        return assetFieldItemDao.selectFirst(condition);
    }
}