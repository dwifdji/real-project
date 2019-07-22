
package com.winback.core.dao.assistant.impl;

import javax.annotation.Resource;

import com.winback.core.facade.assistant.vo.HelpItem;
import org.springframework.stereotype.Service;

import com.winback.core.condition.assistant.THelpItemCondition;
import com.winback.core.dao.assistant.mapper.THelpItemMapper;
import com.winback.core.model.assistant.THelpItem;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.assistant.THelpItemDao;

import java.util.List;

@Service
public class THelpItemDaoImpl extends AbstractDaoImpl<THelpItem, THelpItemCondition, BaseMapper<THelpItem,THelpItemCondition>> implements THelpItemDao{
	
	@Resource
	private THelpItemMapper tHelpItemMapper;
	
	@Override
	protected BaseMapper<THelpItem, THelpItemCondition> daoSupport() {
		return tHelpItemMapper;
	}

	@Override
	protected THelpItemCondition blankCondition() {
		return new THelpItemCondition();
	}

	@Override
	public List<HelpItem> getHelpItemList(Boolean display) {
		return tHelpItemMapper.getHelpItemList(display);
	}
}
