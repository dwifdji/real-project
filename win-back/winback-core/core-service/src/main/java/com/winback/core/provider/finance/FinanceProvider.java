package com.winback.core.provider.finance;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageSerializable;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.utils.DateUtil;
import com.winback.arch.core.sysconfig.properties.GatewayProperties;
import com.winback.core.commons.constants.CaseEnum;
import com.winback.core.commons.constants.FinanceEnum;
import com.winback.core.dao.payment.TFinanceExpendDao;
import com.winback.core.dao.payment.TFinanceInvoiceDao;
import com.winback.core.dao.payment.TFinanceReceivableDao;
import com.winback.core.dto._case.UpdateCaseReq;
import com.winback.core.facade.finance.FinanceFacade;
import com.winback.core.facade.finance.req.FinanceReq;
import com.winback.core.model.payment.TFinanceExpend;
import com.winback.core.model.payment.TFinanceInvoice;
import com.winback.core.model.payment.TFinanceReceivable;
import com.winback.core.service._case.CaseService;
import com.winback.core.service.finance.FinanceService;
import com.winback.core.vo.finance.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/22 13:50
 */
@Component
@Service(version = "1.0.0")
public class FinanceProvider implements FinanceFacade {

    @Autowired
    private GatewayProperties gatewayProperties;


    @Autowired
    private FinanceService financeService;

    @Autowired
    private CaseService caseService;


    @Autowired
    private TFinanceInvoiceDao financeInvoiceDao;


    @Autowired
    private TFinanceExpendDao financeExpendDao;


    @Autowired
    private TFinanceReceivableDao financeReceivableDao;


    @Override
    public ResponseModel getExpendList(FinanceReq.expendListReq req) {

        PageInfo info = financeService.getExpendList(req);

        String totalAmount = financeService.getExpendSum(req);
        FinanceVo vo = new FinanceVo();

        vo.setList(fromList(info.getList()));
        vo.setTotal(info.getTotal());
        vo.setTotalAmount(StringUtils.isEmpty(totalAmount)?"0":totalAmount);
        return ResponseModel.succ(vo);
    }

    private List<ExpendVo> fromList(List<ExpendVo> list) {

        List<ExpendVo> voList = new ArrayList<>();

        for(ExpendVo vo :list){
            vo.setStatusDesc(FinanceEnum.EXPEND_STATUS.getDesc(vo.getStatus()));
            voList.add(vo);
        }

        return  voList;
    }

    @Override
    public ResponseModel getReceivableList(FinanceReq.receivableListReq req) {

        PageInfo info = financeService.getReceivableList(req);

        String totalAmount = financeService.getReceivableSum(req);
        FinanceVo vo = new FinanceVo();

        vo.setList(getReceivableFormat(info.getList()));
        vo.setTotal(info.getTotal());
        vo.setTotalAmount(StringUtils.isEmpty(totalAmount)?"0":totalAmount);

        return ResponseModel.succ(vo);
    }


    private List<ReceivableVo> getReceivableFormat(List<ReceivableVo> list) {

        List<ReceivableVo> voList = new ArrayList<>();

        for(ReceivableVo vo :list){

            vo.setStatusDesc(FinanceEnum.RECEIVABLE_STATUS.getDesc(vo.getStatus()));
            vo.setType(FinanceEnum.RECEIVABLE_TYPE.getDesc(vo.getType()));
            voList.add(vo);
        }

        return voList;

    }
    @Override
    public ResponseModel getInvoiceList(FinanceReq.invoiceListReq req) {

        PageInfo info = financeService.getInvoiceList(req);

        String totalAmount = financeService.getInvoiceSum(req);
        FinanceVo vo = new FinanceVo();
        vo.setList(getInvoiceFormat(info.getList()));
        vo.setTotal(info.getTotal());
        vo.setTotalAmount(StringUtils.isEmpty(totalAmount)?"0":totalAmount);

        return ResponseModel.succ(vo);
    }

    private List<InvoiceVo> getInvoiceFormat(List<InvoiceVo> list) {

        List<InvoiceVo> invoiceVoList = new ArrayList<>();

        for(InvoiceVo vo : list){
            vo.setStatusDesc(FinanceEnum.INVOICE_STATUS.getDesc(vo.getStatus()));
            vo.setType(FinanceEnum.INVOICE_TYPE.getDesc(vo.getType()));
            invoiceVoList.add(vo);
        }

        return invoiceVoList;
    }

    @Override
    @Transactional
    public ResponseModel audit(FinanceReq.auditReq req) {

        financeService.audit(req);

        String status = getStatusByType(req.getType());

        for(String id :req.getIds()){
            updateAuditStatus(id,req,status);
        }

        return ResponseModel.succ();
    }

    private void updateAuditStatus(String id, FinanceReq.auditReq req,String status) {

        Integer caseId = getCaseId(id,req.getType());

        updateCaseStatus(String.valueOf(caseId),req.getLoginId(),status);

    }

    private Integer getCaseId(String id, String type) {


        if("1".equals(type)){
            return financeExpendDao.selectById(id).getCaseId();
        }else if("2".equals(type)){
            return financeReceivableDao.selectById(id).getCaseId();
        }else if("3".equals(type)){
            return financeInvoiceDao.selectById(id).getCaseId();
         }

        return null;
    }

