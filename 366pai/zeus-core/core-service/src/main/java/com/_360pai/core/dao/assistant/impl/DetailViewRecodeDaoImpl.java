
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.DetailViewRecodeCondition;
import com._360pai.core.dao.assistant.mapper.DetailViewRecodeMapper;
import com._360pai.core.model.assistant.DetailViewRecode;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.DetailViewRecodeDao;

@Service
public class DetailViewRecodeDaoImpl extends AbstractDaoImpl<DetailViewRecode, DetailViewRecodeCondition, BaseMapper<DetailViewRecode,DetailViewRecodeCondition>> implements DetailViewRecodeDao{
	
	@Resource
	private DetailViewRecodeMapper detailViewRecodeMapper;
	
	@Override
	protected BaseMapper<DetailViewRecode, DetailViewRecodeCondition> daoSupport() {
		return detailViewRecodeMapper;
	}

	@Override
	protected DetailViewRecodeCondition blankCondition() {
		return new DetailViewRecodeCondition();
	}

}
