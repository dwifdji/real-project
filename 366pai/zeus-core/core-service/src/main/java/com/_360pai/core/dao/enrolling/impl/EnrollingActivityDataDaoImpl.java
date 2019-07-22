
package com._360pai.core.dao.enrolling.impl;

import javax.annotation.Resource;

import com._360pai.core.model.asset.AssetData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.enrolling.EnrollingActivityDataCondition;
import com._360pai.core.dao.enrolling.mapper.EnrollingActivityDataMapper;
import com._360pai.core.model.enrolling.EnrollingActivityData;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.enrolling.EnrollingActivityDataDao;

import java.util.List;

@Service
public class EnrollingActivityDataDaoImpl extends AbstractDaoImpl<EnrollingActivityData, EnrollingActivityDataCondition, BaseMapper<EnrollingActivityData,EnrollingActivityDataCondition>> implements EnrollingActivityDataDao{
	
	@Resource
	private EnrollingActivityDataMapper enrollingActivityDataMapper;
	
	@Override
	protected BaseMapper<EnrollingActivityData, EnrollingActivityDataCondition> daoSupport() {
		return enrollingActivityDataMapper;
	}

	@Override
	protected EnrollingActivityDataCondition blankCondition() {
		return new EnrollingActivityDataCondition();
	}

	@Override
	public PageInfo<EnrollingActivityData> find(int page, int perPage) {
		PageHelper.startPage(page, perPage);
		List<EnrollingActivityData> list = enrollingActivityDataMapper.selectAll();
		return new PageInfo<>(list);
	}
}
