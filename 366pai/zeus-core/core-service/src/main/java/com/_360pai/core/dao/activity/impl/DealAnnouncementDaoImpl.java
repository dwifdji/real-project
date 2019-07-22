
package com._360pai.core.dao.activity.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.activity.DealAnnouncementCondition;
import com._360pai.core.dao.activity.mapper.DealAnnouncementMapper;
import com._360pai.core.model.activity.DealAnnouncement;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.activity.DealAnnouncementDao;

@Service
public class DealAnnouncementDaoImpl extends AbstractDaoImpl<DealAnnouncement, DealAnnouncementCondition, BaseMapper<DealAnnouncement,DealAnnouncementCondition>> implements DealAnnouncementDao{
	
	@Resource
	private DealAnnouncementMapper dealAnnouncementMapper;
	
	@Override
	protected BaseMapper<DealAnnouncement, DealAnnouncementCondition> daoSupport() {
		return dealAnnouncementMapper;
	}

	@Override
	protected DealAnnouncementCondition blankCondition() {
		return new DealAnnouncementCondition();
	}

}
