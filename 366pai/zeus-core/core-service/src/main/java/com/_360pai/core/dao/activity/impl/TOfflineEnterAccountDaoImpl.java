
package com._360pai.core.dao.activity.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.activity.TOfflineEnterAccountCondition;
import com._360pai.core.dao.activity.mapper.TOfflineEnterAccountMapper;
import com._360pai.core.model.activity.TOfflineEnterAccount;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.activity.TOfflineEnterAccountDao;

@Service
public class TOfflineEnterAccountDaoImpl extends AbstractDaoImpl<TOfflineEnterAccount, TOfflineEnterAccountCondition, BaseMapper<TOfflineEnterAccount,TOfflineEnterAccountCondition>> implements TOfflineEnterAccountDao{
	
	@Resource
	private TOfflineEnterAccountMapper tOfflineEnterAccountMapper;
	
	@Override
	protected BaseMapper<TOfflineEnterAccount, TOfflineEnterAccountCondition> daoSupport() {
		return tOfflineEnterAccountMapper;
	}

	@Override
	protected TOfflineEnterAccountCondition blankCondition() {
		return new TOfflineEnterAccountCondition();
	}

}
