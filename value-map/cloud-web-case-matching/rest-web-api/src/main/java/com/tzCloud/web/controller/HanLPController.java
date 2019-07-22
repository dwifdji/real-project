package com.tzCloud.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.facade.caseMatching.CaseHtmlAnalysisFacade;
import com.tzCloud.core.facade.caseMatching.HanLPFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaolei
 * @create 2019-03-18 10:21
 */
@RestController
@RequestMapping(value = "/open")
public class HanLPController {
    @Reference(version = "1.0.0")
    private HanLPFacade hanLPFacade;
    @Reference(version = "1.0.0")
    private CaseHtmlAnalysisFacade caseHtmlAnalysisFacade;

    @GetMapping(value = "/hanLP/getDictionary")
    public String getDictionary(String key)
    {
        return hanLPFacade.getDictionary(key);
    }

    @GetMapping(value = "/hanLP/reload")
    public void reload()
    {
        hanLPFacade.reload();
    }

    @GetMapping(value = "/hanLP/loadConfig")
    public void loadConfig()
    {
        hanLPFacade.loadConfig();
    }

    @GetMapping(value = "/analysis/analysisFull")
    public void analysisFull()
    {
        int pageNum=1, pageSize=1000;
        PageInfoResp pageInfoResp;
        while (true)
        {
            pageInfoResp = caseHtmlAnalysisFacade.analysisFull(pageNum, pageSize);
            System.out.printf("total: %d, pageNum: %d, pageSize: %d", pageInfoResp.getTotal(), pageNum, pageSize);
            if (!pageInfoResp.isHasNextPage())
            {
                break;
            }
            pageNum++ ;
        }
    }

    @GetMapping(value = "/analysis/analysisIncrement")
    public void analysisIncrement()
    {
        int pageNum=1, pageSize=1000;
        PageInfoResp pageInfoResp;
        while (true)
        {
            pageInfoResp = caseHtmlAnalysisFacade.analysisIncrement(pageNum, pageSize);
            System.out.printf("total: %d, pageNum: %d, pageSize: %d", pageInfoResp.getTotal(), pageNum, pageSize);
            if (!pageInfoResp.isHasNextPage())
            {
                break;
            }
            pageNum++ ;
        }
    }
}
