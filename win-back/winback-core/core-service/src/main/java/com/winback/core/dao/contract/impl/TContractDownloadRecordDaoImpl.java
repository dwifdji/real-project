
package com.winback.core.dao.contract.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition.contract.TContractDownloadRecordCondition;
import com.winback.core.dao.contract.mapper.TContractDownloadRecordMapper;
import com.winback.core.model.contract.TContractDownloadRecord;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.contract.TContractDownloadRecordDao;

import java.util.List;

@Service
public class TContractDownloadRecordDaoImpl extends AbstractDaoImpl<TContractDownloadRecord, TContractDownloadRecordCondition, BaseMapper<TContractDownloadRecord,TContractDownloadRecordCondition>> implements TContractDownloadRecordDao{
	
	@Resource
	private TContractDownloadRecordMapper tContractDownloadRecordMapper;
	
	@Override
	protected BaseMapper<TContractDownloadRecord, TContractDownloadRecordCondition> daoSupport() {
		return tContractDownloadRecordMapper;
	}

	@Override
	protected TContractDownloadRecordCondition blankCondition() {
		return new TContractDownloadRecordCondition();
	}

	@Override
	public String getLatestEmail(Integer accountId) {
		return tContractDownloadRecordMapper.getLatestEmail(accountId);
	}

	@Override
	public void saveDownloadRecord(Integer accountId, Integer contractId) {
		TContractDownloadRecord downloadRecord = new TContractDownloadRecord();
		downloadRecord.setAccountId(accountId);
		downloadRecord.setContractId(contractId);
		tContractDownloadRecordMapper.insert(downloadRecord);
	}

	@Override
	public void saveDownloadRecord(Integer accountId, String email, List<Integer> contractIds) {
		for (Integer contractId : contractIds) {
			TContractDownloadRecord downloadRecord = new TContractDownloadRecord();
			downloadRecord.setAccountId(accountId);
			downloadRecord.setEmail(email);
			downloadRecord.setContractId(contractId);
			tContractDownloadRecordMapper.insert(downloadRecord);
		}
	}

	@Override
	public void saveDownloadRecord(Integer accountId, String email, Integer contractId) {
		TContractDownloadRecord downloadRecord = new TContractDownloadRecord();
		downloadRecord.setAccountId(accountId);
		downloadRecord.setEmail(email);
		downloadRecord.setContractId(contractId);
		tContractDownloadRecordMapper.insert(downloadRecord);
	}
}
