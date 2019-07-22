
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import com._360pai.core.condition.account.TUserApplyRecordCondition;
import com._360pai.core.model.account.TUserApplyRecord;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.UserVerifyApplicationCondition;
import com._360pai.core.dao.account.mapper.UserVerifyApplicationMapper;
import com._360pai.core.model.account.UserVerifyApplication;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.UserVerifyApplicationDao;

import java.util.List;

@Service
public class UserVerifyApplicationDaoImpl extends AbstractDaoImpl<UserVerifyApplication, UserVerifyApplicationCondition, BaseMapper<UserVerifyApplication,UserVerifyApplicationCondition>> implements UserVerifyApplicationDao{
	
	@Resource
	private UserVerifyApplicationMapper userVerifyApplicationMapper;
	
	@Override
	protected BaseMapper<UserVerifyApplication, UserVerifyApplicationCondition> daoSupport() {
		return userVerifyApplicationMapper;
	}

	@Override
	protected UserVerifyApplicationCondition blankCondition() {
		return new UserVerifyApplicationCondition();
	}

	@Override
	public UserVerifyApplication getApprovedByCertificateNumber(String certificateNumber) {
		UserVerifyApplicationCondition condition = new UserVerifyApplicationCondition();
		condition.setCertificateNumber(certificateNumber);
		List<UserVerifyApplication> list = userVerifyApplicationMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
