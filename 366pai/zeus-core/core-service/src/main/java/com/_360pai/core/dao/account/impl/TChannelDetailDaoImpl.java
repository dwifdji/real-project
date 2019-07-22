
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.TChannelDetailCondition;
import com._360pai.core.dao.account.mapper.TChannelDetailMapper;
import com._360pai.core.model.account.TChannelDetail;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.TChannelDetailDao;

@Service
public class TChannelDetailDaoImpl extends AbstractDaoImpl<TChannelDetail, TChannelDetailCondition, BaseMapper<TChannelDetail,TChannelDetailCondition>> implements TChannelDetailDao{
	
	@Resource
	private TChannelDetailMapper tChannelDetailMapper;
	
	@Override
	protected BaseMapper<TChannelDetail, TChannelDetailCondition> daoSupport() {
		return tChannelDetailMapper;
	}

	@Override
	protected TChannelDetailCondition blankCondition() {
		return new TChannelDetailCondition();
	}

}
