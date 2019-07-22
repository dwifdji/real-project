
package com.winback.core.dao.account.impl;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.core.commons.constants.AccountEnum;
import com.winback.core.model.account.TAccount;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.winback.core.condition.account.TLawyerApplyRecordCondition;
import com.winback.core.dao.account.mapper.TLawyerApplyRecordMapper;
import com.winback.core.model.account.TLawyerApplyRecord;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.account.TLawyerApplyRecordDao;

import java.util.List;
import java.util.Map;

@Service
public class TLawyerApplyRecordDaoImpl extends AbstractDaoImpl<TLawyerApplyRecord, TLawyerApplyRecordCondition, BaseMapper<TLawyerApplyRecord,TLawyerApplyRecordCondition>> implements TLawyerApplyRecordDao{
	
	@Resource
	private TLawyerApplyRecordMapper tLawyerApplyRecordMapper;
	
	@Override
	protected BaseMapper<TLawyerApplyRecord, TLawyerApplyRecordCondition> daoSupport() {
		return tLawyerApplyRecordMapper;
	}

	@Override
	protected TLawyerApplyRecordCondition blankCondition() {
		return new TLawyerApplyRecordCondition();
	}

	@Override
	public boolean hasPendingApply(Integer accountId) {
		TLawyerApplyRecordCondition condition = new TLawyerApplyRecordCondition();
		condition.setAccountId(accountId);
		condition.setStatus(AccountEnum.ApplyStatus.PENDING.getKey());
		List<TLawyerApplyRecord> list = tLawyerApplyRecordMapper.selectByCondition(condition);
		return list.size() > 0 ? true : false;
	}

	@Override
	public TLawyerApplyRecord findLatestByAccountId(Integer accountId) {
		TLawyerApplyRecordCondition condition = new TLawyerApplyRecordCondition();
		condition.setAccountId(accountId);
		List<TLawyerApplyRecord> list = tLawyerApplyRecordMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageInfo<TLawyerApplyRecord> getList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TLawyerApplyRecord> list = tLawyerApplyRecordMapper.getList(params);
		return new PageInfo<>(list);
	}
}
