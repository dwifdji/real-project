package com._360pai.core.service.asset.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.asset.TAssetFieldFilterOptionItemCondition;
import com._360pai.core.dao.asset.TAssetFieldFilterOptionItemDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.asset.TAssetFieldFilterOptionItem;
import com._360pai.core.service.asset.TAssetFieldFilterOptionItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TAssetFieldFilterOptionItemServiceImpl implements TAssetFieldFilterOptionItemService {

    @Autowired
    private TAssetFieldFilterOptionItemDao tAssetFieldFilterOptionItemDao;


    @Override
    public int addTAssetFieldFilterOptionItem(TAssetFieldFilterOptionItem params) {
        TAssetFieldFilterOptionItem tAssetFieldFilterOptionItemByName = findTAssetFieldFilterOptionItemByName(params);
        if (tAssetFieldFilterOptionItemByName != null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "名称已存在");
        }
        return tAssetFieldFilterOptionItemDao.insert(params);
    }

    @Override
    public int editTAssetFieldFilterOptionItem(TAssetFieldFilterOptionItem params) {
        TAssetFieldFilterOptionItem tAssetFieldFilterOptionItemById = findTAssetFieldFilterOptionItemById(params);
        if (tAssetFieldFilterOptionItemById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的商品不存在");
        }
        return tAssetFieldFilterOptionItemDao.updateById(params);
    }

    @Override
    public int deleteTAssetFieldFilterOptionItem(TAssetFieldFilterOptionItem params) {
        TAssetFieldFilterOptionItem tAssetFieldFilterOptionItemById = findTAssetFieldFilterOptionItemById(params);
        if (tAssetFieldFilterOptionItemById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的商品不存在");
        }
        return tAssetFieldFilterOptionItemDao.deleteTAssetFieldFilterOptionItem(params.getId());
    }

    @Override
    public Object detailAssetTemplateFieldOptionItem(Integer filterOptionId, Integer filterId) {
        TAssetFieldFilterOptionItemCondition itemCondition = new TAssetFieldFilterOptionItemCondition();
        itemCondition.setFilterId(filterId);
        itemCondition.setFilterOptionId(filterOptionId);
        return tAssetFieldFilterOptionItemDao.selectList(itemCondition);
    }

    private TAssetFieldFilterOptionItem findTAssetFieldFilterOptionItemById(TAssetFieldFilterOptionItem params) {
        TAssetFieldFilterOptionItemCondition condition = new TAssetFieldFilterOptionItemCondition();
        condition.setId(params.getId());
        return tAssetFieldFilterOptionItemDao.selectOneResult(condition);
    }

    private TAssetFieldFilterOptionItem findTAssetFieldFilterOptionItemByName(TAssetFieldFilterOptionItem params) {
        TAssetFieldFilterOptionItemCondition condition = new TAssetFieldFilterOptionItemCondition();
        condition.setItemName(params.getItemName());
        return tAssetFieldFilterOptionItemDao.selectOneResult(condition);
    }
}