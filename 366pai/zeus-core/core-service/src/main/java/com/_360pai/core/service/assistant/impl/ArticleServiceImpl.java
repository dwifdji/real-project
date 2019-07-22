package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.assistant.ArticleCondition;
import com._360pai.core.dao.assistant.ArticleCategoryDao;
import com._360pai.core.dao.assistant.ArticleDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.assistant.Article;
import com._360pai.core.model.assistant.ArticleCategory;
import com._360pai.core.service.assistant.ArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ArticleCategoryDao articleCategoryDao;


    @Override
    public PageInfo selectArticle(int page, int perPage, ArticleCondition condition) {
        PageHelper.startPage(page, perPage);
        PageHelper.orderBy("order_num asc");
        List<Article> articles = articleDao.selectList(condition);
        for (Article article : articles) {
            Integer categoryId = article.getCategoryId();
            if (categoryId == null) {
                continue;
            }
            ArticleCategory articleCategory = articleCategoryDao.selectById(categoryId);
            article.setCategoryName(articleCategory.getName());
        }
        return new PageInfo<>(articles);
    }

    @Override
    public Object detailArticle(Article params) {
        return findArticleById(params);
    }

    @Override
    public int addArticle(Article params) {
        return articleDao.insert(params);
    }

    @Override
    public int editArticle(Article params) {
        Article articleById = findArticleById(params);
        if (articleById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的文章不存在");
        }
        return articleDao.updateById(params);
    }

    @Override
    public int deleteArticle(Article params) {
        Article articleById = findArticleById(params);
        if (articleById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "删除的文章不存在");
        }
        return articleDao.deleteArticle(params.getId());
    }

    private Article findArticleById(Article params) {
        ArticleCondition condition = new ArticleCondition();
        condition.setId(params.getId());
        return articleDao.selectOneResult(condition);
    }
}