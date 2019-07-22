
package com.winback.core.dao.assistant.impl;

import javax.annotation.Resource;

import com.winback.core.condition.assistant.TComAreaCondition;
import com.winback.core.model.assistant.TComArea;
import org.springframework.stereotype.Service;

import com.winback.core.condition.assistant.TComCityCondition;
import com.winback.core.dao.assistant.mapper.TComCityMapper;
import com.winback.core.model.assistant.TComCity;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.assistant.TComCityDao;

import java.util.List;

@Service
public class TComCityDaoImpl extends AbstractDaoImpl<TComCity, TComCityCondition, BaseMapper<TComCity,TComCityCondition>> implements TComCityDao{
	
	@Resource
	private TComCityMapper tComCityMapper;
	
	@Override
	protected BaseMapper<TComCity, TComCityCondition> daoSupport() {
		return tComCityMapper;
	}

	@Override
	protected TComCityCondition blankCondition() {
		return new TComCityCondition();
	}

	@Override
	public String getName(String code) {
		TComCityCondition condition = new TComCityCondition();
		condition.setCode(code);
		List<TComCity> list = tComCityMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0).getName();
		}
		return "";
	}

	@Override
	public TComCity findBy(String code) {
		TComCityCondition condition = new TComCityCondition();
		condition.setCode(code);
		List<TComCity> list = tComCityMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
