package com._360pai.core.service.account.impl;

import com._360pai.core.condition.account.TUserApplyRecordCondition;
import com._360pai.core.dao.account.TUserApplyRecordDao;
import com._360pai.core.model.account.TUserApplyRecord;
import com._360pai.core.service.account.UserVerifyApplicationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserVerifyApplicationServiceImpl	implements UserVerifyApplicationService{

	@Autowired
	private TUserApplyRecordDao tUserApplyRecordDao;

	@Override
	public int saveUserApplyRecord(TUserApplyRecord userApplyRecord) {
		int i = tUserApplyRecordDao.insert(userApplyRecord);
		return i;
	}

	@Override
	public int updateUserApplyRecord(TUserApplyRecord userApplyRecord) {
		int i = tUserApplyRecordDao.updateById(userApplyRecord);
		return i;
	}


	@Override
	public PageInfo<TUserApplyRecord> getUserApplyRecord(TUserApplyRecordCondition userApplyRecord, int page, int perPage, String orderStr) {

		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotEmpty(orderStr)) {
			PageHelper.orderBy(orderStr);
		}
		List<TUserApplyRecord> list = tUserApplyRecordDao.selectList(userApplyRecord);
		return new PageInfo<TUserApplyRecord>(list);
	}

	@Override
	public TUserApplyRecord getUserApplyRecordById(Long id) {
		TUserApplyRecordCondition condition = new TUserApplyRecordCondition();
		condition.setId(id);
		return tUserApplyRecordDao.selectFirst(condition);
	}

	@Override
	public List<TUserApplyRecord> getApplyRecordByAccountId(Integer accountId, String status) {
		TUserApplyRecordCondition condition = new TUserApplyRecordCondition();
		condition.setAccountId(accountId);
		condition.setStatus(status);
		return tUserApplyRecordDao.selectList(condition);
	}
}