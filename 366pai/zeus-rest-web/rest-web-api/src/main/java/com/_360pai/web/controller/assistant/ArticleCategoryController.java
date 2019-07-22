package com._360pai.web.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.ArticleCategoryFacade;
import com._360pai.core.facade.assistant.req.ArticleCategoryReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: ArticleCategoryController 首页帮助中心
 * @ProjectName zeus-rest-web
 * @Description:
 * @date 2018/8/20 18:42
 */
@RestController
public class ArticleCategoryController {

    @Reference(version = "1.0.0")
    private ArticleCategoryFacade articleCategoryFacade;

    @GetMapping(value = "/open/articleCategory/list")
    public ResponseModel selectArticleCategory(ArticleCategoryReq req) {
        //查询上线的
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        req.setStatus(ArticleCategoryReq.ONLINE);
        PageInfo pageInfo = articleCategoryFacade.selectArticleCategoryList(req);
        model.setContent(pageInfo);
        return model;
    }
}
