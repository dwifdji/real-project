package com._360pai.core.service.account.impl;

import com._360pai.core.condition.account.TCompanyApplyRecordCondition;
import com._360pai.core.dao.account.TCompanyApplyRecordDao;
import com._360pai.core.model.account.TCompanyApplyRecord;
import com._360pai.core.service.account.CompanyVerifyApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CompanyVerifyApplicationServiceImpl	implements CompanyVerifyApplicationService{

	@Autowired
	private TCompanyApplyRecordDao tCompanyApplyRecordDao;

	@Override
	public boolean saveCompanyApplyRecord(TCompanyApplyRecord companyApplyRecord) {
		return tCompanyApplyRecordDao.insert(companyApplyRecord) == 1;
	}

	@Override
	public boolean updateCompanyApplyRecord(TCompanyApplyRecord companyApplyRecord) {
		return tCompanyApplyRecordDao.updateById(companyApplyRecord) == 1;
	}

	@Override
	public TCompanyApplyRecord findCompanyApplyRecordById(Long id) {
		TCompanyApplyRecordCondition condition = new TCompanyApplyRecordCondition();
		condition.setId(id);
		return tCompanyApplyRecordDao.selectFirst(condition);
	}

	@Override
	public List<TCompanyApplyRecord> getApplyRecordByAccountId(Integer accountId, String status) {
		TCompanyApplyRecordCondition condition = new TCompanyApplyRecordCondition();
		condition.setAccountId(accountId);
		condition.setStatus(status);
		return tCompanyApplyRecordDao.selectAll();
	}
}