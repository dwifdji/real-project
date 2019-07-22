package com._360pai.core.job.jobHandler;

import com._360pai.core.job.JobUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述 第三方预招商 状态同步
 *
 * @author : whisky_vip
 * @date : 2018/12/11 14:15
 */
@JobHandler(value = "thirdEnrollingActivityStatusHandler")
@Component
public class ThirdEnrollingActivityStatusHandler extends IJobHandler {

    @Autowired
    private JobUtils jobUtils;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        try {
            jobUtils.thirdEnrollingActivityStatusProcess();
        } catch (Exception e) {
            e.printStackTrace();
            XxlJobLogger.log("error execute job ThirdEnrollingActivityStatusHandler thirdEnrollingActivityStatusProcess");
        }
        return SUCCESS;
    }
}
