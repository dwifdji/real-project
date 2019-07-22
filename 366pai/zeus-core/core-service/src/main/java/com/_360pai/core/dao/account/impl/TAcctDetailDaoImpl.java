
package com._360pai.core.dao.account.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TAcctDetailCondition;
import com._360pai.core.dao.account.TAcctDetailDao;
import com._360pai.core.dao.account.mapper.TAcctDetailMapper;
import com._360pai.core.facade.account.vo.CommissionVo;
import com._360pai.core.facade.account.vo.InviteCommissionVo;
import com._360pai.core.facade.account.vo.WithdrawAcctDetailVo;
import com._360pai.core.model.account.TAcctDetail;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TAcctDetailDaoImpl extends AbstractDaoImpl<TAcctDetail, TAcctDetailCondition, BaseMapper<TAcctDetail,TAcctDetailCondition>> implements TAcctDetailDao{
	
	@Resource
	private TAcctDetailMapper tAcctDetailMapper;
	
	@Override
	protected BaseMapper<TAcctDetail, TAcctDetailCondition> daoSupport() {
		return tAcctDetailMapper;
	}

	@Override
	protected TAcctDetailCondition blankCondition() {
		return new TAcctDetailCondition();
	}

	@Override
	public PageInfo getInviteCommissionList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<InviteCommissionVo> list = tAcctDetailMapper.getInviteCommissionList(params);
		return new PageInfo<>(list);
	}

	@Override
	public InviteCommissionVo getInviteCommission(Long id) {
		return tAcctDetailMapper.getInviteCommission(id);
	}

	@Override
	public PageInfo getMyCommissionList(int page, int perPage, Integer acctId, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TAcctDetail> list = tAcctDetailMapper.getMyCommissionList(acctId);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo getWithdrawList(int page, int perPage, Integer acctId) {
		PageHelper.startPage(page, perPage);
		List<TAcctDetail> list = tAcctDetailMapper.getWithdrawList(acctId);
		return new PageInfo<TAcctDetail>(list);
	}

	@Override
	public PageInfo getFrontListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TAcctDetail> list = tAcctDetailMapper.getFrontList(params);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TAcctDetail> list = tAcctDetailMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo getFirstVerifyWithdrawListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<WithdrawAcctDetailVo> list = tAcctDetailMapper.getFirstVerifyWithdrawList(params);
		return new PageInfo<>(list);
	}

	@Override
	public Map<String, Object> getFirstVerifyWithdrawSummaryInfo(Map<String, Object> params) {
		return tAcctDetailMapper.getFirstVerifyWithdrawSummaryInfo(params);
	}

	@Override
	public PageInfo getInvoiceVerifyWithdrawListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<WithdrawAcctDetailVo> list = tAcctDetailMapper.getInvoiceVerifyWithdrawList(params);
		return new PageInfo<>(list);
	}

	@Override
	public Map<String, Object> getInvoiceVerifyWithdrawSummaryInfo(Map<String, Object> params) {
		return tAcctDetailMapper.getInvoiceVerifyWithdrawSummaryInfo(params);
	}

	@Override
	public List<TAcctDetail> getNoBatchDetail() {
		return tAcctDetailMapper.getNoBatchDetail();
	}

	@Override
	public boolean hasUncompletedWithdrawRecords(Integer bankAccountId) {
		return tAcctDetailMapper.countUncompletedWithdrawRecords(bankAccountId) > 0  ? true : false;
	}

	@Override
	public boolean hasRelatedWithdrawRecords(Integer bankAccountId) {
		return tAcctDetailMapper.countRelatedWithdrawRecords(bankAccountId) > 0  ? true : false;
	}
}
