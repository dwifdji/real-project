
package com.winback.core.dao.contract.impl;

import javax.annotation.Resource;

import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.exception.BusinessException;
import org.springframework.stereotype.Service;

import com.winback.core.condition.contract.TContractBigTypeCondition;
import com.winback.core.dao.contract.mapper.TContractBigTypeMapper;
import com.winback.core.model.contract.TContractBigType;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.contract.TContractBigTypeDao;

import java.util.List;

@Service
public class TContractBigTypeDaoImpl extends AbstractDaoImpl<TContractBigType, TContractBigTypeCondition, BaseMapper<TContractBigType,TContractBigTypeCondition>> implements TContractBigTypeDao{
	
	@Resource
	private TContractBigTypeMapper tContractBigTypeMapper;
	
	@Override
	protected BaseMapper<TContractBigType, TContractBigTypeCondition> daoSupport() {
		return tContractBigTypeMapper;
	}

	@Override
	protected TContractBigTypeCondition blankCondition() {
		return new TContractBigTypeCondition();
	}

	@Override
	public List<TContractBigType> getFrontList() {
		TContractBigTypeCondition condition = new TContractBigTypeCondition();
		condition.setDeleteFlag(false);
		condition.setDisplay(true);
		return tContractBigTypeMapper.selectByCondition(condition);
	}

	@Override
	public List<TContractBigType> getBackList() {
		TContractBigTypeCondition condition = new TContractBigTypeCondition();
		condition.setDeleteFlag(false);
		return tContractBigTypeMapper.selectByCondition(condition);
	}

	@Override
	public boolean isExist(String name) {
		TContractBigTypeCondition condition = new TContractBigTypeCondition();
		condition.setName(name);
		condition.setDeleteFlag(false);
		List<TContractBigType> list = tContractBigTypeMapper.selectByCondition(condition);
		return list.size() > 0;
	}

	@Override
	public TContractBigType save(String name) {
		TContractBigType contractBigType = new TContractBigType();
		contractBigType.setName(name);
		int result = tContractBigTypeMapper.insert(contractBigType);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return contractBigType;
	}

	@Override
	public TContractBigType findBy(String name) {
		TContractBigTypeCondition condition = new TContractBigTypeCondition();
		condition.setName(name);
		condition.setDeleteFlag(false);
		List<TContractBigType> list = tContractBigTypeMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
