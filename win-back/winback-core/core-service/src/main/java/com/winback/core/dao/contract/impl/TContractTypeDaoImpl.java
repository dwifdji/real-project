
package com.winback.core.dao.contract.impl;

import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.condition.contract.TContractBigTypeCondition;
import com.winback.core.condition.contract.TContractTypeCondition;
import com.winback.core.dao.contract.TContractTypeDao;
import com.winback.core.dao.contract.mapper.TContractTypeMapper;
import com.winback.core.exception.BusinessException;
import com.winback.core.model.contract.TContractBigType;
import com.winback.core.model.contract.TContractType;
import com.winback.core.vo.operate.CaseBriefVO;
import com.winback.core.vo.operate.ContractTypeVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TContractTypeDaoImpl extends AbstractDaoImpl<TContractType, TContractTypeCondition, BaseMapper<TContractType,TContractTypeCondition>> implements TContractTypeDao{
	
	@Resource
	private TContractTypeMapper tContractTypeMapper;
	
	@Override
	protected BaseMapper<TContractType, TContractTypeCondition> daoSupport() {
		return tContractTypeMapper;
	}

	@Override
	protected TContractTypeCondition blankCondition() {
		return new TContractTypeCondition();
	}

	@Override
	public List<TContractType> findFrontBy(Integer bigTypeId) {
		TContractTypeCondition condition = new TContractTypeCondition();
		condition.setBigTypeId(bigTypeId);
		condition.setDeleteFlag(false);
		condition.setDisplay(true);
		return tContractTypeMapper.selectByCondition(condition);
	}

	@Override
	public List<TContractType> findBackBy(Integer bigTypeId) {
		TContractTypeCondition condition = new TContractTypeCondition();
		condition.setBigTypeId(bigTypeId);
		condition.setDeleteFlag(false);
		return tContractTypeMapper.selectByCondition(condition);
	}

	@Override
	public String getName(Integer id) {
		TContractType contractType = super.selectById(id);
		if (contractType != null) {
			return contractType.getName();
		}
		return "";
	}

	@Override
	public List<ContractTypeVO> getContractTypeList() {
		return tContractTypeMapper.getContractTypeList();
	}

	@Override
	public boolean isExist(String name) {
		TContractTypeCondition condition = new TContractTypeCondition();
		condition.setName(name);
		condition.setDeleteFlag(false);
		List<TContractType> list = tContractTypeMapper.selectByCondition(condition);
		return list.size() > 0;
	}

	@Override
	public TContractType save(String name, Integer bigTypeId) {
		TContractType contractBigType = new TContractType();
		contractBigType.setName(name);
		contractBigType.setBigTypeId(bigTypeId);
		int result = tContractTypeMapper.insert(contractBigType);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return contractBigType;
	}

	@Override
	public TContractType findBy(String name) {
		TContractTypeCondition condition = new TContractTypeCondition();
		condition.setName(name);
		condition.setDeleteFlag(false);
		List<TContractType> list = tContractTypeMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TContractType findBy(String name, Integer bigTypeId) {
		TContractTypeCondition condition = new TContractTypeCondition();
		condition.setName(name);
		condition.setBigTypeId(bigTypeId);
		condition.setDeleteFlag(false);
		List<TContractType> list = tContractTypeMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
