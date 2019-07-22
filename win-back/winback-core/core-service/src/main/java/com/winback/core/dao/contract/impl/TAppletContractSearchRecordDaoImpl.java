
package com.winback.core.dao.contract.impl;

import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.condition.contract.TAppletContractSearchRecordCondition;
import com.winback.core.dao.contract.TAppletContractSearchRecordDao;
import com.winback.core.dao.contract.mapper.TAppletContractSearchRecordMapper;
import com.winback.core.model.contract.TAppletContractSearchRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TAppletContractSearchRecordDaoImpl extends AbstractDaoImpl<TAppletContractSearchRecord, TAppletContractSearchRecordCondition, BaseMapper<TAppletContractSearchRecord,TAppletContractSearchRecordCondition>> implements TAppletContractSearchRecordDao{
	
	@Resource
	private TAppletContractSearchRecordMapper tAppletContractSearchRecordMapper;
	
	@Override
	protected BaseMapper<TAppletContractSearchRecord, TAppletContractSearchRecordCondition> daoSupport() {
		return tAppletContractSearchRecordMapper;
	}

	@Override
	protected TAppletContractSearchRecordCondition blankCondition() {
		return new TAppletContractSearchRecordCondition();
	}

	@Override
	public void saveSearchRecord(Integer extBindId, String keyword) {
		TAppletContractSearchRecord searchRecord = new TAppletContractSearchRecord();
		searchRecord.setExtBindId(extBindId);
		searchRecord.setKeyword(keyword);
		tAppletContractSearchRecordMapper.insert(searchRecord);
	}

	@Override
	public List<String> getKeywordList(Integer extBindId) {
		return tAppletContractSearchRecordMapper.getKeywordList(extBindId);
	}

	@Override
	public void clearSearchRecord(Integer extBindId) {
		tAppletContractSearchRecordMapper.clearSearchRecord(extBindId);
	}

	@Override
	public void deleteSearchRecord(Integer extBindId, String keyword) {
		tAppletContractSearchRecordMapper.deleteSearchRecord(extBindId, keyword);
	}
}
