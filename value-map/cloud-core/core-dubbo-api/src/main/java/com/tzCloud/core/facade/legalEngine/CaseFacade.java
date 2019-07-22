package com.tzCloud.core.facade.legalEngine;

import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.core.facade.legalEngine.req.CaseSearchReq;
import com.tzCloud.core.facade.legalEngine.vo.CaseHtmlDataVo;

/**
 * @author xdrodger
 * @Title: CaseFacade
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019-04-19 13:19
 */
public interface CaseFacade {

    ResponseModel searchCase(CaseSearchReq.SearchListReq req);

    ResponseModel searchCaseSidebar(CaseSearchReq.SearchListReq req);

    ResponseModel getCaseDetail(CaseSearchReq.BaseReq req);

    CaseHtmlDataVo getByDocId(String docId);

}
