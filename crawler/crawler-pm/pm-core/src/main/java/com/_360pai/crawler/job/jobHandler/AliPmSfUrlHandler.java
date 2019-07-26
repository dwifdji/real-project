package com._360pai.crawler.job.jobHandler;


import cn.wanghaomiao.seimi.spring.common.CrawlerCache;
import cn.wanghaomiao.seimi.struct.CrawlerModel;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;


/**
 * 阿里司法拍卖url
 */
@JobHandler(value="aliPmSfUrlHandler")
@Component
public class AliPmSfUrlHandler extends IJobHandler {



	@Override
	public ReturnT<String> execute(String param) {
		XxlJobLogger.log("start execute job aliPmSfUrlHandler");
		try {

			CrawlerModel aliSfUrl = CrawlerCache.getCrawlerModel("aliSfUrlCity");
			aliSfUrl.startRequest();

		} catch (Exception e) {
			e.printStackTrace();
			XxlJobLogger.log("error execute job aliPmSfUrlHandler");
		}
		XxlJobLogger.log("end execute job aliPmSfUrlHandler");
		return SUCCESS;
	}

}
