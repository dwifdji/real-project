package com._360pai.core.facade.asset;

import com._360pai.core.facade.asset.req.TAssetFieldFilterOptionItemReq;
import com._360pai.core.facade.asset.req.TAssetFieldFilterOptionReq;
import com._360pai.core.facade.asset.req.TAssetFieldFilterReq;
import com.github.pagehelper.PageInfo;

public interface TAssetFieldFilterFacade {
    PageInfo findAssetTemplateFieldList(int page, int perPage);

    Object detailAssetTemplateField(TAssetFieldFilterReq req);

    int addAssetTemplateField(TAssetFieldFilterReq req);

    int editAssetTemplateField(TAssetFieldFilterReq req);

    int addAssetTemplateFieldOption(TAssetFieldFilterOptionReq req);

    int editAssetTemplateFieldOption(TAssetFieldFilterOptionReq req);

    int editWeightAssetTemplateFieldOption(TAssetFieldFilterOptionReq req);

    int deleteAssetTemplateFieldOption(TAssetFieldFilterOptionReq req);

    int addAssetTemplateFieldOptionItem(TAssetFieldFilterOptionItemReq req);

    int editAssetTemplateFieldOptionItem(TAssetFieldFilterOptionItemReq req);

    int deleteAssetTemplateFieldOptionItem(TAssetFieldFilterOptionItemReq req);

    Object detailAssetTemplateFieldOptionItem(TAssetFieldFilterOptionItemReq req);
}
