package com._360pai.core.facade.assistant;

import com._360pai.core.facade.assistant.req.ArticleCategoryReq;
import com.github.pagehelper.PageInfo;

public interface ArticleCategoryFacade {

    PageInfo selectArticleCategoryList(ArticleCategoryReq req);

    int addArticleCategoryList(ArticleCategoryReq req);

    int editArticleCategoryList(ArticleCategoryReq req);

    int deleteArticleCategoryList(ArticleCategoryReq req);

}
