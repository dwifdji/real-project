
package com._360pai.core.dao.enrolling.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.enrolling.EnrollingActivityCommissionOrderCondition;
import com._360pai.core.dao.enrolling.EnrollingActivityCommissionOrderDao;
import com._360pai.core.dao.enrolling.mapper.EnrollingActivityCommissionOrderMapper;
import com._360pai.core.dto.enrolling.EnrollingListReqDto;
import com._360pai.core.model.enrolling.EnrollingActivityCommissionOrder;
import com._360pai.core.model.enrolling.EnrollingShareProfitInfo;
import com._360pai.core.vo.enrolling.EnrollingActivityCommissionOrderVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EnrollingActivityCommissionOrderDaoImpl extends AbstractDaoImpl<EnrollingActivityCommissionOrder, EnrollingActivityCommissionOrderCondition, BaseMapper<EnrollingActivityCommissionOrder,EnrollingActivityCommissionOrderCondition>> implements EnrollingActivityCommissionOrderDao{
	
	@Resource
	private EnrollingActivityCommissionOrderMapper enrollingActivityCommissionOrderMapper;
	
	@Override
	protected BaseMapper<EnrollingActivityCommissionOrder, EnrollingActivityCommissionOrderCondition> daoSupport() {
		return enrollingActivityCommissionOrderMapper;
	}

	@Override
	protected EnrollingActivityCommissionOrderCondition blankCondition() {
		return new EnrollingActivityCommissionOrderCondition();
	}

	@Override
	public List<EnrollingActivityCommissionOrderVo> getCommissionOrderList(EnrollingListReqDto dto) {

		return enrollingActivityCommissionOrderMapper.getCommissionOrderList(dto);
	}

	@Override
	public EnrollingShareProfitInfo getEnrollingShareProfitInfo(String orderId) {
		return enrollingActivityCommissionOrderMapper.getEnrollingShareProfitInfo(orderId);
	}


}
