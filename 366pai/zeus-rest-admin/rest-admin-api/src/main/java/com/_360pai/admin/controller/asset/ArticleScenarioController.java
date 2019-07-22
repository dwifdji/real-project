package com._360pai.admin.controller.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.ArticleScenarioFacade;
import com._360pai.core.facade.assistant.req.ArticleScenarioReq;
import com.alibaba.dubbo.config.annotation.Reference;
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
 * @Title: ArticleScenariosController 帮助场景设置
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/21 9:15
 */
@RestController
public class ArticleScenarioController {

    private static Logger logger = LoggerFactory.getLogger(ArticleScenarioController.class);

    @Reference(version = "1.0.0")
    private ArticleScenarioFacade articleScenarioFacade;

    /**
     * 功能描述: 帮助场景配置列表
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/21 9:45
     */
    @GetMapping(value = "/admin/article_scenarios/list")
    public ResponseModel selectArticleScenario(ArticleScenarioReq req) {
        ResponseModel responseModel = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        PageInfo pageInfo = articleScenarioFacade.selectArticleScenario(req);
        responseModel.setContent(pageInfo);
        return responseModel;
    }

    /**
     * 功能描述:  添加帮助场景配置列表
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/21 9:47
     */
    @PostMapping(value = "/admin/article_scenarios/add")
    public ResponseModel add(@RequestBody ArticleScenarioReq req) {
        Assert.notNull(req.getName(), "名称不能为空");
        Assert.notNull(req.getArticleId(), "文章不能为空");
        Assert.notNull(req.getDesc(), "描述不能为空");
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        articleScenarioFacade.addArticleScenario(req);
        return model;
    }

    /**
     * 功能描述:  修改帮助场景配置列表
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/21 9:49
     */
    @PostMapping(value = "/admin/article_scenarios/edit")
    public ResponseModel edit(@RequestBody ArticleScenarioReq req) {
        logger.info("ArticleScenarioController");
        Assert.notNull(req.getId(), "id不能为空");
        Assert.notNull(req.getName(), "名称不能为空");
        Assert.notNull(req.getArticleId(), "文章不能为空");
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        articleScenarioFacade.editArticleScenario(req);
        return model;
    }
}
