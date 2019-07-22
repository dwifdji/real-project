package com.winback.admin.controller._case;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.winback.admin.controller.AbstractController;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.common.utils.ExcelUtil;
import com.winback.arch.common.utils.JsonUtil;
import com.winback.core.commons.constants.CaseEnum;
import com.winback.core.facade._case.CaseFacade;
import com.winback.core.facade._case.req.*;
import com.winback.core.facade._case.resp.CaseAssetVo;
import com.winback.core.facade.account.req.AdminAccountReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author xdrodger
 * @Title: CaseController
 * @ProjectName winback
 * @Description:
 * @date 2019/2/14 18:49
 */
@Slf4j
@RestController
public class CaseController extends AbstractController {
    @Reference(version = "1.0.0")
    private CaseFacade caseFacade;

    /**
     * 案由列表接口(分页)
     */
    @RequiresPermissions(value = {"system_mgt_1_1_1", "case_mgt_1_1_1", "user_mgt_1_3_1", "user_mgt_1_3_2", "user_mgt_1_5_1"}, logical = Logical.OR)
    @GetMapping(value = "/confined/case/brief/list")
    public ResponseModel getCaseBriefList(AdminCaseReq.QueryReq req) {
        if (req.getPageFlag() != null && req.getPageFlag()) {
            return ResponseModel.succ(caseFacade.getCaseBriefListByPage(req));
        }
        return ResponseModel.succ(caseFacade.getCaseBriefList(req));
    }

    /**
     * 添加案由接口
     */
    @PostMapping(value = "/confined/case/brief/add")
    public ResponseModel addCaseBrief(@RequestBody AdminCaseReq.AddCaseBriefReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(caseFacade.addCaseBrief(req));
    }

    /**
     * 修改案由接口
     */
    @PostMapping(value = "/confined/case/brief/edit")
    public ResponseModel editCaseBrief(@RequestBody AdminCaseReq.EditCaseBriefReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(caseFacade.editCaseBrief(req));
    }

    /**
     * 删除案由接口
     */
    @PostMapping(value = "/confined/case/brief/delete")
    public ResponseModel deleteCaseBrief(@RequestBody AdminCaseReq.DeleteCaseBriefReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(caseFacade.deleteCaseBrief(req));
    }



