package com._360pai.core.job.jobHandler;

import com._360pai.gateway.facade.FddSignatureFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;


/**
 * 法大大异步主动查询回调
 */
@JobHandler(value="fddQueryCallBackHandler")
@Component
public class FddQueryCallBackHandler extends IJobHandler {

	@Reference(version = "1.0.0")
	private FddSignatureFacade fddSignatureFacade;

	@Override
	public ReturnT<String> execute(String param) throws Exception {
		XxlJobLogger.log("start execute job fddQueryCallBackHandler");
		try {
			fddSignatureFacade.querySignQuartz();
		} catch (Exception e) {
			e.printStackTrace();
			XxlJobLogger.log("error execute job fddQueryCallBackHandler");
		}
		XxlJobLogger.log("end execute job fddQueryCallBackHandler");
		return SUCCESS;
	}

}
