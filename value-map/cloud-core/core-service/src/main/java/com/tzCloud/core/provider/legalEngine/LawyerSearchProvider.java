package com.tzCloud.core.provider.legalEngine;

import com.alibaba.dubbo.config.annotation.Service;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.arch.common.constant.RedisKeyConstant;
import com.tzCloud.arch.core.redis.RedisCachemanager;
import com.tzCloud.core.constant.CaseEnum;
import com.tzCloud.core.facade.caseMatching.resp.CaseListForLawyerResp;
import com.tzCloud.core.facade.legalEngine.LawyerSearchFacade;
import com.tzCloud.core.facade.legalEngine.req.LawyerSearchReq;
import com.tzCloud.core.facade.legalEngine.resp.*;
import com.tzCloud.core.service.CollectService;
import com.tzCloud.core.service.TParseLawyerInfoService;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xiaolei
 * @create 2019-04-23 13:21
 */
@Service(version = "1.0.0")
@Component
public class LawyerSearchProvider implements LawyerSearchFacade {

    @Resource
    private TParseLawyerInfoService tParseLawyerInfoService;
    @Autowired
    private RedisCachemanager redisCachemanager;
    @Autowired
    private CollectService collectService;

    @Override
    public LawyerSearchVO searchList(LawyerSearchReq.SearchList req) {
        return tParseLawyerInfoService.searchList(req);
    }

    @Override
    public LawFirmAnalysisVO lawFirmAnalysis(String lawFirm) {
        Assert.notNull(lawFirm, "参数为空");
        Object o = redisCachemanager.hGet(RedisKeyConstant.LAW_FIRM_ANALYSIS, lawFirm);
        if (o == null) {
            try {
                LawFirmAnalysisVO lawFirmAnalysisVO = tParseLawyerInfoService.lawFirmAnalysis(lawFirm);
                if (lawFirmAnalysisVO != null) {
                    redisCachemanager.hSet(RedisKeyConstant.LAW_FIRM_ANALYSIS, lawFirm, lawFirmAnalysisVO);
                }
                return lawFirmAnalysisVO;
            } catch (Exception e ) {
                e.printStackTrace();
            }
        }
        return (LawFirmAnalysisVO) o;
    }

    @Override
    public PageInfoResp<LawyerInfoVO> searchLawyerListByLawFirm(LawyerSearchReq.LawFirmAnalysis req) {
        return tParseLawyerInfoService.searchLawyerListByLawFirm(req.getLawFirm(), req.getAccountId(), req.getPage(), req.getPerPage());
    }

    @Override
    public LawyerSearchVO searchES(LawyerSearchReq.SearchList req) {
        return tParseLawyerInfoService.searchES(req);
    }

    @Override
    public LawFirmSearchVO searchLawFirmES(LawyerSearchReq.SearchLawFirmList req) {
        return tParseLawyerInfoService.searchLawFirmES(req);
    }

    @Override
    public LawFirmInfoVO getLawFirmIntro(String lawFirm) {
        return tParseLawyerInfoService.getLawFirmIntro(lawFirm);
    }

    @Override
    public LawyerInfoVO getLawyerIntro(String lawyerId, Integer accountId) {
        Assert.notNull(lawyerId,"参数为空" );
        Object o = redisCachemanager.hGet(RedisKeyConstant.LAWYER_INTRO, lawyerId);
        LawyerInfoVO lawyerIntro ;
        if (o == null) {
            lawyerIntro = tParseLawyerInfoService.getLawyerIntro(lawyerId);
            if (lawyerIntro != null) {
                redisCachemanager.hSet(RedisKeyConstant.LAWYER_INTRO, lawyerId, lawyerIntro);
            } else {
                return new LawyerInfoVO();
            }
        } else {
            lawyerIntro = (LawyerInfoVO) o;
        }
        if (accountId != null) {
            boolean collect = collectService.isCollect(accountId, lawyerId, CaseEnum.CollectTypeEum.LAWYER.name());
            lawyerIntro.setAttentionFlag(collect?"1":"0");
        }
        return lawyerIntro;
    }

    @Override
    public LawyerCaseStatisticsVO getLawyerCaseStatistics(String lawyerId) {
        Assert.notNull(lawyerId,"参数为空" );
        Object o = redisCachemanager.hGet(RedisKeyConstant.LAWYER_CASE_STATISTICS, lawyerId);
        if (o == null) {
            try {
                LawyerCaseStatisticsVO lawyerCaseStatistics = tParseLawyerInfoService.getLawyerCaseStatistics(lawyerId);
                if (lawyerCaseStatistics != null) {
                    redisCachemanager.hSet(RedisKeyConstant.LAWYER_CASE_STATISTICS, lawyerId, lawyerCaseStatistics);
                    return lawyerCaseStatistics;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (LawyerCaseStatisticsVO) o;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageInfoResp<CaseListForLawyerResp> getLawyerCase(LawyerSearchReq.SearchLawyerCaseList req) {
//        Assert.notNull(req.getLawyerId(),"参数为空" );
//        Object o = redisCachemanager.hGet(RedisKeyConstant.LAWYER_CASE_list, req.getLawyerId()+"_"+req.getBrief());
//        if (o == null) {
//            try {
//                PageInfoResp<CaseListForLawyerResp> lawyerCase = tParseLawyerInfoService.getLawyerCase(req);
//                if (lawyerCase != null) {
//                    redisCachemanager.hSet(RedisKeyConstant.LAWYER_CASE_list, req.getLawyerId()+"_"+req.getBrief(), lawyerCase);
//                    return lawyerCase;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return (PageInfoResp<CaseListForLawyerResp>) o;
        return tParseLawyerInfoService.getLawyerCase(req);
    }

}
