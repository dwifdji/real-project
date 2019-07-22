
package com.winback.core.dao._case.impl;

import javax.annotation.Resource;

import com.winback.core.dto._case.CaseStepRecordDto;
import com.winback.core.vo._case.TCaseStepRecordVO;
import org.springframework.stereotype.Service;

import com.winback.core.condition._case.TCaseStepRecordCondition;
import com.winback.core.dao._case.mapper.TCaseStepRecordMapper;
import com.winback.core.model._case.TCaseStepRecord;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao._case.TCaseStepRecordDao;

import java.util.List;

@Service
public class TCaseStepRecordDaoImpl extends AbstractDaoImpl<TCaseStepRecord, TCaseStepRecordCondition, BaseMapper<TCaseStepRecord,TCaseStepRecordCondition>> implements TCaseStepRecordDao{
	
	@Resource
	private TCaseStepRecordMapper tCaseStepRecordMapper;
	
	@Override
	protected BaseMapper<TCaseStepRecord, TCaseStepRecordCondition> daoSupport() {
		return tCaseStepRecordMapper;
	}

	@Override
	protected TCaseStepRecordCondition blankCondition() {
		return new TCaseStepRecordCondition();
	}

	@Override
	public List<TCaseStepRecordVO> getCaseStepRecordList(CaseStepRecordDto params) {

		return tCaseStepRecordMapper.getCaseStepRecordList(params);
	}
}
