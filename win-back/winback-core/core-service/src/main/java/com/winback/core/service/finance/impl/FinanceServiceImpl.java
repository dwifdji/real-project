package com.winback.core.service.finance.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageSerializable;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.utils.DateUtil;
import com.winback.core.commons.constants.FinanceEnum;
import com.winback.core.dao.payment.TFinanceExpendDao;
import com.winback.core.dao.payment.TFinanceInvoiceDao;
import com.winback.core.dao.payment.TFinanceReceivableDao;
import com.winback.core.dto.finance.CommonDto;
import com.winback.core.dto.finance.ExpendDto;
import com.winback.core.dto.finance.InvoiceDto;
import com.winback.core.dto.finance.ReceivableDto;
import com.winback.core.facade.finance.req.FinanceReq;
import com.winback.core.model.payment.TFinanceExpend;
import com.winback.core.model.payment.TFinanceInvoice;
import com.winback.core.model.payment.TFinanceReceivable;
import com.winback.core.service.finance.FinanceService;
import com.winback.core.vo.finance.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/22 15:39
 */
@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private TFinanceInvoiceDao financeInvoiceDao;


    @Autowired
    private TFinanceExpendDao financeExpendDao;


    @Autowired
    private TFinanceReceivableDao financeReceivableDao;


    @Override
    public PageInfo getExpendList(FinanceReq.expendListReq req) {


        PageHelper.startPage(req.getPage(), req.getPerPage());

        ExpendDto dto = new ExpendDto();

        BeanUtils.copyProperties(req, dto);

        List<ExpendVo> list = financeExpendDao.getExpendList(dto);


        return new PageInfo<>(list);
    }



    @Override
    public PageInfo getReceivableList(FinanceReq.receivableListReq req) {

        PageHelper.startPage(req.getPage(), req.getPerPage());

        ReceivableDto dto = new ReceivableDto();

        BeanUtils.copyProperties(req, dto);

        List<ReceivableVo> list = financeReceivableDao.getReceivableList(dto);


        return new PageInfo<>(list);

    }



    @Override
    public PageInfo getInvoiceList(FinanceReq.invoiceListReq req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());

        InvoiceDto dto = new InvoiceDto();

        BeanUtils.copyProperties(req, dto);

        List<InvoiceVo> list = financeInvoiceDao.getInvoiceList(dto);


        return new PageInfo<>(list);
    }



    @Override
    public ResponseModel audit(FinanceReq.auditReq req) {

        for(String id :req.getIds()){
            //放款审核
            if("1".equals(req.getType())){
                TFinanceExpend expend = new TFinanceExpend();
                expend.setId(Integer.valueOf(id));
                expend.setStatus(Integer.valueOf(req.getStatus()));
                expend.setUpdateTime(DateUtil.getSysTime());
                expend.setFinId(req.getLoginId());
                expend.setExpendTime(DateUtil.getSysTime());
                financeExpendDao.updateById(expend);
                //回款审核
            }else if("2".equals(req.getType())){

                TFinanceReceivable receivable = new TFinanceReceivable();
                receivable.setId(Integer.valueOf(id));
                receivable.setStatus(Integer.valueOf(req.getStatus()));
                receivable.setUpdateTime(DateUtil.getSysTime());
                receivable.setFinId(req.getLoginId());
                 financeReceivableDao.updateById(receivable);

                //发票审核
            }else  if("3".equals(req.getType())){
                TFinanceInvoice invoice = new TFinanceInvoice();
                invoice.setId(Integer.valueOf(id));
                invoice.setStatus(Integer.valueOf(req.getStatus()));
                invoice.setUpdateTime(DateUtil.getSysTime());
                invoice.setFinId(req.getLoginId());
                invoice.setInvoiceTime(DateUtil.getSysTime());
                financeInvoiceDao.updateById(invoice);
            }
        }
        return ResponseModel.succ();
    }

    @Override
    public int saveExpend(TFinanceExpend req) {
        return financeExpendDao.insert(req);
    }

    @Override
    public int saveReceivable(TFinanceReceivable req) {
        return financeReceivableDao.insert(req);
    }

    @Override
    public int saveInvoice(TFinanceInvoice req) {
        return financeInvoiceDao.insert(req);
    }

    @Override
    public PageInfo getExpendAuditList(FinanceReq.commonReq req) {

        PageHelper.startPage(req.getPage(), req.getPerPage());

        CommonDto dto = new CommonDto();

        BeanUtils.copyProperties(req, dto);

        List<ExpendAuditVo> list = financeExpendDao.getExpendAuditList(dto);

        return new PageInfo<>(list);

     }

    @Override
    public PageInfo getReceivableAuditList(FinanceReq.commonReq req) {

        PageHelper.startPage(req.getPage(), req.getPerPage());

        CommonDto dto = new CommonDto();

        BeanUtils.copyProperties(req, dto);

        List<ReceivableAuditVo> list = financeReceivableDao.getReceivableAuditList(dto);


        return new PageInfo<>(list);

     }






    @Override
    public PageInfo getInvoiceAuditList(FinanceReq.commonReq req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());

        CommonDto dto = new CommonDto();

        BeanUtils.copyProperties(req, dto);

        List<InvoiceAuditVo> list = financeInvoiceDao.getInvoiceAuditList(dto);

        return new PageInfo<>(list);
    }

    @Override
    public ResponseModel uploadCertificate(FinanceReq.uploadCertificateReq req) {
        if("1".equals(req.getType())){
            TFinanceExpend expend = new TFinanceExpend();
            expend.setId(Integer.valueOf(req.getId()));
            expend.setCertificate(req.getCertificateUrl());
            expend.setUpdateTime(DateUtil.getSysTime());
            financeExpendDao.updateById(expend);

        }else if("2".equals(req.getType())){

            TFinanceInvoice invoice = new TFinanceInvoice();
            invoice.setId(Integer.valueOf(req.getId()));
            invoice.setCertificate(req.getCertificateUrl());
            invoice.setUpdateTime(DateUtil.getSysTime());
            financeInvoiceDao.updateById(invoice);
        }else if("3".equals(req.getType())){
            TFinanceReceivable receivable = new TFinanceReceivable();
            receivable.setId(Integer.valueOf(req.getId()));
            receivable.setCertificate(req.getCertificateUrl());
            receivable.setUpdateTime(DateUtil.getSysTime());

            financeReceivableDao.updateById(receivable);
        }

        return ResponseModel.succ();
    }

    @Override
    public List<WorkBenchVO> getFinanceWorkBench() {

        return financeExpendDao.getFinanceWorkBench();
    }


    @Override
    public String getExpendSum(FinanceReq.expendListReq req) {
        ExpendDto   dto = new ExpendDto();
        BeanUtils.copyProperties(req, dto);

        return financeExpendDao.getExpendSum(dto);
    }

    @Override
    public String getReceivableSum(FinanceReq.receivableListReq req) {

        ReceivableDto   dto = new ReceivableDto();
        BeanUtils.copyProperties(req, dto);
        return financeReceivableDao.getReceivableSum(dto);
    }

    @Override
    public String getInvoiceSum(FinanceReq.invoiceListReq req) {

        InvoiceDto   dto = new InvoiceDto();
        BeanUtils.copyProperties(req, dto);
        return financeInvoiceDao.getInvoiceSum(dto);
    }
}
