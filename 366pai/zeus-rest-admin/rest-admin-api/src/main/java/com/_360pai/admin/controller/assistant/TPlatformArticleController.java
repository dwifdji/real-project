package com._360pai.admin.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.TPlatformArticleTypeFacade;
import com._360pai.core.facade.assistant.req.TPlatformArticleReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: TPlatformArticleController
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/27 15:44
 */
@RestController
public class TPlatformArticleController {

    @Reference(version = "1.0.0")
    private TPlatformArticleTypeFacade tPlatformArticleTypeFacade;

    @GetMapping(value = "/admin/plate/article/list")
    public ResponseModel getArticles(TPlatformArticleReq req) {

        PageInfo pageInfo = tPlatformArticleTypeFacade.getArticles(req);
        return ResponseModel.succ(pageInfo);
    }

    @GetMapping(value = "/admin/plate/article/detail")
    public ResponseModel detailArticle(@RequestBody TPlatformArticleReq req) {
        Object pageInfo = tPlatformArticleTypeFacade.detailArticle(req);
        return ResponseModel.succ(pageInfo);
    }

    @PostMapping(value = "/admin/plate/article/add")
    public ResponseModel addArticle(@RequestBody TPlatformArticleReq req) {
        Assert.notNull(req.getArticleTypeId(), "文章类型ID不能为空");
        Assert.notNull(req.getArticleName(), "文章名称不能为空");
        Assert.notNull(req.getStatus(), "文章状态不能为空");
        Assert.notNull(req.getPublicAt(), "发布时间不能为空");
        int pageInfo = tPlatformArticleTypeFacade.addArticle(req);
        return ResponseModel.succ(pageInfo);
    }

    @PostMapping(value = "/admin/plate/article/edit")
    public ResponseModel editArticle(@RequestBody TPlatformArticleReq req) {
        Assert.notNull(req.getId(), "文章ID不能为空");
        Assert.notNull(req.getArticleTypeId(), "文章类型ID不能为空");
        Assert.notNull(req.getArticleName(), "文章名称不能为空");
        Assert.notNull(req.getStatus(), "文章状态不能为空");
        Assert.notNull(req.getPublicAt(), "发布时间不能为空");
        int pageInfo = tPlatformArticleTypeFacade.editArticle(req);
        return ResponseModel.succ(pageInfo);
    }

    @PostMapping(value = "/admin/plate/article/delete")
    public ResponseModel deleteArticle(@RequestBody TPlatformArticleReq req) {
        Assert.notNull(req.getId(), "文章ID不能为空");
        int pageInfo = tPlatformArticleTypeFacade.deleteArticle(req);
        return ResponseModel.succ(pageInfo);
    }

}
