package com._360pai.core.provider.asset;

import com._360pai.core.facade.asset.TAssetTemplateCategoryFacade;
import com._360pai.core.facade.asset.req.TAssetFieldItemReq;
import com._360pai.core.facade.asset.req.TAssetTemplateCategoryReq;
import com._360pai.core.model.asset.TAssetFieldItem;
import com._360pai.core.model.asset.TAssetTemplateCategory;
import com._360pai.core.service.asset.TAssetFieldItemService;
import com._360pai.core.service.asset.TAssetTemplateCategoryService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: TAssetTemplateCategoryProviderF
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/31 13:07
 */
@Component
@Service(version = "1.0.0")
public class TAssetTemplateCategoryProvider implements TAssetTemplateCategoryFacade {
    @Autowired
    private TAssetTemplateCategoryService tAssetTemplateCategoryService;
    @Autowired
    private TAssetFieldItemService tAssetFieldItemService;

    @Override
    public PageInfo TemplateCategoryList(TAssetTemplateCategoryReq req) {
        return tAssetTemplateCategoryService.TemplateCategoryList(req.getPage(), req.getPerPage());
    }

    @Override
    public int editTemplateCategory(TAssetTemplateCategoryReq req) {
        return tAssetTemplateCategoryService.editTemplateCategory(copyTemplateCategory(req));
    }

    @Override
    public int addTemplateGroup(TAssetTemplateCategoryReq req) {
        return tAssetTemplateCategoryService.addTemplateGroup(req.getId(), req.getGroupIds());
    }

    @Override
    public Object getTemplateGroup(Integer tempId) {
        return tAssetTemplateCategoryService.getTemplateGroup(tempId);
    }

    @Override
    public Object setTemplateGroupField(TAssetFieldItemReq req) {
        TAssetFieldItem tAssetFieldItem = new TAssetFieldItem();
        BeanUtils.copyProperties(req, tAssetFieldItem);
        return tAssetTemplateCategoryService.setTemplateGroupField(tAssetFieldItem);
    }

    @Override
    public Object getTemplate(TAssetTemplateCategoryReq req) {
        return tAssetTemplateCategoryService.getTemplate(req.getCategoryId());
    }

    @Override
    public Object getTemplateCopy(TAssetTemplateCategoryReq req) {
        return tAssetTemplateCategoryService.getTemplateCopy(req);
    }

    @Override
    public Object getTemplateParty(Integer categoryId, Integer partyId) {
        return tAssetTemplateCategoryService.getTemplateParty(categoryId,partyId);
    }

    @Override
    public Object getTemplateGroupField(TAssetFieldItemReq req) {
        TAssetFieldItem tAssetFieldItem = new TAssetFieldItem();
        BeanUtils.copyProperties(req, tAssetFieldItem);
        return tAssetFieldItemService.getTemplateGroupField(tAssetFieldItem);
    }

    @Override
    public int deleteTemplateGroupField(TAssetFieldItemReq req) {
        return tAssetFieldItemService.deleteTemplateGroupField(req.getId());
    }

    @Override
    public int addTemplateCategory(TAssetTemplateCategoryReq req) {
        return tAssetTemplateCategoryService.addTemplateCategoryList(copyTemplateCategory(req));
    }

    private TAssetTemplateCategory copyTemplateCategory(TAssetTemplateCategoryReq req) {
        TAssetTemplateCategory tAssetTemplateCategory = new TAssetTemplateCategory();
        BeanUtils.copyProperties(req, tAssetTemplateCategory);
        return tAssetTemplateCategory;
    }
}
