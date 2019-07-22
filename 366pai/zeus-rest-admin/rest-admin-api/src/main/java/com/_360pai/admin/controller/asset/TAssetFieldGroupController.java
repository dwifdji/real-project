package com._360pai.admin.controller.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.asset.TAssetFieldGroupFacade;
import com._360pai.core.facade.asset.req.TAssetFieldGroupReq;
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
 * @Title: TAssetFieldGroupController
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/4 15:36
 */
@RestController
public class TAssetFieldGroupController {
    @Reference(version = "1.0.0")
    private TAssetFieldGroupFacade tAssetFieldGroupFacade;

    private static Logger logger = LoggerFactory.getLogger(AssetFieldGroupController.class);

    @Reference(version = "1.0.0")
    TAssetFieldGroupFacade tassetFieldGroupFacade;

    /**
     * 功能描述: 更新债权字段分组
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/16 16:46
     */
    @PostMapping(value = "/admin/t/edit_asset_field_group")
    public ResponseModel updateAssetFieldGroup(@RequestBody TAssetFieldGroupReq req) {
        logger.info("updateTAssetFieldGroup请求参数================{}", req);
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        tassetFieldGroupFacade.updateAssetFieldGroup(req);
        return model;
    }

    /**
     * 功能描述: 新增债权字段分组
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/16 16:46
     */
    @PostMapping(value = "/admin/t/add_asset_field_group")
    public ResponseModel insertAssetFieldGroup(@RequestBody TAssetFieldGroupReq req) {
        logger.info("updateTAssetFieldGroup请求参数================{}", req);
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        tassetFieldGroupFacade.insertAssetFieldGroup(req);
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
    @GetMapping(value = "/admin/t/asset_field_groups")
    public ResponseModel selectAllAssetFieldGroupList(TAssetFieldGroupReq req) {
        logger.info("selectAllTAssetFieldGroupList请求参数================{}", req);
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        PageInfo pageInfo = tassetFieldGroupFacade.selectAllAssetFieldGroupList(req);
        logger.info("selectAllTAssetFieldGroupList请求结果================{}", pageInfo);
        model.setContent(pageInfo);
        return model;
    }
}
