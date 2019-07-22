
package com._360pai.core.dao.activity.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.activity.AgencyPortalActivityCondition;
import com._360pai.core.dao.activity.mapper.AgencyPortalActivityMapper;
import com._360pai.core.model.activity.AgencyPortalActivity;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.activity.AgencyPortalActivityDao;

import java.util.Date;
import java.util.List;

@Service
public class AgencyPortalActivityDaoImpl extends AbstractDaoImpl<AgencyPortalActivity, AgencyPortalActivityCondition, BaseMapper<AgencyPortalActivity,AgencyPortalActivityCondition>> implements AgencyPortalActivityDao{
	
	@Resource
	private AgencyPortalActivityMapper agencyPortalActivityMapper;
	
	@Override
	protected BaseMapper<AgencyPortalActivity, AgencyPortalActivityCondition> daoSupport() {
		return agencyPortalActivityMapper;
	}

	@Override
	protected AgencyPortalActivityCondition blankCondition() {
		return new AgencyPortalActivityCondition();
	}

    @Override
    public int deleteNotify(Integer id) {
        return agencyPortalActivityMapper.deleteNotify(id);
    }

    @Override
    public List<AgencyPortalActivity> getByActivity(Integer activityId) {
        return agencyPortalActivityMapper.getByActivity(activityId);
    }

	@Override
	public AgencyPortalActivity getOrCreateInstance(Integer activityId, Integer agencyPortalId, Integer agencyId) {
		AgencyPortalActivityCondition condition = new AgencyPortalActivityCondition();
		condition.setActivityId(activityId);
		condition.setAgencyPortalId(agencyPortalId);
		List<AgencyPortalActivity> list = agencyPortalActivityMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		AgencyPortalActivity agencyPortalActivity = new AgencyPortalActivity();
		agencyPortalActivity.setActivityId(activityId);
		agencyPortalActivity.setAgencyPortalId(agencyPortalId);
		agencyPortalActivity.setAgencyId(agencyId);
		agencyPortalActivity.setCreatedAt(new Date());
		agencyPortalActivityMapper.insert(agencyPortalActivity);
		return agencyPortalActivity;
	}
}
