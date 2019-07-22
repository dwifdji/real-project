package com._360pai.core.provider.assistant;

import com._360pai.core.condition.assistant.ArticleCondition;
import com._360pai.core.facade.assistant.ArticleFacade;
import com._360pai.core.facade.assistant.req.ArticleReq;
import com._360pai.core.model.assistant.Article;
import com._360pai.core.service.assistant.ArticleService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: ArticleFacadeImpl
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/22 9:12
 */
@Component
@Service(version = "1.0.0")
public class ArticleProvider implements ArticleFacade {

    @Autowired
    private ArticleService articleService;

    @Override
    public PageInfo selectArticle(ArticleReq req) {
        ArticleCondition condition = new ArticleCondition();
        condition.setCategoryId(req.getCategoryId());
        condition.setStatus(req.getStatus());
        return articleService.selectArticle(req.getPage(), req.getPerPage(), condition);
    }

    @Override
    public int addArticle(ArticleReq req) {
        return articleService.addArticle(copyArticle(req));
    }

    @Override
    public int editArticle(ArticleReq req) {
        return articleService.editArticle(copyArticle(req));
    }

    @Override
    public int deleteArticle(ArticleReq req) {
        return articleService.deleteArticle(copyArticle(req));
    }

    @Override
    public Object detailArticle(ArticleReq req) {
        return articleService.detailArticle(copyArticle(req));
    }

    private Article copyArticle(ArticleReq req) {
        Article article = new Article();
        BeanUtils.copyProperties(req, article);
        return article;
    }
}
