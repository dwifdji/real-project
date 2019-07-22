package com.tzCloud.core.provider.caseMatching;

import com.alibaba.dubbo.config.annotation.Service;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.facade.caseMatching.CaseHtmlDsrxxFacade;
import com.tzCloud.core.service.CaseHtmlDsrxxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaolei
 * @create 2019-03-26 10:59
 */
@Service(version = "1.0.0")
@Component
public class CaseHtmlDsrxxProvider implements CaseHtmlDsrxxFacade
{

    @Autowired
    private CaseHtmlDsrxxService caseHtmlDsrxxService;

    @Override
    public PageInfoResp parseDsrxxIncrement(int pageNum, int pageSize)
    {
        return caseHtmlDsrxxService.parseDsrxxIncrement(pageNum, pageSize);
    }
}
