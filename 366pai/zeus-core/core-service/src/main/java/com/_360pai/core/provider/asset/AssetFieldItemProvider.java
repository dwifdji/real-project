package com._360pai.core.provider.asset;

import com._360pai.core.facade.asset.AssetFieldItemFacade;
import com._360pai.core.facade.asset.req.AssetFieldItemReq;
import com._360pai.core.model.asset.AssetFieldItem;
import com._360pai.core.service.asset.AssetFieldItemService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: AssetFieldItemProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/16 17:20
 */
@Component
@Service(version = "1.0.0")
public class AssetFieldItemProvider implements AssetFieldItemFacade {

    @Autowired
    private AssetFieldItemService assetFieldItemService;

    @Override
    public int addAssetFieldItem(AssetFieldItemReq req) {
        AssetFieldItem item = new AssetFieldItem();
        BeanUtils.copyProperties(req, item);
        return assetFieldItemService.insert(item);
    }

    @Override
    public int deleteItem(AssetFieldItemReq req) {
        AssetFieldItem item = new AssetFieldItem();
        BeanUtils.copyProperties(req,item);
        return assetFieldItemService.delete(item);
    }

    @Override
    public Object updateItem(AssetFieldItemReq req) {
        AssetFieldItem item = new AssetFieldItem();
        BeanUtils.copyProperties(req,item);
        return assetFieldItemService.update(item);
    }
}
