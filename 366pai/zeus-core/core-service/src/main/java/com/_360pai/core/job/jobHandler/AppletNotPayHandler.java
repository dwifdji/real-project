package com._360pai.core.job.jobHandler;

import com._360pai.core.facade.applet.AppletFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;


/**
 * 小程序为支付订单查询
 */
@JobHandler(value="appletNotPayHandler")
@Component
public class AppletNotPayHandler extends IJobHandler {

	@Reference(version = "1.0.0")
	private AppletFacade appletFacade;

	@Override
	public ReturnT<String> execute(String param) {
		XxlJobLogger.log("start execute job appletNotPayHandler");
		try {
			appletFacade.queryAppletOrderQuartz();
		} catch (Exception e) {
			e.printStackTrace();
			XxlJobLogger.log("error execute job appletNotPayHandler");
		}
		XxlJobLogger.log("end execute job appletNotPayHandler");
		return SUCCESS;
	}

}
