
package com._360pai.core.dao.fastway.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.fastway.TFastwayDisposeApplyCondition;
import com._360pai.core.dao.fastway.mapper.TFastwayDisposeApplyMapper;
import com._360pai.core.model.fastway.TFastwayDisposeApply;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.fastway.TFastwayDisposeApplyDao;

import java.util.List;
import java.util.Map;

@Service
public class TFastwayDisposeApplyDaoImpl extends AbstractDaoImpl<TFastwayDisposeApply, TFastwayDisposeApplyCondition, BaseMapper<TFastwayDisposeApply,TFastwayDisposeApplyCondition>> implements TFastwayDisposeApplyDao{
	
	@Resource
	private TFastwayDisposeApplyMapper tFastwayDisposeApplyMapper;
	
	@Override
	protected BaseMapper<TFastwayDisposeApply, TFastwayDisposeApplyCondition> daoSupport() {
		return tFastwayDisposeApplyMapper;
	}

	@Override
	protected TFastwayDisposeApplyCondition blankCondition() {
		return new TFastwayDisposeApplyCondition();
	}

	@Override
	public List<TFastwayDisposeApply> findByParam(Map<String, Object> query) {
		return tFastwayDisposeApplyMapper.findByParam(query);
	}
}
