package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.assistant.ArticleScenarioCondition;
import com._360pai.core.dao.assistant.ArticleScenarioDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.assistant.ArticleScenario;
import com._360pai.core.service.assistant.ArticleScenarioService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleScenarioServiceImpl implements ArticleScenarioService {

    @Autowired
    private ArticleScenarioDao articleScenarioDao;


    @Override
    public PageInfo selectArticleScenario(int page, int perPage) {
        PageHelper.startPage(page, perPage);
        List<Map>  articleScenarios = articleScenarioDao.selectArticleScenario();
        return new PageInfo<>(articleScenarios);
    }

    @Override
    public int addArticleScenario(ArticleScenario params) {
        if (StringUtils.isBlank(params.getName())) throw new BusinessException(ApiCallResult.FAILURE.getCode(), "名称不能为空");
        ArticleScenario articleScenarioByName = findArticleScenarioByName(params);
        if (articleScenarioByName != null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "名称重复");
        }
        return articleScenarioDao.insert(params);
    }

    @Override
    public int editArticleScenario(ArticleScenario params) {
        ArticleScenario articleScenarioById = findArticleScenarioById(params);
        if (articleScenarioById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的配置不存在");
        }
        ArticleScenario articleScenario = new ArticleScenario();
        articleScenario.setId(params.getId());
        articleScenario.setDesc(params.getDesc());
        articleScenario.setArticleId(params.getArticleId());
        return articleScenarioDao.updateById(articleScenario);
    }

    private ArticleScenario findArticleScenarioByName(ArticleScenario params) {
        ArticleScenarioCondition condition = new ArticleScenarioCondition();
        condition.setName(params.getName());
        return articleScenarioDao.selectOneResult(condition);
    }

    private ArticleScenario findArticleScenarioById(ArticleScenario params) {
        ArticleScenarioCondition condition = new ArticleScenarioCondition();
        condition.setId(params.getId());
        return articleScenarioDao.selectOneResult(condition);
    }
}