package com._360pai.core.provider.asset;

import com._360pai.core.facade.asset.AssetTemplateFieldFacade;
import com._360pai.core.facade.asset.req.AssetTemplateFieldOptionReq;
import com._360pai.core.facade.asset.req.AssetTemplateFieldReq;
import com._360pai.core.model.asset.AssetTemplateField;
import com._360pai.core.model.asset.AssetTemplateFieldOption;
import com._360pai.core.service.asset.AssetTemplateFieldOptionService;
import com._360pai.core.service.asset.AssetTemplateFieldService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: AssetTemplateFieldProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/17 9:48
 */
@Component
@Service(version = "1.0.0")
public class AssetTemplateFieldProvider implements AssetTemplateFieldFacade {

    @Autowired
    private AssetTemplateFieldService assetTemplateFieldService;
    @Autowired
    private AssetTemplateFieldOptionService assetTemplateFieldOptionService;

    @Override
    public PageInfo findAssetTemplateFieldList(int page, int prePaeg) {
        return assetTemplateFieldService.findAssetTemplateFieldList(page, prePaeg);
    }

    @Override
    public int addAssetTemplateField(AssetTemplateFieldReq param) {
        return assetTemplateFieldService.addAssetTemplateField(copyAssetTemplateField(param));
    }

    @Override
    public int editAssetTemplateField(AssetTemplateFieldReq param) {
        return assetTemplateFieldService.editAssetTemplateField(copyAssetTemplateField(param));
    }

    @Override
    public int deleteAssetTemplateField(AssetTemplateFieldReq param) {
        return assetTemplateFieldService.deleteAssetTemplateField(copyAssetTemplateField(param));
    }

    @Override
    public Object detailAssetTemplateField(AssetTemplateFieldReq param) {
        return assetTemplateFieldService.detailAssetTemplateField(copyAssetTemplateField(param));
    }

    @Override
    public int addAssetTemplateFieldOption(AssetTemplateFieldOptionReq param) {
        return assetTemplateFieldOptionService.addAssetTemplateFieldOption(copyAssetTemplateFieldOption(param));
    }

    @Override
    public int editAssetTemplateFieldOption(AssetTemplateFieldOptionReq param) {
        return assetTemplateFieldOptionService.editAssetTemplateFieldOption(copyAssetTemplateFieldOption(param));
    }

    @Override
    public int editWeightAssetTemplateFieldOption(AssetTemplateFieldOptionReq param) {
        return assetTemplateFieldOptionService.editWeightAssetTemplateFieldOption(copyAssetTemplateFieldOption(param));
    }

    @Override
    public int deleteAssetTemplateFieldOption(AssetTemplateFieldOptionReq param) {
        return assetTemplateFieldOptionService.deleteAssetTemplateFieldOption(copyAssetTemplateFieldOption(param));
    }

    private AssetTemplateField copyAssetTemplateField(AssetTemplateFieldReq req) {
        AssetTemplateField assetTemplateField = new AssetTemplateField();
        BeanUtils.copyProperties(req, assetTemplateField);
        return assetTemplateField;
    }

    private AssetTemplateFieldOption copyAssetTemplateFieldOption(AssetTemplateFieldOptionReq req) {
        AssetTemplateFieldOption assetTemplateFieldOption = new AssetTemplateFieldOption();
        BeanUtils.copyProperties(req, assetTemplateFieldOption);
        return assetTemplateFieldOption;
    }
}
