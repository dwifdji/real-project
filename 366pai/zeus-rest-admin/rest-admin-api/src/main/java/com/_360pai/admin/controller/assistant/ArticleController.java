package com._360pai.admin.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.facade.assistant.ArticleFacade;
import com._360pai.core.facade.assistant.req.ArticleReq;
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
 * @Title: ArticleController  帮助文档
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/22 9:11
 */
@RestController
public class ArticleController {

    @Reference(version = "1.0.0")
    private ArticleFacade articleFacade;

    /**
     * 功能描述: 文档列表
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 13:22
     */
    @RequiresPermissions("yygl_bzzxgl:wd_list")
    @GetMapping(value = "/admin/articles/list")
    public ResponseModel selectArticle(ArticleReq req) {
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        PageInfo pageInfo = articleFacade.selectArticle(req);
        model.setContent(pageInfo);
        return model;
    }

    /**
     * 功能描述: 新增文档
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 13:22
     */
    @PostMapping(value = "/admin/articles/add")
    public ResponseModel addArticle(@RequestBody ArticleReq req) {
        Assert.notNull(req.getTitle(), "标题不能为空");
        Assert.notNull(req.getCategoryId(), "分类不能为空");
        Assert.notNull(req.getStatus(), "状态不能为空");
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        int i = articleFacade.addArticle(req);
        if (i < 0) {
            return ResponseModel.fail(ApiCallResult.FAILURE.getCode());
        }
        return model;
    }

    /**
     * 功能描述: 修改文档
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 13:22
     */
    @PostMapping(value = "/admin/articles/edit")
    public ResponseModel editArticle(@RequestBody ArticleReq req) {
        Assert.notNull(req.getId(), "id不能为空");
        Assert.notNull(req.getTitle(), "标题不能为空");
        Assert.notNull(req.getCategoryId(), "分类不能为空");
        Assert.notNull(req.getStatus(), "状态不能为空");
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        int i = articleFacade.editArticle(req);
        if (i < 0) {
            return ResponseModel.fail(ApiCallResult.FAILURE.getCode());
        }
        return model;
    }

    /**
     * 功能描述:  删除文档
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 13:23
     */
    @PostMapping(value = "/admin/articles/delete")
    public ResponseModel deleteArticle(@RequestBody ArticleReq req) {
        Assert.notNull(req.getId(), "id不能为空");
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        int i = articleFacade.deleteArticle(req);
        if (i < 0) {
            return ResponseModel.fail(ApiCallResult.FAILURE.getCode());
        }
        return model;
    }

    /**
     * 功能描述: 文档详情
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 13:28
     */
    @GetMapping(value = "/admin/articles/detail")
    public ResponseModel detailArticle(ArticleReq req) {
        Assert.notNull(req.getId(), "id不能为空");
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        Object o = articleFacade.detailArticle(req);
        model.setContent(o);
        return model;
    }

}
