package com._360pai.core.service.enrolling;


import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportDataVo;
import com._360pai.core.facade.enrolling.req.EnrollingImportReq;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.EnrollingActivityImportData;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface EnrollingActivityImportService {



    /**
     *
     *获取用户列表
     * @param
     * @param
      */
    PageInfo getUserList(EnrollingImportReq.getUserListReq req);



	/**
	 *
	 *获取上传的列表
	 * @param
	 * @param
	 */
	PageInfo getUploadActivityList(EnrollingImportReq.getUploadActivityListReq req);



	/**
	 *
	 *批量导入
	 * @param
	 * @param
	 */
	ResponseModel uploadActivity(List<EnrollingActivityImportDataVo> activityList);



	/**
	 *
	 *获取详情
	 * @param
	 * @param
	 */
	ResponseModel getUploadActivityDetails(EnrollingImportReq.getUploadActivityDetailsReq req);



	/**
	 *
	 *修改信息
	 * @param
	 * @param
	 */
	ResponseModel updateActivity(EnrollingActivity req);


	/**
	 *
	 *修改导入信息
	 * @param
	 * @param
	 */
	ResponseModel updateImportActivity(EnrollingImportReq.updateActivityReq req);



	/**
	 *
	 *批量审核接口
	 * @param
	 * @param
	 */
	void batchAuditActivity(EnrollingImportReq.auditActivityReq req);



	ResponseModel saveFundServiceInfo(EnrollingImportReq.getFundServiceReq req);



	ResponseModel deleteImportActivity(EnrollingImportReq.activityIdReq req);



	void updateImportActivityByActivityId(EnrollingActivityImportData data);
}