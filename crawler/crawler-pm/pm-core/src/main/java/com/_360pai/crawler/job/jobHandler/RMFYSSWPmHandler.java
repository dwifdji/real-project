package com._360pai.crawler.job.jobHandler;

import cn.wanghaomiao.seimi.spring.common.CrawlerCache;
import cn.wanghaomiao.seimi.struct.Request;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

@JobHandler(value="rmfysswPmHandler")
@Component
public class RMFYSSWPmHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String s) throws Exception {

        XxlJobLogger.log("start execute job rmfysswPmHandler");
        try {
            Request request = new Request();
            request.setCrawlerName("rmfysswTransactionData");
            request.setUrl("https://www1.rmfysszc.gov.cn/projects.shtml?dh=3&gpstate=1&wsbm_slt=1");
            request.setCallBack("start");
            CrawlerCache.consumeRequest(request);

        } catch (Exception e) {
            e.printStackTrace();
            XxlJobLogger.log("error execute job rmfysswPmHandler");
        }
        XxlJobLogger.log("end execute job rmfysswPmHandler");
        return SUCCESS;
    }
}
