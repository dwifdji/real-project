package com.tzCloud.core.job.jobHandler;

import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.facade.caseMatching.CaseHtmlDsrxxFacade;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xiaolei
 * @create 2019-03-19 16:06
 */
@JobHandler(value="caseDsrxxJobHandler")
@Component
public class CaseDsrxxJobHandler extends IJobHandler {

    @Resource
    private CaseHtmlDsrxxFacade caseHtmlDsrxxFacade;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        XxlJobLogger.log("start job CaseDsrxxJobHandler");
        try {
            int pageNum=1, pageSize=100;
            PageInfoResp pageInfoResp;
            while (true)
            {
                XxlJobLogger.log("CaseDsrxxJobHandler pageNum: " + pageNum + " pageSize: " + pageSize);
                pageInfoResp = caseHtmlDsrxxFacade.parseDsrxxIncrement(pageNum, pageSize);
                if (!pageInfoResp.isHasNextPage())
                {
                    break;
                }
                pageNum++ ;
            }
        } catch (Exception e) {
            e.printStackTrace();
            XxlJobLogger.log("error job CaseAnalysisJobHandler");
        }
        return SUCCESS;
    }
}
