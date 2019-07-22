package com._360pai.admin.controller.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.asset.TAssetFieldFilterFacade;
import com._360pai.core.facade.asset.req.TAssetFieldFilterOptionItemReq;
import com._360pai.core.facade.asset.req.TAssetFieldFilterOptionReq;
import com._360pai.core.facade.asset.req.TAssetFieldFilterReq;
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
 * @Title: TAssetFieldFilterController
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/13 18:42
 */
@RestController
public class TAssetFieldFilterController {

    private static Logger logger = LoggerFactory.getLogger(TAssetFieldFilterController.class);
    @Reference(version = "1.0.0")
    private TAssetFieldFilterFacade tAssetFieldFilterFacade;

    /**
     * 功能描述:
     *
     * @param: 所有一级筛选器
     * @return:
     * @auther: zxiao
     * @date: 2018/8/17 10:14
     */
    @GetMapping(value = "/admin/asset_template_fields")
    public ResponseModel findAssetTemplateField(TAssetFieldFilterReq req) {
        logger.info("TAssetFieldFilterController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        PageInfo pageInfo = tAssetFieldFilterFacade.findAssetTemplateFieldList(req.getPage(), req.getPerPage());
        logger.info("TAssetFieldFilterController请求结果======={}", JSON.toJSONString(pageInfo));
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
    @GetMapping(value = "/admin/detail_asset_template_field")
    public ResponseModel detail(TAssetFieldFilterReq req) {
        logger.info("TAssetFieldFilterController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        Assert.notNull(req.getId(), "id不能为空");
        Object object = tAssetFieldFilterFacade.detailAssetTemplateField(req);
        logger.info("TAssetFieldFilterController请求结果======={}", JSON.toJSONString(object));
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
    @PostMapping(value = "/admin/add_asset_template_field")
    public ResponseModel add(@RequestBody TAssetFieldFilterReq req) {
        logger.info("TAssetFieldFilterController请求参数======={}", JSON.toJSONString(req));
        Assert.notNull(req.getName(), "一级筛选器名称不能为空");
        Assert.notNull(req.getExtensible(), "一级筛选器是否可扩展不能为空");
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        int i = tAssetFieldFilterFacade.addAssetTemplateField(req);
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
    @PostMapping(value = "/admin/edit_asset_template_field")
    public ResponseModel edit(@RequestBody TAssetFieldFilterReq req) {
        logger.info("TAssetFieldFilterController请求参数======={}", JSON.toJSONString(req));
        Assert.notNull(req.getId(), "id不能为空");
        Assert.notNull(req.getName(), "名称不能为空");
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        int i = tAssetFieldFilterFacade.editAssetTemplateField(req);
        if (i <= 0) {
            return ResponseModel.fail();
        }
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
    @PostMapping(value = "/admin/add_asset_template_field_option")
    public ResponseModel addOptions(@RequestBody TAssetFieldFilterOptionReq req) {
        logger.info("TAssetFieldFilterController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        int i = tAssetFieldFilterFacade.addAssetTemplateFieldOption(req);
        if (i <= 0) {
            return ResponseModel.fail();
        }
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
    @PostMapping(value = "/admin/edit_asset_template_field_option")
    public ResponseModel editOptions(@RequestBody TAssetFieldFilterOptionReq req) {
        logger.info("TAssetFieldFilterController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        int i = tAssetFieldFilterFacade.editAssetTemplateFieldOption(req);
        if (i <= 0) {
            return ResponseModel.fail();
        }
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
    @PostMapping(value = "/admin/editWeight_asset_template_field_option")
    public ResponseModel editWeightOptions(@RequestBody TAssetFieldFilterOptionReq req) {
        logger.info("TAssetFieldFilterController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        int i = tAssetFieldFilterFacade.editWeightAssetTemplateFieldOption(req);
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
    @PostMapping(value = "/admin/delete_asset_template_field_option")
    public ResponseModel deleteOptions(@RequestBody TAssetFieldFilterOptionReq req) {
        logger.info("TAssetFieldFilterController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        int i = tAssetFieldFilterFacade.deleteAssetTemplateFieldOption(req);
        return model;
    }

    /**
     * 功能描述: 添加模板三级筛选选项
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/17 12:03
     */
    @PostMapping(value = "/admin/add_asset_template_field_option_item")
    public ResponseModel addOptionItem(@RequestBody TAssetFieldFilterOptionItemReq req) {
        logger.info("TAssetFieldFilterController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        int i = tAssetFieldFilterFacade.addAssetTemplateFieldOptionItem(req);
        if (i <= 0) {
            return ResponseModel.fail();
        }
        return model;
    }

    /**
     * 功能描述: 修改模板三级筛选选项
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/17 12:03
     */
    @PostMapping(value = "/admin/edit_asset_template_field_option_item")
    public ResponseModel editOptionItem(@RequestBody TAssetFieldFilterOptionItemReq req) {
        logger.info("TAssetFieldFilterController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        int i = tAssetFieldFilterFacade.editAssetTemplateFieldOptionItem(req);
        if (i <= 0) {
            return ResponseModel.fail();
        }
        return model;
    }

    /**
     * 功能描述: 删除模板三级筛选选项
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/17 12:03
     */
    @PostMapping(value = "/admin/delete_asset_template_field_option_item")
    public ResponseModel deleteOptionItem(@RequestBody TAssetFieldFilterOptionItemReq req) {
        logger.info("TAssetFieldFilterController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        int i = tAssetFieldFilterFacade.deleteAssetTemplateFieldOptionItem(req);
        if (i <= 0) {
            return ResponseModel.fail();
        }
        return model;
    }

    /**
     * 功能描述: 二级筛选详情
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/17 12:03
     */
    @PostMapping(value = "/admin/detail_asset_template_field_option_item")
    public ResponseModel detailOptionItem(@RequestBody TAssetFieldFilterOptionItemReq req) {
        logger.info("TAssetFieldFilterController请求参数======={}", JSON.toJSONString(req));
        Assert.notNull(req.getFilterId(), "一级筛选器ID不能为空");
        Assert.notNull(req.getFilterOptionId(), "二级筛选器ID不能为空");
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        Object object = tAssetFieldFilterFacade.detailAssetTemplateFieldOptionItem(req);
        model.setContent(object);
        return model;
    }
}
