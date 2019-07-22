package com._360pai.admin.controller.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.asset.AssetCategoryFacade;
import com._360pai.core.facade.asset.req.AssetCategoryReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: AssetCategoryController
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/16 13:17
 */
@RestController
public class AssetCategoryController {

    private static Logger logger = LoggerFactory.getLogger(AssetCategoryController.class);

    @Reference(version = "1.0.0")
    AssetCategoryFacade assetCategoryFacade;

    /**
     * 功能描述:  获取模板列表
     *
     * @return:
     * @auther: zxiao
     * @date: 2018/8/16 14:24
     */
    @GetMapping(value = "/admin/asset_categories")
    public ResponseModel getAll(AssetCategoryReq req) {
        logger.info("AssetCategoryController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        PageInfo assetCategoryList = assetCategoryFacade.findAssetCategoryList(req);
        logger.info("AssetCategoryController请求结果======={}", JSON.toJSONString(assetCategoryList));
        model.setContent(assetCategoryList);
        return model;
    }

    /**
     * 功能描述:  添加模板
     *
     * @return:
     * @auther: zxiao
     * @date: 2018/8/16 14:24
     */
    @PostMapping(value = "/admin/add_asset_category")
    public ResponseModel addCategory(@RequestBody AssetCategoryReq req) {
        logger.info("AssetCategoryController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        model.setContent(assetCategoryFacade.addCategory(req));
        return model;
    }

    /**
     * 功能描述: 修改模板
     *
     * @return:
     * @auther: zxiao
     * @date: 2018/8/16 14:24
     */
    @PostMapping(value = "/admin/edit_asset_category")
    public ResponseModel edit(@RequestBody AssetCategoryReq req) {
        logger.info("AssetCategoryController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        model.setContent(assetCategoryFacade.updateCategory(req));
        return model;
    }

    /**
     * 功能描述: 获取模板的筛选器列表 维护模字段
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/16 15:07
     */
    @GetMapping(value = "/admin/asset_category/field_items")
    public ResponseModel getFieldItems(AssetCategoryReq req) {
        logger.info("AssetCategoryController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        Object fieldItems = assetCategoryFacade.getFieldItems(req);
        logger.info("AssetCategoryController请求结果======={}", JSON.toJSONString(fieldItems));
        model.setContent(fieldItems);
        return model;
    }


}
