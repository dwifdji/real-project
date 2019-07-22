
package com.winback.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition.assistant.TAppVersionUpdateCondition;
import com.winback.core.dao.assistant.mapper.TAppVersionUpdateMapper;
import com.winback.core.model.assistant.TAppVersionUpdate;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.assistant.TAppVersionUpdateDao;

import java.util.List;

@Service
public class TAppVersionUpdateDaoImpl extends AbstractDaoImpl<TAppVersionUpdate, TAppVersionUpdateCondition, BaseMapper<TAppVersionUpdate,TAppVersionUpdateCondition>> implements TAppVersionUpdateDao{
	
	@Resource
	private TAppVersionUpdateMapper tAppVersionUpdateMapper;
	
	@Override
	protected BaseMapper<TAppVersionUpdate, TAppVersionUpdateCondition> daoSupport() {
		return tAppVersionUpdateMapper;
	}

	@Override
	protected TAppVersionUpdateCondition blankCondition() {
		return new TAppVersionUpdateCondition();
	}

	@Override
	public TAppVersionUpdate findLatestBy(String deviceType) {
		TAppVersionUpdateCondition condition = new TAppVersionUpdateCondition();
		condition.setDeviceType(deviceType);
		condition.setDeleteFlag(false);
		List<TAppVersionUpdate> list = tAppVersionUpdateMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
