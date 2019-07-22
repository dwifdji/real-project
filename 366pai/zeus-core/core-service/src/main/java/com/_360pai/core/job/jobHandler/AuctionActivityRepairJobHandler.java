package com._360pai.core.job.jobHandler;

import com._360pai.core.service.activity.AuctionActivityService;
import com._360pai.core.service.agreement.DelegationAgreementService;
import com.alibaba.fastjson.JSONObject;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 修复拍卖活动job（状态异常的）
 */
@JobHandler(value="auctionActivityRepairJobHandler")
@Component
public class AuctionActivityRepairJobHandler extends IJobHandler {

	@Autowired
	private AuctionActivityService auctionActivityService;
	@Autowired
	private DelegationAgreementService delegationAgreementService;

	@Override
	public ReturnT<String> execute(String param) throws Exception {
		XxlJobLogger.log("start execute job auctionActivityRepairJobHandler");
		try {
			processActivity();
		} catch (Exception e) {
			e.printStackTrace();
			XxlJobLogger.log("error execute job auctionActivityRepairJobHandler processActivity");
		}
		try {
			processDelegationAgreement();
		} catch (Exception e) {
			e.printStackTrace();
			XxlJobLogger.log("error execute job auctionActivityRepairJobHandler processDelegationAgreement");
		}
		XxlJobLogger.log("end execute job auctionActivityRepairJobHandler");
		return SUCCESS;
	}

	private void processActivity() {
		List<Integer> list = auctionActivityService.getNeedRepairAuctionActivity();
		boolean success = false;
		for (Integer activityId : list) {
			try {
				success = auctionActivityService.repairAuctionActivity(activityId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			XxlJobLogger.log("activityId={}，process={}", activityId, success ? "success" : "fail");
		}
		XxlJobLogger.log("execute job auctionActivityRepairJobHandler repairActivity size = " + list.size());
	}

	private void processDelegationAgreement() {
		List<Integer> list = delegationAgreementService.getAllSignedTimeout();
		boolean success = false;
		for (Integer activityId : list) {
			try {
				success = delegationAgreementService.processDelegationAgreementSignedTimeout(activityId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			XxlJobLogger.log("activityId={}，process={}", activityId, success ? "success" : "fail");
		}
		XxlJobLogger.log("execute job auctionActivityRepairJobHandler processDelegationAgreement size = " + list.size());
	}
}
