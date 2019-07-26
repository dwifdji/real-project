package com._360pai.crawler.job.jobHandler;


import cn.wanghaomiao.seimi.spring.common.CrawlerCache;
import cn.wanghaomiao.seimi.struct.Request;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;


/**
 * 公拍网司法数据爬取job
 */
@JobHandler(value="gPaiPmHandler")
@Component
public class GPaiPmHandler extends IJobHandler {



	@Override
	public ReturnT<String> execute(String param) {
		XxlJobLogger.log("start execute job gPaiPmHandler");
		try {

			Request request = new Request();
			request.setCrawlerName("gPai");
			request.setUrl("http://s.gpai.net/sf/search.do?");
			request.setCallBack("start");
			CrawlerCache.consumeRequest(request);

		} catch (Exception e) {
			e.printStackTrace();
			XxlJobLogger.log("error execute job gPaiPmHandler");
		}
		XxlJobLogger.log("end execute job aliPmSfUrlHandler");
		return SUCCESS;
	}

}
