
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.PlatformAnnouncementCondition;
import com._360pai.core.dao.assistant.mapper.PlatformAnnouncementMapper;
import com._360pai.core.model.assistant.PlatformAnnouncement;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.PlatformAnnouncementDao;

@Service
public class PlatformAnnouncementDaoImpl extends AbstractDaoImpl<PlatformAnnouncement, PlatformAnnouncementCondition, BaseMapper<PlatformAnnouncement,PlatformAnnouncementCondition>> implements PlatformAnnouncementDao{
	
	@Resource
	private PlatformAnnouncementMapper platformAnnouncementMapper;
	
	@Override
	protected BaseMapper<PlatformAnnouncement, PlatformAnnouncementCondition> daoSupport() {
		return platformAnnouncementMapper;
	}

	@Override
	protected PlatformAnnouncementCondition blankCondition() {
		return new PlatformAnnouncementCondition();
	}

    @Override
    public int deletePlatformAnnouncement(Integer paramsId) {

		return platformAnnouncementMapper.deletePlatformAnnouncement(paramsId);
    }

	@Override
	public int addViewCount(Integer id) {
		return platformAnnouncementMapper.addViewCount(id);
	}
}
