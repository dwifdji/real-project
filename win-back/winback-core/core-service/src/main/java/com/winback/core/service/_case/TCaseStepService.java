package com.winback.core.service._case;

import com.github.pagehelper.PageInfo;
import com.winback.core.facade._case.req.CaseStepReq;
import com.winback.core.model._case.TCaseStep;
import com.winback.core.vo._case.*;

import java.util.List;

public interface TCaseStepService {

    PageInfo getCaseSteps(CaseStepReq.CaseStepListReq caseStepListReq);

    Integer saveCaseStep(CaseStepReq.SaveCaseStepReq saveCaseStepReq);

    Integer updateCaseStep(CaseStepReq.UpdateOrDeleteReq req);

    Integer deleteCaseStep(CaseStepReq.UpdateOrDeleteReq req);

    Integer updateCaseStepBranch(CaseStepReq.UpdateBranchReq req);

    Integer saveStepTemplate(CaseStepReq.StepTemplateSaveReq req);

    Integer updateOrDeleteStepTemplate(CaseStepReq.StepTemplateUpdateReq req);

    List<TCaseStepNoticeTemplateVO> getTemplateByStepId(CaseStepReq.CaseTemplateReq req);

    TCaseStepDetailVO getCaseStepById(String id);

    List<TCaseStepSelectVO> getCaseStepNotBranch(CaseStepReq.CaseStepListReq req);

    Integer saveCaseStepRecord(CaseStepReq.CaseStepRecordSaveReq req);

    PageInfo getCaseStepRecordList(CaseStepReq.CaseStepRecordListReq req);

    List<TCaseCurrentStepVO> getCurrentSteps(CaseStepReq.CaseStepRecordListReq req);

    List<TCaseStepMsgTemplateVO> getAllStepMsg(CaseStepReq.CaseStepRecordListReq req);

    PageInfo getLawsuitManagements(CaseStepReq.StatusCaseReq statusCaseReq);

    String getApplyPerson(String caseId);

    TCaseStep getFirstStep(String type);

    void updateNextIds(String type, String id);
}
