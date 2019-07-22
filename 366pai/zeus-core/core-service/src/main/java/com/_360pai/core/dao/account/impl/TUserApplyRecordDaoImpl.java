
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import com._360pai.core.condition.account.UserCondition;
import com._360pai.core.model.account.TAccount;
import com._360pai.core.model.account.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.TUserApplyRecordCondition;
import com._360pai.core.dao.account.mapper.TUserApplyRecordMapper;
import com._360pai.core.model.account.TUserApplyRecord;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.TUserApplyRecordDao;

import java.util.List;
import java.util.Map;

@Service
public class TUserApplyRecordDaoImpl extends AbstractDaoImpl<TUserApplyRecord, TUserApplyRecordCondition, BaseMapper<TUserApplyRecord,TUserApplyRecordCondition>> implements TUserApplyRecordDao{
	
	@Resource
	private TUserApplyRecordMapper tUserApplyRecordMapper;
	
	@Override
	protected BaseMapper<TUserApplyRecord, TUserApplyRecordCondition> daoSupport() {
		return tUserApplyRecordMapper;
	}

	@Override
	protected TUserApplyRecordCondition blankCondition() {
		return new TUserApplyRecordCondition();
	}

	@Override
	public PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TUserApplyRecord> list = tUserApplyRecordMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public TUserApplyRecord getApprovedByCertificateNumber(String certificateNumber) {
		TUserApplyRecordCondition condition = new TUserApplyRecordCondition();
		condition.setCertificateNumber(certificateNumber);
		List<TUserApplyRecord> list = tUserApplyRecordMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<TUserApplyRecord> getByAccountId(Integer accountId) {
		TUserApplyRecordCondition condition = new TUserApplyRecordCondition();
		condition.setAccountId(accountId);
		return tUserApplyRecordMapper.selectByCondition(condition);
	}
}
