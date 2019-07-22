package com.winback.admin.controller.finance;


import com.alibaba.dubbo.config.annotation.Reference;
import com.winback.admin.controller.AbstractController;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.facade.finance.FinanceFacade;
import com.winback.core.facade.finance.req.FinanceReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 描述：财务管理
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/22 11:09
 */
@Slf4j
@RestController
public class FinanceController extends AbstractController {




    @Reference(version = "1.0.0")
    private FinanceFacade financeFacade;

    /**
     * 获取支出列表
     * @return
     */
    @RequiresPermissions("finance_mgt_1_1_1")
    @GetMapping("/confined/finance/getExpendList")
    public ResponseModel getExpendList(FinanceReq.expendListReq req) {



        return financeFacade.getExpendList(req);
    }




    /**
     * 获取回款记录列表
     * @return
     */
    @RequiresPermissions("finance_mgt_1_2_1")
    @GetMapping("/confined/finance/getReceivableList")
    public ResponseModel getReceivableList(FinanceReq.receivableListReq req) {


        return financeFacade.getReceivableList(req);
    }




    /**
     * 获取发票记录类表
     * @return
     */
    @RequiresPermissions("finance_mgt_1_3_1")
    @GetMapping("/confined/finance/getInvoiceList")
    public ResponseModel getInvoiceList(FinanceReq.invoiceListReq req) {



        return financeFacade.getInvoiceList(req);
    }




    /**
     * 财务审核接口
     * @return
     */
    @PostMapping("/confined/finance/audit")
    public ResponseModel audit(@RequestBody FinanceReq.auditReq req) {

        if(req.getIds()==null||req.getIds().length<1) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        req.setLoginId(loadCurLoginId());
        return financeFacade.audit(req);
    }



    /**
     * 放款财务审核接口
     * @return
     */
    @RequiresPermissions("case_mgt_1_1_14")
    @PostMapping("/confined/finance/expendAudit")
    public ResponseModel expendAudit(@RequestBody FinanceReq.auditReq req) {

        if(req.getIds()==null||req.getIds().length<1) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        req.setLoginId(loadCurLoginId());
        return financeFacade.audit(req);
    }


    /**
     * 回款财务审核接口
     * @return
     */
    @RequiresPermissions("case_mgt_1_1_17")
    @PostMapping("/confined/finance/receivableAudit")
    public ResponseModel receivableAudit(@RequestBody FinanceReq.auditReq req) {

        if(req.getIds()==null||req.getIds().length<1) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        req.setLoginId(loadCurLoginId());
        return financeFacade.audit(req);
    }



    /**
     * 发票审核接口
     * @return
     */
    @RequiresPermissions("case_mgt_1_1_18")
    @PostMapping("/confined/finance/invoiceAudit")
    public ResponseModel invoiceAudit(@RequestBody FinanceReq.auditReq req) {

        if(req.getIds()==null||req.getIds().length<1) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        req.setLoginId(loadCurLoginId());
        return financeFacade.audit(req);
    }




    /**
     * 添加放款记录
     * @return
     */
    @RequiresPermissions("case_mgt_1_1_22")
    @PostMapping("/confined/finance/saveExpend")
    public ResponseModel saveExpend(@RequestBody FinanceReq.saveExpendReq req) {



        req.setLoginId(loadCurLoginId());
        return financeFacade.saveExpend(req);
    }




    /**
     * 添加回款记录
     * @return
     */
    @RequiresPermissions("case_mgt_1_1_23")
    @PostMapping("/confined/finance/saveReceivable")
    public ResponseModel saveReceivable(@RequestBody FinanceReq.saveReceivableReq req) {

        req.setLoginId(loadCurLoginId());

        return financeFacade.saveReceivable(req);
    }




    /**
     * 添加发票记录
     * @return
     */
    @PostMapping("/confined/finance/saveInvoice")
    public ResponseModel saveInvoice(@RequestBody FinanceReq.saveInvoiceReq req) {


        req.setLoginId(loadCurLoginId());
        return financeFacade.saveInvoice(req);
    }




    /**
     * 获取放款审核类表
     * @return
     */
    @GetMapping("/confined/finance/getExpendAuditList")
    public ResponseModel getExpendAuditList(FinanceReq.commonReq req) {



        return financeFacade.getExpendAuditList(req);
    }




    /**
     * 获取回款审核列表
     * @return
     */
    @GetMapping("/confined/finance/getReceivableAuditList")
    public ResponseModel getReceivableAuditList(FinanceReq.commonReq req) {



        return financeFacade.getReceivableAuditList(req);
    }




    /**
     * 获取发票审核接口
     * @return
     */
    @GetMapping("/confined/finance/getInvoiceAuditList")
    public ResponseModel getInvoiceAuditList(FinanceReq.commonReq req) {



        return financeFacade.getInvoiceAuditList(req);
    }



    /**
     *上传凭证
     * @return
     */
    @PostMapping("/confined/finance/uploadCertificate")
    public ResponseModel uploadCertificate(@RequestBody FinanceReq.uploadCertificateReq req) {


        return financeFacade.uploadCertificate(req);
    }



}
