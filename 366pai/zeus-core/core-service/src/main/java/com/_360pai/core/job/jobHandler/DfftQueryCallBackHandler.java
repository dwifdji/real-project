package com._360pai.core.job.jobHandler;

import com._360pai.gateway.facade.PayFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;


/**
 * 东方付通异步主动查询回调
 */
@JobHandler(value="dfftQueryCallBackHandler")
@Component
public class DfftQueryCallBackHandler extends IJobHandler {

	@Reference(version = "1.0.0")
	private PayFacade payFacade;

	@Override
	public ReturnT<String> execute(String param) throws Exception {
		XxlJobLogger.log("start execute job dfftQueryCallBackHandler");
		try {
			payFacade.queryOrderQuartz();
		} catch (Exception e) {
			e.printStackTrace();
			XxlJobLogger.log("error execute job dfftQueryCallBackHandler");
		}
		XxlJobLogger.log("end execute job dfftQueryCallBackHandler");
		return SUCCESS;
	}

}
