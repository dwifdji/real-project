package com.winback.core.service.systemsite;

import com.github.pagehelper.PageInfo;
import com.winback.core.facade.systemsite.req.SystemSiteReq;
import com.winback.core.vo.systemsite.CaseStatusMsgVO;

public interface CaseStatusMsgTemplateService {

    Integer saveCaseStatusMsg(SystemSiteReq.CaseStatusMsgSaveReq req);

    Integer updateOrDeleteCaseStatusMsg(SystemSiteReq.CaseStatusMsgUpdateeReq req);

    PageInfo getCaseStatusMsgList(SystemSiteReq.CaseStatusListReq req);

    CaseStatusMsgVO getCaseStatusMsgById(SystemSiteReq.CaseStatusListReq req);
}
