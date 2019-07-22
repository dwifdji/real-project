package com.tzCloud.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.arch.core.redis.RedisCachemanager;
import com.tzCloud.core.common.SystemContent;
import com.tzCloud.core.facade.caseMatching.CaseMatchingFacade;
import com.tzCloud.core.facade.caseMatching.req.CaseMatchingReq;
import com.tzCloud.core.facade.caseMatching.resp.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 特资云--案由匹配
 *
 * @author : whisky_vip
 * @date : 2019/2/22 13:58
 */
@RestController
@Slf4j
public class CaseMatchingController extends AbstractController{
    @Reference(version = "1.0.0")
    CaseMatchingFacade caseMatchingFacade;

    @Autowired
    private RedisCachemanager redisCachemanager;

    /**
     * 获取案由匹配类型
     */
    @RequestMapping(value = "/open/caseMatching/getBrief")
    public ResponseModel getCaseTypeByQueryString(@RequestBody CaseMatchingReq.BriefSearch req) {
        Assert.notNull(req.getSearchStr(), "briefStr 参数不对");
        PageInfoResp<BriefResp> briefRespPageInfo = caseMatchingFacade.getBrief(req);
        return ResponseModel.succ(briefRespPageInfo);
    }

    /**
     * 法院列表
     */
    @RequestMapping(value = "/open/caseMatching/getCourtList")
    public ResponseModel getCourtList(@RequestBody CaseMatchingReq.CaseMatchingSearch req) {
        Assert.notNull(req.getBriefStr(), "briefStr 参数不对");
        PageInfoResp<CourtResp> courtList = caseMatchingFacade.getCourtList(req);
        return ResponseModel.succ(courtList);
    }


    /**
     * 律师推荐排行榜列表
     */
    @RequestMapping(value = "/open/caseMatching/getLawyerRankingList")
    public ResponseModel getLawyerRankingList(@RequestBody CaseMatchingReq.CaseMatchingSearch req) {
        Assert.notNull(req.getBriefStr(), "briefStr 参数不对");
        PageInfoResp<LawyerForRankingListResp> lawyerForRankingList = caseMatchingFacade.getLawyerRankingList(req);
        return ResponseModel.succ(lawyerForRankingList);
    }


    /**
     * 律师事务所列表
     */
    @RequestMapping(value = "/open/caseMatching/getLawFirmList")
    public ResponseModel getLawFirmList(@RequestBody CaseMatchingReq.LawFirmSearch req) {
        Assert.notNull(req.getBriefStr(), "briefStr 参数不对");
//        Assert.notNull(req.getCourtName(), "courtName 参数不对");
        PageInfoResp<LawyerFirmResp> courtList = caseMatchingFacade.getLawFirmList(req);
        return ResponseModel.succ(courtList);
    }

    /**
     * 获取律师列表
     */
    @RequestMapping(value = "/open/caseMatching/getLawyerList")
    public ResponseModel getLawyerList(@RequestBody CaseMatchingReq.LawyerSearch req) {
        Assert.notNull(req.getBriefStr(), "briefStr 参数不对");
        Assert.notNull(req.getCourtName(), "courtName 参数不对");
        Assert.notNull(req.getLawFirmName(), "lawFirmName 参数不对");
        PageInfoResp<LawyerForSearchingListResp> courtList = caseMatchingFacade.getLawyerList(req);
        return ResponseModel.succ(courtList);
    }


    /**
     * 律师详情
     */
    @RequestMapping(value = "/open/caseMatching/getLawyerDetail")
    public ResponseModel getLawyerDetail(@RequestBody CaseMatchingReq.LawyerDetail req) {
        Assert.notNull(req.getLawyerName(), "lawyerName 参数不对");
        Assert.notNull(req.getLawFirmName(), "lawFirmName 参数不对");
        LawyerDetailResp lawyerDetail = (LawyerDetailResp) redisCachemanager.hGet("layerDetail", req.getLawyerName() + req.getLawFirmName());
        if (lawyerDetail == null) {
            req.setLoginId(loadCurLoginId());
            lawyerDetail = caseMatchingFacade.getLawyerDetail(req);
            redisCachemanager.hSet("layerDetail", req.getLawyerName() + req.getLawFirmName(), lawyerDetail);
        }
        //设置为默认图片
        if (StringUtils.isBlank(lawyerDetail.getLawyerImgUrl())) {
            lawyerDetail.setLawyerImgUrl(SystemContent.LAWYER_IMAG_URL);
        }
        return ResponseModel.succ(lawyerDetail);
    }


    /**
     * 律师详情--根据个人查案例
     */
    @RequestMapping(value = "/open/caseMatching/getCaseListByLawyer")
    public ResponseModel getCaseListByLawyer(@RequestBody CaseMatchingReq.CaseListByLawyer req) {
        PageInfoResp<CaseListForLawyerResp> list = caseMatchingFacade.getCaseListByLawyer(req);
        return ResponseModel.succ(list);
    }


    /**
     * 案由详情
     */
    @RequestMapping(value = "/open/caseMatching/getCaseDetail")
    public ResponseModel caseDetail(@RequestBody CaseMatchingReq.CaseDetail req) {
        CaseDetailResp caseDetailResp = caseMatchingFacade.getCaseDetail(req);
        return ResponseModel.succ(caseDetailResp);
    }

    /**
     * 案由详情
     */
    @RequestMapping(value = "/open/caseMatching/initCaseDetail")
    public ResponseModel initCaseDetail(@RequestParam("initNum") Integer initNum) {
        caseMatchingFacade.initCaseDetail(initNum);
        return ResponseModel.succ();
    }

    @RequestMapping(value = "/open/caseMatching/initAllCaseDetail")
    public ResponseModel initAllCaseDetail() {
        caseMatchingFacade.initAllCaseDetail();
        return ResponseModel.succ();
    }

}
