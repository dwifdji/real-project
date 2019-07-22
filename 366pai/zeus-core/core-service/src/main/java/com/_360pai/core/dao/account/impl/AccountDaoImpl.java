
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.AccountCondition;
import com._360pai.core.dao.account.mapper.AccountMapper;
import com._360pai.core.model.account.Account;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.AccountDao;

import java.util.List;

@Service
public class AccountDaoImpl extends AbstractDaoImpl<Account, AccountCondition, BaseMapper<Account,AccountCondition>> implements AccountDao{
	
	@Resource
	private AccountMapper accountMapper;
	
	@Override
	protected BaseMapper<Account, AccountCondition> daoSupport() {
		return accountMapper;
	}

	@Override
	protected AccountCondition blankCondition() {
		return new AccountCondition();
	}

	@Override
	public Account getByMobile(String mobile) {
		AccountCondition condition = new AccountCondition();
		condition.setMobile(mobile);
		List<Account> list = accountMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
