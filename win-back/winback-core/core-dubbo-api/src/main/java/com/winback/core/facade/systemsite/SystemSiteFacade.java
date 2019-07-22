package com.winback.core.facade.systemsite;

import com.winback.arch.common.ResponseModel;
import com.winback.core.facade.systemsite.req.SystemSiteReq;

public interface SystemSiteFacade {

    ResponseModel saveCaseSetting(SystemSiteReq.SaveCaseSiteReq req);

    ResponseModel updateOrDeleteCaseSetting(SystemSiteReq.UpdateCaseSiteReq req);

    ResponseModel getCaseSettingList(SystemSiteReq.CaseSiteListReq req);

    ResponseModel saveCaseStatusMsg(SystemSiteReq.CaseStatusMsgSaveReq req);

    ResponseModel updateOrDeleteCaseStatusMsg(SystemSiteReq.CaseStatusMsgUpdateeReq req);

    ResponseModel getCaseStatusMsgList(SystemSiteReq.CaseStatusListReq req);

    ResponseModel getCaseSettingById(SystemSiteReq.CaseSiteListReq req);

    ResponseModel getCaseStatusMsgById(SystemSiteReq.CaseStatusListReq req);

    ResponseModel getCaseMainStatusList();
}
