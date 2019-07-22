
package com._360pai.core.dao.order.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.order.TServiceAccountBankCondition;
import com._360pai.core.dao.order.mapper.TServiceAccountBankMapper;
import com._360pai.core.model.order.TServiceAccountBank;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.order.TServiceAccountBankDao;

@Service
public class TServiceAccountBankDaoImpl extends AbstractDaoImpl<TServiceAccountBank, TServiceAccountBankCondition, BaseMapper<TServiceAccountBank,TServiceAccountBankCondition>> implements TServiceAccountBankDao{
	
	@Resource
	private TServiceAccountBankMapper tServiceAccountBankMapper;
	
	@Override
	protected BaseMapper<TServiceAccountBank, TServiceAccountBankCondition> daoSupport() {
		return tServiceAccountBankMapper;
	}

	@Override
	protected TServiceAccountBankCondition blankCondition() {
		return new TServiceAccountBankCondition();
	}

	@Override
	public int updateByPartyId(TServiceAccountBank bank) {
		return tServiceAccountBankMapper.updateByPartyId(bank);
	}
}
