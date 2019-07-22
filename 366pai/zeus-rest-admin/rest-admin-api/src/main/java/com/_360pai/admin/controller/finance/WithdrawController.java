package com._360pai.admin.controller.finance;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.DownloadUtil;
import com._360pai.arch.common.utils.ToolUtil;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.AcctFacade;
import com._360pai.core.facade.account.req.AcctReq;
import com._360pai.core.facade.account.req.SearchBatchReq;
import com._360pai.core.facade.account.req.WithdrawReq;
import com._360pai.core.facade.account.resp.BatchOrderInfoResp;
import com._360pai.core.facade.account.vo.InvoiceVo;
import com._360pai.core.facade.account.vo.WithdrawAcctDetailVo;
import com._360pai.core.facade.finance.ServiceWithdrawRecordFacade;
import com._360pai.core.facade.finance.req.WithdrawRecordReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-10-09 18:28
 */
@RestController
public class WithdrawController extends AbstractController {

    @Reference(version = "1.0.0")
    private ServiceWithdrawRecordFacade serviceWithdrawRecordFacade;
    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;

    @Reference(version = "1.0.0")
    private AcctFacade acctFacade;

    @GetMapping("/admin/finance/getUserWithdrawRecord")
    public ResponseModel getUserWithdrawRecord(WithdrawRecordReq.GetAdminWithdrawRecord req) {
        req.setAccountType(SystemConstant.ACCOUNT_USER_TYPE);
        req.setTimeBy("createTime");
        PageInfoResp pageInfoResp = serviceWithdrawRecordFacade.getAdminWithdrawRecordPage(req);
        return ResponseModel.succ(pageInfoResp);
    }

    @GetMapping("/admin/finance/getCompanyWithdrawRecord")
    public ResponseModel getCompanyWithdrawRecord(WithdrawRecordReq.GetAdminWithdrawRecord req) {
        req.setAccountType(SystemConstant.ACCOUNT_COMPANY_TYPE);
        req.setTimeBy("createTime");
        PageInfoResp pageInfoResp = serviceWithdrawRecordFacade.getAdminWithdrawRecordPage(req);
        return ResponseModel.succ(pageInfoResp);
    }

    /**
     * 提现初审列表接口
     */
    @RequiresPermissions("cwgl_txgl:withdraw_first_verify_list")
    @GetMapping("/admin/withdraw/first/verify/list")
    public ResponseModel firstVerifyWithdrawList(AcctReq.QueryReq req) {
        return accountFacade.getFirstVerifyWithdrawListByPage(req);
    }

    /**
     * 提现初审列表下载接口
     */
    @GetMapping("/admin/withdraw/first/verify/list/download")
    public ResponseModel firstVerifyWithdrawListDownload(AcctReq.QueryReq req, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<>();
        params.put("fileName", "提现初审汇总");
        params.put("sheetName", "提现初审统计数据");
        List<Map<String, Object>> list = new ArrayList<>();
        req.setPage(1);
        req.setPerPage(100);
        while (true) {
            ResponseModel resp = accountFacade.getFirstVerifyWithdrawListByPage(req);
            Map<String, Object> content = (Map<String, Object>) resp.getContent();
            Boolean hasNextPage = (Boolean) content.get("hasNextPage");
            List<WithdrawAcctDetailVo> itemList = (List<WithdrawAcctDetailVo>) content.get("list");
            for (WithdrawAcctDetailVo item : itemList) {
                Map<String, Object> map = ToolUtil.convertBeanToMap(item);
                map.put("createTime", DateUtil.getNormDateStr(item.getCreateTime()));
                list.add(map);
            }
            if (!hasNextPage) {
                break;
            }
            req.setPage(req.getPage() + 1);
        }
        params.put("list", list);
        String[] keys = new String[]{
                "id", "typeDesc", "name", "bankName", "bankAccountNo", "mobile", "createTime", "amount", "statusDesc", "firstVerifyOperator"
        };
        params.put("keys", keys);
        String[] columnNames = new String[]{
                "订单编号", "用户类型", "用户名称", "开户银行", "银行账户", "注册账号", "申请时间", "申请金额", "申请状态", "初审人员"
        };
        params.put("columnNames", columnNames);
        DownloadUtil.downloadExcel(request, response, params);
        return ResponseModel.succ();
    }

    /**
     * 提现初审详情接口
     */
    @GetMapping("/admin/withdraw/first/verify/detail")
    public ResponseModel firstVerifyWithdrawDetail(AcctReq.BaseReq req) {
        return ResponseModel.succ(accountFacade.getFirstVerifyWithdrawDetail(req));
    }

