package com._360pai.core.service.assistant;

import com._360pai.core.facade.assistant.req.TPlatformArticleReq;
import com._360pai.core.facade.assistant.req.TPlatformArticleTypeReq;
import com._360pai.core.model.assistant.TPlatformArticle;
import com._360pai.core.model.assistant.TPlatformArticleType;
import com.github.pagehelper.PageInfo;

public interface TPlatformArticleTypeService {
    PageInfo getTypes(TPlatformArticleTypeReq req);

    int addType(TPlatformArticleType params);

    int editType(TPlatformArticleType params);

    int deleteType(TPlatformArticleType params);

    PageInfo getArticles(TPlatformArticleReq req);

    int addArticle(TPlatformArticle copyArticle);

    int editArticle(TPlatformArticle copyArticle);

    int deleteArticle(TPlatformArticle copyArticle);

    Object detailArticle(TPlatformArticle copyArticle);

    Object detailType(Integer id);
}
