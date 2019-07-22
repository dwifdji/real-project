package com.winback.core.job.jobHandler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.winback.gateway.facade.PayFacade;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;


/**
 * 支付补偿job
 */
@JobHandler(value="payQueryCallBackHandler")
@Component
public class PayQueryCallBackHandler extends IJobHandler {

	@Reference(version = "1.0.0")
	private PayFacade payFacade;

	@Override
	public ReturnT<String> execute(String param) throws Exception {
		XxlJobLogger.log("start execute job payQueryCallBackHandler");
		try {
			payFacade.quartzQueryOrder();
		} catch (Exception e) {
			e.printStackTrace();
			XxlJobLogger.log("error execute job payQueryCallBackHandler");
		}
		XxlJobLogger.log("end execute job payQueryCallBackHandler");
		return SUCCESS;
	}

}
