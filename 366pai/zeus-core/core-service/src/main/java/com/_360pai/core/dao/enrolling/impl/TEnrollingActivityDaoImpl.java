
package com._360pai.core.dao.enrolling.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.enrolling.TEnrollingActivityCondition;
import com._360pai.core.dao.enrolling.TEnrollingActivityDao;
import com._360pai.core.dao.enrolling.mapper.TEnrollingActivityMapper;
import com._360pai.core.model.enrolling.TEnrollingActivity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TEnrollingActivityDaoImpl extends AbstractDaoImpl<TEnrollingActivity, TEnrollingActivityCondition, BaseMapper<TEnrollingActivity,TEnrollingActivityCondition>> implements TEnrollingActivityDao{
	
	@Resource
	private TEnrollingActivityMapper tEnrollingActivityMapper;
	
	@Override
	protected BaseMapper<TEnrollingActivity, TEnrollingActivityCondition> daoSupport() {
		return tEnrollingActivityMapper;
	}

	@Override
	protected TEnrollingActivityCondition blankCondition() {
		return new TEnrollingActivityCondition();
	}

	@Override
	public List<TEnrollingActivity> getOldList(String ids) {
		return tEnrollingActivityMapper.getOldList(ids);
	}
}
