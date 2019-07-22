package com._360pai.admin.controller.lease;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.facade.lease.LeaseFacade;
import com._360pai.core.facade.lease.req.LeaseReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 租赁权拍卖
 */
@RestController
public class LeaseController extends AbstractController {

    @Reference(version = "1.0.0")
    private LeaseFacade leaseFacade;


    /**
     *
     *添加员工
     */
    @PostMapping("/admin/saveLeaseStaff")
    public ResponseModel updateLeaseStaff(@RequestBody LeaseReq.LeaseStaff req) {

        return leaseFacade.saveLeaseStaff(req);
    }


    /**
     *
     *修改员工
     */
    @PostMapping("/admin/updateLeaseStaff")
    public ResponseModel saveLeaseStaff(@RequestBody LeaseReq.LeaseStaff req) {

        if(StringUtils.isEmpty(req.getId())){

            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        return leaseFacade.updateLeaseStaff(req);
    }


    /**
     * 获取员工详情
     */
     @GetMapping("/admin/getLeaseStaffInfo")
    public ResponseModel getLeaseStaffInfo(LeaseReq.LeaseStaffInfo req) {
         if(StringUtils.isEmpty(req.getId())){

             return ResponseModel.fail(ApiCallResult.EMPTY);
         }

         return leaseFacade.getLeaseStaffInfo(req);
    }


    /**
     * 获取员工列表
     */
    @GetMapping("/admin/getLeaseStaffList")
    public ResponseModel getLeaseStaffList(LeaseReq.LeaseStaffInfo req) {

        if(StringUtils.isEmpty(req.getComId())){

            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        return leaseFacade.getLeaseStaffList(req);
    }



    /**
     * 审核等级设置
     */
    @PostMapping("/admin/updateAuditInfo")
    public ResponseModel updateAuditInfo(@RequestBody  LeaseReq.LeaseStaffInfo req) {

        if(StringUtils.isEmpty(req.getComId())||StringUtils.isEmpty(req.getAuditNum())){

            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        return leaseFacade.updateAuditInfo(req);
    }





}
