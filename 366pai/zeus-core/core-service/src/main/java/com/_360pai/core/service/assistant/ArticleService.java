package com._360pai.core.service.assistant;

import com._360pai.core.condition.assistant.ArticleCondition;
import com._360pai.core.model.assistant.Article;
import com.github.pagehelper.PageInfo;

public interface ArticleService {


    int addArticle(Article params);

    int editArticle(Article params);

    int deleteArticle(Article params);

    PageInfo selectArticle(int page, int perPage, ArticleCondition condition);

    Object detailArticle(Article req);
}