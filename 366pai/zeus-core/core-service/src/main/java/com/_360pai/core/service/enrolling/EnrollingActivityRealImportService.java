package com._360pai.core.service.enrolling;


import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportDataVo;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportRealDataVo;
import com._360pai.core.facade.enrolling.req.EnrollingImportReq;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.EnrollingActivityImportData;
import com._360pai.core.model.enrolling.EnrollingActivityImportRealData;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface EnrollingActivityRealImportService {







	/**
	 *
	 *获取上传的列表
	 * @param
	 * @param
	 */
	PageInfo getUploadRealActivityList(EnrollingImportReq.getUploadActivityListReq req);



	/**
	 *
	 *批量导入
	 * @param
	 * @param
	 */
	ResponseModel uploadActivity(List<EnrollingActivityImportRealDataVo> activityList);



	/**
	 *
	 *获取详情
	 * @param
	 * @param
	 */
	ResponseModel getRealUploadActivityDetails(EnrollingImportReq.getUploadActivityDetailsReq req);



	/**
	 *
	 *修改信息
	 * @param
	 * @param
	 */
	ResponseModel updateRealActivity(EnrollingActivity req);


	/**
	 *
	 *修改导入信息
	 * @param
	 * @param
	 */
	ResponseModel updateRealImportActivity(EnrollingImportReq.updateRealActivityReq req);



	/**
	 *
	 *批量审核接口
	 * @param
	 * @param
	 */
	void batchAuditActivity(EnrollingImportReq.auditActivityReq req);









	void updateImportActivityByActivityId(EnrollingActivityImportRealData data);
}