package com._360pai.core.facade.asset;

import com._360pai.arch.common.exception.BaseBusinessException;
import com._360pai.core.facade.asset.req.TAssetFieldItemReq;
import com._360pai.core.facade.asset.req.TAssetTemplateCategoryReq;
import com.github.pagehelper.PageInfo;

public interface TAssetTemplateCategoryFacade {

    PageInfo TemplateCategoryList(TAssetTemplateCategoryReq req);

    int addTemplateCategory(TAssetTemplateCategoryReq req);

    int editTemplateCategory(TAssetTemplateCategoryReq req);

    int addTemplateGroup(TAssetTemplateCategoryReq req);

    Object getTemplateGroup(Integer tempId);

    Object setTemplateGroupField(TAssetFieldItemReq req);

    Object getTemplate(TAssetTemplateCategoryReq req);

    Object getTemplateCopy(TAssetTemplateCategoryReq req) throws BaseBusinessException;

    Object getTemplateGroupField(TAssetFieldItemReq req);

    Object getTemplateParty(Integer categoryId, Integer partyId);

    int deleteTemplateGroupField(TAssetFieldItemReq req);
}
