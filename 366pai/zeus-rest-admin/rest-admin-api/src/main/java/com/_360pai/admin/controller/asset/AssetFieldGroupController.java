package com._360pai.admin.controller.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.asset.AssetFieldGroupFacade;
import com._360pai.core.facade.asset.req.AssetFieldGroupReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: AssetFieldGroupController
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/16 16:30
 */
@RestController
public class AssetFieldGroupController {

    private static Logger logger = LoggerFactory.getLogger(AssetFieldGroupController.class);

    @Reference(version = "1.0.0")
    AssetFieldGroupFacade assetFieldGroupFacade;

    /**
     * 功能描述: 更新债权字段分组
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/16 16:46
     */
    @PostMapping(value = "/admin/edit_asset_field_group")
    public ResponseModel updateAssetFieldGroup(@RequestBody AssetFieldGroupReq req) {
        logger.info("updateAssetFieldGroup请求参数================{}", req);
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        assetFieldGroupFacade.updateAssetFieldGroup(req);
        return model;
    }

    /**
     * 功能描述: 债券字段分组列表
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/16 16:57
     */
    @GetMapping(value = "/admin/asset_field_groups")
    public ResponseModel selectAllAssetFieldGroupList(AssetFieldGroupReq req) {
        logger.info("selectAllAssetFieldGroupList请求参数================{}", req);
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        PageInfo pageInfo = assetFieldGroupFacade.selectAllAssetFieldGroupList(req);
        logger.info("selectAllAssetFieldGroupList请求结果================{}", pageInfo);
        model.setContent(pageInfo);
        return model;
    }

}