    /**
     * 提现发票审核列表接口
     */
    @GetMapping("/admin/withdraw/invoice/verify/list/download")
    public ResponseModel invoiceVerifyWithdrawListDownload(AcctReq.QueryReq req, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<>();
        params.put("fileName", "发票审核汇总");
        params.put("sheetName", "发票审核统计数据");
        List<Map<String, Object>> list = new ArrayList<>();
        req.setPage(1);
        req.setPerPage(100);
        while (true) {
            ResponseModel resp = accountFacade.getInvoiceVerifyWithdrawListByPage(req);
            Map<String, Object> content = (Map<String, Object>) resp.getContent();
            Boolean hasNextPage = (Boolean) content.get("hasNextPage");
            List<WithdrawAcctDetailVo> itemList = (List<WithdrawAcctDetailVo>) content.get("list");
            for (WithdrawAcctDetailVo item : itemList) {
                Map<String, Object> map = ToolUtil.convertBeanToMap(item);
                map.put("createTime", DateUtil.getNormDateStr(item.getCreateTime()));
                InvoiceVo invoice = item.getInvoice();
                if (invoice != null) {
                    map.put("invoiceTypeDesc", invoice.getTypeDesc());
                } else {
                    map.put("invoiceTypeDesc", "");
                }
                list.add(map);
            }
            if (!hasNextPage) {
                break;
            }
            req.setPage(req.getPage() + 1);
        }
        params.put("list", list);
        String[] keys = new String[]{
                "batchId", "id", "typeDesc", "name", "mobile", "createTime", "amount", "invoiceTypeDesc", "statusDesc", "invoiceVerifyOperator"
        };
        params.put("keys", keys);
        String[] columnNames = new String[]{
                "审批单号", "订单编号", "用户类型", "用户名称", "注册账号", "申请时间", "申请金额", "发票类型", "审核状态", "审核人员"
        };
        params.put("columnNames", columnNames);
        DownloadUtil.downloadExcel(request, response, params);
        return ResponseModel.succ();
    }

    /**
     * 提现发票审核列表接口
     */
    @RequiresPermissions("cwgl_txgl:withdraw_invoice_verify_list")
    @GetMapping("/admin/withdraw/invoice/verify/list")
    public ResponseModel invoiceVerifyWithdrawList(AcctReq.QueryReq req) {
        return accountFacade.getInvoiceVerifyWithdrawListByPage(req);
    }

    /**
     * 提现发票审核详情接口
     */
    @GetMapping("/admin/withdraw/invoice/verify/detail")
    public ResponseModel invoiceVerifyWithdrawDetail(AcctReq.BaseReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        return ResponseModel.succ(accountFacade.getInvoiceVerifyWithdrawDetail(req));
    }


    /**
     * 提现初审通过
     */
    @PostMapping("/admin/withdraw/firstVerifySuc")
    public ResponseModel firstVerifySuc(@RequestBody WithdrawReq req) {
        req.setOperatorId(loadCurLoginId());
        req.setStatus(AccountEnum.AcctDetailStatus.FIRST_VERIFY_SUCCESS.getKey());
        boolean flag = acctFacade.firstVerify(req);
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }


    /**
     * 提现初审拒绝
     */
    @PostMapping("/admin/withdraw/firstVerifyFail")
    public ResponseModel firstVerifyFail(@RequestBody WithdrawReq req) {
        req.setOperatorId(loadCurLoginId());
        req.setStatus(AccountEnum.AcctDetailStatus.FIRST_VERIFY_FAIL.getKey());
        boolean flag = acctFacade.firstVerify(req);
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }

    /**
     * 提现发票审核通过
     */
    @PostMapping("/admin/withdraw/verifyInvoiceSuc")
    public ResponseModel verifyInvoiceSuc(@RequestBody WithdrawReq req) {
        req.setOperatorId(loadCurLoginId());
        req.setStatus(AccountEnum.AcctDetailStatus.INVOICE_VERIFY_SUCCESS.getKey());
        boolean flag = acctFacade.verifyInvoice(req);
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }

    /**
     * 提现发票审核拒绝
     */
    @PostMapping("/admin/withdraw/verifyInvoiceFail")
    public ResponseModel verifyInvoiceFail(@RequestBody WithdrawReq req) {
        req.setOperatorId(loadCurLoginId());
        req.setStatus(AccountEnum.AcctDetailStatus.INVOICE_VERIFY_FAIL.getKey());
        boolean flag = acctFacade.verifyInvoice(req);
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }

    /**
     * 提现预生成审批单
     */
    @PostMapping("/admin/withdraw/preBatchOrder")
    public ResponseModel preBatchOrder(@RequestBody WithdrawReq req) {
        req.setOperatorId(loadCurLoginId());
        Assert.notEmpty(req.getDetailIdList(), "detailIdList 参数不能为空");
        return ResponseModel.succ(acctFacade.preBatchOrder(req));
    }


    /**
     * 生成审批单
     */
    @PostMapping("/admin/withdraw/saveBatchOrder")
    public ResponseModel saveBatchOrder(@RequestBody WithdrawReq req) {
        req.setOperatorId(loadCurLoginId());
        boolean flag = acctFacade.saveOrUpdateBatchOrder(null,req.getDetailIdList(),loadCurLoginId());
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }

