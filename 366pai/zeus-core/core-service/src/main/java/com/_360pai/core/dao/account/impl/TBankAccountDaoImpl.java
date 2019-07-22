
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.TBankAccountCondition;
import com._360pai.core.dao.account.mapper.TBankAccountMapper;
import com._360pai.core.model.account.TBankAccount;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.TBankAccountDao;

@Service
public class TBankAccountDaoImpl extends AbstractDaoImpl<TBankAccount, TBankAccountCondition, BaseMapper<TBankAccount,TBankAccountCondition>> implements TBankAccountDao{
	
	@Resource
	private TBankAccountMapper tBankAccountMapper;
	
	@Override
	protected BaseMapper<TBankAccount, TBankAccountCondition> daoSupport() {
		return tBankAccountMapper;
	}

	@Override
	protected TBankAccountCondition blankCondition() {
		return new TBankAccountCondition();
	}

}
