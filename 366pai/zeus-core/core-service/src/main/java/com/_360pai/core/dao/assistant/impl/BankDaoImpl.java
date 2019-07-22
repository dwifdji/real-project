
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.BankCondition;
import com._360pai.core.dao.assistant.mapper.BankMapper;
import com._360pai.core.model.assistant.Bank;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.BankDao;

import java.util.List;

@Service
public class BankDaoImpl extends AbstractDaoImpl<Bank, BankCondition, BaseMapper<Bank,BankCondition>> implements BankDao{
	
	@Resource
	private BankMapper bankMapper;
	
	@Override
	protected BaseMapper<Bank, BankCondition> daoSupport() {
		return bankMapper;
	}

	@Override
	protected BankCondition blankCondition() {
		return new BankCondition();
	}

	@Override
	public Bank getById(Integer id) {
		BankCondition condition = new BankCondition();
		condition.setId(id);
		List<Bank> list = bankMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public String getName(Integer id) {
		if (id == null) {
			return "";
		}
		Bank bank = super.selectById(id);
		if (bank != null) {
			return bank.getName();
		}
		return "";
	}
}
