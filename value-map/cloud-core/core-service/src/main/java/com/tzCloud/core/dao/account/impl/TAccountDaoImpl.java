
package com.tzCloud.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.account.TAccountCondition;
import com.tzCloud.core.dao.account.mapper.TAccountMapper;
import com.tzCloud.core.model.account.TAccount;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.account.TAccountDao;

@Service
public class TAccountDaoImpl extends AbstractDaoImpl<TAccount, TAccountCondition, BaseMapper<TAccount,TAccountCondition>> implements TAccountDao{
	
	@Resource
	private TAccountMapper tAccountMapper;
	
	@Override
	protected BaseMapper<TAccount, TAccountCondition> daoSupport() {
		return tAccountMapper;
	}

	@Override
	protected TAccountCondition blankCondition() {
		return new TAccountCondition();
	}

}
