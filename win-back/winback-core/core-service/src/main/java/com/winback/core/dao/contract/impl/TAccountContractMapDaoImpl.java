
package com.winback.core.dao.contract.impl;

import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.condition.contract.TAccountContractMapCondition;
import com.winback.core.dao.contract.TAccountContractMapDao;
import com.winback.core.dao.contract.mapper.TAccountContractMapMapper;
import com.winback.core.model.contract.TAccountContractMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TAccountContractMapDaoImpl extends AbstractDaoImpl<TAccountContractMap, TAccountContractMapCondition, BaseMapper<TAccountContractMap,TAccountContractMapCondition>> implements TAccountContractMapDao{
	
	@Resource
	private TAccountContractMapMapper tAccountContractMapMapper;
	
	@Override
	protected BaseMapper<TAccountContractMap, TAccountContractMapCondition> daoSupport() {
		return tAccountContractMapMapper;
	}

	@Override
	protected TAccountContractMapCondition blankCondition() {
		return new TAccountContractMapCondition();
	}

	@Override
	public TAccountContractMap findBy(Integer accountId, Integer contractId) {
		TAccountContractMapCondition condition = new TAccountContractMapCondition();
		condition.setAccountId(accountId);
		condition.setContractId(contractId);
		condition.setDeleteFlag(false);
		List<TAccountContractMap> list = tAccountContractMapMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean hasBuy(Integer accountId, Integer contractId) {
		TAccountContractMapCondition condition = new TAccountContractMapCondition();
		condition.setAccountId(accountId);
		condition.setContractId(contractId);
		condition.setDeleteFlag(false);
		List<TAccountContractMap> list = tAccountContractMapMapper.selectByCondition(condition);
		return list.size() > 0;
	}

	@Override
	public List<Integer> getContractIdList(Integer accountId) {
		return tAccountContractMapMapper.getContractIdList(accountId);
	}
}
