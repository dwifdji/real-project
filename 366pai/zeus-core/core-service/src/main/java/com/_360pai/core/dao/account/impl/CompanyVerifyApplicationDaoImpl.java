
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.condition.account.AgencyApplicationCondition;
import com._360pai.core.model.account.AgencyApplication;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.CompanyVerifyApplicationCondition;
import com._360pai.core.dao.account.mapper.CompanyVerifyApplicationMapper;
import com._360pai.core.model.account.CompanyVerifyApplication;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.CompanyVerifyApplicationDao;

import java.util.List;

@Service
public class CompanyVerifyApplicationDaoImpl extends AbstractDaoImpl<CompanyVerifyApplication, CompanyVerifyApplicationCondition, BaseMapper<CompanyVerifyApplication,CompanyVerifyApplicationCondition>> implements CompanyVerifyApplicationDao{
	
	@Resource
	private CompanyVerifyApplicationMapper companyVerifyApplicationMapper;
	
	@Override
	protected BaseMapper<CompanyVerifyApplication, CompanyVerifyApplicationCondition> daoSupport() {
		return companyVerifyApplicationMapper;
	}

	@Override
	protected CompanyVerifyApplicationCondition blankCondition() {
		return new CompanyVerifyApplicationCondition();
	}

	@Override
	public CompanyVerifyApplication getApprovedByLicense(String license) {
		CompanyVerifyApplicationCondition condition = new CompanyVerifyApplicationCondition();
		condition.setLicense(license);
		condition.setStatus(AccountEnum.ApplyStatus.APPROVED.getKey());
		List<CompanyVerifyApplication> list = companyVerifyApplicationMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
