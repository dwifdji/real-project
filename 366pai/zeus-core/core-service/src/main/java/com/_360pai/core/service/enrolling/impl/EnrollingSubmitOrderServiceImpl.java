package com._360pai.core.service.enrolling.impl;

import com._360pai.core.aspact.EnrollingEmailService;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.condition.enrolling.EnrollingActivityCondition;
import com._360pai.core.condition.enrolling.EnrollingSubmitOrderCondition;
import com._360pai.core.dao.enrolling.EnrollingActivityDao;
import com._360pai.core.dao.enrolling.EnrollingSubmitOrderDao;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.EnrollingSubmitOrder;
import com._360pai.core.service.enrolling.EnrollingSubmitOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class EnrollingSubmitOrderServiceImpl implements EnrollingSubmitOrderService {

	@Autowired
	private EnrollingSubmitOrderDao enrollingSubmitOrderDao;

	@Autowired
	private EnrollingActivityDao enrollingActivityDao;

	@Autowired
	private EnrollingEmailService enrollingEmailService;


	@Override
	public int saveSubmitOrder(EnrollingSubmitOrder order) {

		return enrollingSubmitOrderDao.insert(order);
	}

	@Override
	public EnrollingSubmitOrder getSubmitOrder(EnrollingSubmitOrderCondition order) {
		return enrollingSubmitOrderDao.selectFirst(order);
	}

	@Override
	@Transactional
	public void updateSubmitOrderAndActivity(EnrollingSubmitOrder order) {

		enrollingSubmitOrderDao.updateById(order);


		EnrollingActivityCondition condition = new EnrollingActivityCondition();
		condition.setId(order.getActivityId());

		EnrollingActivity activity = enrollingActivityDao.selectFirst(condition);

		if(activity==null){
			return;
		}

		activity.setStatus(EnrollingEnum.STATUS.ONLINE.getType());

		enrollingActivityDao.updateById(activity);


	}


	/**
	 *
	 *发送审核邮件
	 */
	private void sendAuditEmail(EnrollingActivity params) {

		try{

			enrollingEmailService.sendEnrollingAudit(params.getCode(),params.getType());

		}catch (Exception e){

			log.error("发送预招商审核邮件异常，异常信息为：{}",e);
		}

	}
}