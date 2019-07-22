package com._360pai.admin.controller.enrolling;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.facade.enrolling.EnrollingImportFacade;
import com._360pai.core.facade.enrolling.req.EnrollingImportReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 描述：长城资产导入
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 11:09
 */
@RestController
public class EnrollingImportController extends AbstractController{

    @Reference(version = "1.0.0")
    private EnrollingImportFacade enrollingImportFacade;



    /**
     * 获取委托人列表
     */
    @GetMapping(value = "/admin/enrollingActivity/getUserList")
    public ResponseModel getUserList(EnrollingImportReq.getUserListReq req) {

        return enrollingImportFacade.getUserList(req);
    }



    /**
     * 获取上传列表
     */
    @GetMapping(value = "/admin/enrollingActivity/getUploadActivityList")
    public ResponseModel getUploadActivityList(EnrollingImportReq.getUploadActivityListReq req) {
        req.setOperatorId(loadCurLoginId());

        return enrollingImportFacade.getUploadActivityList(req);
    }



    /**
     * 上传成长资产招商
     */
    @PostMapping(value = "/admin/enrollingActivity/uploadActivity")
    public ResponseModel uploadActivity(@RequestParam("file") MultipartFile multfile, @RequestParam("usrId") String usrId) {

        if(StringUtils.isEmpty(usrId)){
            return ResponseModel.fail("请选择用户！");
        }

        if(multfile==null){
            return ResponseModel.fail("请选择导入文件！");
        }


        byte[] buffer;
        try {
            buffer = multfile.getBytes();
        } catch (IOException e) {
           return ResponseModel.fail("获取文件错误！");
        }
        EnrollingImportReq.uploadActivityReq req = new EnrollingImportReq.uploadActivityReq();
        req.setUserId(usrId);
        req.setBytes(buffer);
        req.setOperatorId(loadCurLoginId());
        ResponseModel responseModel = enrollingImportFacade.uploadActivity(req);

        return responseModel;


    }






    /**
     * 获取招商详情
     */
    @GetMapping(value = "/admin/enrollingActivity/getUploadActivityDetails")
    public ResponseModel getUploadActivityDetails(EnrollingImportReq.getUploadActivityDetailsReq req) {
        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        req.setType("admin");
        return enrollingImportFacade.getUploadActivityDetails(req);
    }



    /**
     * 保存上传的详情
     */
    @PostMapping(value = "/admin/enrollingActivity/updateImages")
    public ResponseModel updateImages(@RequestBody EnrollingImportReq.updateImagesReq req) {

        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }


        return enrollingImportFacade.updateImages(req);
    }



    /**
     * 提交预招商活动
     */
    @PostMapping(value = "/admin/enrollingActivity/submitActivity")
    public ResponseModel submitActivity(@RequestBody EnrollingImportReq.activityIdReq req) {

        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        return enrollingImportFacade.submitActivity(req);
    }




    /**
     * 审核预招商活动
     */
    @PostMapping(value = "/admin/enrollingActivity/auditActivity")
    public ResponseModel auditActivity(@RequestBody EnrollingImportReq.auditActivityReq req) {

        Integer loadCurLoginId = loadCurLoginId();

        if(req.getActivityIds()==null||StringUtils.isEmpty(req.getType())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        req.setOperatorId(loadCurLoginId);

        return enrollingImportFacade.auditActivity(req);
    }




    /**
     * 修改预招商接口
     */
    @PostMapping(value = "/admin/enrollingActivity/updateActivity")
    public ResponseModel updateActivity(@RequestBody EnrollingImportReq.updateActivityReq req) {

        return enrollingImportFacade.updateImportActivity(req);
    }



    /**
     * 删除提交接口
     */
    @PostMapping(value = "/admin/enrollingActivity/deleteActivity")
    public ResponseModel deleteActivity(@RequestBody EnrollingImportReq.activityIdReq req) {

        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        req.setDeleteFlag("1");

        return enrollingImportFacade.submitActivity(req);
    }




}
