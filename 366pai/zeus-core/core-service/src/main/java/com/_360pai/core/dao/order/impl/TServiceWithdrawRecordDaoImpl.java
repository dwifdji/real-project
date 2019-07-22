
package com._360pai.core.dao.order.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.order.TServiceWithdrawRecordCondition;
import com._360pai.core.dao.order.mapper.TServiceWithdrawRecordMapper;
import com._360pai.core.model.order.TServiceWithdrawRecord;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.order.TServiceWithdrawRecordDao;

import java.util.List;
import java.util.Map;

@Service
public class TServiceWithdrawRecordDaoImpl extends AbstractDaoImpl<TServiceWithdrawRecord, TServiceWithdrawRecordCondition, BaseMapper<TServiceWithdrawRecord,TServiceWithdrawRecordCondition>> implements TServiceWithdrawRecordDao{
	
	@Resource
	private TServiceWithdrawRecordMapper tServiceWithdrawRecordMapper;
	
	@Override
	protected BaseMapper<TServiceWithdrawRecord, TServiceWithdrawRecordCondition> daoSupport() {
		return tServiceWithdrawRecordMapper;
	}

	@Override
	protected TServiceWithdrawRecordCondition blankCondition() {
		return new TServiceWithdrawRecordCondition();
	}

	@Override
	public List<TServiceWithdrawRecord> selectWithdrawRecordByParam(Map<String, Object> queryParam) {
		return tServiceWithdrawRecordMapper.selectWithdrawRecordByParam(queryParam);
	}

	@Override
	public List<TServiceWithdrawRecord> selectAdminWithdrawRecordByParam(Map<String, Object> queryParam) {
		return tServiceWithdrawRecordMapper.selectAdminWithdrawRecordByParam(queryParam);
	}
}
