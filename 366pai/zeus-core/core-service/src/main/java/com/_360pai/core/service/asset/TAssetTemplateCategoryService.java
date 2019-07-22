package com._360pai.core.service.asset;

import com._360pai.core.facade.asset.req.TAssetTemplateCategoryReq;
import com._360pai.core.model.asset.TAssetFieldItem;
import com._360pai.core.model.asset.TAssetTemplateCategory;
import com.github.pagehelper.PageInfo;

public interface TAssetTemplateCategoryService{


    PageInfo TemplateCategoryList(int page, int perPage);

    int addTemplateCategoryList(TAssetTemplateCategory params);

    int editTemplateCategory(TAssetTemplateCategory params);

    int addTemplateGroup(Integer assetTemplateCategoryId, Integer[] groupIds);

    Object getTemplateGroup(Integer copyTemplateCategory);

    Object setTemplateGroupField(TAssetFieldItem tAssetFieldItem);

    Object getTemplate(Integer categoryId);

    Object getTemplateCopy(TAssetTemplateCategoryReq req);

    Object getTemplateParty(Integer categoryId, Integer partyId);

    TAssetTemplateCategory selectByTemplateId(Integer templateId);

    TAssetTemplateCategory getAssetTemplateCategoryById(Integer categoryId);
}