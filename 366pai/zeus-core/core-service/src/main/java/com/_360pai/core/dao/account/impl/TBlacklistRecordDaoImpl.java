
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.TBlacklistRecordCondition;
import com._360pai.core.dao.account.mapper.TBlacklistRecordMapper;
import com._360pai.core.model.account.TBlacklistRecord;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.TBlacklistRecordDao;

@Service
public class TBlacklistRecordDaoImpl extends AbstractDaoImpl<TBlacklistRecord, TBlacklistRecordCondition, BaseMapper<TBlacklistRecord,TBlacklistRecordCondition>> implements TBlacklistRecordDao{
	
	@Resource
	private TBlacklistRecordMapper tBlacklistRecordMapper;
	
	@Override
	protected BaseMapper<TBlacklistRecord, TBlacklistRecordCondition> daoSupport() {
		return tBlacklistRecordMapper;
	}

	@Override
	protected TBlacklistRecordCondition blankCondition() {
		return new TBlacklistRecordCondition();
	}

}
