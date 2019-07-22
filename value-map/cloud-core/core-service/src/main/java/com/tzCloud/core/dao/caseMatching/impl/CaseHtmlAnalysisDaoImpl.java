
package com.tzCloud.core.dao.caseMatching.impl;

import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.caseMatching.CaseHtmlAnalysisCondition;
import com.tzCloud.core.dao.caseMatching.CaseHtmlAnalysisDao;
import com.tzCloud.core.dao.caseMatching.mapper.CaseHtmlAnalysisMapper;
import com.tzCloud.core.model.caseMatching.CaseHtmlAnalysis;
import com.tzCloud.core.model.caseMatching.CaseHtmlData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CaseHtmlAnalysisDaoImpl extends AbstractDaoImpl<CaseHtmlAnalysis, CaseHtmlAnalysisCondition, BaseMapper<CaseHtmlAnalysis,CaseHtmlAnalysisCondition>> implements CaseHtmlAnalysisDao {
	
	@Resource
	private CaseHtmlAnalysisMapper caseHtmlAnalysisMapper;
	
	@Override
	protected BaseMapper<CaseHtmlAnalysis, CaseHtmlAnalysisCondition> daoSupport() {
		return caseHtmlAnalysisMapper;
	}

	@Override
	protected CaseHtmlAnalysisCondition blankCondition() {
		return new CaseHtmlAnalysisCondition();
	}

	@Override
	public int insertOrUpdateAnalysisList(List<CaseHtmlAnalysis> list) {
		return caseHtmlAnalysisMapper.insertOrUpdateAnalysisList(list);
	}

	@Override
	public List<CaseHtmlData> findExist() {
		return caseHtmlAnalysisMapper.findExist();
	}

	@Override
	public List<CaseHtmlData> findByEarlyTime(String updateTime) {
		return caseHtmlAnalysisMapper.findByEarlyTime(updateTime);
	}

	@Override
	public int insertOrUpdateAnalysis(CaseHtmlAnalysis analysis) {
		return caseHtmlAnalysisMapper.insertOrUpdateAnalysis(analysis);
	}
}
