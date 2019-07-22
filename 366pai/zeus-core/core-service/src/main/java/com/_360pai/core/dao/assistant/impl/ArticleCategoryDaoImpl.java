
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.ArticleCategoryCondition;
import com._360pai.core.dao.assistant.mapper.ArticleCategoryMapper;
import com._360pai.core.model.assistant.ArticleCategory;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.ArticleCategoryDao;

@Service
public class ArticleCategoryDaoImpl extends AbstractDaoImpl<ArticleCategory, ArticleCategoryCondition, BaseMapper<ArticleCategory, ArticleCategoryCondition>> implements ArticleCategoryDao {

    @Resource
    private ArticleCategoryMapper articleCategoryMapper;

    @Override
    protected BaseMapper<ArticleCategory, ArticleCategoryCondition> daoSupport() {
        return articleCategoryMapper;
    }

    @Override
    protected ArticleCategoryCondition blankCondition() {
        return new ArticleCategoryCondition();
    }

    @Override
    public int deleteArticleCategory(Integer paramsId) {
        return articleCategoryMapper.deleteArticleCategory(paramsId);
    }
}
