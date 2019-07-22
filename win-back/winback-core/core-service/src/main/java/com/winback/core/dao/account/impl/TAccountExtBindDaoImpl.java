
package com.winback.core.dao.account.impl;

import javax.annotation.Resource;

import com.winback.core.commons.constants.AccountEnum;
import org.springframework.stereotype.Service;

import com.winback.core.condition.account.TAccountExtBindCondition;
import com.winback.core.dao.account.mapper.TAccountExtBindMapper;
import com.winback.core.model.account.TAccountExtBind;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.account.TAccountExtBindDao;

import java.util.List;

@Service
public class TAccountExtBindDaoImpl extends AbstractDaoImpl<TAccountExtBind, TAccountExtBindCondition, BaseMapper<TAccountExtBind,TAccountExtBindCondition>> implements TAccountExtBindDao{
	
	@Resource
	private TAccountExtBindMapper tAccountExtBindMapper;
	
	@Override
	protected BaseMapper<TAccountExtBind, TAccountExtBindCondition> daoSupport() {
		return tAccountExtBindMapper;
	}

	@Override
	protected TAccountExtBindCondition blankCondition() {
		return new TAccountExtBindCondition();
	}

	@Override
	public TAccountExtBind findAppletByOpenId(String openId) {
		TAccountExtBindCondition condition = new TAccountExtBindCondition();
		condition.setExtType(AccountEnum.ExtType.APPLET.getKey());
		condition.setExtUserId(openId);
		List<TAccountExtBind> list = tAccountExtBindMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TAccountExtBind findAppletByAccountId(Integer accountId) {
		TAccountExtBindCondition condition = new TAccountExtBindCondition();
		condition.setExtType(AccountEnum.ExtType.APPLET.getKey());
		condition.setAccountId(accountId);
		List<TAccountExtBind> list = tAccountExtBindMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
