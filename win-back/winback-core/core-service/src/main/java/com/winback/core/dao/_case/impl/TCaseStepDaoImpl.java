
package com.winback.core.dao._case.impl;

import javax.annotation.Resource;

import com.winback.core.dto._case.CaseStatusStepDto;
import com.winback.core.facade._case.req.CaseStepReq;
import com.winback.core.vo._case.*;
import com.winback.core.vo.operate.CaseStepVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.winback.core.condition._case.TCaseStepCondition;
import com.winback.core.dao._case.mapper.TCaseStepMapper;
import com.winback.core.model._case.TCaseStep;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao._case.TCaseStepDao;

import java.util.ArrayList;
import java.util.List;

@Service
public class TCaseStepDaoImpl extends AbstractDaoImpl<TCaseStep, TCaseStepCondition, BaseMapper<TCaseStep,TCaseStepCondition>> implements TCaseStepDao{
	
	@Resource
	private TCaseStepMapper tCaseStepMapper;
	
	@Override
	protected BaseMapper<TCaseStep, TCaseStepCondition> daoSupport() {
		return tCaseStepMapper;
	}

	@Override
	protected TCaseStepCondition blankCondition() {
		return new TCaseStepCondition();
	}

	@Override
	public List<TCaseStepGroupVO> getCaseSteps(String type) {

		return tCaseStepMapper.getCaseSteps(type);
	}

	@Override
	public TCaseStep getOrderDescByTypeGroup(String groupId) {
		return tCaseStepMapper.getOrderDescByTypeGroup(groupId);
	}

	@Override
	public void updateOrderDescByGroupId(Integer groupId, Integer orderDesc) {
		tCaseStepMapper.updateOrderDescByGroupId(groupId, orderDesc);
	}

	@Override
	public TCaseStepDetailVO getCaseStepById(String id) {
		return tCaseStepMapper.getCaseStepById(id);
	}

	@Override
	public List<TCaseStepSelectVO> getCaseStepNotBranch(String type) {
		return tCaseStepMapper.getCaseStepNotBranch(type);
	}

	@Override
	public List<TCaseCurrentStepVO> getCurrentSteps(String caseId, String type) {

		return tCaseStepMapper.getCurrentSteps(caseId, type);
	}

	@Override
	public List<TCaseStepMsgTemplateVO> getAllStepMsg(String type) {

		return tCaseStepMapper.getAllStepMsg(type);
	}

	@Override
	public List<TCaseStatusStepVO> getLawsuitManagements(CaseStatusStepDto params) {

		return tCaseStepMapper.getLawsuitManagements(params);
	}

	@Override
	public List<CaseStepVO> getAllCaseStep(String type) {
		return tCaseStepMapper.getAllCaseStep(type);
	}

	@Override
	public String getApplyPerson(String caseId) {
		return tCaseStepMapper.getApplyPerson(caseId);
	}

	@Override
	public void saveCaseBranchTypeList(List<TCaseStep> tCaseStepBranches) {
		 tCaseStepMapper.saveCaseBranchTypeList(tCaseStepBranches);
	}

	@Override
	public void deleteBatchCaseStepBranch(Integer id) {
		tCaseStepMapper.deleteBatchCaseStepBranch(id);
	}

	@Override
	public TCaseStep getFirstStep(String type) {
		return tCaseStepMapper.getFirstStep(type);
	}

	@Override
	public TCaseStep getLastLitigationStep(String type, String caseId) {
		return tCaseStepMapper.getLastLitigationStep(type, caseId);
	}

	@Override
	public List<TCaseStep> getAllSmaSteps(String type) {
		return tCaseStepMapper.getAllSmaSteps(type);
	}

	@Override
	public void batchUpdateCaseStep(ArrayList<TCaseStep> newCaseSteps) {
		tCaseStepMapper.batchUpdateCaseStep(newCaseSteps);
	}
}
