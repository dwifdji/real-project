
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.TPlatformArticleCondition;
import com._360pai.core.dao.assistant.mapper.TPlatformArticleMapper;
import com._360pai.core.model.assistant.TPlatformArticle;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.TPlatformArticleDao;

@Service
public class TPlatformArticleDaoImpl extends AbstractDaoImpl<TPlatformArticle, TPlatformArticleCondition, BaseMapper<TPlatformArticle,TPlatformArticleCondition>> implements TPlatformArticleDao{
	
	@Resource
	private TPlatformArticleMapper tPlatformArticleMapper;
	
	@Override
	protected BaseMapper<TPlatformArticle, TPlatformArticleCondition> daoSupport() {
		return tPlatformArticleMapper;
	}

	@Override
	protected TPlatformArticleCondition blankCondition() {
		return new TPlatformArticleCondition();
	}

	@Override
	public int addViewCount(Integer id) {
		return tPlatformArticleMapper.addViewCount(id);
	}
}
