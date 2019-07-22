package com._360pai.core.job.jobHandler;

import com._360pai.core.service.assistant.AssistantService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xdrodger
 * @Title: ActivityExpireHandler
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/7/4 13:12
 */
@JobHandler(value="setActivityExpireTimeJobHandler")
@Component
public class SetActivityExpireTimeJobHandler extends IJobHandler {
    @Autowired
    private AssistantService assistantService;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("start execute job setActivityExpireTimeJobHandler");
        try {
            assistantService.setActivityExpireTime();
        } catch (Exception e) {
            e.printStackTrace();
            XxlJobLogger.log("error execute job setActivityExpireTimeJobHandler");
        }
        XxlJobLogger.log("end execute job setActivityExpireTimeJobHandler");
        return SUCCESS;
    }
}
