package com._360pai.core.facade.asset;

import com._360pai.core.facade.asset.req.AssetTemplateFieldOptionReq;
import com._360pai.core.facade.asset.req.AssetTemplateFieldReq;
import com.github.pagehelper.PageInfo;

/**
 * @author zxiao
 * @Title: AssetTemplateFieldFacade
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/17 9:47
 */
public interface AssetTemplateFieldFacade {

    PageInfo findAssetTemplateFieldList(int page, int prePaeg);

    int addAssetTemplateField(AssetTemplateFieldReq param);

    int editAssetTemplateField(AssetTemplateFieldReq param);

    int deleteAssetTemplateField(AssetTemplateFieldReq param);

    Object detailAssetTemplateField(AssetTemplateFieldReq param);

    int addAssetTemplateFieldOption(AssetTemplateFieldOptionReq param);

    int editAssetTemplateFieldOption(AssetTemplateFieldOptionReq param);

    int editWeightAssetTemplateFieldOption(AssetTemplateFieldOptionReq param);

    int deleteAssetTemplateFieldOption(AssetTemplateFieldOptionReq param);
}
