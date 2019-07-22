package com._360pai.core.provider.asset;

import com._360pai.core.facade.asset.AssetCategoryFacade;
import com._360pai.core.facade.asset.req.AssetCategoryReq;
import com._360pai.core.model.asset.AssetCategory;
import com._360pai.core.service.asset.AssetCategoryService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: AssetCategoryProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/16 13:18
 */
@Component
@Service(version = "1.0.0")
public class AssetCategoryProvider implements AssetCategoryFacade {

    @Autowired
    private AssetCategoryService assetCategoryService;

    @Override
    public PageInfo findAssetCategoryList(AssetCategoryReq req) {
        return assetCategoryService.findAssetCategoryList(req.getPage(), req.getPerPage());
    }

    @Override
    public int addCategory(AssetCategoryReq req) {
        AssetCategory assetCategory = new AssetCategory();
        BeanUtils.copyProperties(req, assetCategory);
        return assetCategoryService.insert(assetCategory);
    }

    @Override
    public int updateCategory(AssetCategoryReq req) {
        AssetCategory assetCategory = new AssetCategory();
        BeanUtils.copyProperties(req, assetCategory);
        return assetCategoryService.updateCategory(assetCategory);
    }

    @Override
    public Object getFieldItems(AssetCategoryReq req) {
        return assetCategoryService.getFieldItems(req.getId());
    }
}
