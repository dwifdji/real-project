package com._360pai.admin.controller.enrolling;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.facade.enrolling.EnrollingImportFacade;
import com._360pai.core.facade.enrolling.EnrollingRealImportFacade;
import com._360pai.core.facade.enrolling.req.EnrollingImportReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 描述：物权导入
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 11:09
 */
@RestController
public class EnrollingRealImportController extends AbstractController{

    @Reference(version = "1.0.0")
    private EnrollingRealImportFacade enrollingRealImportFacade;



    @Reference(version = "1.0.0")
    private EnrollingImportFacade enrollingImportFacade;



    /**
     * 获取物权上传列表
     */
    @GetMapping(value = "/admin/enrollingActivity/getRealUploadActivityList")
    public ResponseModel getRealUploadActivityList(EnrollingImportReq.getUploadActivityListReq req) {
        req.setOperatorId(loadCurLoginId());

        return enrollingRealImportFacade.getUploadRealActivityList(req);
    }



    /**
     * 上传成长资产招商
     */
    @PostMapping(value = "/admin/enrollingActivity/uploadRealActivity")
    public ResponseModel uploadRealActivity(@RequestParam("file") MultipartFile multfile, @RequestParam("usrId") String usrId) {

        if(StringUtils.isEmpty(usrId)||multfile==null){
            return ResponseModel.fail(ApiCallResult.EMPTY);
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
        ResponseModel responseModel = enrollingRealImportFacade.uploadActivity(req);

        return responseModel;


    }




    /**
     * 获取物权招商导入详情
     */

    @GetMapping(value = "/admin/enrollingActivity/getRealUploadActivityDetails")
    public ResponseModel getRealUploadActivityDetails(EnrollingImportReq.getUploadActivityDetailsReq req) {
        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        req.setType("admin");
        return enrollingRealImportFacade.getRealUploadActivityDetails(req);
    }







    /**
     * 提交物权导入信息
     */
    @PostMapping(value = "/admin/enrollingActivity/submitRealActivity")
    public ResponseModel submitRealActivity(@RequestBody EnrollingImportReq.activityIdReq req) {

        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        return enrollingRealImportFacade.submitActivity(req);
    }




    /**
     * 审核物权导入信息
     */
    @PostMapping(value = "/admin/enrollingActivity/auditRealActivity")
    public ResponseModel auditRealActivity(@RequestBody EnrollingImportReq.auditActivityReq req) {

        Integer loadCurLoginId = loadCurLoginId();

        if(req.getActivityIds()==null||StringUtils.isEmpty(req.getType())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        req.setOperatorId(loadCurLoginId);

        return enrollingRealImportFacade.auditRealActivity(req);
    }




    /**
     * 修改物权导入信息
     */
    @PostMapping(value = "/admin/enrollingActivity/updateRealActivity")
    public ResponseModel updateRealActivity(@RequestBody EnrollingImportReq.updateRealActivityReq req) {

        return enrollingRealImportFacade.updateImportActivity(req);
    }



    /**
     * 删除添加接口
     */
    @PostMapping(value = "/admin/enrollingActivity/deleteRealActivity")
    public ResponseModel deleteRealActivity(@RequestBody EnrollingImportReq.activityIdReq req) {

        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        req.setDeleteFlag("1");

        return enrollingRealImportFacade.submitActivity(req);
    }



    /**
     * 保存图片信息
     */
    @PostMapping(value = "/admin/enrollingActivity/updateRealImages")
    public ResponseModel updateImages(@RequestBody EnrollingImportReq.updateImagesReq req) {

        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }


        return enrollingImportFacade.updateImages(req);
    }


}
