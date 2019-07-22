package com._360pai.core.job.jobHandler;

import com._360pai.core.service.applet.DebtCalculatorService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 债权计算器播报job
 */
@JobHandler(value="debtCalculatorBroadcastJobHandler")
@Component
public class DebtCalculatorBroadcastJobHandler extends IJobHandler {

	@Autowired
	private DebtCalculatorService calculatorService;

	@Override
	public ReturnT<String> execute(String param) throws Exception {
		XxlJobLogger.log("start execute job debtCalculatorBroadcastJobHandler");
		try {
			calculatorService.doBroadcastJob();
		} catch (Exception e) {
			e.printStackTrace();
			XxlJobLogger.log("error execute job debtCalculatorBroadcastJobHandler");
		}

		XxlJobLogger.log("end execute job debtCalculatorBroadcastJobHandler");
		return SUCCESS;
	}

}
