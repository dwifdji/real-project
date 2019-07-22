package com._360pai.web.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.core.filter.PropertyFilterFactory;
import com._360pai.core.facade.assistant.ArticleFacade;
import com._360pai.core.facade.assistant.PlatformArticleFacade;
import com._360pai.core.facade.assistant.req.ArticleReq;
import com._360pai.core.facade.assistant.req.TPlatformArticleReq;
import com._360pai.core.facade.assistant.req.TPlatformArticleTypeReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zxiao
 * @Title: ArticleController  首页帮助中心文章
 * @ProjectName zeus-rest-web
 * @Description:
 * @date 2018/8/22 9:11
 */
@RestController
public class ArticleController {

    @Reference(version = "1.0.0")
    private ArticleFacade articleFacade;
    @Reference(version = "1.0.0")
    private PlatformArticleFacade platformArticleFacade;

    /**
     * 功能描述: 文档列表
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 13:22
     */
    @GetMapping(value = "/open/articles")
    public ResponseModel selectArticle(ArticleReq req) {
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        req.setStatus(ArticleReq.ONLINE);
        PageInfo pageInfo = articleFacade.selectArticle(req);
        model.setContent(pageInfo);
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
    @GetMapping(value = "/open/articles/detail")
    public ResponseModel detailArticle(ArticleReq req) {
        Assert.notNull(req.getId(), "文章id不能为空");
        Map resp = (Map) articleFacade.detailArticle(req);
        if (resp.get("status").equals(ArticleReq.ONLINE))
            return ResponseModel.succ(resp);
        return ResponseModel.fail("文章不存在");
    }

    /**
     * 功能描述:  首页今日头条
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/25 10:56
     */
    @PostMapping(value = "/open/articles/headline")
    public ResponseModel headline(@RequestBody TPlatformArticleTypeReq req) {
        Object resp = platformArticleFacade.headline(req);
        return ResponseModel.succ(resp);
    }


    /**
     * 功能描述:  新闻中心今日头条
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/25 10:56
     */
    @GetMapping(value = "/open/articles/category")
    public ResponseModel category(TPlatformArticleTypeReq req) {
        Object resp = platformArticleFacade.category(req);
        Map map = new HashMap(16);
        map.put("list", resp);
        return ResponseModel.succ(map, PropertyFilterFactory.create(new String[]{"image", "type", "status"}));
    }

    /**
     * 功能描述:  新闻中心今日头条
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/25 10:56
     */
    @GetMapping(value = "/open/articles/categoryList")
    public ResponseModel categoryList(TPlatformArticleReq req) {
        PageInfo pageInfo = platformArticleFacade.categoryList(req);
        return ResponseModel.succ(pageInfo, PropertyFilterFactory.create(new String[]{"detail", "orderNum", "status"}));
    }

    /**
     * 功能描述:  新闻中心今日头条
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/25 10:56
     */
    @GetMapping(value = "/open/articles/category/detail")
    public ResponseModel detail(TPlatformArticleReq req) {
        Object pageInfo = platformArticleFacade.detail(req);
        return ResponseModel.succ(pageInfo);
    }

    /**
     * 新闻中心浏览量增加
     */
    @PostMapping(value = "/open/articles/category/detail/view")
    public ResponseModel view(@RequestBody TPlatformArticleReq req) {
        return platformArticleFacade.view(req);
    }
}
