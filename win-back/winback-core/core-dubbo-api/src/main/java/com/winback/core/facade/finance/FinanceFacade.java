package com.winback.core.facade.finance;

import com.winback.arch.common.ResponseModel;
import com.winback.core.facade.finance.req.FinanceReq;

/**
 * 描述：财务管理Facade 接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/22 13:32
 */
public interface FinanceFacade {


    ResponseModel getExpendList(FinanceReq.expendListReq req);


    ResponseModel getReceivableList(FinanceReq.receivableListReq req);


    ResponseModel getInvoiceList(FinanceReq.invoiceListReq req);


    ResponseModel audit(FinanceReq.auditReq req);


    ResponseModel saveExpend(FinanceReq.saveExpendReq req);


    ResponseModel saveReceivable(FinanceReq.saveReceivableReq req);


    ResponseModel saveInvoice(FinanceReq.saveInvoiceReq req);


    ResponseModel getExpendAuditList(FinanceReq.commonReq req);


    ResponseModel getReceivableAuditList(FinanceReq.commonReq req);


    ResponseModel getInvoiceAuditList(FinanceReq.commonReq req);


    ResponseModel uploadCertificate(FinanceReq.uploadCertificateReq req);

}
