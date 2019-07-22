package com.tzCloud.web.controller.legalEngine;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.core.facade.caseMatching.resp.CaseListForLawyerResp;
import com.tzCloud.core.facade.legalEngine.LawyerSearchFacade;
import com.tzCloud.core.facade.legalEngine.req.LawyerSearchReq;
import com.tzCloud.core.facade.legalEngine.resp.*;
import com.tzCloud.web.controller.AbstractController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaolei
 * @create 2019-04-23 11:29
 */
@RestController
public class LawyerSearchController extends AbstractController {

    @Reference(version = "1.0.0")
    private LawyerSearchFacade lawyerSearchFacade;

    @PostMapping("/open/legalEngine/lawyerSearch/searchListByWay1")
    public ResponseModel searchList(@RequestBody LawyerSearchReq.SearchList req)
    {
        req.setAccountId(loadCurLoginId());
        LawyerSearchVO lawyerSearchVO = lawyerSearchFacade.searchList(req);
        return ResponseModel.succ(lawyerSearchVO);
    }

    @PostMapping("/open/legalEngine/lawyerSearch/searchList")
    public ResponseModel searchES(@RequestBody LawyerSearchReq.SearchList req)
    {
        req.setAccountId(loadCurLoginId());
        LawyerSearchVO lawyerSearchVO = lawyerSearchFacade.searchES(req);
        return ResponseModel.succ(lawyerSearchVO);
    }

    @PostMapping("/open/legalEngine/lawFirmSearch/lawFirmAnalysis")
    public ResponseModel lawFirmAnalysis(@RequestBody LawyerSearchReq.LawFirmAnalysis req)
    {
        LawFirmAnalysisVO lawFirmAnalysisVO = lawyerSearchFacade.lawFirmAnalysis(req.getLawFirm());
        return ResponseModel.succ(lawFirmAnalysisVO);
    }

    @PostMapping("/open/legalEngine/lawFirmSearch/searchLawyerList")
    public ResponseModel searchLawyerList(@RequestBody LawyerSearchReq.LawFirmAnalysis req)
    {
        req.setAccountId(loadCurLoginId());
        PageInfoResp<LawyerInfoVO> pageInfoResp = lawyerSearchFacade.searchLawyerListByLawFirm(req);
        return ResponseModel.succ(pageInfoResp);
    }

    @PostMapping("/open/legalEngine/lawFirmSearch/searchLawFirmES")
    public ResponseModel searchLawFirmES(@RequestBody LawyerSearchReq.SearchLawFirmList req)
    {
        LawFirmSearchVO lawFirmSearchVO = lawyerSearchFacade.searchLawFirmES(req);
        return ResponseModel.succ(lawFirmSearchVO);
    }

    @GetMapping("/open/legalEngine/lawFirmSearch/getLawFirmIntro")
    public ResponseModel getLawFirmIntro(String lawFirm) {
        LawFirmInfoVO lawFirmIntro = lawyerSearchFacade.getLawFirmIntro(lawFirm);
        return ResponseModel.succ(lawFirmIntro);
    }

    /**
     * 律师简介
     */
    @GetMapping("/open/legalEngine/lawyerSearch/getLawyerIntro")
    public ResponseModel getLawyerIntro(String lawyerId) {
        LawyerInfoVO lawyerIntro = lawyerSearchFacade.getLawyerIntro(lawyerId, loadCurLoginId());
        return ResponseModel.succ(lawyerIntro);
    }

    /**
     * 律师案例数据分析
     */
    @GetMapping("/open/legalEngine/lawyerSearch/getLawyerCaseStatistics")
    public ResponseModel getLawyerCaseStatistics(String lawyerId) {
        LawyerCaseStatisticsVO lawyerCaseStatistics = lawyerSearchFacade.getLawyerCaseStatistics(lawyerId);
        return ResponseModel.succ(lawyerCaseStatistics);
    }

    /**
     * 文书列表
     */
    @PostMapping("/open/legalEngine/lawyerSearch/getLawyerCase")
    public ResponseModel getLawyerCase(@RequestBody LawyerSearchReq.SearchLawyerCaseList req) {
        PageInfoResp<CaseListForLawyerResp> lawyerCase = lawyerSearchFacade.getLawyerCase(req);
        return ResponseModel.succ(lawyerCase);
    }

}
