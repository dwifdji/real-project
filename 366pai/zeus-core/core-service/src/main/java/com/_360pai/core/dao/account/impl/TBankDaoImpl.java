
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.TBankCondition;
import com._360pai.core.dao.account.mapper.TBankMapper;
import com._360pai.core.model.account.TBank;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.TBankDao;

import java.util.List;

@Service
public class TBankDaoImpl extends AbstractDaoImpl<TBank, TBankCondition, BaseMapper<TBank,TBankCondition>> implements TBankDao{
	
	@Resource
	private TBankMapper tBankMapper;
	
	@Override
	protected BaseMapper<TBank, TBankCondition> daoSupport() {
		return tBankMapper;
	}

	@Override
	protected TBankCondition blankCondition() {
		return new TBankCondition();
	}

	@Override
	public TBank getByCode(String code) {
		TBankCondition condition = new TBankCondition();
		condition.setCode(code);
		List<TBank> list = tBankMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
