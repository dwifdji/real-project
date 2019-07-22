
package com._360pai.core.dao.activity.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.activity.TAuctionStepRecordCondition;
import com._360pai.core.dao.activity.TAuctionStepRecordDao;
import com._360pai.core.dao.activity.mapper.TAuctionStepRecordMapper;
import com._360pai.core.model.activity.TAuctionStepRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TAuctionStepRecordDaoImpl extends AbstractDaoImpl<TAuctionStepRecord, TAuctionStepRecordCondition, BaseMapper<TAuctionStepRecord,TAuctionStepRecordCondition>> implements TAuctionStepRecordDao{
	
	@Resource
	private TAuctionStepRecordMapper tAuctionStepRecordMapper;
	
	@Override
	protected BaseMapper<TAuctionStepRecord, TAuctionStepRecordCondition> daoSupport() {
		return tAuctionStepRecordMapper;
	}

	@Override
	protected TAuctionStepRecordCondition blankCondition() {
		return new TAuctionStepRecordCondition();
	}

}
