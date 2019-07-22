
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TAccountViewRecordCondition;
import com._360pai.core.dao.account.TAccountViewRecordDao;
import com._360pai.core.dao.account.mapper.TAccountViewRecordMapper;
import com._360pai.core.facade.account.req.AcctReq;
import com._360pai.core.model.account.TAccountViewRecord;
import org.springframework.stereotype.Service;


@Service
public class TAccountViewRecordDaoImpl extends AbstractDaoImpl<TAccountViewRecord, TAccountViewRecordCondition, BaseMapper<TAccountViewRecord,TAccountViewRecordCondition>> implements TAccountViewRecordDao {
	
	@Resource
	private TAccountViewRecordMapper tAccountViewRecordMapper;
	
	@Override
	protected BaseMapper<TAccountViewRecord, TAccountViewRecordCondition> daoSupport() {
		return tAccountViewRecordMapper;
	}

	@Override
	protected TAccountViewRecordCondition blankCondition() {
		return new TAccountViewRecordCondition();
	}

	@Override
	public void updateActivityByPartyId(AcctReq.ViewRecordRequest viewRecordRequest) {
		tAccountViewRecordMapper.updateActivityByPartyId(viewRecordRequest.getAccountId(), viewRecordRequest.getPartyId());
	}
}
