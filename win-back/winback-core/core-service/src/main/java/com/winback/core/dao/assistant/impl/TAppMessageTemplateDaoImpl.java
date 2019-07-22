
package com.winback.core.dao.assistant.impl;

import javax.annotation.Resource;

import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import org.springframework.stereotype.Service;

import com.winback.core.condition.assistant.TAppMessageTemplateCondition;
import com.winback.core.dao.assistant.mapper.TAppMessageTemplateMapper;
import com.winback.core.model.assistant.TAppMessageTemplate;
import com.winback.core.dao.assistant.TAppMessageTemplateDao;

import java.util.List;

@Service
public class TAppMessageTemplateDaoImpl extends AbstractDaoImpl<TAppMessageTemplate, TAppMessageTemplateCondition, BaseMapper<TAppMessageTemplate,TAppMessageTemplateCondition>> implements TAppMessageTemplateDao{
	
	@Resource
	private TAppMessageTemplateMapper tAppMessageTemplateMapper;
	
	@Override
	protected BaseMapper<TAppMessageTemplate, TAppMessageTemplateCondition> daoSupport() {
		return tAppMessageTemplateMapper;
	}

	@Override
	protected TAppMessageTemplateCondition blankCondition() {
		return new TAppMessageTemplateCondition();
	}

	@Override
	public TAppMessageTemplate findBy(String sendType, String type) {
		TAppMessageTemplateCondition condition = new TAppMessageTemplateCondition();
		condition.setSendType(sendType);
		condition.setType(type);
		List<TAppMessageTemplate> list = tAppMessageTemplateMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
