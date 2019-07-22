package com._360pai.core.service.enrolling;


import com._360pai.core.condition.enrolling.EnrollingActivityCommissionOrderCondition;
import com._360pai.core.dto.enrolling.EnrollingListReqDto;
import com._360pai.core.model.enrolling.EnrollingActivityCommissionOrder;
import com._360pai.core.model.enrolling.EnrollingShareProfitInfo;
import com.github.pagehelper.PageInfo;


public interface EnrollingActivityCommissionOrderService{


    PageInfo getCommissionOrderList(EnrollingListReqDto dto);



    EnrollingActivityCommissionOrder getCommissionOrder(EnrollingActivityCommissionOrderCondition condition);


    int saveCommissionOrder(EnrollingActivityCommissionOrder order);


    int updateCommissionOrder(EnrollingActivityCommissionOrder order);



    EnrollingShareProfitInfo getEnrollingShareProfitInfo(String orderId);





}