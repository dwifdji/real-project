package com._360pai.admin.controller.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.asset.AssetFieldItemFacade;
import com._360pai.core.facade.asset.req.AssetFieldItemReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: AssetFieldItemController
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/16 17:18
 */
@RestController
public class AssetFieldItemController {

    private static Logger logger = LoggerFactory.getLogger(AssetFieldItemController.class);

    @Reference(version = "1.0.0")
    private AssetFieldItemFacade assetFieldItemFacade;

    /**
     * 功能描述:  债券模板添加字段
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/16 17:40
     */
    @PostMapping(value = "/admin/add_asset_field_item")
    public ResponseModel addAssetFieldItem(@RequestBody AssetFieldItemReq req) {
        logger.info("AssetFieldItemController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        assetFieldItemFacade.addAssetFieldItem(req);
        return model;
    }

    /**
     * 功能描述: 债券模板字段删除
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/16 17:58
     */
    @PostMapping(value = "/admin/delete_asset_field_item")
    public ResponseModel deleteItem(@RequestBody AssetFieldItemReq req) {
        logger.info("AssetFieldItemController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        model.setContent(assetFieldItemFacade.deleteItem(req));
        return model;
    }

    /**
     * 功能描述: 债券模板字段修改
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/16 18:02
     */
    @PostMapping(value = "/admin/edit_asset_field_item")
    public ResponseModel updateItem(@RequestBody AssetFieldItemReq req) {
        logger.info("AssetFieldItemController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        model.setContent(assetFieldItemFacade.updateItem(req));
        return model;
    }

}
