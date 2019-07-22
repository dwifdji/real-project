package com.winback.admin.controller.systemsite;

import com.alibaba.dubbo.config.annotation.Reference;
import com.winback.arch.common.RequestModel;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.commons.constants.SystemSiteEnum;
import com.winback.core.facade.systemsite.SystemSiteFacade;
import com.winback.core.facade.systemsite.req.SystemSiteReq;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * create by liuahaolei on 2019-01-23
 * 系统设置控制器
 */
@RestController
public class SystemSiteController {

    @Reference(version = "1.0.0")
    private SystemSiteFacade systemSiteFacade;


    /**
     * 新增案件设置
     * @param
     * @return
     */
    @PostMapping("/confined/system/saveCaseSetting")
    public ResponseModel saveCaseSetting(@RequestBody SystemSiteReq.SaveCaseSiteReq req) {
        if(StringUtils.isBlank(req.getType()) || StringUtils.isBlank(req.getName())
                || StringUtils.isBlank(req.getStatus())){
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return systemSiteFacade.saveCaseSetting(req);
    }

    /**
     * 修改或者删除案件设置
     * @return
     */
    @PostMapping("/confined/system/updateOrDeleteCaseSetting")
    public ResponseModel updateOrDeleteCaseSetting(@RequestBody SystemSiteReq.UpdateCaseSiteReq req){
        if(StringUtils.isBlank(req.getUpdateFlag()) || StringUtils.isBlank(req.getId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        if("0".equals(req.getUpdateFlag())) {
            if(StringUtils.isBlank(req.getType()) || StringUtils.isBlank(req.getName())
                    || StringUtils.isBlank(req.getStatus())){
                return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
            }
        }

        return systemSiteFacade.updateOrDeleteCaseSetting(req);
    }

    /**
     * 根据type获取不同的设置列表
     * @param req
     * @return
     */
    @RequiresPermissions("system_mgt_1_2_1")
    @GetMapping("/confined/system/getCaseSettingList")
    public ResponseModel getCaseSettingList(SystemSiteReq.CaseSiteListReq req) {
        if(StringUtils.isBlank(req.getType())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return systemSiteFacade.getCaseSettingList(req);
    }


    /**
     * 根据type获取不同的设置列表
     * @param
     * @return
     */
    @GetMapping("/confined/system/getCaseSettingById")
    public ResponseModel getCaseSettingById(SystemSiteReq.CaseSiteListReq req) {
        if(StringUtils.isBlank(req.getId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return systemSiteFacade.getCaseSettingById(req);
    }


    /**
     * 新增状态信息模板
     * @return
     */
    @PostMapping("/confined/system/saveCaseStatusMsg")
    public ResponseModel saveCaseStatusMsg(@RequestBody SystemSiteReq.CaseStatusMsgSaveReq req) {
        if(StringUtils.isBlank(req.getCaseStatus()) || StringUtils.isBlank(req.getMsgBody())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return systemSiteFacade.saveCaseStatusMsg(req);
    }


    /**
     * 修改或者删除状态信息模板
     * @return
     */
    @PostMapping("/confined/system/updateOrDeleteCaseStatusMsg")
    public ResponseModel updateOrDeleteCaseStatusMsg(@RequestBody SystemSiteReq.CaseStatusMsgUpdateeReq req) {
        if(StringUtils.isBlank(req.getId()) || StringUtils.isBlank(req.getUpdateFlag())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        if("0".equals(req.getUpdateFlag())) {
            if(StringUtils.isBlank(req.getCaseStatusDesc())) {
                return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
            }
        }

        return systemSiteFacade.updateOrDeleteCaseStatusMsg(req);
    }

    /**
     * 查询信息模板列表
     * @return
     */
    @RequiresPermissions("system_mgt_1_4_1")
    @GetMapping("/confined/system/getCaseStatusMsgList")
    public ResponseModel getCaseStatusMsgList(SystemSiteReq.CaseStatusListReq req){


        return systemSiteFacade.getCaseStatusMsgList(req);
    }


    /**
     * 查询信息模板列表
     * @return
     */
    @GetMapping("/confined/system/getCaseStatusMsgById")
    public ResponseModel getCaseStatusMsgById(SystemSiteReq.CaseStatusListReq req){

        if(StringUtils.isBlank(req.getId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        return systemSiteFacade.getCaseStatusMsgById(req);
    }


    @GetMapping("/open/system/getCaseMainStatusList")
    public ResponseModel getCaseMainStatusList(){
        return systemSiteFacade.getCaseMainStatusList();
    }

}