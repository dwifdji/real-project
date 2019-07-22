package com._360pai.admin.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.TPlatformArticleTypeFacade;
import com._360pai.core.facade.assistant.req.TPlatformArticleTypeReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: TPlatformArticleType
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/27 15:44
 */
@RestController
public class TPlatformArticleTypeController {
    @Reference(version = "1.0.0")
    private TPlatformArticleTypeFacade tPlatformArticleTypeFacade;

    @RequiresPermissions("yygl_xwzxgl:xwfl_list")
    @GetMapping(value = "/admin/plate/types")
    public ResponseModel getTypes(TPlatformArticleTypeReq req) {
        PageInfo pageInfo = tPlatformArticleTypeFacade.getTypes(req);
        return ResponseModel.succ(pageInfo);
    }

    @PostMapping(value = "/admin/plate/add")
    public ResponseModel addType(@RequestBody TPlatformArticleTypeReq req) {
        int pageInfo = tPlatformArticleTypeFacade.addType(req);
        return ResponseModel.succ(pageInfo);
    }

    @PostMapping(value = "/admin/plate/edit")
    public ResponseModel editType(@RequestBody TPlatformArticleTypeReq req) {
        int pageInfo = tPlatformArticleTypeFacade.editType(req);
        return ResponseModel.succ(pageInfo);
    }

    @PostMapping(value = "/admin/plate/delete")
    public ResponseModel deleteType(@RequestBody TPlatformArticleTypeReq req) {
        int pageInfo = tPlatformArticleTypeFacade.deleteType(req);
        return ResponseModel.succ(pageInfo);
    }

    @GetMapping(value = "/admin/plate/detail")
    public ResponseModel detailType( TPlatformArticleTypeReq req) {
        Object pageInfo = tPlatformArticleTypeFacade.detailType(req);
        return ResponseModel.succ(pageInfo);
    }
}
