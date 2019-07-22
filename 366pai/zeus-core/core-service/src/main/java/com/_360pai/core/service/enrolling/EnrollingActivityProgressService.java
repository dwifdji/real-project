package com._360pai.core.service.enrolling;


import com._360pai.arch.common.ResponseModel;
import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.model.enrolling.EnrollingActivityProgress;
import com.github.pagehelper.PageInfo;


public interface EnrollingActivityProgressService{

    PageInfo getProgressList(ActivityIdReqDto req);


    ResponseModel saveProgress(EnrollingActivityProgress req);
}