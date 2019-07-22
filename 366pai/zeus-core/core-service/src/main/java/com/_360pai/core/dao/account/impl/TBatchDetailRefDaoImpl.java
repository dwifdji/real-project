
package com._360pai.core.dao.account.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TBatchDetailRefCondition;
import com._360pai.core.dao.account.TBatchDetailRefDao;
import com._360pai.core.dao.account.mapper.TBatchDetailRefMapper;
import com._360pai.core.model.account.TBatchDetailRef;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TBatchDetailRefDaoImpl extends AbstractDaoImpl<TBatchDetailRef, TBatchDetailRefCondition, BaseMapper<TBatchDetailRef,TBatchDetailRefCondition>> implements TBatchDetailRefDao{
	
	@Resource
	private TBatchDetailRefMapper tBatchDetailRefMapper;
	
	@Override
	protected BaseMapper<TBatchDetailRef, TBatchDetailRefCondition> daoSupport() {
		return tBatchDetailRefMapper;
	}

	@Override
	protected TBatchDetailRefCondition blankCondition() {
		return new TBatchDetailRefCondition();
	}

	@Override
	public void deleteByBatchId(Long batchId) {
		tBatchDetailRefMapper.deleteByBatchId(batchId);
	}
}
