package com._360pai.core.facade.assistant;

import com._360pai.core.facade.assistant.req.TPlatformArticleReq;
import com._360pai.core.facade.assistant.req.TPlatformArticleTypeReq;
import com.github.pagehelper.PageInfo;

public interface TPlatformArticleTypeFacade {
    PageInfo getTypes(TPlatformArticleTypeReq req);

    int addType(TPlatformArticleTypeReq req);

    int editType(TPlatformArticleTypeReq req);

    int deleteType(TPlatformArticleTypeReq req);

    PageInfo getArticles(TPlatformArticleReq req);

    int addArticle(TPlatformArticleReq req);

    int editArticle(TPlatformArticleReq req);

    int deleteArticle(TPlatformArticleReq req);

    Object detailArticle(TPlatformArticleReq req);

    Object detailType(TPlatformArticleTypeReq req);
}
