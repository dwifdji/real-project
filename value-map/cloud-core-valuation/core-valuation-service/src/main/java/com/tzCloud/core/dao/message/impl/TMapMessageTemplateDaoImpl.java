
package com.tzCloud.core.dao.message.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.message.TMapMessageTemplateCondition;
import com.tzCloud.core.dao.message.mapper.TMapMessageTemplateMapper;
import com.tzCloud.core.model.message.TMapMessageTemplate;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.message.TMapMessageTemplateDao;

@Service
public class TMapMessageTemplateDaoImpl extends AbstractDaoImpl<TMapMessageTemplate, TMapMessageTemplateCondition, BaseMapper<TMapMessageTemplate,TMapMessageTemplateCondition>> implements TMapMessageTemplateDao{
	
	@Resource
	private TMapMessageTemplateMapper tMapMessageTemplateMapper;
	
	@Override
	protected BaseMapper<TMapMessageTemplate, TMapMessageTemplateCondition> daoSupport() {
		return tMapMessageTemplateMapper;
	}

	@Override
	protected TMapMessageTemplateCondition blankCondition() {
		return new TMapMessageTemplateCondition();
	}

}
