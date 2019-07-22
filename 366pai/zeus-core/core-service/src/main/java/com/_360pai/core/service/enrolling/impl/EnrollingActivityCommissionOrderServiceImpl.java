package com._360pai.core.service.enrolling.impl;

import com._360pai.core.condition.enrolling.EnrollingActivityCommissionOrderCondition;
import com._360pai.core.dao.enrolling.EnrollingActivityCommissionOrderDao;
import com._360pai.core.dto.enrolling.EnrollingListReqDto;
import com._360pai.core.model.enrolling.EnrollingActivityCommissionOrder;
import com._360pai.core.model.enrolling.EnrollingShareProfitInfo;
import com._360pai.core.service.enrolling.EnrollingActivityCommissionOrderService;
import com._360pai.core.vo.enrolling.EnrollingActivityCommissionOrderVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollingActivityCommissionOrderServiceImpl	implements EnrollingActivityCommissionOrderService{

	@Autowired
	private EnrollingActivityCommissionOrderDao enrollingActivityCommissionOrderDao;


	@Override
	public PageInfo getCommissionOrderList(EnrollingListReqDto dto) {

		PageHelper.startPage(dto.getPage(), dto.getPerPage());

		List<EnrollingActivityCommissionOrderVo> list = enrollingActivityCommissionOrderDao.getCommissionOrderList(dto);

		return new PageInfo<>(list);
 	}

	@Override
	public EnrollingActivityCommissionOrder getCommissionOrder(EnrollingActivityCommissionOrderCondition condition) {


		return enrollingActivityCommissionOrderDao.selectFirst(condition);
	}

	@Override
	public int saveCommissionOrder(EnrollingActivityCommissionOrder order) {
		return enrollingActivityCommissionOrderDao.insert(order);
	}

	@Override
	public int updateCommissionOrder(EnrollingActivityCommissionOrder order) {
		return enrollingActivityCommissionOrderDao.updateById(order);
	}

	@Override
	public EnrollingShareProfitInfo getEnrollingShareProfitInfo(String orderId) {
		return enrollingActivityCommissionOrderDao.getEnrollingShareProfitInfo(orderId);
	}


}