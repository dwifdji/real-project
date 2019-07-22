package com.winback.core.service.systemsite;

import com.github.pagehelper.PageInfo;
import com.winback.core.facade.systemsite.req.SystemSiteReq;
import com.winback.core.vo.systemsite.CaseSiteVO;

public interface CaseSettingService {

    Integer saveCaseSetting(SystemSiteReq.SaveCaseSiteReq req);

    Integer updateOrDeleteCaseSetting(SystemSiteReq.UpdateCaseSiteReq req);

    PageInfo getCaseSettingList(SystemSiteReq.CaseSiteListReq req);

    CaseSiteVO getCaseSettingById(SystemSiteReq.CaseSiteListReq req);
}
