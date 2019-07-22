
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.TPlatformArticleTypeCondition;
import com._360pai.core.dao.assistant.mapper.TPlatformArticleTypeMapper;
import com._360pai.core.model.assistant.TPlatformArticleType;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.TPlatformArticleTypeDao;

@Service
public class TPlatformArticleTypeDaoImpl extends AbstractDaoImpl<TPlatformArticleType, TPlatformArticleTypeCondition, BaseMapper<TPlatformArticleType,TPlatformArticleTypeCondition>> implements TPlatformArticleTypeDao{
	
	@Resource
	private TPlatformArticleTypeMapper tPlatformArticleTypeMapper;
	
	@Override
	protected BaseMapper<TPlatformArticleType, TPlatformArticleTypeCondition> daoSupport() {
		return tPlatformArticleTypeMapper;
	}

	@Override
	protected TPlatformArticleTypeCondition blankCondition() {
		return new TPlatformArticleTypeCondition();
	}

}
