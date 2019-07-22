package com._360pai.core.facade.assistant;

import com._360pai.core.facade.assistant.req.ArticleReq;
import com.github.pagehelper.PageInfo;

public interface ArticleFacade {

    PageInfo selectArticle(ArticleReq req);

    int addArticle(ArticleReq req);

    int editArticle(ArticleReq req);

    int deleteArticle(ArticleReq req);

    Object detailArticle(ArticleReq req);
}
