
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.BankAccountCondition;
import com._360pai.core.dao.account.mapper.BankAccountMapper;
import com._360pai.core.model.account.BankAccount;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.BankAccountDao;

import java.util.List;

@Service
public class BankAccountDaoImpl extends AbstractDaoImpl<BankAccount, BankAccountCondition, BaseMapper<BankAccount,BankAccountCondition>> implements BankAccountDao{
	
	@Resource
	private BankAccountMapper bankAccountMapper;
	
	@Override
	protected BaseMapper<BankAccount, BankAccountCondition> daoSupport() {
		return bankAccountMapper;
	}

	@Override
	protected BankAccountCondition blankCondition() {
		return new BankAccountCondition();
	}

	@Override
	public int deleteById(Integer id) {
		return bankAccountMapper.deleteById(id);
	}

	@Override
	public List<BankAccount> getByPartyId(Integer partyId) {
		BankAccountCondition condition = new BankAccountCondition();
		condition.setPartyId(partyId);
		bankAccountMapper.selectByCondition(condition);
		return bankAccountMapper.selectByCondition(condition);
	}

	@Override
	public boolean hasBankAccount(Integer partyId) {
		BankAccountCondition condition = new BankAccountCondition();
		condition.setPartyId(partyId);
		List<BankAccount> list = bankAccountMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public BankAccount getLatestByPartyId(Integer partyId) {
		BankAccountCondition condition = new BankAccountCondition();
		condition.setPartyId(partyId);
		List<BankAccount> list = bankAccountMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
