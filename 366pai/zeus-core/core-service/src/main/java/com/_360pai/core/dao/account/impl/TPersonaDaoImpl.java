
package com._360pai.core.dao.account.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TPersonaCondition;
import com._360pai.core.dao.account.TPersonaDao;
import com._360pai.core.dao.account.mapper.TPersonaMapper;
import com._360pai.core.model.account.TPersona;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TPersonaDaoImpl extends AbstractDaoImpl<TPersona, TPersonaCondition, BaseMapper<TPersona,TPersonaCondition>> implements TPersonaDao {
	
	@Resource
	private TPersonaMapper tPersonaMapper;
	
	@Override
	protected BaseMapper<TPersona, TPersonaCondition> daoSupport() {
		return tPersonaMapper;
	}

	@Override
	protected TPersonaCondition blankCondition() {
		return new TPersonaCondition();
	}

	@Override
	public TPersona selectById(Integer id) {
		TPersonaCondition condition = new TPersonaCondition();
		condition.setId(id);
		List<TPersona> list = tPersonaMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TPersona> list = tPersonaMapper.getList(params);
		return new PageInfo<>(list);
	}
}
