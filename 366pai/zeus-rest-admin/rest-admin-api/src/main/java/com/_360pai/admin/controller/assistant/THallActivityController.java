package com._360pai.admin.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.THallActivityFacade;
import com._360pai.core.facade.assistant.req.THallActivityReq;
import com._360pai.core.facade.assistant.req.THallEnrollingActivityReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: THallActivityController
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/27 12:59
 */
@RestController
public class THallActivityController {
    @Reference(version = "1.0.0")
    private THallActivityFacade tHallActivityFacade;


    @GetMapping(value = "/admin/hallActivity/list")
    public ResponseModel getTHallActivityList(THallActivityReq req) {
        PageInfo pageInfo = tHallActivityFacade.tHallActivityList(req);
        return ResponseModel.succ(pageInfo);
    }

    @PostMapping(value = "/admin/hallActivity/add")
    public ResponseModel addTHallActivity(@RequestBody THallActivityReq req) {
        int pageInfo = tHallActivityFacade.addTHallActivity(req);
        return ResponseModel.succ(pageInfo);
    }

    @PostMapping(value = "/admin/hallActivity/edit")
    public ResponseModel editTHallActivityList(@RequestBody THallActivityReq req) {
        int pageInfo = tHallActivityFacade.editTHallActivityList(req);
        return ResponseModel.succ(pageInfo);
    }

    @PostMapping(value = "/admin/hallActivity/delete")
    public ResponseModel deleteTHallActivityList(@RequestBody THallActivityReq req) {
        int pageInfo = tHallActivityFacade.deleteTHallActivityList(req);
        return ResponseModel.succ(pageInfo);
    }

    @GetMapping(value = "/admin/hallActivity/detail")
    public ResponseModel detailTHallActivityList(THallActivityReq req) {
        Object pageInfo = tHallActivityFacade.detailTHallActivityList(req);
        return ResponseModel.succ(pageInfo);
    }


    @GetMapping(value = "/admin/enrolling/hallActivity/list")
    public ResponseModel getEnrollingTHallActivityList(THallEnrollingActivityReq req) {
        PageInfo pageInfo = tHallActivityFacade.tEnrollingHallActivityList(req);
        return ResponseModel.succ(pageInfo);
    }

    @PostMapping(value = "/admin/enrolling/hallActivity/add")
    public ResponseModel addEnrollingTHallActivity(@RequestBody THallEnrollingActivityReq req) {
        int pageInfo = tHallActivityFacade.addEnrollingHallActivity(req);
        return ResponseModel.succ(pageInfo);
    }

    @PostMapping(value = "/admin/enrolling/hallActivity/edit")
    public ResponseModel editEnrollingTHallActivity(@RequestBody THallEnrollingActivityReq req) {
        int pageInfo = tHallActivityFacade.editEnrollingHallActivity(req);
        return ResponseModel.succ(pageInfo);
    }

    @PostMapping(value = "/admin/enrolling/hallActivity/delete")
    public ResponseModel deleteEnrollingTHallActivity(@RequestBody THallEnrollingActivityReq req) {
        int pageInfo = tHallActivityFacade.deleteEnrollingHallActivity(req);
        return ResponseModel.succ(pageInfo);
    }

    @GetMapping(value = "/admin/enrolling/hallActivity/detail")
    public ResponseModel detailEnrollingTHallActivity(THallEnrollingActivityReq req) {
        Object pageInfo = tHallActivityFacade.detailEnrollingTHallActivity(req);
        return ResponseModel.succ(pageInfo);
    }

}
