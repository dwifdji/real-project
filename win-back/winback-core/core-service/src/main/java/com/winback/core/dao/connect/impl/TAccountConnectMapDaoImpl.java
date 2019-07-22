
package com.winback.core.dao.connect.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition.connect.TAccountConnectMapCondition;
import com.winback.core.dao.connect.mapper.TAccountConnectMapMapper;
import com.winback.core.model.connect.TAccountConnectMap;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.connect.TAccountConnectMapDao;

import java.util.List;

@Service
public class TAccountConnectMapDaoImpl extends AbstractDaoImpl<TAccountConnectMap, TAccountConnectMapCondition, BaseMapper<TAccountConnectMap,TAccountConnectMapCondition>> implements TAccountConnectMapDao{
	
	@Resource
	private TAccountConnectMapMapper tAccountConnectMapMapper;
	
	@Override
	protected BaseMapper<TAccountConnectMap, TAccountConnectMapCondition> daoSupport() {
		return tAccountConnectMapMapper;
	}

	@Override
	protected TAccountConnectMapCondition blankCondition() {
		return new TAccountConnectMapCondition();
	}

	@Override
	public TAccountConnectMap findBy(Integer accountId, String clientId) {
		TAccountConnectMapCondition condition = new TAccountConnectMapCondition();
		condition.setAccountId(accountId);
		condition.setClientId(clientId);
		List<TAccountConnectMap> list = tAccountConnectMapMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TAccountConnectMap findLatestBy(Integer accountId) {
		TAccountConnectMapCondition condition = new TAccountConnectMapCondition();
		condition.setAccountId(accountId);
		List<TAccountConnectMap> list = tAccountConnectMapMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
