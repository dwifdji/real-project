
package com.winback.core.dao._case.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition._case.TCaseStatusUpdateRecordCondition;
import com.winback.core.dao._case.mapper.TCaseStatusUpdateRecordMapper;
import com.winback.core.model._case.TCaseStatusUpdateRecord;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao._case.TCaseStatusUpdateRecordDao;

import java.util.List;

@Service
public class TCaseStatusUpdateRecordDaoImpl extends AbstractDaoImpl<TCaseStatusUpdateRecord, TCaseStatusUpdateRecordCondition, BaseMapper<TCaseStatusUpdateRecord,TCaseStatusUpdateRecordCondition>> implements TCaseStatusUpdateRecordDao{
	
	@Resource
	private TCaseStatusUpdateRecordMapper tCaseStatusUpdateRecordMapper;
	
	@Override
	protected BaseMapper<TCaseStatusUpdateRecord, TCaseStatusUpdateRecordCondition> daoSupport() {
		return tCaseStatusUpdateRecordMapper;
	}

	@Override
	protected TCaseStatusUpdateRecordCondition blankCondition() {
		return new TCaseStatusUpdateRecordCondition();
	}


	@Override
	public List<TCaseStatusUpdateRecord> getPrecheckRecord(String caseId) {
		return tCaseStatusUpdateRecordMapper.getPrecheckRecord(caseId);
	}

	@Override
	public List<TCaseStatusUpdateRecord> getRiskcheckRecord(String caseId) {
		return tCaseStatusUpdateRecordMapper.getRiskcheckRecord(caseId);
	}

	@Override
	public List<TCaseStatusUpdateRecord> getSignContractRecord(String caseId) {
		return tCaseStatusUpdateRecordMapper.getSignContractRecord(caseId);
	}

	@Override
	public List<TCaseStatusUpdateRecord> getStepRecordListByCaseId(String caseId, String recordType) {
		return tCaseStatusUpdateRecordMapper.getStepRecordListByCaseId(caseId,recordType);
	}
}
