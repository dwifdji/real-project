
package com.winback.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition.assistant.TComProvinceCondition;
import com.winback.core.dao.assistant.mapper.TComProvinceMapper;
import com.winback.core.model.assistant.TComProvince;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.assistant.TComProvinceDao;

import java.util.List;

@Service
public class TComProvinceDaoImpl extends AbstractDaoImpl<TComProvince, TComProvinceCondition, BaseMapper<TComProvince,TComProvinceCondition>> implements TComProvinceDao{
	
	@Resource
	private TComProvinceMapper tComProvinceMapper;
	
	@Override
	protected BaseMapper<TComProvince, TComProvinceCondition> daoSupport() {
		return tComProvinceMapper;
	}

	@Override
	protected TComProvinceCondition blankCondition() {
		return new TComProvinceCondition();
	}

	@Override
	public String getName(String code) {
		TComProvinceCondition condition = new TComProvinceCondition();
		condition.setCode(code);
		List<TComProvince> list = tComProvinceMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0).getName();
		}
		return "";
	}
}
