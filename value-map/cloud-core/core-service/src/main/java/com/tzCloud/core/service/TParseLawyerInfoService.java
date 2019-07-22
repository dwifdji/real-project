package com.tzCloud.core.service;

import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.facade.caseMatching.resp.CaseListForLawyerResp;
import com.tzCloud.core.facade.legalEngine.req.LawyerSearchReq;
import com.tzCloud.core.facade.legalEngine.resp.*;

import java.util.List;

/**
 * @author xiaolei
 * @create 2019-04-23 14:01
 */
public interface TParseLawyerInfoService {
    LawyerSearchVO searchList(LawyerSearchReq.SearchList req);
    LawFirmAnalysisVO lawFirmAnalysis(String lawFirm) throws Exception;
    PageInfoResp<LawyerInfoVO> searchLawyerListByLawFirm(String lawFirm, Integer accountId, int pageNum, int pageSize);
    List<LawyerInfoVO> searchListResultLawyerInfoVO(LawyerSearchReq.SearchList req);
    LawyerSearchVO searchES(LawyerSearchReq.SearchList req);
    void searchLawFirmByCondition();
    LawFirmSearchVO searchLawFirmES(LawyerSearchReq.SearchLawFirmList req);
    LawFirmInfoVO getLawFirmIntro(String lawFirm);
    LawyerInfoVO getLawyerIntro(String lawyerId);
    LawyerCaseStatisticsVO getLawyerCaseStatistics(String lawyerId);
    PageInfoResp<CaseListForLawyerResp> getLawyerCase(LawyerSearchReq.SearchLawyerCaseList req);
}
