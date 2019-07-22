package com.tzCloud.core.job.jobHandler;

import com.tzCloud.core.service.LawyerDataService;
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
@JobHandler(value="LawyerDataWinFlagJobHandler")
@Component
public class LawyerDataWinFlagJobHandler extends IJobHandler {

    @Resource
    private LawyerDataService lawyerDataService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        XxlJobLogger.log("start job LawyerDataWinFlagJobHandler");
        try {
            lawyerDataService.winFlagUpdateIncrement();
        } catch (Exception e) {
            e.printStackTrace();
            XxlJobLogger.log("error job LawyerDataWinFlagJobHandler");
        }
        return SUCCESS;
    }
}
