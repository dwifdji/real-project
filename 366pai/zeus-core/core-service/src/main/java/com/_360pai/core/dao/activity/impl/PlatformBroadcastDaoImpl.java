
package com._360pai.core.dao.activity.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.activity.PlatformBroadcastCondition;
import com._360pai.core.dao.activity.mapper.PlatformBroadcastMapper;
import com._360pai.core.model.activity.PlatformBroadcast;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.activity.PlatformBroadcastDao;

import java.util.List;

@Service
public class PlatformBroadcastDaoImpl extends AbstractDaoImpl<PlatformBroadcast, PlatformBroadcastCondition, BaseMapper<PlatformBroadcast,PlatformBroadcastCondition>> implements PlatformBroadcastDao{
	
	@Resource
	private PlatformBroadcastMapper platformBroadcastMapper;
	
	@Override
	protected BaseMapper<PlatformBroadcast, PlatformBroadcastCondition> daoSupport() {
		return platformBroadcastMapper;
	}

	@Override
	protected PlatformBroadcastCondition blankCondition() {
		return new PlatformBroadcastCondition();
	}

    @Override
    public List<PlatformBroadcast> getBroadcastList() {
        return platformBroadcastMapper.getBroadcastList();
    }
}
