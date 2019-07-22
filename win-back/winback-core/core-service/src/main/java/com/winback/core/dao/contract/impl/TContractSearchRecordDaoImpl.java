
package com.winback.core.dao.contract.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition.contract.TContractSearchRecordCondition;
import com.winback.core.dao.contract.mapper.TContractSearchRecordMapper;
import com.winback.core.model.contract.TContractSearchRecord;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.contract.TContractSearchRecordDao;

import java.util.List;

@Service
public class TContractSearchRecordDaoImpl extends AbstractDaoImpl<TContractSearchRecord, TContractSearchRecordCondition, BaseMapper<TContractSearchRecord,TContractSearchRecordCondition>> implements TContractSearchRecordDao{
	
	@Resource
	private TContractSearchRecordMapper tContractSearchRecordMapper;
	
	@Override
	protected BaseMapper<TContractSearchRecord, TContractSearchRecordCondition> daoSupport() {
		return tContractSearchRecordMapper;
	}

	@Override
	protected TContractSearchRecordCondition blankCondition() {
		return new TContractSearchRecordCondition();
	}

	@Override
	public void saveSearchRecord(Integer accountId, String keyword) {
		TContractSearchRecord searchRecord = new TContractSearchRecord();
		searchRecord.setAccountId(accountId);
		searchRecord.setKeyword(keyword);
		tContractSearchRecordMapper.insert(searchRecord);
	}

	@Override
	public List<String> getKeywordList(Integer accountId) {
		return tContractSearchRecordMapper.getKeywordList(accountId);
	}

	@Override
	public void clearSearchRecord(Integer accountId) {
		tContractSearchRecordMapper.clearSearchRecord(accountId);
	}
}
