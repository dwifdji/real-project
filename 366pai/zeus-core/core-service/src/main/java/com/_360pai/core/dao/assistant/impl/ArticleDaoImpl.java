
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.ArticleCondition;
import com._360pai.core.dao.assistant.mapper.ArticleMapper;
import com._360pai.core.model.assistant.Article;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.ArticleDao;

@Service
public class ArticleDaoImpl extends AbstractDaoImpl<Article, ArticleCondition, BaseMapper<Article,ArticleCondition>> implements ArticleDao{
	
	@Resource
	private ArticleMapper articleMapper;
	
	@Override
	protected BaseMapper<Article, ArticleCondition> daoSupport() {
		return articleMapper;
	}

	@Override
	protected ArticleCondition blankCondition() {
		return new ArticleCondition();
	}

    @Override
    public int deleteArticle(Integer paramsId) {
        return articleMapper.deleteArticle(paramsId);
    }
}
