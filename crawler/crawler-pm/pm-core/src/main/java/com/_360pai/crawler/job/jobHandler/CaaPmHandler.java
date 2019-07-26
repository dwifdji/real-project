package com._360pai.crawler.job.jobHandler;


import cn.wanghaomiao.seimi.spring.common.CrawlerCache;
import cn.wanghaomiao.seimi.struct.Request;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;


/**
 * 中拍协数据爬取job
 */
@JobHandler(value="caaPmHandler")
@Component
public class CaaPmHandler extends IJobHandler {



	@Override
	public ReturnT<String> execute(String param) {
		XxlJobLogger.log("start execute job caaPmHandler");
		try {

			Request request = new Request();
			request.setCrawlerName("caa123TransactionData");
			request.setUrl("https://paimai.caa123.org.cn/caa-search-ws/ws/0.1/lots?start=0&count=12");
			request.setCallBack("start");
			CrawlerCache.consumeRequest(request);

		} catch (Exception e) {
			e.printStackTrace();
			XxlJobLogger.log("error execute job caaPmHandler");
		}
		XxlJobLogger.log("end execute job caaPmHandler");
		return SUCCESS;
	}

}
