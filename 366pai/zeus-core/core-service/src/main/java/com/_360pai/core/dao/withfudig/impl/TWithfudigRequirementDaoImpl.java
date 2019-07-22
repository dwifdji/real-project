
package com._360pai.core.dao.withfudig.impl;

import javax.annotation.Resource;

import com._360pai.core.facade.withfudig.resp.WithfudigRequirementDetailResp;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.withfudig.TWithfudigRequirementCondition;
import com._360pai.core.dao.withfudig.mapper.TWithfudigRequirementMapper;
import com._360pai.core.model.withfudig.TWithfudigRequirement;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.withfudig.TWithfudigRequirementDao;

import java.util.List;

@Service
public class TWithfudigRequirementDaoImpl extends AbstractDaoImpl<TWithfudigRequirement, TWithfudigRequirementCondition, BaseMapper<TWithfudigRequirement,TWithfudigRequirementCondition>> implements TWithfudigRequirementDao{
	
	@Resource
	private TWithfudigRequirementMapper tWithfudigRequirementMapper;
	
	@Override
	protected BaseMapper<TWithfudigRequirement, TWithfudigRequirementCondition> daoSupport() {
		return tWithfudigRequirementMapper;
	}

	@Override
	protected TWithfudigRequirementCondition blankCondition() {
		return new TWithfudigRequirementCondition();
	}

	@Override
	public TWithfudigRequirement selectByIdWithoutPay(Integer id) {
		return tWithfudigRequirementMapper.selectByIdWithoutPay(id);
	}

	@Override
	public PageInfo selectListForPlatform(int page, int perPage, TWithfudigRequirementCondition condition) {
		PageHelper.startPage(page, perPage);
		List<TWithfudigRequirement> list = tWithfudigRequirementMapper.selectListForPlatform(condition);
		return new PageInfo<>(list);
	}

	@Override
	public List<TWithfudigRequirement> selectFollowListForPlatform(TWithfudigRequirementCondition condition) {
		return tWithfudigRequirementMapper.selectFollowListForPlatform(condition);
	}

	@Override
	public List<TWithfudigRequirement> myRequirementList(TWithfudigRequirementCondition condition) {
		return tWithfudigRequirementMapper.myRequirementList(condition);
	}
}
