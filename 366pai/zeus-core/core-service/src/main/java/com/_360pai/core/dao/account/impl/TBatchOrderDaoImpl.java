
package com._360pai.core.dao.account.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TBatchOrderCondition;
import com._360pai.core.dao.account.TBatchOrderDao;
import com._360pai.core.dao.account.mapper.TBatchOrderMapper;
import com._360pai.core.model.account.TBatchOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TBatchOrderDaoImpl extends AbstractDaoImpl<TBatchOrder, TBatchOrderCondition, BaseMapper<TBatchOrder,TBatchOrderCondition>> implements TBatchOrderDao{
	
	@Resource
	private TBatchOrderMapper tBatchOrderMapper;
	
	@Override
	protected BaseMapper<TBatchOrder, TBatchOrderCondition> daoSupport() {
		return tBatchOrderMapper;
	}

	@Override
	protected TBatchOrderCondition blankCondition() {
		return new TBatchOrderCondition();
	}

	@Override
	public TBatchOrder selectMaxId() {
		return tBatchOrderMapper.selectMaxId();
	}

	@Override
	public List<TBatchOrder> searchBatchOrder(Long batchOrderNo, String status, String beginTime, String endTime) {
		return tBatchOrderMapper.searchBatchOrder(batchOrderNo,status,beginTime,endTime);
	}
}
