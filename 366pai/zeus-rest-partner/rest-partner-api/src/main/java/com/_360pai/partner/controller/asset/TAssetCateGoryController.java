package com._360pai.partner.controller.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.core.filter.PropertyFilterFactory;
import com._360pai.core.facade.asset.TAssetCateGoryFacade;
import com._360pai.core.facade.asset.req.TAssetCategoryOptionReq;
import com._360pai.core.facade.asset.req.TAssetCategoryReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: TAssetCateGoryController
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/15 16:01
 */
@RestController
public class TAssetCateGoryController {

    @Reference(version = "1.0.0")
    private TAssetCateGoryFacade tAssetCateGoryFacade;

    @PostMapping(value = "/agency/categories")
    public ResponseModel getAllCategories(@RequestBody TAssetCategoryReq req) {
        req.setEnabled(true);
        Object allCateGoryList = tAssetCateGoryFacade.getAllCateGoryList(req);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", allCateGoryList);
        return ResponseModel.succ(jsonObject, PropertyFilterFactory.create(new String[]{"enabled", "businessType", "dealMode"}));
    }

    @PostMapping(value = "/agency/category/options")
    public ResponseModel categoryOptions(@RequestBody TAssetCategoryOptionReq req) {
        Assert.notNull(req.getAssetCategoryId(), "债券类型不能为空");
        Object allCateGoryList = tAssetCateGoryFacade.categoryOptions(req);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", allCateGoryList);
        return ResponseModel.succ(jsonObject, PropertyFilterFactory.create(new String[]{"enable"}));
    }
}
