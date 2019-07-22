
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.condition.account.AgencyApplicationCondition;
import com._360pai.core.model.account.AgencyApplication;
import com._360pai.core.model.account.TAgency;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.TAgencyApplyRecordCondition;
import com._360pai.core.dao.account.mapper.TAgencyApplyRecordMapper;
import com._360pai.core.model.account.TAgencyApplyRecord;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.TAgencyApplyRecordDao;

import java.util.List;
import java.util.Map;

@Service
public class TAgencyApplyRecordDaoImpl extends AbstractDaoImpl<TAgencyApplyRecord, TAgencyApplyRecordCondition, BaseMapper<TAgencyApplyRecord,TAgencyApplyRecordCondition>> implements TAgencyApplyRecordDao{
	
	@Resource
	private TAgencyApplyRecordMapper tAgencyApplyRecordMapper;
	
	@Override
	protected BaseMapper<TAgencyApplyRecord, TAgencyApplyRecordCondition> daoSupport() {
		return tAgencyApplyRecordMapper;
	}

	@Override
	protected TAgencyApplyRecordCondition blankCondition() {
		return new TAgencyApplyRecordCondition();
	}

	@Override
	public PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TAgencyApplyRecord> list = tAgencyApplyRecordMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public TAgencyApplyRecord getApprovedByLicense(String license) {
		TAgencyApplyRecordCondition condition = new TAgencyApplyRecordCondition();
		condition.setLicense(license);
		condition.setStatus(AccountEnum.ApplyStatus.APPROVED.getKey());
		List<TAgencyApplyRecord> list = tAgencyApplyRecordMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
