package com.winback.core.facade._case;

import com.winback.arch.common.ResponseModel;
import com.winback.core.facade._case.req.CaseStepReq;

/**
 * create by liuhaolei on 2019-1-17
 * 后台案件诉讼以及执行处理facade
 */
public interface CaseStepAdminFacade {
    ResponseModel getCaseSteps(CaseStepReq.CaseStepListReq caseStepListReq);

    ResponseModel saveCaseStep(CaseStepReq.SaveCaseStepReq saveCaseStepReq);

    ResponseModel updateCaseStep(CaseStepReq.UpdateOrDeleteReq req);

    ResponseModel deleteCaseStep(CaseStepReq.UpdateOrDeleteReq req);

    ResponseModel updateCaseStepBranch(CaseStepReq.UpdateBranchReq req);

    ResponseModel saveStepTemplate(CaseStepReq.StepTemplateSaveReq req);

    ResponseModel updateOrDeleteStepTemplate(CaseStepReq.StepTemplateUpdateReq req);

    ResponseModel getTemplateByStepId(CaseStepReq.CaseTemplateReq req);

    ResponseModel getCaseStepById(CaseStepReq.CaseStepListReq caseStepListReq);

    ResponseModel getCaseStepNotBranch(CaseStepReq.CaseStepListReq req);

    ResponseModel saveCaseStepRecord(CaseStepReq.CaseStepRecordSaveReq req);

    ResponseModel getCaseStepRecordList(CaseStepReq.CaseStepRecordListReq req);

    ResponseModel getCurrentStepMsg(CaseStepReq.CaseStepRecordListReq req);

    ResponseModel getLawsuitManagements(CaseStepReq.StatusCaseReq statusCaseReq);

}
