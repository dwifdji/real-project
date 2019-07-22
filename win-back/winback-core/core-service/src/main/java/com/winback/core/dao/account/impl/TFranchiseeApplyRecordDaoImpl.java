
package com.winback.core.dao.account.impl;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.core.commons.constants.AccountEnum;
import com.winback.core.condition.account.TLawyerApplyRecordCondition;
import com.winback.core.model.account.TLawyerApplyRecord;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.winback.core.condition.account.TFranchiseeApplyRecordCondition;
import com.winback.core.dao.account.mapper.TFranchiseeApplyRecordMapper;
import com.winback.core.model.account.TFranchiseeApplyRecord;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.account.TFranchiseeApplyRecordDao;

import java.util.List;
import java.util.Map;

@Service
public class TFranchiseeApplyRecordDaoImpl extends AbstractDaoImpl<TFranchiseeApplyRecord, TFranchiseeApplyRecordCondition, BaseMapper<TFranchiseeApplyRecord,TFranchiseeApplyRecordCondition>> implements TFranchiseeApplyRecordDao{
	
	@Resource
	private TFranchiseeApplyRecordMapper tFranchiseeApplyRecordMapper;
	
	@Override
	protected BaseMapper<TFranchiseeApplyRecord, TFranchiseeApplyRecordCondition> daoSupport() {
		return tFranchiseeApplyRecordMapper;
	}

	@Override
	protected TFranchiseeApplyRecordCondition blankCondition() {
		return new TFranchiseeApplyRecordCondition();
	}

	@Override
	public boolean hasPendingApply(Integer accountId) {
		TFranchiseeApplyRecordCondition condition = new TFranchiseeApplyRecordCondition();
		condition.setAccountId(accountId);
		condition.setStatus(AccountEnum.ApplyStatus.PENDING.getKey());
		List<TFranchiseeApplyRecord> list = tFranchiseeApplyRecordMapper.selectByCondition(condition);
		return list.size() > 0 ? true : false;
	}

	@Override
	public TFranchiseeApplyRecord findLatestByAccountId(Integer accountId) {
		TFranchiseeApplyRecordCondition condition = new TFranchiseeApplyRecordCondition();
		condition.setAccountId(accountId);
		List<TFranchiseeApplyRecord> list = tFranchiseeApplyRecordMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageInfo<TFranchiseeApplyRecord> getList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TFranchiseeApplyRecord> list = tFranchiseeApplyRecordMapper.getList(params);
		return new PageInfo<>(list);
	}
}
