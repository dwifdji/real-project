package com._360pai.admin.controller.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.asset.TAssetFieldFacade;
import com._360pai.core.facade.asset.req.TAssetFieldReq;
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
 * @Title: TAssetFieldController
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/4 16:01
 */
@RestController
public class TAssetFieldController {

    private static Logger logger = LoggerFactory.getLogger(TAssetFieldController.class);

    @Reference(version = "1.0.0")
    private TAssetFieldFacade tAssetFieldFacade;

    /**
     * 功能描述:  新建债权描述字段
     *
     * @return:
     * @auther: zxiao
     * @date: 2018/8/16 16:24
     */
    @PostMapping(value = "/admin/t/add_asset_field")
    public ResponseModel addAssetField(@RequestBody TAssetFieldReq req) {
        Assert.notNull(req.getLabel(),"中文名称不能为空");
        Assert.notNull(req.getName(),"字段名称不能为空");
        logger.info("AssetFieldController的addAssetField方法请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        tAssetFieldFacade.insert(req);
        return model;
    }

    /**
     * 功能描述: 更新债权描述字段
     *
     * @return:
     * @auther: zxiao
     * @date: 2018/8/16 16:26
     */
    @PostMapping(value = "/admin/t/edit_asset_field")
    public ResponseModel editAssetField(@RequestBody TAssetFieldReq req) {
        logger.info("AssetFieldController的edit_asset_field方法请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        tAssetFieldFacade.updateAssetField(req);
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
    @GetMapping(value = "/admin/t/search_asset_fields")
    public ResponseModel searchAssetFields(TAssetFieldReq req) {
        logger.info("TAssetFieldController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        PageInfo pageInfo = tAssetFieldFacade.searchAssetFields(req);
        logger.info("TAssetFieldController请求结果======={}", JSON.toJSONString(req));
        model.setContent(pageInfo);
        return model;
    }

    /**
     * 功能描述: 获取当前分组下的字段
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/16 18:29
     */
    @GetMapping(value = "/admin/t/find_group_asset_fields")
    public ResponseModel findAssetFieldsByGroupId(TAssetFieldReq req) {
        logger.info("TAssetFieldController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        Object object = tAssetFieldFacade.findAssetFieldsByGroupId(req);
        logger.info("TAssetFieldController请求结果======={}", JSON.toJSONString(req));
        model.setContent(object);
        return model;
    }

}
