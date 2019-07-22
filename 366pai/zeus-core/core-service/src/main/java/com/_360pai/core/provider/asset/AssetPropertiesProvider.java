package com._360pai.core.provider.asset;

import com._360pai.core.condition.asset.AssetPropertyCondition;
import com._360pai.core.facade.asset.AssetPropertiesFacade;
import com._360pai.core.facade.asset.req.AssetPropertyReq;
import com._360pai.core.model.asset.AssetProperty;
import com._360pai.core.service.asset.AssetPropertyService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: AssetPropertiesProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/16 18:37
 */
@Component
@Service(version = "1.0.0")
public class AssetPropertiesProvider implements AssetPropertiesFacade {

    @Autowired
    private AssetPropertyService assetPropertyService;

    @Override
    public PageInfo getPropertiesList(AssetPropertyReq req) {
        AssetProperty assetProperty = new AssetProperty();
        BeanUtils.copyProperties(req, assetProperty);
        //属性的类型
        AssetPropertyCondition condition = new AssetPropertyCondition();
        condition.setType(req.getType());
        return assetPropertyService.getPropertiesList(req.getPage(), req.getPerPage(),condition);
    }

    @Override
    public Object getProperties() {
        return assetPropertyService.getProperties();
    }

    @Override
    public int addAssetProperties(AssetPropertyReq req) {
        return assetPropertyService.addAssetProperties(copy(req));
    }

    @Override
    public int editAssetProperties(AssetPropertyReq req) {
        return assetPropertyService.editAssetProperties(copy(req));
    }

    private AssetProperty copy(AssetPropertyReq req) {
        AssetProperty assetProperty = new AssetProperty();
        BeanUtils.copyProperties(req, assetProperty);
        return assetProperty;
    }
}
