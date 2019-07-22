
package com.winback.core.dao.account.impl;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.exception.BusinessException;
import com.winback.core.model.account.TLawyerApplyRecord;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.winback.core.condition.account.TLawyerCondition;
import com.winback.core.dao.account.mapper.TLawyerMapper;
import com.winback.core.model.account.TLawyer;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.account.TLawyerDao;

import java.util.List;
import java.util.Map;

@Service
public class TLawyerDaoImpl extends AbstractDaoImpl<TLawyer, TLawyerCondition, BaseMapper<TLawyer,TLawyerCondition>> implements TLawyerDao{
	
	@Resource
	private TLawyerMapper tLawyerMapper;
	
	@Override
	protected BaseMapper<TLawyer, TLawyerCondition> daoSupport() {
		return tLawyerMapper;
	}

	@Override
	protected TLawyerCondition blankCondition() {
		return new TLawyerCondition();
	}

	@Override
	public TLawyer findByAccountId(Integer accountId) {
		TLawyerCondition condition = new TLawyerCondition();
		condition.setAccountId(accountId);
		List<TLawyer> list = tLawyerMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TLawyer createFromApply(TLawyerApplyRecord applyRecord) {
		TLawyer lawyer = new TLawyer();
		BeanUtils.copyProperties(applyRecord, lawyer);
		lawyer.setId(null);
		int result = tLawyerMapper.insert(lawyer);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return lawyer;
	}

	@Override
	public PageInfo<TLawyer> getList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TLawyer> list = tLawyerMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public boolean isExistLawyerLicenseNumber(String lawyerLicenseNumber) {
		TLawyerCondition condition = new TLawyerCondition();
		condition.setLawyerLicenseNumber(lawyerLicenseNumber);
		List<TLawyer> list = tLawyerMapper.selectByCondition(condition);
		return list.size() > 0;
	}
}
