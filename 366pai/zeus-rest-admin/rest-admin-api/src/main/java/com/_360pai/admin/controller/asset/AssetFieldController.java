package com._360pai.admin.controller.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.asset.AssetFieldFacade;
import com._360pai.core.facade.asset.req.AssetFieldReq;
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
 * @Title: AssetFieldController
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/16 16:12
 */
@RestController
public class AssetFieldController {

    private static Logger logger = LoggerFactory.getLogger(AssetFieldController.class);

    @Reference(version = "1.0.0")
    private AssetFieldFacade assetFieldFacade;

    /**
     * 功能描述:  新建债权描述字段
     *
     * @return:
     * @auther: zxiao
     * @date: 2018/8/16 16:24
     */
    @PostMapping(value = "/admin/add_asset_field")
    public ResponseModel addAssetField(@RequestBody AssetFieldReq req) {
        logger.info("AssetFieldController的addAssetField方法请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        assetFieldFacade.insert(req);
        return model;
    }

    /**
     * 功能描述: 更新债权描述字段
     *
     * @return:
     * @auther: zxiao
     * @date: 2018/8/16 16:26
     */
    @PostMapping(value = "/admin/edit_asset_field")
    public ResponseModel editAssetField(@RequestBody AssetFieldReq req) {
        logger.info("AssetFieldController的edit_asset_field方法请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        assetFieldFacade.updateAssetField(req);
        return model;
    }

    /**
     * 功能描述: 获取债权描述字段列表
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/16 18:29
     */
    @GetMapping(value = "/admin/search_asset_fields")
    public ResponseModel searchAssetFields(AssetFieldReq req) {
        logger.info("AssetFieldController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        PageInfo pageInfo = assetFieldFacade.searchAssetFields(req);
        logger.info("AssetFieldController请求结果======={}", JSON.toJSONString(req));
        model.setContent(pageInfo);
        return model;
    }
}
