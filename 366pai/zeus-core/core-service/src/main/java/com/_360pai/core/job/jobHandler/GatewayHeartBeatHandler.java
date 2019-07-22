package com._360pai.core.job.jobHandler;

import com._360pai.gateway.facade.EmailFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;


/**
 * 第三方系统心跳检查job
 */
@JobHandler(value="gatewayHeartBeatHandler")
@Component
public class GatewayHeartBeatHandler extends IJobHandler {

	@Reference(version = "1.0.0")
	private EmailFacade emailFacade;

	@Override
	public ReturnT<String> execute(String param) {
		XxlJobLogger.log("start execute job gatewayHeartBeatHandler");
		try {
			emailFacade.gatewayHeartBeatQuartz();
		} catch (Exception e) {
			e.printStackTrace();
			XxlJobLogger.log("error execute job gatewayHeartBeatHandler");
		}
		XxlJobLogger.log("end execute job gatewayHeartBeatHandler");
		return SUCCESS;
	}

}
