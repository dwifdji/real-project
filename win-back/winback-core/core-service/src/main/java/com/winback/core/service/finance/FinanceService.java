package com.winback.core.service.finance;

import com.github.pagehelper.PageInfo;
import com.winback.arch.common.ResponseModel;
import com.winback.core.facade.finance.req.FinanceReq;
import com.winback.core.model.payment.TFinanceExpend;
import com.winback.core.model.payment.TFinanceInvoice;
import com.winback.core.model.payment.TFinanceReceivable;
import com.winback.core.vo.finance.WorkBenchVO;

import java.util.List;


/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/22 14:33
 */
public interface FinanceService {


    /**
     *获取出款类表
     */
    PageInfo getExpendList(FinanceReq.expendListReq req);


    /**
     *出款统计金额
     */
    String getExpendSum(FinanceReq.expendListReq req);



    /**
     *获取回款列表
     * */
    PageInfo  getReceivableList(FinanceReq.receivableListReq req);


    /**
     *回款统计金额
     */
    String  getReceivableSum(FinanceReq.receivableListReq req);



    /**
     *获取发票列表
     */
    PageInfo  getInvoiceList(FinanceReq.invoiceListReq req);


    /**
     *发票
     */
    String  getInvoiceSum(FinanceReq.invoiceListReq req);




    /**
     *审核信息
     */
    ResponseModel audit(FinanceReq.auditReq req);



    /**
     *保存出款记录
     */
    int saveExpend(TFinanceExpend req);



    /**
     *保存回款记录
     */
    int saveReceivable(TFinanceReceivable req);



    /**
     *保存发票记录
     */
    int saveInvoice(TFinanceInvoice req);




    /**
     *获取出款审核列表
     */
    PageInfo getExpendAuditList(FinanceReq.commonReq req);


    /**
     *获取回款审核类表
     */
    PageInfo getReceivableAuditList(FinanceReq.commonReq req);




    /**
     *获取审核列表
     */
    PageInfo getInvoiceAuditList(FinanceReq.commonReq req);


    /**
     *更新凭证信息
     */
    ResponseModel uploadCertificate(FinanceReq.uploadCertificateReq req);

    /**
     *
     * 控制台财务信息
     */
    List<WorkBenchVO> getFinanceWorkBench();
}