    private String getStatusByType(String type) {
        if("1".equals(type)){

            return CaseEnum.CaseStep.HAS_LOAN.getKey();
        }else if("2".equals(type)){
            return CaseEnum.CaseStep.HAS_RECEIVED_PAY.getKey();
        }else if("3".equals(type)){
            return CaseEnum.CaseStep.HAS_INVOICED.getKey();
        }
        return null;
    }

    @Override
    public ResponseModel saveExpend(FinanceReq.saveExpendReq req) {

        TFinanceExpend financeExpend = new TFinanceExpend();
        financeExpend.setAcctName(req.getAcctName());
        financeExpend.setAcctNo(req.getAcctNo());
        financeExpend.setAmount(new BigDecimal(req.getAmount()));
        financeExpend.setBankName(req.getAcctBankName());
        financeExpend.setCaseId(Integer.valueOf(req.getCaseId()));
        financeExpend.setMsg(req.getMsg());
        financeExpend.setCreateTime(DateUtil.getSysTime());
        financeExpend.setOperId(req.getLoginId());

        financeService.saveExpend(financeExpend);

        //添加放款审核后更新case表状态
        updateCaseStatus(req.getCaseId(),req.getLoginId(),CaseEnum.CaseStep.ADD_LOAN_APPLY.getKey());


        return ResponseModel.succ();
    }

    private void updateCaseStatus(String caseId, Integer loginId, String status) {

        UpdateCaseReq req = new UpdateCaseReq();
        req.setId(Integer.valueOf(caseId));
        req.setOperate(status);
        req.setOperatorId(loginId);
        caseService.updateCaseOperate(req);

    }

    @Override
    public ResponseModel saveReceivable(FinanceReq.saveReceivableReq req) {

        TFinanceReceivable receivable = new TFinanceReceivable();

        receivable.setAmount(new BigDecimal(req.getAmount()));
        receivable.setCaseAmount(new BigDecimal(req.getCaseAmount()));
        receivable.setCaseId(Integer.valueOf(req.getCaseId()));
        receivable.setCertificate(req.getCertificateUrl());
        receivable.setCost(new BigDecimal(req.getCost()));
        receivable.setMsg(req.getMsg());
        receivable.setCreateTime(DateUtil.getSysTime());
        receivable.setOperId(req.getLoginId());
        receivable.setType(Integer.valueOf(req.getType()));
        financeService.saveReceivable(receivable);


        //添加回款更新case表状态
        updateCaseStatus(req.getCaseId(),req.getLoginId(),CaseEnum.CaseStep.ADD_RECEIVED_APPLY.getKey());


        return ResponseModel.succ();
    }

    @Override
    public ResponseModel saveInvoice(FinanceReq.saveInvoiceReq req) {

        TFinanceInvoice invoice = new TFinanceInvoice();
        invoice.setAmount(new BigDecimal(req.getAmount()));
        invoice.setCaseId(Integer.valueOf(req.getCaseId()));
        invoice.setComDuty(req.getDutyNo());
        invoice.setComName(req.getComName());
        invoice.setCreateTime(DateUtil.getSysTime());
        invoice.setType(Integer.valueOf(req.getType()));
        invoice.setPhone(req.getPhone());
        invoice.setOperId(req.getLoginId());
        invoice.setDeleteFlag(false);

        financeService.saveInvoice(invoice);

        //添加开票更新case表状态
        updateCaseStatus(req.getCaseId(),req.getLoginId(),CaseEnum.CaseStep.ADD_INVOICE_APPLY.getKey());

        return ResponseModel.succ();
    }

    @Override
    public ResponseModel getExpendAuditList(FinanceReq.commonReq req) {
        return ResponseModel.succ(financeService.getExpendAuditList(req));
    }

    @Override
    public ResponseModel getReceivableAuditList(FinanceReq.commonReq req) {

        PageInfo pageInfo = financeService.getReceivableAuditList(req);

        PageSerializable page = new PageSerializable<>();
        page.setList(getReceivableAuditFormat(pageInfo.getList()));
        page.setTotal(pageInfo.getTotal());
        return ResponseModel.succ(page);

     }


    private List<ReceivableAuditVo> getReceivableAuditFormat(List<ReceivableAuditVo> list) {

        List<ReceivableAuditVo> voList = new ArrayList<>();

        for(ReceivableAuditVo vo :list){

            vo.setStatusDesc(FinanceEnum.RECEIVABLE_STATUS.getDesc(vo.getStatus()));
            vo.setType(FinanceEnum.RECEIVABLE_TYPE.getDesc(vo.getType()));
            voList.add(vo);
        }

        return voList;

    }

    @Override
    public ResponseModel getInvoiceAuditList(FinanceReq.commonReq req) {
        return ResponseModel.succ(financeService.getInvoiceAuditList(req));
    }

    @Override
    public ResponseModel uploadCertificate(FinanceReq.uploadCertificateReq req) {
        return financeService.uploadCertificate(req);
    }
}
