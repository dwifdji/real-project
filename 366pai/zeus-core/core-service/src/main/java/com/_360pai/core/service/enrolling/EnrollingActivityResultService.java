package com._360pai.core.service.enrolling;


import com._360pai.arch.common.ResponseModel;
import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.model.enrolling.EnrollingActivityResult;


public interface EnrollingActivityResultService{

    EnrollingActivityResult getResult(ActivityIdReqDto req);


    ResponseModel saveResult(EnrollingActivityResult req);


    void updateResult(EnrollingActivityResult req);
}