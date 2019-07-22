package com._360pai.core.service.account.impl;

import com._360pai.core.condition.account.TAgencyApplyRecordCondition;
import com._360pai.core.dao.account.AgencyApplicationDao;
import com._360pai.core.dao.account.TAgencyApplyRecordDao;
import com._360pai.core.model.account.TAgencyApplyRecord;
import com._360pai.core.service.account.AgencyApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgencyApplicationServiceImpl	implements AgencyApplicationService{

	@Autowired
	private TAgencyApplyRecordDao tAgencyApplyRecordDao;


	@Override
	public boolean saveAgencyApplyRecord(TAgencyApplyRecord agencyApplyRecord) {
		return tAgencyApplyRecordDao.insert(agencyApplyRecord) == 1;
	}

	@Override
	public boolean updateAgencyApplyRecord(TAgencyApplyRecord agencyApplyRecord) {
		return tAgencyApplyRecordDao.updateById(agencyApplyRecord) == 1;
	}

	@Override
	public TAgencyApplyRecord getAgencyApplyById(Long id) {
		TAgencyApplyRecordCondition condition = new TAgencyApplyRecordCondition();
		condition.setId(id);
		return tAgencyApplyRecordDao.selectFirst(condition);
	}
}