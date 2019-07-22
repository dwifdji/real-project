
package com.winback.core.dao.account.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.condition.account.TFranchiseeCondition;
import com.winback.core.dao.account.TFranchiseeDao;
import com.winback.core.dao.account.mapper.TFranchiseeMapper;
import com.winback.core.exception.BusinessException;
import com.winback.core.model._case.TCase;
import com.winback.core.model.account.TAccount;
import com.winback.core.model.account.TFranchisee;
import com.winback.core.model.account.TFranchiseeApplyRecord;
import com.winback.core.model.account.TLawyer;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TFranchiseeDaoImpl extends AbstractDaoImpl<TFranchisee, TFranchiseeCondition, BaseMapper<TFranchisee,TFranchiseeCondition>> implements TFranchiseeDao{
	
	@Resource
	private TFranchiseeMapper tFranchiseeMapper;
	
	@Override
	protected BaseMapper<TFranchisee, TFranchiseeCondition> daoSupport() {
		return tFranchiseeMapper;
	}

	@Override
	protected TFranchiseeCondition blankCondition() {
		return new TFranchiseeCondition();
	}

	@Override
	public TFranchisee findByAccountId(Integer accountId) {
		TFranchiseeCondition condition = new TFranchiseeCondition();
		condition.setAccountId(accountId);
		List<TFranchisee> list = tFranchiseeMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TFranchisee createFromApply(TFranchiseeApplyRecord applyRecord) {
		TFranchisee franchisee = new TFranchisee();
		franchisee.setAccountId(applyRecord.getAccountId());
		franchisee.setName(applyRecord.getName());
		franchisee.setType(applyRecord.getType());
		franchisee.setCertificateNumber(applyRecord.getCertificateNumber());
		franchisee.setCertificateFrontImg(applyRecord.getCertificateFrontImg());
		franchisee.setCertificateBackImg(applyRecord.getCertificateBackImg());
		franchisee.setLicenseImg(applyRecord.getLicenseImg());
		franchisee.setLicenseNumber(applyRecord.getLicenseNumber());
		franchisee.setSelfIntroduction(applyRecord.getSelfIntroduction());
		int result = tFranchiseeMapper.insert(franchisee);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return franchisee;
	}

	@Override
	public PageInfo<TFranchisee> getList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TFranchisee> list = tFranchiseeMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<TAccount> getInviteCustomerList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TAccount> list = tFranchiseeMapper.getInviteCustomerList(params);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<TCase> getInviteCaseList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TCase> list = tFranchiseeMapper.getInviteCaseList(params);
		return new PageInfo<>(list);
	}
}
