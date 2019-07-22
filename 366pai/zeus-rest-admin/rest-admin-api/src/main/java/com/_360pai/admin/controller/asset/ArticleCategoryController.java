package com._360pai.admin.controller.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.ArticleCategoryFacade;
import com._360pai.core.facade.assistant.req.ArticleCategoryReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: ArticleCategoryController 分类列表
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/20 18:42
 */
@RestController
public class ArticleCategoryController {

    @Reference(version = "1.0.0")
    private ArticleCategoryFacade articleCategoryFacade;

    @RequiresPermissions("yygl_bzzxgl:wdfl_list")
    @GetMapping(value = "/admin/articleCategory/list")
    public ResponseModel selectArticleCategory(ArticleCategoryReq req) {
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        PageInfo pageInfo = articleCategoryFacade.selectArticleCategoryList(req);
        model.setContent(pageInfo);
        return model;
    }

    @PostMapping(value = "/admin/articleCategory/add")
    public ResponseModel addArticleCategory(@RequestBody ArticleCategoryReq params) {
        Assert.notNull(params.getName(), "名称不能为空");
        Assert.notNull(params.getStatus(), "状态不能为空");
        Assert.notNull(params.getImg(), "图片不能为空");
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        articleCategoryFacade.addArticleCategoryList(params);
        return model;
    }

    @PostMapping(value = "/admin/articleCategory/edit")
    public ResponseModel editArticleCategory(@RequestBody ArticleCategoryReq params) {
        Assert.notNull(params.getId(), "id不能为空");
        Assert.notNull(params.getName(), "名称不能为空");
        Assert.notNull(params.getStatus(), "状态不能为空");
        Assert.notNull(params.getImg(), "图片不能为空");
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        articleCategoryFacade.editArticleCategoryList(params);
        return model;
    }

    @PostMapping(value = "/admin/articleCategory/delete")
    public ResponseModel deleteArticleCategory(@RequestBody ArticleCategoryReq params) {
        Assert.notNull(params.getId(), "id不能为空");
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        articleCategoryFacade.deleteArticleCategoryList(params);
        return model;
    }

}
