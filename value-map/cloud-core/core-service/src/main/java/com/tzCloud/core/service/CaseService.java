package com.tzCloud.core.service;

import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.core.facade.legalEngine.req.CaseSearchReq;
import com.tzCloud.core.facade.legalEngine.vo.Case;
import com.tzCloud.core.facade.legalEngine.vo.CaseDetailVo;
import com.tzCloud.core.facade.legalEngine.vo.JudgePerson;
import com.tzCloud.core.model.caseMatching.CaseHtmlData;
import com.tzCloud.core.model.legalEngine.TCase;
import com.tzCloud.core.model.legalEngine.TJudgePerson;

/**
 * @author xdrodger
 * @Title: CaseService
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/4/24 16:34
 */
public interface CaseService {
    ResponseModel searchCase(CaseSearchReq.SearchListReq req);

    ResponseModel searchCaseSidebar(CaseSearchReq.SearchListReq req);

    Case convertToCase(TCase model);

    String getBriefName(Integer briefId);

    JudgePerson getJudgePerson(String docId);

    JudgePerson convertToJudgePerson(TJudgePerson model);

    CaseDetailVo getCaseDetail(CaseSearchReq.BaseReq req);

    Case searchESByDocId(String docId);

    CaseHtmlData getHtmlData(String docId);

    int getTotalCaseCount();
}
