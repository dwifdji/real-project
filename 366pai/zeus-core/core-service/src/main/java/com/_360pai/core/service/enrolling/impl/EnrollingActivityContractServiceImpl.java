package com._360pai.core.service.enrolling.impl;

import com._360pai.core.condition.enrolling.EnrollingActivityContractCondition;
import com._360pai.core.dao.enrolling.EnrollingActivityContractDao;
import com._360pai.core.model.enrolling.EnrollingActivityContract;
import com._360pai.core.service.enrolling.EnrollingActivityContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollingActivityContractServiceImpl	implements EnrollingActivityContractService{

	@Autowired
	private EnrollingActivityContractDao enrollingActivityContractDao;


	@Override
	public EnrollingActivityContract getEnrollingActivityContract(EnrollingActivityContractCondition contractCondition) {
		return enrollingActivityContractDao.selectFirst(contractCondition);
	}

	@Override
	public int saveEnrollingActivityContract(EnrollingActivityContract contract) {

		return enrollingActivityContractDao.insert(contract);
	}

	@Override
	public int updateEnrollingActivityContract(EnrollingActivityContract contract) {
		return enrollingActivityContractDao.updateById(contract);
	}
}