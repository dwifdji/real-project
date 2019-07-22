
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.UserCondition;
import com._360pai.core.dao.account.mapper.UserMapper;
import com._360pai.core.model.account.User;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.UserDao;

import java.util.List;

@Service
public class UserDaoImpl extends AbstractDaoImpl<User, UserCondition, BaseMapper<User,UserCondition>> implements UserDao{
	
	@Resource
	private UserMapper userMapper;
	
	@Override
	protected BaseMapper<User, UserCondition> daoSupport() {
		return userMapper;
	}

	@Override
	protected UserCondition blankCondition() {
		return new UserCondition();
	}

	@Override
	public User getByAccountId(Integer accountId) {
		UserCondition condition = new UserCondition();
		condition.setAccountId(accountId);
		List<User> list = userMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public User getByCertificateNumber(String certificateNumber) {
		UserCondition condition = new UserCondition();
		condition.setCertificateNumber(certificateNumber);
		List<User> list = userMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
