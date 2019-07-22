
package com._360pai.core.dao.disposal.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.disposal.TDisposeSurveyCondition;
import com._360pai.core.dao.disposal.mapper.TDisposeSurveyMapper;
import com._360pai.core.model.disposal.TDisposeSurvey;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.disposal.TDisposeSurveyDao;

@Service
public class TDisposeSurveyDaoImpl extends AbstractDaoImpl<TDisposeSurvey, TDisposeSurveyCondition, BaseMapper<TDisposeSurvey,TDisposeSurveyCondition>> implements TDisposeSurveyDao{
	
	@Resource
	private TDisposeSurveyMapper tDisposeSurveyMapper;
	
	@Override
	protected BaseMapper<TDisposeSurvey, TDisposeSurveyCondition> daoSupport() {
		return tDisposeSurveyMapper;
	}

	@Override
	protected TDisposeSurveyCondition blankCondition() {
		return new TDisposeSurveyCondition();
	}

}
