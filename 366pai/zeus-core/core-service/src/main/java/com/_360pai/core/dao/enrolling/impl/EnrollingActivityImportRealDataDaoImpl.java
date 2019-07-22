
package com._360pai.core.dao.enrolling.impl;

import javax.annotation.Resource;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportDataListReq;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportDataListVo;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportRealDataVo;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.enrolling.EnrollingActivityImportRealDataCondition;
import com._360pai.core.dao.enrolling.mapper.EnrollingActivityImportRealDataMapper;
import com._360pai.core.model.enrolling.EnrollingActivityImportRealData;
import com._360pai.core.dao.enrolling.EnrollingActivityImportRealDataDao;

import java.util.Date;
import java.util.List;

@Service
public class EnrollingActivityImportRealDataDaoImpl extends AbstractDaoImpl<EnrollingActivityImportRealData, EnrollingActivityImportRealDataCondition, BaseMapper<EnrollingActivityImportRealData,EnrollingActivityImportRealDataCondition>> implements EnrollingActivityImportRealDataDao{
	
	@Resource
	private EnrollingActivityImportRealDataMapper enrollingActivityImportRealDataMapper;
	
	@Override
	protected BaseMapper<EnrollingActivityImportRealData, EnrollingActivityImportRealDataCondition> daoSupport() {
		return enrollingActivityImportRealDataMapper;
	}

	@Override
	protected EnrollingActivityImportRealDataCondition blankCondition() {
		return new EnrollingActivityImportRealDataCondition();
	}


	@Override
	public void batchSaveRealImportData(List<EnrollingActivityImportRealDataVo> list) {

		enrollingActivityImportRealDataMapper.batchSaveRealImportData(list);

	}


	@Override
	public List<EnrollingActivityImportDataListVo> getRealImportDateList(EnrollingActivityImportDataListReq req) {
		return enrollingActivityImportRealDataMapper.getRealImportDateList(req);
	}

	@Override
	public void batchUpdateRealImportData(List<String> list, String beginAt, String endAt,String refuseReason) {

		enrollingActivityImportRealDataMapper.batchUpdateRealImportData(list,beginAt,endAt,refuseReason);
	}
}
