
package com._360pai.core.dao.comprador.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.comprador.TCompradorRecommendCondition;
import com._360pai.core.dao.comprador.mapper.TCompradorRecommendMapper;
import com._360pai.core.model.comprador.TCompradorRecommend;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.comprador.TCompradorRecommendDao;

@Service
public class TCompradorRecommendDaoImpl extends AbstractDaoImpl<TCompradorRecommend, TCompradorRecommendCondition, BaseMapper<TCompradorRecommend,TCompradorRecommendCondition>> implements TCompradorRecommendDao{
	
	@Resource
	private TCompradorRecommendMapper tCompradorRecommendMapper;
	
	@Override
	protected BaseMapper<TCompradorRecommend, TCompradorRecommendCondition> daoSupport() {
		return tCompradorRecommendMapper;
	}

	@Override
	protected TCompradorRecommendCondition blankCondition() {
		return new TCompradorRecommendCondition();
	}

}
