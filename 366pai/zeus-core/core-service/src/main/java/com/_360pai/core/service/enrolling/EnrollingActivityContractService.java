package com._360pai.core.service.enrolling;


import com._360pai.core.condition.enrolling.EnrollingActivityContractCondition;
import com._360pai.core.model.enrolling.EnrollingActivityContract;

public interface EnrollingActivityContractService{


    EnrollingActivityContract getEnrollingActivityContract(EnrollingActivityContractCondition contractCondition);


    int saveEnrollingActivityContract(EnrollingActivityContract contract);


    int updateEnrollingActivityContract(EnrollingActivityContract contract);

}