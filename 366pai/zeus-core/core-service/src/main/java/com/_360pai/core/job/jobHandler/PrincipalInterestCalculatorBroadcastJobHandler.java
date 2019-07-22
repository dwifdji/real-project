package com._360pai.core.job.jobHandler;

import com._360pai.core.service.applet.PrincipalInterestCalculatorService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 本息计算器播报job
 */
@JobHandler(value="principalInterestCalculatorBroadcastJobHandler")
@Component
public class PrincipalInterestCalculatorBroadcastJobHandler extends IJobHandler {

	@Autowired
	private PrincipalInterestCalculatorService calculatorService;

	@Override
	public ReturnT<String> execute(String param) throws Exception {
		XxlJobLogger.log("start execute job principalInterestCalculatorBroadcastJobHandler");
		try {
			calculatorService.doBroadcastJob();
		} catch (Exception e) {
			e.printStackTrace();
			XxlJobLogger.log("error execute job principalInterestCalculatorBroadcastJobHandler");
		}

		XxlJobLogger.log("end execute job principalInterestCalculatorBroadcastJobHandler");
		return SUCCESS;
	}

}
