package com._360pai.core.service.enrolling;


import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.model.enrolling.EnrollingActivityShareStats;
import com.github.pagehelper.PageInfo;


public interface EnrollingActivityShareStatsService{

    PageInfo getShareList(ActivityIdReqDto req);


    void  saveEnrollingActivityShare(EnrollingActivityShareStats req);


}