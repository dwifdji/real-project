
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import com._360pai.core.condition.account.TCompanyCondition;
import com._360pai.core.model.account.TCompany;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.CompanyCondition;
import com._360pai.core.dao.account.mapper.CompanyMapper;
import com._360pai.core.model.account.Company;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.CompanyDao;

import java.util.List;

@Service
public class CompanyDaoImpl extends AbstractDaoImpl<Company, CompanyCondition, BaseMapper<Company,CompanyCondition>> implements CompanyDao{
	
	@Resource
	private CompanyMapper companyMapper;
	
	@Override
	protected BaseMapper<Company, CompanyCondition> daoSupport() {
		return companyMapper;
	}

	@Override
	protected CompanyCondition blankCondition() {
		return new CompanyCondition();
	}

	@Override
	public List<Company> getByAdminId(Integer accountId) {
		CompanyCondition condition = new CompanyCondition();
		condition.setAdminId(accountId);
		return companyMapper.selectByCondition(condition);
	}

	@Override
	public Company getByLicense(String license) {
		CompanyCondition condition = new CompanyCondition();
		condition.setLicense(license);
		List<Company> list = companyMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
