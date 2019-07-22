package com.tzCloud.core.provider.caseMatching;

import com.alibaba.dubbo.config.annotation.Service;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.facade.caseMatching.CaseHtmlAnalysisFacade;
import com.tzCloud.core.service.CaseHtmlAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaolei
 * @create 2019-03-26 09:50
 */
@Service(version = "1.0.0")
@Component
public class CaseHtmlAnalysisProvider implements CaseHtmlAnalysisFacade
{

    @Autowired
    private CaseHtmlAnalysisService caseHtmlAnalysisService;

    @Override
    public PageInfoResp analysisIncrement(int pageNum, int pageSize)
    {
        return caseHtmlAnalysisService.analysisIncrement(pageNum, pageSize);
    }

    @Override
    public PageInfoResp analysisFull(int pageNum, int pageSize)
    {
        return caseHtmlAnalysisService.analysisFull(pageNum, pageSize);
    }
}
