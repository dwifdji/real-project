
package com._360pai.core.dao.enrolling.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.enrolling.EnrollingActivityImportDataCondition;
import com._360pai.core.dao.enrolling.EnrollingActivityImportDataDao;
import com._360pai.core.dao.enrolling.mapper.EnrollingActivityImportDataMapper;
import com._360pai.core.facade.enrolling.req.*;
import com._360pai.core.facade.enrolling.resp.EnrollingActivityImportDataResp;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.EnrollingActivityImportData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EnrollingActivityImportDataDaoImpl extends AbstractDaoImpl<EnrollingActivityImportData, EnrollingActivityImportDataCondition, BaseMapper<EnrollingActivityImportData,EnrollingActivityImportDataCondition>> implements EnrollingActivityImportDataDao{
	
	@Resource
	private EnrollingActivityImportDataMapper enrollingActivityImportDataMapper;
	
	@Override
	protected BaseMapper<EnrollingActivityImportData, EnrollingActivityImportDataCondition> daoSupport() {
		return enrollingActivityImportDataMapper;
	}

	@Override
	protected EnrollingActivityImportDataCondition blankCondition() {
		return new EnrollingActivityImportDataCondition();
	}


	@Override
	public void batchSaveImportData(List<EnrollingActivityImportDataVo> list) {
		enrollingActivityImportDataMapper.batchSaveImportData(list);
	}


	@Override
	public void batchSaveEnrollingActivity(List<EnrollingActivity> list) {
		enrollingActivityImportDataMapper.batchSaveEnrollingActivity(list);
	}

	@Override
	public List<EnrollingActivityImportUserVo> getApplyUserList(EnrollingActivityImportUserReq req) {
		return enrollingActivityImportDataMapper.getApplyUserList(req);
	}

	@Override
	public List<EnrollingActivityImportDataListVo> getImportDateList(EnrollingActivityImportDataListReq req) {
		return enrollingActivityImportDataMapper.getImportDateList(req);
	}


	@Override
	public void batchAuditActivity(EnrollingImportReq.auditActivityReq req) {


		enrollingActivityImportDataMapper.batchAuditActivity(req.getActivityIdList(),req.getStatus(),req.getBeginAt(),req.getEndAt(),req.getRefuseReason(),req.getOperatorId(),req.getOperatorAt());
	}

	@Override
	public EnrollingActivityImportDataResp getImportDataDetail(String activityId) {
		return enrollingActivityImportDataMapper.getImportDataDetail(activityId);
	}
}
