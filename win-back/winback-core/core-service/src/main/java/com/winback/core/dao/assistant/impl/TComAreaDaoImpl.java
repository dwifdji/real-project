
package com.winback.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition.assistant.TComAreaCondition;
import com.winback.core.dao.assistant.mapper.TComAreaMapper;
import com.winback.core.model.assistant.TComArea;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.assistant.TComAreaDao;

import java.util.List;

@Service
public class TComAreaDaoImpl extends AbstractDaoImpl<TComArea, TComAreaCondition, BaseMapper<TComArea,TComAreaCondition>> implements TComAreaDao{
	
	@Resource
	private TComAreaMapper tComAreaMapper;
	
	@Override
	protected BaseMapper<TComArea, TComAreaCondition> daoSupport() {
		return tComAreaMapper;
	}

	@Override
	protected TComAreaCondition blankCondition() {
		return new TComAreaCondition();
	}

	@Override
	public String getName(String code) {
		TComAreaCondition condition = new TComAreaCondition();
		condition.setCode(code);
		List<TComArea> list = tComAreaMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0).getName();
		}
		return "";
	}
}
