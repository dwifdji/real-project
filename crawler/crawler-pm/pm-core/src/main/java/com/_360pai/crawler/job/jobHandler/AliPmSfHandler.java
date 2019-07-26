package com._360pai.crawler.job.jobHandler;


import cn.wanghaomiao.seimi.spring.common.CrawlerCache;
import cn.wanghaomiao.seimi.struct.CrawlerModel;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;


/**
 * 阿里司法拍卖数据爬取job
 */
@JobHandler(value="aliPmSfHandler")
@Component
public class AliPmSfHandler extends IJobHandler {



	@Override
	public ReturnT<String> execute(String param) {
		XxlJobLogger.log("start execute job aliPmSfHandler");
		try {

			CrawlerModel aliSf = CrawlerCache.getCrawlerModel("getAliPmSfByUrlCrawler");
			aliSf.startRequest();

		} catch (Exception e) {
			e.printStackTrace();

			XxlJobLogger.log("error execute job aliPmSfHandler");
		}
		XxlJobLogger.log("end execute job aliPmSfHandler");
		return SUCCESS;
	}

}
