package com._360pai.admin.controller.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.asset.AssetCategoryGroupFacade;
import com._360pai.core.facade.asset.req.AssetCategoryGroupReq;
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
 * @Title: AssetCategoryGroupController
 * @ProjectName zeus-parent
 * @Description: 债券类型controller
 * @date 2018/8/16 9:41
 */

@RestController
public class AssetCategoryGroupController {

    private static Logger logger = LoggerFactory.getLogger(AssetCategoryGroupController.class);

    @Reference(version = "1.0.0")
    AssetCategoryGroupFacade assetCategoryGroupFacade;

    /**
     * 功能描述: 不带分页的查询债券类型
     * 获取债权类型列表
     *
     * @param:
     * @return: ResponseModel
     * @auther:
     * @date: 2018/8/16
     */
    @GetMapping(value = "/admin/all_asset_category_groups")
    public ResponseModel getAllAssetCategoryGroups(AssetCategoryGroupReq req) {
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        Object cateGoryGroupList = assetCategoryGroupFacade.getAllCateGoryGroupList(req);
        logger.info("AssetCategoryGroupController请求结果======={}", JSON.toJSONString(cateGoryGroupList));
        model.setContent(cateGoryGroupList);
        return model;
    }

    /**
     * 功能描述: 分页的查询债券类型
     * 获取债权类型详情
     *
     * @param: req
     * @return: ResponseModel
     * @auther:
     * @date: 2018/8/16
     */
    @GetMapping(value = "/admin/asset_category_groups")
    public ResponseModel getAssetCategoryGroups(AssetCategoryGroupReq req) {
        logger.info("AssetCategoryGroupController请求参数======={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        PageInfo pageInfo = assetCategoryGroupFacade.getCateGoryGroupList(req);
        model.setContent(pageInfo);
        logger.info("请求结果======={}", JSON.toJSONString(pageInfo));
        return model;
    }

    /**
     * 功能描述: 新建债权类型
     *
     * @param: req
     * @return: ResponseModel
     * @auther: zxiao
     * @date: 2018/8/16 10:39
     */
    @PostMapping(value = "/admin/add_asset_category_group")
    public ResponseModel addCategoryGroup(@RequestBody AssetCategoryGroupReq req) {
        logger.info("新建债权类型请求参数==============={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        assetCategoryGroupFacade.addCateGoryGroup(req);
        return model;
    }

    /**
     * 功能描述: 更新债权类型
     *
     * @param: req
     * @return: ResponseModel
     * @auther: zxiao
     * @date: 2018/8/16 10:39
     */
    @PostMapping(value = "/admin/edit_asset_category_group")
    public ResponseModel editCategoryGroup(@RequestBody AssetCategoryGroupReq req) {
        logger.info("更新债权类型请求参数==============={}", JSON.toJSONString(req));
        Assert.notNull(req.getId(), "id不能为空");
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        assetCategoryGroupFacade.updateCateGoryGroup(req);
        return model;
    }

    /**
     * 功能描述: 获取债权类型中的模板筛选器
     *
     * @param: req
     * @return: ResponseModel
     * @auther: zxiao
     * @date: 2018/8/16 10:39
     */
    @GetMapping(value = "/admin/asset_category_group/findFields")
    public ResponseModel findFields(AssetCategoryGroupReq req) {
        logger.info("获取债权类型中的模板筛选器请求参数==============={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        model.setContent(assetCategoryGroupFacade.selectCateGoryGroupFields(req));
        return model;
    }

    /**
     * 功能描述: 给置顶债权类型添加筛选器id,该过程或清空选项
     *
     * @param: req
     * @return: ResponseModel
     * @auther: zxiao
     * @date: 2018/8/16 10:39
     */
    @PostMapping(value = "/admin/asset_category_groups/fields")
    public ResponseModel setFields(@RequestBody AssetCategoryGroupReq req) {
        logger.info("给置顶债权类型添加筛选器id请求参数==============={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        assetCategoryGroupFacade.editCateGoryGroupFields(req);
        return model;
    }

    /**
     * 功能描述: 列出某债权类型的选项结果
     *
     * @param: req
     * @return: ResponseModel
     * @auther: zxiao
     * @date: 2018/8/16 10:39
     */
    @GetMapping(value = "/admin/asset_category_groups/options_result")
    public ResponseModel searchCategoryResult(AssetCategoryGroupReq req) {
        logger.info("列出某债权类型的选项结果请求参数==============={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        PageInfo pageInfo = assetCategoryGroupFacade.searchResultByGroupId(req);
        model.setContent(pageInfo);
        return model;
    }

    /**
     * 功能描述:
     *
     * @param: 绑定债权类型
     * @return:
     * @auther: zxiao
     * @date: 2018/8/17 15:04
     */
    @PostMapping(value = "/admin/asset_template_options_result/bind")
    public ResponseModel bind(@RequestBody AssetCategoryGroupReq req) {
        logger.info("绑定债权类型请求参数==============={}", JSON.toJSONString(req));
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        assetCategoryGroupFacade.bind(req);
        return model;
    }
}
