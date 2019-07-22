package com._360pai.core.service.enrolling;


import com._360pai.core.condition.enrolling.EnrollingDepositCondition;
import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.dto.enrolling.EnrollingListReqDto;
import com._360pai.core.facade.enrolling.req.EnrollingReq.activityIdTypeReq;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.EnrollingDeposit;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface EnrollingDepositService{

    PageInfo getEnrollingDepositList(ActivityIdReqDto req);


    PageInfo getAuditList(EnrollingListReqDto req);


	Integer saveEnrollingDeposit(EnrollingDeposit enrollingDeposit);


	EnrollingDeposit getFilterModel(activityIdTypeReq req);


    EnrollingDeposit getEnrollingDepositById(String id);


    int updateEnrollingDeposit(EnrollingDeposit deposit);


    List<EnrollingDeposit> getEnrollingDepositList(EnrollingDepositCondition condition);

    List<EnrollingActivity> getBeginIn5MinuteList(Integer partyId);

    List<EnrollingActivity> getEndIn5MinuteList(Integer partyId);
}