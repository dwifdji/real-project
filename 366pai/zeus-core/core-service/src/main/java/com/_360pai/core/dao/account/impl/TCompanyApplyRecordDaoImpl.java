
package com._360pai.core.dao.account.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.condition.account.CompanyVerifyApplicationCondition;
import com._360pai.core.condition.account.TCompanyApplyRecordCondition;
import com._360pai.core.dao.account.TCompanyApplyRecordDao;
import com._360pai.core.dao.account.mapper.TCompanyApplyRecordMapper;
import com._360pai.core.model.account.CompanyVerifyApplication;
import com._360pai.core.model.account.TCompanyApplyRecord;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TCompanyApplyRecordDaoImpl extends AbstractDaoImpl<TCompanyApplyRecord, TCompanyApplyRecordCondition, BaseMapper<TCompanyApplyRecord,TCompanyApplyRecordCondition>> implements TCompanyApplyRecordDao{
	
	@Resource
	private TCompanyApplyRecordMapper tCompanyApplyRecordMapper;
	
	@Override
	protected BaseMapper<TCompanyApplyRecord, TCompanyApplyRecordCondition> daoSupport() {
		return tCompanyApplyRecordMapper;
	}

	@Override
	protected TCompanyApplyRecordCondition blankCondition() {
		return new TCompanyApplyRecordCondition();
	}

	@Override
	public PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TCompanyApplyRecord> list = tCompanyApplyRecordMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public TCompanyApplyRecord getApprovedByLicense(String license) {
		TCompanyApplyRecordCondition condition = new TCompanyApplyRecordCondition();
		condition.setLicense(license);
		condition.setStatus(AccountEnum.ApplyStatus.APPROVED.getKey());
		List<TCompanyApplyRecord> list = tCompanyApplyRecordMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<TCompanyApplyRecord> getByAccountId(Integer accountId) {
		TCompanyApplyRecordCondition condition = new TCompanyApplyRecordCondition();
		condition.setAccountId(accountId);
		return tCompanyApplyRecordMapper.selectByCondition(condition);
	}
}
