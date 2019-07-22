package com._360pai.core.service.assistant;


import com._360pai.core.model.assistant.ArticleScenario;
import com.github.pagehelper.PageInfo;

public interface ArticleScenarioService {

    PageInfo selectArticleScenario(int page, int perPage);

    int addArticleScenario(ArticleScenario params);

    int editArticleScenario(ArticleScenario params);
}