package com._360pai.core.service.asset.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.asset.TAssetCategoryCondition;
import com._360pai.core.condition.asset.TAssetCategoryOptionCondition;
import com._360pai.core.condition.asset.TAssetTemplateCategoryCondition;
import com._360pai.core.dao.asset.TAssetCategoryDao;
import com._360pai.core.dao.asset.TAssetCategoryOptionDao;
import com._360pai.core.dao.asset.TAssetTemplateCategoryDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.asset.TAssetCategory;
import com._360pai.core.model.asset.TAssetCategoryOption;
import com._360pai.core.model.asset.TAssetTemplateCategory;
import com._360pai.core.service.asset.TAssetCategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TAssetCategoryServiceImpl implements TAssetCategoryService {

    @Autowired
    private TAssetCategoryDao tAssetCategoryDao;
    @Autowired
    private TAssetTemplateCategoryDao assetTemplateCategoryDao;
    @Autowired
    private TAssetCategoryOptionDao tAssetCategoryOptionDao;


    @Override
    public Object getAllCateGoryList(TAssetCategoryCondition categoryCondition) {
        PageHelper.orderBy("order_num asc");
        List<TAssetCategory> tAssetCategories = tAssetCategoryDao.selectList(categoryCondition);
        for (TAssetCategory category : tAssetCategories) {
            TAssetCategoryOptionCondition categoryOptionCondition = new TAssetCategoryOptionCondition();
            categoryOptionCondition.setAssetCategoryId(category.getId());
            PageHelper.orderBy("order_num desc");
            List<TAssetCategoryOption> tAssetCategoryOptions = tAssetCategoryOptionDao.selectList(categoryOptionCondition);
            category.setHasOptions(!tAssetCategoryOptions.isEmpty());
            category.setOptionList(tAssetCategoryOptions);
        }
        return tAssetCategories;
    }

    @Override
    public PageInfo getCateGoryList(int page, int perPage, TAssetCategoryCondition categoryCondition) {
        PageHelper.startPage(page, perPage);
        List<TAssetCategory> tAssetCategories = tAssetCategoryDao.selectList(categoryCondition);
        return new PageInfo<>(tAssetCategories);
    }

    @Override
    public Object addCateGory(TAssetCategory params) {
        TAssetCategory tAssetCategoryByName = findTAssetCategoryByName(params);
        if (tAssetCategoryByName != null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "名称重复");
        }
        return tAssetCategoryDao.insert(params);
    }

    @Override
    public Object updateCateGory(TAssetCategory params) {
        TAssetCategory tAssetCategoryByName = findTAssetCategoryById(params);
        if (tAssetCategoryByName == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的类型不存在");
        }
        return tAssetCategoryDao.updateById(params);
    }

    @Override
    public PageInfo getAllTempByCateGoryId(int page, int perPage, Integer categoryId) {
        PageHelper.startPage(page, perPage);
        TAssetTemplateCategoryCondition categoryCondition = new TAssetTemplateCategoryCondition();
        categoryCondition.setCategoryId(categoryId);
        List<TAssetTemplateCategory> tAssetTemplateCategories = assetTemplateCategoryDao.selectList(categoryCondition);
        return new PageInfo<>(tAssetTemplateCategories);
    }

    @Override
    public Object categoryOptions(Integer assetCategoryId) {
        TAssetCategoryOptionCondition categoryOptionCondition = new TAssetCategoryOptionCondition();
        categoryOptionCondition.setAssetCategoryId(assetCategoryId);
        return tAssetCategoryOptionDao.selectList(categoryOptionCondition);
    }

    @Override
    public TAssetCategoryOption categoryOptionByOptionId(Integer optionId) {
        return tAssetCategoryOptionDao.selectById(optionId);
    }

    @Override
    public TAssetCategory getCategoryById(Integer categoryId) {
        return tAssetCategoryDao.selectById(categoryId);
    }

    public TAssetCategory findTAssetCategoryByName(TAssetCategory params) {
        TAssetCategoryCondition categoryCondition = new TAssetCategoryCondition();
        categoryCondition.setName(params.getName());
        return tAssetCategoryDao.selectOneResult(categoryCondition);
    }

    public TAssetCategory findTAssetCategoryById(TAssetCategory params) {
        TAssetCategoryCondition categoryCondition = new TAssetCategoryCondition();
        categoryCondition.setId(params.getId());
        return tAssetCategoryDao.selectOneResult(categoryCondition);
    }


}