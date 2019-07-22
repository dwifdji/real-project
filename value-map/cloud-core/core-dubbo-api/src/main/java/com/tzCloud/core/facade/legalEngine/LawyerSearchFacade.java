package com.tzCloud.core.facade.legalEngine;

import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.facade.caseMatching.resp.CaseListForLawyerResp;
import com.tzCloud.core.facade.legalEngine.req.LawyerSearchReq;
import com.tzCloud.core.facade.legalEngine.resp.*;

import java.util.Map;

/**
 * @author xiaolei
 * @create 2019-04-23 13:02
 */
public interface LawyerSearchFacade {

    /**
     * 律师列表接口
     * @param req
     * @return
     */
    LawyerSearchVO searchList(LawyerSearchReq.SearchList req);

    /**
     * 律所数据分析接口
     * @param lawFirm
     * @return
     */
    LawFirmAnalysisVO lawFirmAnalysis(String lawFirm);

    /**
     * 律所下属律师列表接口
     * @param req
     * @return
     */
    PageInfoResp<LawyerInfoVO> searchLawyerListByLawFirm(LawyerSearchReq.LawFirmAnalysis req);

    /**
     * 律师列表查询es
     * @param req
     * @return
     */
    LawyerSearchVO searchES(LawyerSearchReq.SearchList req);

    /**
     * 律师列表查询es
     * @param req
     * @return
     */
    LawFirmSearchVO searchLawFirmES(LawyerSearchReq.SearchLawFirmList req);

    /**
     * 获取律所简介
     * @param lawFirm
     * @return
     */
    LawFirmInfoVO getLawFirmIntro(String lawFirm);

    /**
     * es中聚合律师简介
     * @param lawyerId
     * @return
     */
    LawyerInfoVO getLawyerIntro(String lawyerId, Integer accountId);

    /**
     *  es 律师案例统计
     * @param lawyerId
     * @return
     */
    LawyerCaseStatisticsVO getLawyerCaseStatistics(String lawyerId);

    /**
     * 根据律师id查询 案例
     * @param req
     * @return
     */
    PageInfoResp<CaseListForLawyerResp> getLawyerCase(LawyerSearchReq.SearchLawyerCaseList req);
}
