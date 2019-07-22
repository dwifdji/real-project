package com._360pai.admin.controller.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.asset.AssetPropertiesFacade;
import com._360pai.core.facade.asset.req.AssetPropertyReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: AssetPropertiesController
 * @ProjectName zeus-rest-admin
 * @Description:
 * @date 2018/8/16 18:35
 */
@RestController
public class AssetPropertyController {

    private static Logger logger = LoggerFactory.getLogger(AssetPropertyController.class);

    @Reference(version = "1.0.0")
    private AssetPropertiesFacade assetPropertiesFacade;

    /**
     * 功能描述: 获取标的属性列表
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/16 18:50
     */
    @RequiresPermissions("yygl_yylxgl:rmpplx_list")
    @GetMapping(value = "/admin/asset_properties")
    public ResponseModel getPropertiesList(AssetPropertyReq req) {
        logger.info("getPropertiesList请求参数============={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        PageInfo pageInfo = assetPropertiesFacade.getPropertiesList(req);
        logger.info("getPropertiesList请求结果============{}", JSON.toJSONString(pageInfo));
        model.setContent(pageInfo);
        return model;
    }

    /**
     * 功能描述: 增加标的属性
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/17 9:41
     */
    @PostMapping(value = "/admin/add_asset_property")
    public ResponseModel addAssetProperties(@RequestBody AssetPropertyReq req) {
        logger.info("addAssetProperties请求参数============={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        assetPropertiesFacade.addAssetProperties(req);
        return model;
    }

    /**
     * 功能描述: 更新标的属性
     *
     * @return:
     * @auther: zxiao
     * @date: 2018/8/17 9:40
     */
    @PostMapping(value = "/admin/edit_asset_property")
    public ResponseModel editAssetProperties(@RequestBody AssetPropertyReq req) {
        logger.info("addAssetProperties请求参数============={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        assetPropertiesFacade.editAssetProperties(req);
        return model;
    }

}
