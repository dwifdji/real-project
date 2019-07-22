package com._360pai.core.facade.enrolling;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.enrolling.req.EnrollingImportReq;

/**
 * 描述：物权资产导入资产导入Facade接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 11:11
 */
public interface EnrollingRealImportFacade {




    /**
     *
     *获取上传列表
     * @param  req
     */
    ResponseModel getUploadRealActivityList(EnrollingImportReq.getUploadActivityListReq req);



    /**
     * 上传成长资产招商
     */
    ResponseModel uploadActivity(EnrollingImportReq.uploadActivityReq req);



    /**
     * 获取招商详情
     */
    ResponseModel getRealUploadActivityDetails(EnrollingImportReq.getUploadActivityDetailsReq req);


    /**
     * 保存上传的详情
     */
    ResponseModel updateImages(EnrollingImportReq.updateImagesReq req);


    /**
     * 提交预招商活动
     */
    ResponseModel submitActivity(EnrollingImportReq.activityIdReq req);



    /**
     * 审核预招商活动
     */
    ResponseModel auditRealActivity(EnrollingImportReq.auditActivityReq req);



    /**
     * 修改预招商活动
     */
    ResponseModel updateImportActivity(EnrollingImportReq.updateRealActivityReq req);



}
