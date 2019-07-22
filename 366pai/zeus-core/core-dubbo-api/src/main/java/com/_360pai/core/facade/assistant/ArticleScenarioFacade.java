package com._360pai.core.facade.assistant;

import com._360pai.core.facade.assistant.req.ArticleScenarioReq;
import com.github.pagehelper.PageInfo;

public interface ArticleScenarioFacade {
    PageInfo selectArticleScenario(ArticleScenarioReq req);

    int addArticleScenario(ArticleScenarioReq req);

    int editArticleScenario(ArticleScenarioReq req);
}
