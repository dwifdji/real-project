package com._360pai.crawler.job.jobHandler;

import cn.wanghaomiao.seimi.spring.common.CrawlerCache;
import cn.wanghaomiao.seimi.struct.Request;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

@JobHandler(value="jdPmSiFaHandler")
@Component
public class JDPmSiFaHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String s) throws Exception {

        XxlJobLogger.log("start execute job jdPmSiFaHandler");
        try {
            Request request = new Request();
            request.setCrawlerName("jdTransactionDataSiFa");
            request.setUrl("https://auction.jd.com/sifa_list.html");
            request.setCallBack("start");
            CrawlerCache.consumeRequest(request);

        } catch (Exception e) {
            e.printStackTrace();
            XxlJobLogger.log("error execute job jdPmSiFaHandler");
        }
        XxlJobLogger.log("end execute job jdPmSiFaHandler");
        return SUCCESS;
    }
}
