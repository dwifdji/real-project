package com._360pai.admin.controller.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.asset.AssetTemplateFieldFacade;
import com._360pai.core.facade.asset.req.AssetTemplateFieldOptionReq;
import com._360pai.core.facade.asset.req.AssetTemplateFieldReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: AssetTemplateFieldController
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/17 9:44
 */
@RestController
public class AssetTemplateFieldController {

    private static Logger logger = LoggerFactory.getLogger(AssetTemplateFieldController.class);

    @Reference(version = "1.0.0")
    private AssetTemplateFieldFacade assetTemplateFieldFacade;

    /**
     * 功能描述:
     *
     * @param: 所有模板筛选器
     * @return:
     * @auther: zxiao
     * @date: 2018/8/17 10:14
     */
    @GetMapping(value = "/admin/t/asset_template_fields")
    public ResponseModel findAssetTemplateField(AssetTemplateFieldReq req) {
        logger.info("AssetTemplateFieldController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        PageInfo pageInfo = assetTemplateFieldFacade.findAssetTemplateFieldList(req.getPage(), req.getPerPage());
        logger.info("AssetTemplateFieldController请求结果======={}", JSON.toJSONString(pageInfo));
        model.setContent(pageInfo);
        return model;
    }

    /**
     * 功能描述:
     *
     * @param: 模板筛选器详情
     * @return:
     * @auther: zxiao
     * @date: 2018/8/17 10:14
     */
    @GetMapping(value = "/admin/t/detail_asset_template_field")
    public ResponseModel detail(AssetTemplateFieldReq req) {
        logger.info("AssetTemplateFieldController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        Object object = assetTemplateFieldFacade.detailAssetTemplateField(req);
        logger.info("AssetTemplateFieldController请求结果======={}", JSON.toJSONString(object));
        model.setContent(object);
        return model;
    }

    /**
     * 功能描述: 增加筛选器
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/17 10:38
     */
    @PostMapping(value = "/admin/t/add_asset_template_field")
    public ResponseModel add(@RequestBody AssetTemplateFieldReq req) {
        logger.info("AssetTemplateFieldController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        assetTemplateFieldFacade.addAssetTemplateField(req);
        return model;
    }

    /**
     * 功能描述: 更新筛选器
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/17 10:39
     */
    @PostMapping(value = "/admin/t/edit_asset_template_field")
    public ResponseModel edit(@RequestBody AssetTemplateFieldReq req) {
        logger.info("AssetTemplateFieldController请求参数======={}", JSON.toJSONString(req));
        Assert.notNull(req.getId(),"id不能为空");
        Assert.notNull(req.getName(),"名称不能为空");
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        assetTemplateFieldFacade.editAssetTemplateField(req);
        return model;
    }

    /**
     * 功能描述: 增加模板筛选字段
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/17 11:57
     */
    @PostMapping(value = "/admin/t/add_asset_template_field_option")
    public ResponseModel addOptions(@RequestBody AssetTemplateFieldOptionReq req) {
        logger.info("AssetTemplateFieldController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        assetTemplateFieldFacade.addAssetTemplateFieldOption(req);
        return model;
    }

    /**
     * 功能描述: 更新模板筛选选项名称
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/17 11:57
     */
    @PostMapping(value = "/admin/t/edit_asset_template_field_option")
    public ResponseModel editOptions(@RequestBody AssetTemplateFieldOptionReq req) {
        logger.info("AssetTemplateFieldController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        assetTemplateFieldFacade.editAssetTemplateFieldOption(req);
        return model;
    }

    /**
     * 功能描述: 更新模板筛选选项权重
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/17 11:57
     */
    @PostMapping(value = "/admin/t/editWeight_asset_template_field_option")
    public ResponseModel editWeightOptions(@RequestBody AssetTemplateFieldOptionReq req) {
        logger.info("AssetTemplateFieldController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        assetTemplateFieldFacade.editWeightAssetTemplateFieldOption(req);
        return model;
    }

    /**
     * 功能描述: 删除模板筛选选项
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/17 12:03
     */
    @PostMapping(value = "/admin/t/delete_asset_template_field_option")
    public ResponseModel deleteOptions(@RequestBody AssetTemplateFieldOptionReq req) {
        logger.info("AssetTemplateFieldController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        assetTemplateFieldFacade.deleteAssetTemplateFieldOption(req);
        return model;
    }
}
