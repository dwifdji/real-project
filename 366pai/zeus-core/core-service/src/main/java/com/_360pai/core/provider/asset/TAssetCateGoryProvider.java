package com._360pai.core.provider.asset;

import com._360pai.core.condition.asset.TAssetCategoryCondition;
import com._360pai.core.facade.asset.TAssetCateGoryFacade;
import com._360pai.core.facade.asset.req.TAssetCategoryOptionReq;
import com._360pai.core.facade.asset.req.TAssetCategoryReq;
import com._360pai.core.model.asset.TAssetCategory;
import com._360pai.core.model.asset.TAssetTemplateCategory;
import com._360pai.core.service.asset.TAssetCategoryService;
import com._360pai.core.service.asset.TAssetTemplateCategoryService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zxiao
 * @Title: TAssetCateGoryProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/31 10:53
 */
@Component
@Service(version = "1.0.0")
public class TAssetCateGoryProvider implements TAssetCateGoryFacade {

    @Resource
    private TAssetCategoryService tAssetCategoryService;
    @Resource
    private TAssetTemplateCategoryService assetTemplateCategoryService;

    @Override
    public Object getAllCateGoryList(TAssetCategoryReq req) {
        TAssetCategoryCondition categoryCondition = new TAssetCategoryCondition();
        categoryCondition.setEnabled(req.getEnabled());
        categoryCondition.setBusinessType(req.getBusinessType());
        return tAssetCategoryService.getAllCateGoryList(categoryCondition);
    }

    @Override
    public PageInfo getCateGoryList(TAssetCategoryReq req) {
        TAssetCategoryCondition categoryCondition = new TAssetCategoryCondition();
        categoryCondition.setBusinessType(req.getBusinessType());
        categoryCondition.setEnabled(req.getEnabled());
        return tAssetCategoryService.getCateGoryList(req.getPage(), req.getPerPage(), categoryCondition);
    }

    @Override
    public Object addCateGory(TAssetCategoryReq req) {
        return tAssetCategoryService.addCateGory(copyTAssetCategory(req));
    }

    @Override
    public Object updateCateGory(TAssetCategoryReq req) {
        return tAssetCategoryService.updateCateGory(copyTAssetCategory(req));
    }

    @Override
    public PageInfo getAllTempByCateGoryId(TAssetCategoryReq req) {
        return tAssetCategoryService.getAllTempByCateGoryId(req.getPage(), req.getPerPage(), req.getCategoryId());
    }

    @Override
    public Object categoryOptions(TAssetCategoryOptionReq req) {
        return tAssetCategoryService.categoryOptions(req.getAssetCategoryId());
    }

    @Override
    public String getCategoryOptionNameByCategoryId(Integer categoryId) {
        TAssetTemplateCategory templateCategoryById = assetTemplateCategoryService.getAssetTemplateCategoryById(categoryId);
        if (templateCategoryById != null) {
            TAssetCategory categoryById = tAssetCategoryService.getCategoryById(templateCategoryById.getCategoryId());
            return categoryById.getName();
        }
        return null;
    }

    private TAssetCategory copyTAssetCategory(TAssetCategoryReq req) {
        TAssetCategory category = new TAssetCategory();
        BeanUtils.copyProperties(req, category);
        return category;
    }
}
