
package com.winback.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition.account.TAccountContactsCondition;
import com.winback.core.dao.account.mapper.TAccountContactsMapper;
import com.winback.core.model.account.TAccountContacts;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.account.TAccountContactsDao;

import java.util.List;

@Service
public class TAccountContactsDaoImpl extends AbstractDaoImpl<TAccountContacts, TAccountContactsCondition, BaseMapper<TAccountContacts,TAccountContactsCondition>> implements TAccountContactsDao{
	
	@Resource
	private TAccountContactsMapper tAccountContactsMapper;
	
	@Override
	protected BaseMapper<TAccountContacts, TAccountContactsCondition> daoSupport() {
		return tAccountContactsMapper;
	}

	@Override
	protected TAccountContactsCondition blankCondition() {
		return new TAccountContactsCondition();
	}

	@Override
	public List<TAccountContacts> find(Integer accountId) {
		TAccountContactsCondition condition = new TAccountContactsCondition();
		condition.setAccountId(accountId);
		condition.setDeleteFlag(false);
		return tAccountContactsMapper.selectByCondition(condition);
	}

	@Override
	public int batchSave(List<TAccountContacts> list) {
		return tAccountContactsMapper.batchSave(list);
	}
}
