package com._360pai.core.dto.enrolling;

import com._360pai.arch.common.RequestModel;

/**
 * @author: liuhaolei
 * @Title: ActivityPersionDto
 * @ProjectName: core-service
 * @Description: 
 * @Date: 2018-09-07
 */
public class ActivityPersionDto extends RequestModel{

	private String activityId;
	
	private String partyId;
	
	private String accountId;

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
