
package com._360pai.core.dao.assistant.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.assistant.THallActivityCondition;
import com._360pai.core.dao.assistant.THallActivityDao;
import com._360pai.core.dao.assistant.mapper.THallActivityMapper;
import com._360pai.core.model.assistant.THallActivity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class THallActivityDaoImpl extends AbstractDaoImpl<THallActivity, THallActivityCondition, BaseMapper<THallActivity,THallActivityCondition>> implements THallActivityDao {
	
	@Resource
	private THallActivityMapper tHallActivityMapper;
	
	@Override
	protected BaseMapper<THallActivity, THallActivityCondition> daoSupport() {
		return tHallActivityMapper;
	}

	@Override
	protected THallActivityCondition blankCondition() {
		return new THallActivityCondition();
	}

    @Override
    public List<Map> getTHallActivityList(Integer id) {
		return tHallActivityMapper.selectHallActivityList(id);
	}
}
