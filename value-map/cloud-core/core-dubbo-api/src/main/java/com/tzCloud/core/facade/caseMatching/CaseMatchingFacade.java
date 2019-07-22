package com.tzCloud.core.facade.caseMatching;

import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.core.facade.caseMatching.req.CaseMatchingReq;
import com.tzCloud.core.facade.caseMatching.resp.*;

/**
 * @author : whisky_vip
 * @date : 2019/2/22 14:08
 */
public interface CaseMatchingFacade {
    /**
     * 根据字段获取案由列表（返回前8条）
     *
     * @param req
     * @return
     */
    PageInfoResp<BriefResp> getBrief(CaseMatchingReq.BriefSearch req);

    /**
     * 法院列表
     *
     * @param req
     * @return
     */
    PageInfoResp<CourtResp> getCourtList(CaseMatchingReq.CaseMatchingSearch req);


    /**
     * 律师事务所列表
     *
     * @param req
     * @return
     */
    PageInfoResp<LawyerFirmResp> getLawFirmList(CaseMatchingReq.LawFirmSearch req);

    /*              ------------------------------------------------------------------         */


    /**
     * 律师推荐排行榜列表
     *
     * @param req
     * @return
     */
    PageInfoResp<LawyerForRankingListResp> getLawyerRankingList(CaseMatchingReq.CaseMatchingSearch req);


    /**
     * 获取律师详情
     *
     * @param req
     * @return
     */
    LawyerDetailResp getLawyerDetail(CaseMatchingReq.LawyerDetail req);

    /**
     * 律师详情--根据个人查案例
     *
     * @param req
     * @return
     */
    PageInfoResp<CaseListForLawyerResp>  getCaseListByLawyer(CaseMatchingReq.CaseListByLawyer req);

    /**
     * 获取案由详情
     *
     * @param req
     * @return
     */
    CaseDetailResp getCaseDetail(CaseMatchingReq.CaseDetail req);

    PageInfoResp<LawyerForSearchingListResp> getLawyerList(CaseMatchingReq.LawyerSearch req);

    void initCaseDetail(Integer initNum);

    void initAllCaseDetail();

}
