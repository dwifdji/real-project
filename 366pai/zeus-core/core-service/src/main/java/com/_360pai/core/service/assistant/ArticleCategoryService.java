package com._360pai.core.service.assistant;


import com._360pai.core.condition.assistant.ArticleCategoryCondition;
import com._360pai.core.model.assistant.ArticleCategory;
import com.github.pagehelper.PageInfo;

public interface ArticleCategoryService {
    PageInfo selectArticleCategoryList(int page, int perPage, ArticleCategoryCondition categoryCondition);

    int addArticleCategory(ArticleCategory params);

    int editArticleCategory(ArticleCategory params);

    int deleteArticleCategory(ArticleCategory params);
}