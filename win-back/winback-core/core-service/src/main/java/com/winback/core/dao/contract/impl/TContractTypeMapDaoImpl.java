
package com.winback.core.dao.contract.impl;

import javax.annotation.Resource;

import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.exception.BusinessException;
import com.winback.core.model.contract.TContractType;
import org.springframework.stereotype.Service;

import com.winback.core.condition.contract.TContractTypeMapCondition;
import com.winback.core.dao.contract.mapper.TContractTypeMapMapper;
import com.winback.core.model.contract.TContractTypeMap;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.contract.TContractTypeMapDao;

import java.util.List;

@Service
public class TContractTypeMapDaoImpl extends AbstractDaoImpl<TContractTypeMap, TContractTypeMapCondition, BaseMapper<TContractTypeMap,TContractTypeMapCondition>> implements TContractTypeMapDao{
	
	@Resource
	private TContractTypeMapMapper tContractTypeMapMapper;
	
	@Override
	protected BaseMapper<TContractTypeMap, TContractTypeMapCondition> daoSupport() {
		return tContractTypeMapMapper;
	}

	@Override
	protected TContractTypeMapCondition blankCondition() {
		return new TContractTypeMapCondition();
	}

	@Override
	public String getFullName(Integer contractId) {
		return tContractTypeMapMapper.getFullName(contractId);
	}

	@Override
	public void saveContractTypeMap(Integer contractId, Integer contractTypeId) {
		TContractTypeMap contractTypeMap = new TContractTypeMap();
		contractTypeMap.setContractId(contractId);
		contractTypeMap.setContractTypeId(contractTypeId);
		int result = tContractTypeMapMapper.insert(contractTypeMap);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
	}

	@Override
	public void syncContractTypeMap(Integer contractId, Integer contractTypeId) {
		TContractTypeMapCondition condition = new TContractTypeMapCondition();
		condition.setContractId(contractId);
		condition.setDeleteFlag(false);
		List<TContractTypeMap> list = tContractTypeMapMapper.selectByCondition(condition);
		if (list.size() > 0) {
			TContractTypeMap contractTypeMap = list.get(0);
			if (!contractTypeMap.getContractTypeId().equals(contractTypeId)) {
				contractTypeMap.setDeleteFlag(true);
				int result =tContractTypeMapMapper.updateById(contractTypeMap);
				if (result == 0) {
					throw new BusinessException(ApiCallResult.FAILURE);
				}
				saveContractTypeMap(contractId, contractTypeId);
			}
		} else {
			saveContractTypeMap(contractId, contractTypeId);
		}
	}

	@Override
	public TContractType getContractType(Integer contractId) {
		return tContractTypeMapMapper.getContractType(contractId);
	}
}