    /**
     *终审成功
     */
    @PostMapping("/admin/withdraw/lastVerifySuc")
    public ResponseModel lastVerifySuc(@RequestBody WithdrawReq req) {
        req.setOperatorId(loadCurLoginId());
        req.setStatus(AccountEnum.AcctDetailStatus.LAST_VERIFY_SUCCESS.getKey());
        boolean flag = acctFacade.lastVerify(req);
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }

    /**
     *终审拒绝
     */
    @PostMapping("/admin/withdraw/lastVerifyFail")
    public ResponseModel lastVerifyFail(@RequestBody WithdrawReq req) {
        req.setOperatorId(loadCurLoginId());
        req.setStatus(AccountEnum.AcctDetailStatus.LAST_VERIFY_FAIL.getKey());
        boolean flag = acctFacade.lastVerify(req);
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }

    /**
     *出款
     */
    @PostMapping("/admin/withdraw/hasPay")
    public ResponseModel hasPay(@RequestBody WithdrawReq req) {
        req.setOperatorId(loadCurLoginId());
        req.setStatus(AccountEnum.AcctDetailStatus.HAS_PAY.getKey());
        boolean flag = acctFacade.hasPay(req);
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }

    /**
     *取消出款
     */
    @PostMapping("/admin/withdraw/cancelPay")
    public ResponseModel cancelPay(@RequestBody WithdrawReq req) {
        req.setOperatorId(loadCurLoginId());
        req.setStatus(AccountEnum.AcctDetailStatus.INVOICE_VERIFY_SUCCESS.getKey());
        boolean flag = acctFacade.cancelPay(req);
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }

    /**
     *红冲
     */
    @PostMapping("/admin/withdraw/hc")
    public ResponseModel hc(@RequestBody WithdrawReq req) {
        req.setOperatorId(loadCurLoginId());
        req.setStatus(AccountEnum.AcctDetailStatus.HC.getKey());
        boolean flag = acctFacade.hc(req);
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }

    /**
     *历史审批单搜索
     */
    @RequiresPermissions(value = {"cwgl_txgl:withdraw_final_verify_list", "cwgl_txgl:withdraw_payment_out_list"}, logical = Logical.OR)
    @GetMapping("/admin/withdraw/searchBatchOrder")
    public ResponseModel searchBatchOrder(SearchBatchReq req) {
        return acctFacade.searchBatchOrder(req);
    }

    /**
     *历史审批单搜索
     */
    @GetMapping("/admin/withdraw/searchBatchOrder/download")
    public ResponseModel searchBatchOrderDownload(SearchBatchReq req, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<>();
        params.put("fileName", "审批单列表");
        params.put("sheetName", "审批单列表数据");
        List<Map<String, Object>> list = new ArrayList<>();
        req.setPage(1);
        req.setPerPage(100);
        while (true) {
            ResponseModel resp = acctFacade.searchBatchOrder(req);
            Map<String, Object> content = (Map<String, Object>) resp.getContent();
            Boolean hasNextPage = (Boolean) content.get("hasNextPage");
            List<BatchOrderInfoResp> itemList = (List<BatchOrderInfoResp>) content.get("list");
            for (BatchOrderInfoResp item : itemList) {
                Map<String, Object> map = ToolUtil.convertBeanToMap(item);
                list.add(map);
            }
            if (!hasNextPage) {
                break;
            }
            req.setPage(req.getPage() + 1);
        }
        params.put("list", list);
        String[] keys = new String[]{
                "batchOrderNo", "totalAmount", "batchOrderCreateTime"
        };
        params.put("keys", keys);
        String[] columnNames = new String[]{
                "审批单号", "申请金额", "生成审批单时间"
        };
        params.put("columnNames", columnNames);
        DownloadUtil.downloadExcel(request, response, params);
        return ResponseModel.succ();
    }

    /**
     *更新审批单
     */
    @PostMapping("/admin/withdraw/updateBatchOrder")
    public ResponseModel updateBatchOrder(@RequestBody WithdrawReq req) {
        if(StringUtils.isEmpty(req.getBatchOrderNo())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        boolean flag = acctFacade.saveOrUpdateBatchOrder(Long.parseLong(req.getBatchOrderNo()),req.getDetailIdList(),loadCurLoginId());
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }

    /**
     *删除审批单明细
     */
    @PostMapping("/admin/withdraw/deleteDetailRef")
    public ResponseModel deleteDetailRef(@RequestBody WithdrawReq req) {
        if(req.getRefId() == null){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        boolean flag = acctFacade.deleteDetailRef(req);
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }


    /** 根据审批单号查询列表 **/
    @PostMapping("/admin/withdraw/getDetailByBatchNo")
    public ResponseModel getDetailByBatchNo(@RequestBody WithdrawReq req) {
        if(req.getBatchOrderNo() == null){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        return acctFacade.getDetailByBatchNo(req);
    }

    /** 查询未添加到审批单中的明细列表 **/
    @PostMapping("/admin/withdraw/getNoBatchDetail")
    public ResponseModel getNoBatchDetail() {

        return ResponseModel.succ(acctFacade.getNoBatchDetail());
    }


}
