
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.ArticleScenarioCondition;
import com._360pai.core.dao.assistant.mapper.ArticleScenarioMapper;
import com._360pai.core.model.assistant.ArticleScenario;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.ArticleScenarioDao;

import java.util.List;
import java.util.Map;

@Service
public class ArticleScenarioDaoImpl extends AbstractDaoImpl<ArticleScenario, ArticleScenarioCondition, BaseMapper<ArticleScenario,ArticleScenarioCondition>> implements ArticleScenarioDao{
	
	@Resource
	private ArticleScenarioMapper articleScenarioMapper;
	
	@Override
	protected BaseMapper<ArticleScenario, ArticleScenarioCondition> daoSupport() {
		return articleScenarioMapper;
	}

	@Override
	protected ArticleScenarioCondition blankCondition() {
		return new ArticleScenarioCondition();
	}

    @Override
    public List<Map> selectArticleScenario() {
        return articleScenarioMapper.selectArticleScenario();
    }
}
