
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import com._360pai.core.common.constants.PartyBlackListActionEnum;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.model.account.TUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.PartyBlackListActionCondition;
import com._360pai.core.dao.account.mapper.PartyBlackListActionMapper;
import com._360pai.core.model.account.PartyBlackListAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.PartyBlackListActionDao;

import java.util.List;

@Service
public class PartyBlackListActionDaoImpl extends AbstractDaoImpl<PartyBlackListAction, PartyBlackListActionCondition, BaseMapper<PartyBlackListAction,PartyBlackListActionCondition>> implements PartyBlackListActionDao{
	
	@Resource
	private PartyBlackListActionMapper partyBlackListActionMapper;
	
	@Override
	protected BaseMapper<PartyBlackListAction, PartyBlackListActionCondition> daoSupport() {
		return partyBlackListActionMapper;
	}

	@Override
	protected PartyBlackListActionCondition blankCondition() {
		return new PartyBlackListActionCondition();
	}

	@Override
	public PageInfo getListByPage(int page, int perPage, PartyBlackListActionCondition condition, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<PartyBlackListAction> list = partyBlackListActionMapper.selectByCondition(condition);
		return new PageInfo<>(list);
	}

	@Override
	public PartyBlackListAction getLatestByPartyId(Integer partyId) {
		PartyBlackListActionCondition condition = new PartyBlackListActionCondition();
		condition.setPartyId(partyId);
		condition.setAction(PartyBlackListActionEnum.LOCK.getKey());
		List<PartyBlackListAction> list = partyBlackListActionMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
