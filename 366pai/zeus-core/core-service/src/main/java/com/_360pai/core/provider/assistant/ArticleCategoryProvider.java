package com._360pai.core.provider.assistant;

import com._360pai.core.condition.assistant.ArticleCategoryCondition;
import com._360pai.core.facade.assistant.ArticleCategoryFacade;
import com._360pai.core.facade.assistant.req.ArticleCategoryReq;
import com._360pai.core.model.assistant.ArticleCategory;
import com._360pai.core.service.assistant.ArticleCategoryService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: ArticleCategoryProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/20 18:44
 */
@Component
@Service(version = "1.0.0")
public class ArticleCategoryProvider implements ArticleCategoryFacade {

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Override
    public PageInfo selectArticleCategoryList(ArticleCategoryReq req) {
        ArticleCategoryCondition categoryCondition = new ArticleCategoryCondition();
        categoryCondition.setStatus(req.getStatus());
        return articleCategoryService.selectArticleCategoryList(req.getPage(), req.getPerPage(), categoryCondition);
    }

    @Override
    public int addArticleCategoryList(ArticleCategoryReq req) {
        return articleCategoryService.addArticleCategory(copyArticleCategory(req));
    }

    @Override
    public int editArticleCategoryList(ArticleCategoryReq req) {
        return articleCategoryService.editArticleCategory(copyArticleCategory(req));
    }

    @Override
    public int deleteArticleCategoryList(ArticleCategoryReq req) {
        return articleCategoryService.deleteArticleCategory(copyArticleCategory(req));
    }

    private ArticleCategory copyArticleCategory(ArticleCategoryReq req) {
        ArticleCategory articleCategory = new ArticleCategory();
        BeanUtils.copyProperties(req, articleCategory);
        return articleCategory;
    }
}
