package com.tzCloud.core.service;

import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.facade.caseMatching.req.CaseMatchingReq;
import com.tzCloud.core.facade.caseMatching.resp.*;
import com.tzCloud.core.model.caseMatching.CaseHtmlAnalysis;
import com.tzCloud.core.model.caseMatching.CaseHtmlData;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/3/26 14:24
 */
public interface ViewTableService {


    PageInfoResp<CourtResp> getCourtList(CaseMatchingReq.CaseMatchingSearch req);

    PageInfoResp<LawyerFirmResp> getLawFirmList(CaseMatchingReq.LawFirmSearch req);

    PageInfoResp<LawyerForSearchingListResp> getLawyerList(CaseMatchingReq.LawyerSearch req);

    PageInfoResp<LawyerForRankingListResp> getLawyerRankingList(CaseMatchingReq.CaseMatchingSearch req);

    PageInfoResp<CaseListForLawyerResp> getCaseListByLawyer(CaseMatchingReq.CaseListByLawyer req);

    LawyerDetailResp getLawyerDetail(CaseMatchingReq.LawyerDetail req) throws Exception;

    CaseDetailResp getCaseDetail(CaseMatchingReq.CaseDetail req);

    void getInitLawyerList(Integer initNum) throws Exception;

    void initAllCaseDetail();

    CaseHtmlAnalysis getCaseHtmlAnalysis(String docId);

}
