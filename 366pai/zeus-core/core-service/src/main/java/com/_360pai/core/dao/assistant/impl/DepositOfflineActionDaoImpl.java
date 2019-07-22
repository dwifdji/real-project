
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import com._360pai.core.facade.assistant.vo.DepositVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.DepositOfflineActionCondition;
import com._360pai.core.dao.assistant.mapper.DepositOfflineActionMapper;
import com._360pai.core.model.assistant.DepositOfflineAction;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.DepositOfflineActionDao;

import java.util.List;
import java.util.Map;

@Service
public class DepositOfflineActionDaoImpl extends AbstractDaoImpl<DepositOfflineAction, DepositOfflineActionCondition, BaseMapper<DepositOfflineAction,DepositOfflineActionCondition>> implements DepositOfflineActionDao{
	
	@Resource
	private DepositOfflineActionMapper depositOfflineActionMapper;
	
	@Override
	protected BaseMapper<DepositOfflineAction, DepositOfflineActionCondition> daoSupport() {
		return depositOfflineActionMapper;
	}

	@Override
	protected DepositOfflineActionCondition blankCondition() {
		return new DepositOfflineActionCondition();
	}

	@Override
	public PageInfo getList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<DepositVo> list = depositOfflineActionMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo getRefundList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<DepositVo> list = depositOfflineActionMapper.getRefundList(params);
		return new PageInfo<>(list);
	}
}
