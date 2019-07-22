package com.tzCloud.core.job.jobHandler;

import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.facade.caseMatching.LawyerDataFacade;
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
@JobHandler(value="LawyerDataIdentityJobHandler")
@Component
public class LawyerDataIdentityJobHandler extends IJobHandler {

    @Resource
    private LawyerDataFacade lawyerDataFacade;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        XxlJobLogger.log("start job LawyerDataIdentityJobHandler");
        try {
            int pageNum=1, pageSize=100;
            PageInfoResp pageInfoResp;
            while (true)
            {
                XxlJobLogger.log("LawyerDataIdentityJobHandler pageNum: " + pageNum + " pageSize: " + pageSize);
                pageInfoResp = lawyerDataFacade.lawyerIdentityIncrement(pageNum, pageSize);
                if (!pageInfoResp.isHasNextPage())
                {
                    break;
                }
                pageNum++ ;
            }
        } catch (Exception e) {
            e.printStackTrace();
            XxlJobLogger.log("error job LawyerDataIdentityJobHandler");
        }
        return SUCCESS;
    }
}
