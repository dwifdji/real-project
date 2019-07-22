
package com.tzCloud.core.dao.caseMatching.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.caseMatching.TBriefCondition;
import com.tzCloud.core.dao.caseMatching.mapper.TBriefMapper;
import com.tzCloud.core.model.caseMatching.TBrief;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.caseMatching.TBriefDao;

@Service
public class TBriefDaoImpl extends AbstractDaoImpl<TBrief, TBriefCondition, BaseMapper<TBrief,TBriefCondition>> implements TBriefDao{
	
	@Resource
	private TBriefMapper tBriefMapper;
	
	@Override
	protected BaseMapper<TBrief, TBriefCondition> daoSupport() {
		return tBriefMapper;
	}

	@Override
	protected TBriefCondition blankCondition() {
		return new TBriefCondition();
	}

}
