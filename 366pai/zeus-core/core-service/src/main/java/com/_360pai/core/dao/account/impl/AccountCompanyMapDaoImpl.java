
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import com._360pai.core.facade.account.vo.CompanyMemberVo;
import com._360pai.core.model.account.TCompany;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.AccountCompanyMapCondition;
import com._360pai.core.dao.account.mapper.AccountCompanyMapMapper;
import com._360pai.core.model.account.AccountCompanyMap;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.AccountCompanyMapDao;

import java.util.List;
import java.util.Map;

@Service
public class AccountCompanyMapDaoImpl extends AbstractDaoImpl<AccountCompanyMap, AccountCompanyMapCondition, BaseMapper<AccountCompanyMap,AccountCompanyMapCondition>> implements AccountCompanyMapDao{
	
	@Resource
	private AccountCompanyMapMapper accountCompanyMapMapper;
	
	@Override
	protected BaseMapper<AccountCompanyMap, AccountCompanyMapCondition> daoSupport() {
		return accountCompanyMapMapper;
	}

	@Override
	protected AccountCompanyMapCondition blankCondition() {
		return new AccountCompanyMapCondition();
	}

	@Override
	public List<AccountCompanyMap> getByAccountId(Integer accountId) {
		AccountCompanyMapCondition condition = new AccountCompanyMapCondition();
		condition.setAccountId(accountId);
		condition.setIsDelete(false);
		return accountCompanyMapMapper.selectByCondition(condition);
	}

	@Override
	public List<AccountCompanyMap> getByCompanyId(Integer accountId) {
		return null;
	}

	@Override
	public AccountCompanyMap getByAccountIdCompanyId(Integer accountId, Integer companyId) {
		AccountCompanyMapCondition condition = new AccountCompanyMapCondition();
		condition.setAccountId(accountId);
		condition.setCompanyId(companyId);
		List<AccountCompanyMap> list = accountCompanyMapMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<CompanyMemberVo> list = accountCompanyMapMapper.getList(params);
		return new PageInfo<>(list);
	}
}
