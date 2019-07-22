package com._360pai.core.facade.asset;

import com._360pai.core.facade.asset.req.AssetPropertyReq;
import com.github.pagehelper.PageInfo;

/**
 * @author zxiao
 * @Title: AssetPropertiesFacade
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/16 18:36
 */
public interface AssetPropertiesFacade {
    PageInfo getPropertiesList(AssetPropertyReq req) ;

    Object getProperties() ;

    int addAssetProperties(AssetPropertyReq req) ;

    int editAssetProperties(AssetPropertyReq req) ;
}
