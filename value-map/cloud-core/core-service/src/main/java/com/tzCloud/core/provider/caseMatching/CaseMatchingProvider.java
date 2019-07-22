package com.tzCloud.core.provider.caseMatching;

import com.alibaba.dubbo.config.annotation.Service;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.core.facade.caseMatching.CaseMatchingFacade;
import com.tzCloud.core.facade.caseMatching.req.CaseMatchingReq;
import com.tzCloud.core.facade.caseMatching.resp.*;
import com.tzCloud.core.service.TBriefService;
import com.tzCloud.core.service.ViewTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/2/26 10:20
 */
@Component
@Slf4j
@Service(version = "1.0.0")
public class CaseMatchingProvider implements CaseMatchingFacade {

    @Resource
    private TBriefService    tBriefService;
    @Resource
    private ViewTableService viewTableService;

    @Override
    public PageInfoResp<BriefResp> getBrief(CaseMatchingReq.BriefSearch req) {
        return tBriefService.getBriefList(req);
    }

    @Override
    public PageInfoResp<CourtResp> getCourtList(CaseMatchingReq.CaseMatchingSearch req) {
        return viewTableService.getCourtList(req);
    }

    @Override
    public PageInfoResp<LawyerFirmResp> getLawFirmList(CaseMatchingReq.LawFirmSearch req) {
        return viewTableService.getLawFirmList(req);
    }

    @Override
    public PageInfoResp<LawyerForSearchingListResp> getLawyerList(CaseMatchingReq.LawyerSearch req) {
        return viewTableService.getLawyerList(req);
    }

    @Override
    public void initCaseDetail(Integer initNum) {
        try {
            viewTableService.getInitLawyerList(initNum);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void initAllCaseDetail() {
        try {
            viewTableService.initAllCaseDetail();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public PageInfoResp<LawyerForRankingListResp> getLawyerRankingList(CaseMatchingReq.CaseMatchingSearch req) {
        return viewTableService.getLawyerRankingList(req);
    }


    @Override
    public LawyerDetailResp getLawyerDetail(CaseMatchingReq.LawyerDetail req) {
        try {
            return viewTableService.getLawyerDetail(req);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("报错:",e);
            return new LawyerDetailResp();
        }
    }

    @Override
    public PageInfoResp<CaseListForLawyerResp> getCaseListByLawyer(CaseMatchingReq.CaseListByLawyer req) {
        return viewTableService.getCaseListByLawyer(req);
    }

    @Override
    public CaseDetailResp getCaseDetail(CaseMatchingReq.CaseDetail req) {
        return viewTableService.getCaseDetail(req);
    }

}
