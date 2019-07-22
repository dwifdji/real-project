package com.winback.admin.controller.caseflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.winback.admin.controller.AbstractController;
import com.winback.admin.vo.LoginInfo;
import com.winback.arch.common.RequestModel;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.facade._case.CaseStepAdminFacade;
import com.winback.core.facade._case.req.CaseStepReq;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Scanner;

/**
 * create by liuhaolei on 2019-1-17
 * 诉讼以及执行控制器
 */
@RestController
public class CaseStepController extends AbstractController {

    @Reference(version = "1.0.0")
    private CaseStepAdminFacade caseStepAdminFacade;

    /**
     * 查询案件诉讼或者跟踪步骤列表
     * @return
     */
    @RequiresPermissions(value = {"lawsuit_mgt_1_2_1", "lawsuit_mgt_1_3_1", "executive_mgt_1_2_1", "executive_mgt_1_3_1"}, logical = Logical.OR)
    @GetMapping("/confined/caseFlow/getCaseSteps")
    public ResponseModel getCaseSteps(CaseStepReq.CaseStepListReq caseStepListReq) {

        if (StringUtils.isBlank(caseStepListReq.getType())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return caseStepAdminFacade.getCaseSteps(caseStepListReq);
    }

    /**
     * 下一步select选项列表
     * @param req
     * @return
     */
    @GetMapping("/confined/caseFlow/getCaseStepNotBranch")
    public ResponseModel getCaseStepNotBranch(CaseStepReq.CaseStepListReq req) {
        if (StringUtils.isBlank(req.getType())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return caseStepAdminFacade.getCaseStepNotBranch(req);
    }

    /**
     * 根据id查询诉讼或跟踪列表
     * @return
     */
    @GetMapping("/confined/caseFlow/getCaseStepById")
    public ResponseModel getCaseStepById(CaseStepReq.CaseStepListReq caseStepListReq) {

        if (StringUtils.isBlank(caseStepListReq.getId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return caseStepAdminFacade.getCaseStepById(caseStepListReq);
    }

    /**
     * 保存诉讼或者修改步骤
     * @return
     */
    @PostMapping("/confined/caseFlow/saveCaseStep")
    public ResponseModel saveCaseStep(@RequestBody CaseStepReq.SaveCaseStepReq saveCaseStepReq) {

        if (StringUtils.isBlank(saveCaseStepReq.getHaveBranchFlag())){
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        if (StringUtils.isBlank(saveCaseStepReq.getGroupId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        if ("1".equals(saveCaseStepReq.getHaveBranchFlag())){
            if( saveCaseStepReq.getCaseBranchReqList() == null) {
                return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
            }
        }

        if (StringUtils.isBlank(saveCaseStepReq.getName()) ||
                StringUtils.isBlank(saveCaseStepReq.getNameDesc())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return caseStepAdminFacade.saveCaseStep(saveCaseStepReq);
    }


    /**
     * 修改诉讼或者执行步骤
     * @return
     */
    @PostMapping("/confined/caseFlow/updateCaseStep")
    public ResponseModel updateCaseStep(@RequestBody CaseStepReq.UpdateOrDeleteReq req) {
        if (StringUtils.isBlank(req.getId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        return caseStepAdminFacade.updateCaseStep(req);
    }


    @PostMapping("/confined/caseFlow/updateCaseStepBranch")
    public ResponseModel updateCaseStepBranch(@RequestBody CaseStepReq.UpdateBranchReq req) {
        if (StringUtils.isBlank(req.getId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        return caseStepAdminFacade.updateCaseStepBranch(req);
    }


    /**
     * 删除诉讼或者执行步骤
     * @param req
     * @return
     */
    @PostMapping("/confined/caseFlow/deleteCaseStep")
    public ResponseModel updateOrDeleteCaseStep(@RequestBody CaseStepReq.UpdateOrDeleteReq req) {
        if (StringUtils.isBlank(req.getId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        return caseStepAdminFacade.deleteCaseStep(req);
    }

    /**
     * 查询案件诉讼或者跟踪列表
     * @return
     */
    @GetMapping("/confined/caseFlow/getCaseFlows")
    public ResponseModel getCaseFlows() {

        return null;
    }

    /**
     * 保存步骤信息模板
     * @return
     */
    @PostMapping("/confined/caseFlow/saveStepTemplate")
    public ResponseModel saveStepTemplate(@RequestBody CaseStepReq.StepTemplateSaveReq req) {
        if (StringUtils.isBlank(req.getName()) || StringUtils.isBlank(req.getNameDesc())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        return caseStepAdminFacade.saveStepTemplate(req);
    }

    /**
     * 删除或者修改步骤信息模板
     * @return
     */
    @PostMapping("/confined/caseFlow/updateOrDeleteStepTemplate")
    public ResponseModel updateOrDeleteStepTemplate(@RequestBody CaseStepReq.StepTemplateUpdateReq req) {
        if (StringUtils.isBlank(req.getId()) || StringUtils.isBlank(req.getUpdateFlag())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return caseStepAdminFacade.updateOrDeleteStepTemplate(req);
    }

    /**
     * 根据id查询模板
     * @return
     */
    @RequiresPermissions(value = {"lawsuit_mgt_1_3_1", "executive_mgt_1_3_1"}, logical = Logical.OR)
    @GetMapping("/confined/caseFlow/getTemplateByStepId")
    public ResponseModel getTemplateByStepId(CaseStepReq.CaseTemplateReq req) {
        if (StringUtils.isBlank(req.getId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return caseStepAdminFacade.getTemplateByStepId(req);
    }

    /**
     * 保存案件诉讼或者执行记录
     * @param
     * @return
     */
    @PostMapping("/confined/caseFlow/saveCaseStepRecord")
    public ResponseModel saveCaseStepRecord(@RequestBody CaseStepReq.CaseStepRecordSaveReq req) {
        if (StringUtils.isBlank(req.getCaseId())
        || StringUtils.isBlank(req.getType()) || StringUtils.isBlank(req.getPushMsg())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        LoginInfo loginInfo = loadCurLoginInfo();
        if (loginInfo.getId() == null) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
        }
        req.setAccountId(loginInfo.getId().toString());
        req.setUpdateSource("admin");
        return caseStepAdminFacade.saveCaseStepRecord(req);
    }


    /**
     * 查询更新记录
     * @param
     * @return
     */
    @GetMapping("/confined/caseFlow/getCaseStepRecordList")
    public ResponseModel getCaseStepRecordList(CaseStepReq.CaseStepRecordListReq req) {
        if (StringUtils.isBlank(req.getCaseId()) || StringUtils.isBlank(req.getType())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return caseStepAdminFacade.getCaseStepRecordList(req);
    }

    /**
     * 查询当前步骤以及信息模板选择器
     * @param
     * @return
     */
    @GetMapping("/confined/caseFlow/getCurrentStepMsg")
    public ResponseModel getCurrentStepMsg(CaseStepReq.CaseStepRecordListReq req) {

        if (StringUtils.isBlank(req.getCaseId()) || StringUtils.isBlank(req.getType())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return caseStepAdminFacade.getCurrentStepMsg(req);
    }


    /**
     * 获取诉讼或者
     * @return
     */
    @RequiresPermissions(value = {"lawsuit_mgt_1_1_1", "executive_mgt_1_1_1"}, logical = Logical.OR)
    @GetMapping("/confined/caseFlow/getLawsuitManagements")
    public ResponseModel getLawsuitManagements(CaseStepReq.StatusCaseReq statusCaseReq) {



        return caseStepAdminFacade.getLawsuitManagements(statusCaseReq);
    }


}
