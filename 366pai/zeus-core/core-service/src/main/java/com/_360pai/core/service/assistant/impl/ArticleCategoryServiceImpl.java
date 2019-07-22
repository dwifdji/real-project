package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.assistant.ArticleCategoryCondition;
import com._360pai.core.condition.assistant.ArticleCondition;
import com._360pai.core.dao.assistant.ArticleCategoryDao;
import com._360pai.core.dao.assistant.ArticleDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.assistant.Article;
import com._360pai.core.model.assistant.ArticleCategory;
import com._360pai.core.service.assistant.ArticleCategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

    @Autowired
    private ArticleCategoryDao articleCategoryDao;
    @Autowired
    private ArticleDao articleDao;

    @Override
    public PageInfo selectArticleCategoryList(int page, int perPage, ArticleCategoryCondition categoryCondition) {
        PageHelper.startPage(page, perPage);
        PageHelper.orderBy("order_num asc");
        List<ArticleCategory> articleCategories = articleCategoryDao.selectList(categoryCondition);
        return new PageInfo<>(articleCategories);
    }

    @Override
    public int addArticleCategory(ArticleCategory params) {
        if (StringUtils.isBlank(params.getName())) throw new BusinessException(ApiCallResult.FAILURE.getCode(), "名称不能为空");
        ArticleCategory articleCategoryByName = findArticleCategoryByName(params);
        if (articleCategoryByName != null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "名称不能重复");
        }
        return articleCategoryDao.insert(params);
    }

    @Override
    public int editArticleCategory(ArticleCategory params) {
        if (StringUtils.isBlank(params.getName())) throw new BusinessException(ApiCallResult.FAILURE.getCode(), "名称不能为空");
        ArticleCategory articleCategoryById = findArticleCategoryById(params);
        if (articleCategoryById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的分类不存在");
        }
        return articleCategoryDao.updateById(params);
    }

    @Override
    public int deleteArticleCategory(ArticleCategory params) {
        if (params.getId() <= 0) throw new BusinessException(ApiCallResult.FAILURE.getCode(), "删除的分类不存在");
        ArticleCategory articleCategoryById = findArticleCategoryById(params);
        if (articleCategoryById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "删除的分类不存在");
        }
        ArticleCondition article = new ArticleCondition();
        article.setCategoryId(params.getId());
        List<Article> articles = articleDao.selectList(article);
        if (!articles.isEmpty()) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "该分类以关联文章不能删除");
        }
        return articleCategoryDao.deleteArticleCategory(params.getId());
    }

    private ArticleCategory findArticleCategoryByName(ArticleCategory param) {
        ArticleCategoryCondition categoryCondition = new ArticleCategoryCondition();
        categoryCondition.setName(param.getName());
        return articleCategoryDao.selectOneResult(categoryCondition);
    }

    private ArticleCategory findArticleCategoryById(ArticleCategory param) {
        ArticleCategoryCondition categoryCondition = new ArticleCategoryCondition();
        categoryCondition.setId(param.getId());
        return articleCategoryDao.selectOneResult(categoryCondition);
    }


}