    /**
     * 发布案件接口
     */
    @PostMapping(value = "/confined/case/publishCase")
    public ResponseModel publishCase(@RequestBody CaseCommReq req) {
        log.info("开始调用 publishCase,参数:{}", JSON.toJSONString(req));
        if (req.getCaseAmount() == null || StringUtils.isEmpty(req.getPlaintiff())
                || StringUtils.isEmpty(req.getDefendant())
                || StringUtils.isEmpty(req.getCaseDesc())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        if (req.getPlaintiff().length() > 200) {
            return ResponseModel.fail("原告最多输入200个字符");
        }
        if (req.getDefendant().length() > 200) {
            return ResponseModel.fail("被告最多输入200个字符");
        }
        if(req.getCaseTypeId() == null ||req.getCaseTypeId() == 0){
            req.setCaseTypeId(1);
        }
        req.setAccountId(-1);
        if (StringUtils.isBlank(req.getSubSource())) {
            req.setSubSource(CaseEnum.SubSource.ADMIN.getKey());
        }
        return caseFacade.publishCase(req);
    }


   /**
     * 更新案件基本信息
     */
   @RequiresPermissions("case_mgt_1_1_20")
    @PostMapping(value = "/confined/case/updateCaseBaseInfo")
    public ResponseModel updateCaseBaseInfo(@RequestBody CaseCommReq req) {
        log.info("开始调用 updateCaseBaseInfo,参数:{}", JSON.toJSONString(req));
        if (StringUtils.isEmpty(req.getCaseId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        if (StringUtils.isNotBlank(req.getPlaintiff()) && req.getPlaintiff().length() > 200) {
            return ResponseModel.fail("原告最多输入200个字符");
        }
        if (StringUtils.isNotBlank(req.getDefendant()) && req.getDefendant().length() > 200) {
            return ResponseModel.fail("被告最多输入200个字符");
        }
        return caseFacade.updateCaseBaseInfo(req);
    }


    /**
     * 搜索案件列表
     */
    @RequiresPermissions("case_mgt_1_1_1")
    @PostMapping(value = "/confined/case/searchCase")
    public ResponseModel searchCase(@RequestBody CaseCommReq req) {
        log.info("开始调用 searchCase,参数:{}", JSON.toJSONString(req));
        return caseFacade.searchCase(req);
    }

    /**
     * 获取已经申请承接案件的律师列表
     */
    @PostMapping(value = "/confined/case/getAcceptedLawyers")
    public ResponseModel getAcceptedLawyers(@RequestBody CaseCommReq req) {
        log.info("开始调用 getAcceptedLawyers,参数:{}", JSON.toJSONString(req));
        if(StringUtils.isEmpty(req.getCaseId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        return caseFacade.getAcceptedLawyers(req);
    }



    /**
     * 获取已经申请承接案件的律师列表
     */
    @PostMapping(value = "/confined/case/searchLawyer")
    public ResponseModel searchLawyer(@RequestBody CaseCommReq req) {
        log.info("开始调用 searchLawyer,参数:{}", JSON.toJSONString(req));
        return caseFacade.searchLawyer(req);
    }


    /**
     * 上传文件
     */
    @PostMapping(value = "/confined/case/uploadFile")
    public ResponseModel uploadFile(@RequestBody AttachmentReq req) {
        log.info("开始调用 uploadFile,参数:{}", JSON.toJSONString(req));
        if(StringUtils.isEmpty(req.getCaseId()) || StringUtils.isEmpty(req.getCaseStatus())
            || StringUtils.isEmpty(req.getAttachUrl()) || StringUtils.isEmpty(req.getAttachName())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        return caseFacade.uploadFile(req);
    }


    /**
     * 预检审核
     */
    @RequiresPermissions("case_mgt_1_1_10")
    @PostMapping(value = "/confined/case/preCheck")
    public ResponseModel preCheck(@RequestBody CaseCommReq req) {
        log.info("开始调用 preCheck,参数:{}", JSON.toJSONString(req));
        if(StringUtils.isEmpty(req.getCaseId()) || StringUtils.isEmpty(req.getMainStatus())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setOperatorId(loadCurLoginId());
        return caseFacade.preCheck(req);
    }


    /**
     * 诉讼风控审核
     */
    @RequiresPermissions("case_mgt_1_1_11")
    @PostMapping(value = "/confined/case/lawsuitRiskCheck")
    public ResponseModel lawsuitRiskCheck(@RequestBody CaseCommReq req) {
        log.info("开始调用 lawsuitRiskCheck,参数:{}", JSON.toJSONString(req));
        if(StringUtils.isEmpty(req.getCaseId()) || StringUtils.isEmpty(req.getMainStatus())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setOperatorId(loadCurLoginId());
        return caseFacade.riskCheck(req);
    }

    /**
     * 执行风控审核
     */
    @RequiresPermissions("case_mgt_1_1_12")
    @PostMapping(value = "/confined/case/executeRiskCheck")
    public ResponseModel executeRiskCheck(@RequestBody CaseCommReq req) {
        log.info("开始调用 executeRiskCheck,参数:{}", JSON.toJSONString(req));
        if(StringUtils.isEmpty(req.getCaseId()) || StringUtils.isEmpty(req.getMainStatus())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setOperatorId(loadCurLoginId());
        return caseFacade.riskCheck(req);
    }


    /**
     * 合同线下签约
     */
    @RequiresPermissions("case_mgt_1_1_13")
    @PostMapping(value = "/confined/case/signContract")
    public ResponseModel signContract(@RequestBody CaseCommReq req) {
        log.info("开始调用 signContract,参数:{}", JSON.toJSONString(req));
        if(StringUtils.isEmpty(req.getCaseId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setOperatorId(loadCurLoginId());
        req.setMainStatus(CaseEnum.MainStatus.HAS_SIGN_CONTRACT.getKey());
        return caseFacade.signContract(req);
    }


    /**
     * 安排承接律师
     */
    @RequiresPermissions("case_mgt_1_1_21")
    @PostMapping(value = "/confined/case/setCaseLawyer")
    public ResponseModel setCaseLawyer(@RequestBody CaseCommReq req) {
        log.info("开始调用 setCaseLawyer,参数:{}", JSON.toJSONString(req));
        if(StringUtils.isEmpty(req.getCaseId()) || req.getLawyerAccountId() == null){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setOperatorId(loadCurLoginId());
        return caseFacade.setCaseLawyer(req);
    }


    /**
     * 获取案件基本信息详情
     */
    @PostMapping(value = "/confined/case/getAdminCaseBaseInfo")
    public ResponseModel getAdminCaseBaseInfo(@RequestBody CaseCommReq req) {
        log.info("开始调用 getAdminCaseBaseInfo,参数:{}", JSON.toJSONString(req));
        if(StringUtils.isEmpty(req.getCaseId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        return caseFacade.getAdminCaseBaseInfo(req);
    }


    /**
     * 获取案件风控信息
     */
    @PostMapping(value = "/confined/case/getAdminCaseRiskInfo")
    public ResponseModel getAdminCaseRiskInfo(@RequestBody CaseCommReq req) {
        log.info("开始调用 getAdminCaseRiskInfo,参数:{}", JSON.toJSONString(req));
        if(StringUtils.isEmpty(req.getCaseId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        return caseFacade.getAdminCaseRiskInfo(req);
    }

    /**
     * 获取案件签约信息
     */
    @PostMapping(value = "/confined/case/getAdminCaseSignInfo")
    public ResponseModel getAdminCaseSignInfo(@RequestBody CaseCommReq req) {
        log.info("开始调用 getAdminCaseSignInfo,参数:{}", JSON.toJSONString(req));
        if(StringUtils.isEmpty(req.getCaseId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        return caseFacade.getAdminCaseSignInfo(req);
    }


    /**
     * 获取案件预检信息
     */
    @PostMapping(value = "/confined/case/getAdminCaseCheckInfo")
    public ResponseModel getAdminCaseCheckInfo(@RequestBody CaseCommReq req) {
        log.info("开始调用 getAdminCaseCheckInfo,参数:{}", JSON.toJSONString(req));
        if(StringUtils.isEmpty(req.getCaseId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        return caseFacade.getAdminCaseCheckInfo(req);
    }




    /**
     * 完成诉讼
     */
    @RequiresPermissions("case_mgt_1_1_15")
    @PostMapping(value = "/confined/case/endLawsuit")
    public ResponseModel endLawsuit(@RequestBody CaseCommReq req) {
        log.info("开始调用 endLawsuit,参数:{}", JSON.toJSONString(req));
        if(StringUtils.isEmpty(req.getCaseId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setOperatorId(loadCurLoginId());
        return caseFacade.endLawsuit(req);
    }


    /**
     * 完成执行
     */
    @RequiresPermissions("case_mgt_1_1_16")
    @PostMapping(value = "/confined/case/endExcute")
    public ResponseModel endExcute(@RequestBody CaseCommReq req) {
        log.info("开始调用 endExcute,参数:{}", JSON.toJSONString(req));
        if(StringUtils.isEmpty(req.getCaseId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setOperatorId(loadCurLoginId());
        return caseFacade.endExcute(req);
    }


    /**
     * 删除附件信息
     */
    @PostMapping(value = "/confined/case/deleteCaseAttachment")
    public ResponseModel deleteCaseAttachment(@RequestBody CaseCommReq req) {
        log.info("开始调用 deleteCaseAttachment,参数:{}", JSON.toJSONString(req));
        if(req.getAttachmentId() == null){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        return caseFacade.deleteCaseAttachment(req);
    }


    /**
     * 债权收购列表
     */
    @RequiresPermissions("case_mgt_1_3_1")
    @PostMapping(value = "/confined/case/searchCaseAsset")
    public ResponseModel searchCaseAsset(@RequestBody CaseAssetReq req) {
        log.info("开始调用 searchCaseAsset,参数:{}", JSON.toJSONString(req));
        return caseFacade.searchCaseAsset(req);
    }


    /**
     * 完成结案
     */
    @RequiresPermissions("case_mgt_1_1_19")
    @PostMapping(value = "/confined/case/endSuccess")
    public ResponseModel endSuccess(@RequestBody CaseCommReq req) {
        log.info("开始调用 endSuccess,参数:{}", JSON.toJSONString(req));
        if(StringUtils.isEmpty(req.getCaseId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setOperatorId(loadCurLoginId());
        return caseFacade.endSuccess(req);
    }


    /**
     * 获取案件状态列表
     */
    @PostMapping(value = "/confined/case/getCaseStatusList")
    public ResponseModel getCaseStatusList(@RequestBody CaseCommReq req) {
        log.info("开始调用 getCaseStatusList,参数:{}", JSON.toJSONString(req));
        return caseFacade.getCaseStatusList();
    }


    /**
     * 律师接单列表
     */
    @RequiresPermissions("case_mgt_1_2_1")
    @PostMapping(value = "/confined/case/searchLawyerOrder")
    public ResponseModel searchLawyerOrder(@RequestBody CaseLawyerOrderReq req) {
        log.info("开始调用 searchLawyerOrder,参数:{}", JSON.toJSONString(req));
        return caseFacade.searchLawyerOrder(req);
    }


    /**
     * 案件状态描述列表
     */
    @PostMapping(value = "/confined/case/getCaseStatusDescList")
    public ResponseModel getCaseStatusDescList(@RequestBody CaseLawyerOrderReq req) {
        log.info("开始调用 getCaseStatusDescList,参数:{}", JSON.toJSONString(req));
        return caseFacade.getCaseStatusDescList();
    }


    /**
     * 修改案件状态描述
     */
    @PostMapping(value = "/confined/case/updateCaseStatusDesc")
    public ResponseModel updateCaseStatusDesc(@RequestBody CaseCommReq req) {
        log.info("开始调用 updateCaseStatusDesc,参数:{}", JSON.toJSONString(req));
        return caseFacade.updateCaseStatusDesc(req);
    }


    /**
     * 债权导出
     */
    @GetMapping(value = "/confined/case/exportCaseAsset")
    public void exportCaseAsset(CaseAssetReq req, HttpServletRequest request, HttpServletResponse response) {
        log.info("开始调用 exportCaseAsset,参数:{}", JSON.toJSONString(req));
        List<CaseAssetVo> list = caseFacade.exportCaseList(req);

        String[] columnNames = new String[]{"注册手机号", "申请时间", "债权金额", "债权描述"};

        String[] keys = new String[]{"mobile", "createTime", "assetAmount", "assetDesc"};

        String fileName = "债权收购";



        OutputStream outputStream = null;
        try {
            String userAgent = request.getHeader("user-agent");
            if (userAgent != null && !userAgent.contains("Edge") &&
                    (userAgent.contains("Firefox") || userAgent.contains("Chrome") || userAgent.contains("Safari"))) {
                fileName = new String((fileName).getBytes(), "ISO8859-1") + ".xls";
            } else {
                //其他浏览器
                fileName = URLEncoder.encode(fileName, "UTF8") + ".xls";
            }

            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            Workbook work = ExcelUtil.createWorkBook(JsonUtil.beanListToMapList(list), keys, columnNames, fileName);
            outputStream = response.getOutputStream();
            work.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("导出数据异常{}", e.getMessage());
        } finally {
            try {
                assert outputStream != null;
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("关闭输出流异常{}", e.getMessage());
            }
        }
    }

    /**
     * 项目经理分配的案件列表接口（分页）
     */
    @GetMapping(value = "/confined/case/getProjectManagerAllocatedCaseList")
    public ResponseModel getProjectManagerAllocatedCaseList(CaseCommReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        return ResponseModel.succ(caseFacade.getProjectManagerAllocatedCaseList(req));
    }

    /**
     * 分配案件给项目经理接口
     */
    @PostMapping(value = "/confined/case/allocatedCaseToProjectManager")
    public ResponseModel allocatedCaseToProjectManager(@RequestBody CaseCommReq req) {
        Assert.notNull(req.getAccountId(), "accountId 参数不能为空");
        Assert.notNull(req.getCaseId(), "caseId 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(caseFacade.allocatedCaseToProjectManager(req));
    }

    /**
     * 取消分配案件给项目经理接口
     */
    @PostMapping(value = "/confined/case/cancelAllocatedCaseToProjectManager")
    public ResponseModel cancelAllocatedCaseToProjectManager(@RequestBody CaseCommReq req) {
        Assert.notNull(req.getCaseId(), "caseId 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(caseFacade.cancelAllocatedCaseToProjectManager(req));
    }
}
