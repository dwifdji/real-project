package com._360pai.core.service.account.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.exception.ExceptionEnumImpl;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.common.utils.ToolUtil;
import com._360pai.core.aspact.EmailService;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.SmsEmailConfig;
import com._360pai.core.condition.account.TBatchDetailRefCondition;
import com._360pai.core.condition.agreement.WithdrawAgreementCondition;
import com._360pai.core.dao.account.*;
import com._360pai.core.dao.agreement.WithdrawAgreementDao;
import com._360pai.core.dao.applet.TAppletShopDao;
import com._360pai.core.dao.payment.AuctionOrderDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.AcctReq;
import com._360pai.core.facade.account.req.SearchBatchReq;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.account.resp.AcctResp;
import com._360pai.core.facade.account.resp.WithdrawResp;
import com._360pai.core.facade.account.vo.*;
import com._360pai.core.model.account.*;
import com._360pai.core.model.agreement.WithdrawAgreement;
import com._360pai.core.model.applet.TAppletShop;
import com._360pai.core.model.payment.AuctionOrder;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.AcctService;
import com._360pai.core.service.assistant.SmsHelperService;
import com._360pai.core.service.assistant.TBankService;
import com._360pai.core.utils.RespConvertUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author RuQ
 * @Title: AcctServiceImpl
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/11/23 16:48
 */

@Service
public class  AcctServiceImpl implements AcctService {

    @Autowired
    private TAcctDao tAcctDao;

    @Autowired
    private TAcctDetailDao tAcctDetailDao;

    @Autowired
    private TBankService tBankService;
    @Autowired
    private TPartyDao partyDao;
    @Autowired
    private AccountService accountService;
    @Autowired
    private WithdrawAgreementDao withdrawAgreementDao;
    @Autowired
    private TInvoiceDao invoiceDao;
    @Autowired
    private TUserDao userDao;
    @Autowired
    private TCompanyDao companyDao;
    @Autowired
    private TAgencyDao agencyDao;

    @Autowired
    private TInvoiceDao tInvoiceDao;


    @Autowired
    private TBatchDetailRefDao tBatchDetailRefDao;

    @Autowired
    private TBatchOrderDao tBatchOrderDao;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SmsHelperService smsHelperService;
    @Autowired
    private AuctionOrderDao auctionOrderDao;
    @Autowired
    private GatewayMqSender mqSender;
    @Autowired
    private TAppletShopDao shopDao;

    @Override
    @Transactional
    public boolean addAcctAmount(Integer acctId, BigDecimal amount,String sourceType,String sourceOrderId,Integer shopId) {
        TAcct tAcct = tAcctDao.getByIdForUpdate(acctId);
        if(tAcct == null){
            throw new BusinessException(ExceptionEnumImpl.HAS_NO_ACCT_ERROR);
        }

        if(sourceType.equals(AccountEnum.AcctOperateType.RECHARGE.getKey())){
            insertAcctDetail(tAcct.getId(),sourceType,amount,amount.add(tAcct.getAvailAmt()),amount.add(tAcct.getTotalAmt()),tAcct.getLockAmt(), sourceOrderId,null,null);
            insertAcctDetail(tAcct.getId(),AccountEnum.AcctOperateType.OTHER.getKey() ,amount,tAcct.getAvailAmt(),tAcct.getTotalAmt(),tAcct.getLockAmt(),sourceOrderId,null,null);
            return true;

        }else{
            insertAcctDetail(tAcct.getId(),sourceType,amount,amount.add(tAcct.getAvailAmt()),amount.add(tAcct.getTotalAmt()),tAcct.getLockAmt(),sourceOrderId,null,shopId);
            TAcct updateParam = new TAcct();
            updateParam.setId(tAcct.getId());
            updateParam.setTotalAmt(amount);
            updateParam.setAvailAmt(amount);
            return tAcctDao.addAmt(updateParam)==1;
        }


    }

    @Override
    @Transactional
    public WithdrawResp withdrawAcctAmount(String type, Integer partyId, BigDecimal amount, Integer bankAccountId) {
        TAcct tAcct = tAcctDao.getByPartyIdTypeForUpdate(type,partyId);
        if(tAcct == null){
            throw new BusinessException(ExceptionEnumImpl.HAS_NO_ACCT_ERROR);
        }

        TBankAccount bankAccount  = tBankService.getBankAccountById(bankAccountId);
        if(bankAccount == null || ! String.valueOf(bankAccount.getAcctId()).equals(String.valueOf(tAcct.getId()))){
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }

        if(amount.compareTo(BigDecimal.ZERO)<=0){
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }

        if(amount.compareTo(tAcct.getAvailAmt())>0 || amount.compareTo(tAcct.getTotalAmt())>0){
            throw new BusinessException(ExceptionEnumImpl.AVAIL_AMOUNT_NOT_ENOUGH_ERROR);
        }

        Long detailId = insertAcctDetail(tAcct.getId(), AccountEnum.AcctOperateType.WITHDRAW.getKey(),amount,tAcct.getAvailAmt().subtract(amount),tAcct.getTotalAmt(),tAcct.getLockAmt().add(amount),null,bankAccountId,null);

        TAcct subParam = new TAcct();
        subParam.setId(tAcct.getId());
        subParam.setAvailAmt(amount);
        tAcctDao.subAmt(subParam);

        TAcct addParam = new TAcct();
        addParam.setId(tAcct.getId());
        addParam.setLockAmt(amount);
        tAcctDao.addAmt(addParam);

        TAcctDetail detail = tAcctDetailDao.selectById(detailId);

        WithdrawResp resp = new WithdrawResp();
        resp.setAcctDetailId(detailId);
        resp.setApplyTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(detail.getCreateTime()));


        String name = "";
        if (AccountEnum.AcctType.AGENCY.getKey().equals(type)) {
            TAgency agency = agencyDao.selectById(partyId);
            if (agency != null) {
                name = agency.getName();
            }
        } else {
            AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(partyId);
            if (accountBaseDto != null) {
                name = accountBaseDto.getName();
            }
        }
        smsHelperService.userWithdrewNotify(emailService.getServicePhone(SmsEmailConfig.Bus_Type_23.getKey()), name);
        return resp;
    }

    @Override
    @Transactional
    public boolean cancelPay(Long detailId,Integer operatorId,String reason){

        TAcctDetail detail = tAcctDetailDao.selectById(detailId);
        if(!AccountEnum.AcctDetailStatus.HAS_PAY.getKey().equals(detail.getStatus())){
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }
        TAcct tAcct = tAcctDao.getByIdForUpdate(detail.getAcctId());

//        TAcct subParam = new TAcct();
//        subParam.setId(tAcct.getId());
//        subParam.setAvailAmt(detail.getAmount());
//        tAcctDao.subAmt(subParam);

        TAcct addParam = new TAcct();
        addParam.setId(tAcct.getId());
        addParam.setLockAmt(detail.getAmount());
        addParam.setTotalAmt(detail.getAmount());
        tAcctDao.addAmt(addParam);

        TAcctDetail updateParam = new TAcctDetail();
        updateParam.setId(detailId);
        updateParam.setCancelTime(new Date());
        updateParam.setCancelPayOperatorId(operatorId);
        updateParam.setCancelPayReason(reason);
        updateParam.setStatus(AccountEnum.AcctDetailStatus.INVOICE_VERIFY_SUCCESS.getKey());
        return tAcctDetailDao.updateById(updateParam) == 1;


    }

    @Override
    @Transactional
    public boolean hc(Long detailId, Integer operatorId, String reason) {
        TAcctDetail detail = tAcctDetailDao.selectById(detailId);
        if(!AccountEnum.AcctDetailStatus.HAS_PAY.getKey().equals(detail.getStatus())){
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }
        TAcct tAcct = tAcctDao.getByIdForUpdate(detail.getAcctId());

        TAcct addParam = new TAcct();
        addParam.setId(tAcct.getId());
        addParam.setAvailAmt(detail.getAmount());
        addParam.setTotalAmt(detail.getAmount());
        tAcctDao.addAmt(addParam);

        TAcctDetail updateParam = new TAcctDetail();
        updateParam.setId(detailId);
        updateParam.setHcTime(new Date());
        updateParam.setHcOperatorId(operatorId);
        updateParam.setHcReason(reason);
        updateParam.setStatus(AccountEnum.AcctDetailStatus.HAS_MARK_HC.getKey());
        tAcctDetailDao.updateById(updateParam);

        TAcctDetail afterUpdateDetail = tAcctDetailDao.selectById(detailId);
        String newId = afterUpdateDetail.getId()+"0";
        afterUpdateDetail.setId(Long.parseLong(newId));
        afterUpdateDetail.setStatus(AccountEnum.AcctDetailStatus.HC.getKey());
        afterUpdateDetail.setAmount(detail.getAmount().multiply(new BigDecimal(-1)));

        TBatchDetailRefCondition refCondition = new TBatchDetailRefCondition();
        refCondition.setDetailId(detailId);
        refCondition.setIsDelete(false);
        TBatchDetailRef batchDetailRef = tBatchDetailRefDao.selectFirst(refCondition);
        saveBatchDetailRef(batchDetailRef.getBatchId(),Long.parseLong(newId));

        return  tAcctDetailDao.insert(afterUpdateDetail) == 1;
    }

    @Override
    public List<TBatchDetailRef> findDetailRefListByBatchId(Long batchId) {
        TBatchDetailRefCondition condition = new TBatchDetailRefCondition();
        condition.setIsDelete(false);
        condition.setBatchId(batchId);
        return tBatchDetailRefDao.selectList(condition);
    }

    @Override
    @Transactional
    public boolean withdrawFail(Long acctDetailId){
        TAcctDetail acctDetail = tAcctDetailDao.selectById(acctDetailId);
        BigDecimal amount = acctDetail.getAmount();
        TAcct tAcct = tAcctDao.getByIdForUpdate(acctDetail.getAcctId());
        TAcct subParam = new TAcct();
        subParam.setId(tAcct.getId());
        subParam.setLockAmt(amount);
        tAcctDao.subAmt(subParam);

        TAcct addParam = new TAcct();
        addParam.setId(tAcct.getId());
        addParam.setAvailAmt(amount);
        return  tAcctDao.addAmt(addParam)==1;
    }

    @Override
    @Transactional
    public boolean releaseAcctLockedAmount(Long acctDetailId) {

        TAcctDetail acctDetail = tAcctDetailDao.selectById(acctDetailId);
        BigDecimal amount = acctDetail.getAmount();

        TAcct tAcct = tAcctDao.getByIdForUpdate(acctDetail.getAcctId());
        if(tAcct == null){
            throw new BusinessException(ExceptionEnumImpl.HAS_NO_ACCT_ERROR);
        }

        if(amount.compareTo(BigDecimal.ZERO)<=0){
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }

        if(tAcct.getLockAmt().compareTo(amount)<0 || tAcct.getTotalAmt().compareTo(amount)<0){
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }
        TAcct updateParam = new TAcct();
        updateParam.setId(tAcct.getId());
        updateParam.setTotalAmt(amount);
        updateParam.setLockAmt(amount);
        return tAcctDao.subAmt(updateParam) == 1;
    }

    @Override
    public PageInfoResp<InviteCommissionVo> getInviteCommissionListByPage(AcctReq.QueryReq req) {
        PageInfoResp<InviteCommissionVo> resp = new PageInfoResp<>();
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(req.getQ())) {
            params.put("q", req.getQ());
        }

        if (StringUtils.isNotBlank(req.getInviteCode())) {
            params.put("inviteCode", req.getInviteCode());
        }
        if (StringUtils.isNotBlank(req.getCreatedAtFrom()) && StringUtils.isNotBlank(req.getCreatedAtTo())) {
            params.put("createdAtFrom", req.getCreatedAtFrom());
            params.put("createdAtTo", req.getCreatedAtTo());
        }
        PageInfo pageInfo = tAcctDetailDao.getInviteCommissionList(req.getPage(), req.getPerPage(), params, "");
        List<InviteCommissionVo> list = pageInfo.getList();
        for (InviteCommissionVo vo : list) {
            vo.setName(StringEscapeUtils.unescapeJava(vo.getName()));
            vo.setSubName(StringEscapeUtils.unescapeJava(vo.getSubName()));
        }
        resp.setList(list);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public InviteCommissionVo getInviteCommission(AcctReq.BaseReq req) {
        TAcctDetail detail = tAcctDetailDao.selectById(Long.parseLong(req.getId()));
        if (detail == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TAcct acct = tAcctDao.selectById(detail.getAcctId());
        InviteCommissionVo vo = new InviteCommissionVo();
        vo.setId(detail.getId() + "");
        vo.setShopInvitedCommission(detail.getAmount());
        vo.setCreateTime(detail.getCreateTime());
        vo.setReceiveTime(detail.getCreateTime());
        if (AccountEnum.AcctType.AGENCY.getKey().equals(acct.getType())) {
            vo.setInviteCode(AccountEnum.InviteType.JG.getKey() + acct.getPartyId());
            TAgency agency = agencyDao.selectById(acct.getPartyId());
            vo.setName(agency.getName());
            vo.setMobile(agency.getMobile());
        } else if (AccountEnum.AcctType.SHOP.getKey().equals(acct.getType())) {
            vo.setInviteCode(AccountEnum.InviteType.DP.getKey() + acct.getPartyId());
            TAppletShop shop = shopDao.selectById(acct.getPartyId());
            vo.setName(shop.getName());
            vo.setMobile(shop.getMobile());
        } else if (AccountEnum.AcctType.USER.getKey().equals(acct.getType()) || AccountEnum.AcctType.COMPANY.getKey().equals(acct.getType())) {
            TAppletShop shop = shopDao.getByPartyId(acct.getPartyId());
            vo.setInviteCode(AccountEnum.InviteType.DP.getKey() + shop.getId());
            AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(acct.getPartyId());
            vo.setName(accountBaseDto.getName());
            vo.setMobile(accountBaseDto.getMobile());
        }
        if (detail.getInviteShopId() != null) {
            TAppletShop subShop = shopDao.selectById(detail.getInviteShopId());
            vo.setSubMobile(subShop.getMobile());
            if (subShop.getPartyId() != null) {
                AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(subShop.getPartyId());
                vo.setSubName(accountBaseDto.getName());
            } else {
                vo.setSubName("");
            }
            vo.setSubInviteCode(AccountEnum.InviteType.DP.getKey() + detail.getInviteShopId());
        }
        return vo;
    }

    @Override
    public PageInfoResp<CommissionVo> getMyCommissionListByPage(AcctReq.QueryReq req) {
        PageInfoResp<CommissionVo> resp = new PageInfoResp<>();
        TAcct acct = tAcctDao.getByPartyIdType(req.getPartyId(), req.getPartyType());
        PageInfo pageInfo = tAcctDetailDao.getMyCommissionList(req.getPage(), req.getPerPage(), acct.getId(), "");
        List<TAcctDetail> list = pageInfo.getList();
        List<CommissionVo> itemList = new ArrayList<>();
        for (TAcctDetail detail : list) {
            try {
                CommissionVo commissionVo = new CommissionVo();
                commissionVo.setId(detail.getId() + "");
                commissionVo.setType(detail.getType());
                commissionVo.setAmount(detail.getAmount());
                if (AccountEnum.AcctOperateType.SHOP_INVITED_COMMISSION.getKey().equals(detail.getType())) {
                    TAppletShop shop = shopDao.selectById(detail.getInviteShopId());
                    if (shop != null) {
                        commissionVo.setMobile(shop.getMobile());
                    }
                } else if (AccountEnum.AcctOperateType.BUY_COMMISSION.getKey().equals(detail.getType())) {
                    AuctionOrder order = auctionOrderDao.selectById(Long.parseLong(detail.getSourceOrderId()));
                    if (order != null) {
                        commissionVo.setMobile(accountService.getNotifierMobile(order.getBuyerId()));
                    }
                }
                commissionVo.setName(AccountEnum.AcctOperateType.getValueByKey(commissionVo.getType()));
                commissionVo.setMobile(ToolUtil.maskMobile(commissionVo.getMobile()));
                commissionVo.setCreateTime(detail.getCreateTime());
                commissionVo.setReleaseTime(commissionVo.getCreateTime());
                itemList.add(commissionVo);
            } catch (Exception e) {
                e.printStackTrace();
                mqSender.sendTryCatchExceptionEmail(detail.getId(), e);
            }
        }
        resp.setList(itemList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public PageInfoResp<AcctDetailVo> getFrontAcctDetailListByPage(AcctReq.QueryReq req) {
        PageInfoResp<AcctDetailVo> resp = new PageInfoResp<>();
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(req.getType())) {
            params.put("type", req.getType());
        }
        if (StringUtils.isNotBlank(req.getStatus())) {
            params.put("status", req.getStatus());
        }
        if (req.getAcctId() != null) {
            params.put("acctId", req.getAcctId());
        }
        if (StringUtils.isNotBlank(req.getCreatedAtFrom()) && StringUtils.isNotBlank(req.getCreatedAtTo())) {
            params.put("createdAtFrom", req.getCreatedAtFrom());
            params.put("createdAtTo", req.getCreatedAtTo());
        }
        if (StringUtils.isNotEmpty(req.getOrderId())) {
            params.put("orderId", req.getOrderId());
        }
        PageInfo pageInfo = tAcctDetailDao.getFrontListByPage(req.getPage(), req.getPerPage(), params, "");
        List<TAcctDetail> list = pageInfo.getList();
        List<AcctDetailVo> itemList = new ArrayList<>();
        for (TAcctDetail detail : list) {
            try {
                AcctDetailVo vo = processAcctDetail(detail);
                setFrontAcctDetailStatus(vo);
                itemList.add(vo);
            } catch (Exception e) {
                e.printStackTrace();
                mqSender.sendTryCatchExceptionEmail(detail.getId(), e);
            }
        }
        resp.setList(itemList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    private void setFrontAcctDetailStatus(AcctDetailVo vo) {
        if (AccountEnum.AcctOperateType.WITHDRAW.getKey().equals(vo.getType())) {
            // 待审核
            if (AccountEnum.AcctDetailStatus.INIT.getKey().equals(vo.getStatus())
                    || AccountEnum.AcctDetailStatus.FIRST_VERIFY_SUCCESS.getKey().equals(vo.getStatus())
                    || AccountEnum.AcctDetailStatus.HAS_SIGN_CONTRACT.getKey().equals(vo.getStatus())
                    || AccountEnum.AcctDetailStatus.HAS_PROVIDE_INVOICE.getKey().equals(vo.getStatus())
                    || AccountEnum.AcctDetailStatus.INVOICE_VERIFY_FAIL.getKey().equals(vo.getStatus())) {
                vo.setStatusDesc(AccountEnum.FrontAcctDetailStatus.NEED_VERIFY.getValue());
            }
            // 已完成
            if (AccountEnum.AcctDetailStatus.HAS_PAY.getKey().equals(vo.getStatus())) {
                vo.setStatusDesc(AccountEnum.FrontAcctDetailStatus.HAS_PAY.getValue());
            }
            // 待出款
            if (AccountEnum.AcctDetailStatus.INVOICE_VERIFY_SUCCESS.getKey().equals(vo.getStatus())
                    || AccountEnum.AcctDetailStatus.LAST_VERIFY_SUCCESS.getKey().equals(vo.getStatus())
                    || AccountEnum.AcctDetailStatus.LAST_VERIFY_FAIL.getKey().equals(vo.getStatus())) {
                vo.setStatusDesc(AccountEnum.FrontAcctDetailStatus.NEED_PAY.getValue());
            }
            // 已失败
            if (AccountEnum.AcctDetailStatus.FIRST_VERIFY_FAIL.getKey().equals(vo.getStatus())
                    || AccountEnum.AcctDetailStatus.HAS_MARK_HC.getKey().equals(vo.getStatus())) {
                vo.setStatusDesc(AccountEnum.FrontAcctDetailStatus.VERIFY_FAIL.getValue());
            }
        }
    }

    @Override
    public PageInfoResp<AcctDetailVo> getAcctDetailListByPage(AcctReq.QueryReq req) {
        PageInfoResp<AcctDetailVo> resp = new PageInfoResp<>();
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(req.getType())) {
            params.put("type", req.getType());
        }
        if (StringUtils.isNotBlank(req.getStatus())) {
            params.put("status", req.getStatus());
        }
        if (req.getAcctId() != null) {
            params.put("acctId", req.getAcctId());
        }
        if (StringUtils.isNotBlank(req.getCreatedAtFrom()) && StringUtils.isNotBlank(req.getCreatedAtTo())) {
            params.put("createdAtFrom", req.getCreatedAtFrom());
            params.put("createdAtTo", req.getCreatedAtTo());
        }
        if (StringUtils.isNotEmpty(req.getOrderId())) {
            params.put("orderId", req.getOrderId());
        }
        PageInfo pageInfo = tAcctDetailDao.getListByPage(req.getPage(), req.getPerPage(), params, "");
        List<TAcctDetail> list = pageInfo.getList();
        List<AcctDetailVo> itemList = new ArrayList<>();
        for (TAcctDetail detail : list) {
            try {
                AcctDetailVo vo = processAcctDetail(detail);
                itemList.add(vo);
            } catch (Exception e) {
                e.printStackTrace();
                mqSender.sendTryCatchExceptionEmail(detail.getId(), e);
            }
        }
        resp.setList(itemList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    private AcctDetailVo processAcctDetail(TAcctDetail detail) {
        AcctDetailVo vo = RespConvertUtil.convertToAcctDetailVo(detail);
        if (AccountEnum.AcctOperateType.RECHARGE.getKey().equals(detail.getType())) {
            vo.setRechargeChannel("微信支付");
            TAcct acct = tAcctDao.selectById(detail.getAcctId());
            AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(acct.getPartyId());
            vo.setRelatedMobile(accountBaseDto.getMobile());
        } else if (AccountEnum.AcctOperateType.OTHER.getKey().equals(detail.getType())) {
            vo.setDesc("开店服务费");
        } else if (AccountEnum.AcctOperateType.BUY_COMMISSION.getKey().equals(detail.getType())) {
            if (StringUtils.isNotEmpty(detail.getSourceOrderId())) {
                AuctionOrder order = auctionOrderDao.selectById(detail.getSourceOrderId());
                if (order != null) {
                    vo.setRelatedMobile(accountService.getNotifierMobile(order.getBuyerId()));
                }
            }
        } else if (AccountEnum.AcctOperateType.SHOP_INVITED_COMMISSION.getKey().equals(detail.getType())) {
            if (StringUtils.isNotEmpty(detail.getSourceOrderId())) {
                TAcct acct = tAcctDao.selectById(Integer.parseInt(detail.getSourceOrderId()));
                if (acct != null) {
                    if (AccountEnum.AcctType.AGENCY.getKey().equals(acct.getType())) {
                        vo.setRelatedMobile(accountService.getAgencyNotifierMobile(acct.getPartyId()));
                    } else if (AccountEnum.AcctType.SHOP.getKey().equals(acct.getType())) {
                        vo.setRelatedMobile(accountService.getShopNotifierMobile(acct.getPartyId()));
                    } else if (AccountEnum.AcctType.USER.getKey().equals(acct.getType()) || AccountEnum.AcctType.COMPANY.getKey().equals(acct.getType())) {
                        vo.setRelatedMobile(accountService.getNotifierMobile(acct.getPartyId()));
                    }
                }
            }
        } else if (AccountEnum.AcctOperateType.WITHDRAW.getKey().equals(detail.getType())) {
            if (StringUtils.isNotEmpty(detail.getContractId())) {
                vo.setWithdrawAgreement(RespConvertUtil.convertToFileInfo(withdrawAgreementDao.getByAcctDetailId(detail.getId())));
            }
            if (detail.getInvoiceId() != null) {
                vo.setInvoice(RespConvertUtil.convertToInvoiceVo(invoiceDao.selectById(detail.getInvoiceId())));
            }
            if(detail.getBankAccountId() != null){
                TBankAccount bankAccount = tBankService.getBankAccountById(detail.getBankAccountId());
                vo.setBankName(bankAccount.getBankName());
                vo.setBankAccountName(bankAccount.getBankAccountName());
                vo.setBankAccountNo(bankAccount.getBankAccountNo());
            }
        }
        return vo;
    }

    @Override
    public AcctDetailVo getFrontAcctDetail(AcctReq.BaseReq req) {
        TAcctDetail detail = tAcctDetailDao.selectById(req.getId());
        if (detail == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!req.getAcctId().equals(detail.getAcctId())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        AcctDetailVo vo = processAcctDetail(detail);
        setFrontAcctDetailStatus(vo);
        return vo;
    }

    @Override
    public ResponseModel getAcctListByPage(AcctReq.QueryReq req) {
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(req.getQ())) {
            params.put("q", req.getQ());
        }
        if (StringUtils.isNotBlank(req.getType())) {
            params.put("type", req.getType());
        }
        PageInfo pageInfo = tAcctDao.getListByPage(req.getPage(), req.getPerPage(), params, "");
        List<AcctVo> itemList = pageInfo.getList();
        for (AcctVo vo : itemList) {
            vo.setTypeDesc(AccountEnum.AcctType.getValueByKey(vo.getType()));
            vo.setName(StringEscapeUtils.unescapeJava(vo.getName()));
        }
        Map<String, Object> data = new HashMap<>();
        data.put("total", pageInfo.getTotal());
        data.put("hasNextPage", pageInfo.isHasNextPage());
        data.put("list", itemList);
        data.putAll(tAcctDao.getSummaryInfo(params));
        return ResponseModel.succ(data);
    }

    @Override
    public AcctVo getAcct(AcctReq.BaseReq req) {
        TAcct acct = tAcctDao.selectById(req.getId());
        if (acct == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        AcctVo vo = RespConvertUtil.convertToAcctVo(acct);
        if (AccountEnum.AcctType.USER.getKey().equals(acct.getType())) {
            TUser user = userDao.selectById(acct.getPartyId());
            if (user != null) {
                vo.setMobile(user.getMobile());
                vo.setName(user.getName());
            }
        } else if (AccountEnum.AcctType.COMPANY.getKey().equals(acct.getType())) {
            TCompany company = companyDao.selectById(acct.getPartyId());
            if (company != null) {
                vo.setMobile(company.getMobile());
                vo.setName(company.getName());
            }
        } else if (AccountEnum.AcctType.AGENCY.getKey().equals(acct.getType())) {
            TAgency agency = agencyDao.selectById(acct.getPartyId());
            if (agency != null) {
                vo.setMobile(agency.getMobile());
                vo.setName(agency.getName());
            }
        } else if (AccountEnum.AcctType.SHOP.getKey().equals(acct.getType())) {
            TAppletShop shop = shopDao.selectById(acct.getPartyId());
            if (shop != null) {
                vo.setMobile(shop.getMobile());
                vo.setName(shop.getName());
            }
        }
        return vo;
    }

    @Override
    public ResponseModel getFirstVerifyWithdrawListByPage(AcctReq.QueryReq req) {
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotEmpty(req.getCreatedAtFrom()) && StringUtils.isNotEmpty(req.getCreatedAtTo())) {
            params.put("createdAtFrom", req.getCreatedAtFrom());
            params.put("createdAtTo", req.getCreatedAtTo());
        }
        if (StringUtils.isNotEmpty(req.getQ())) {
            params.put("q", req.getQ());
        }
        if (StringUtils.isNotEmpty(req.getStatus())) {
            params.put("status", req.getStatus());
        }
        if (StringUtils.isNotEmpty(req.getMobile())) {
            params.put("mobile", req.getMobile());
        }
        if (StringUtils.isNotEmpty(req.getBankAccountNo())) {
            params.put("bankAccountNo", req.getBankAccountNo());
        }
        if (StringUtils.isNotEmpty(req.getType())) {
            params.put("type", req.getType());
        }
        if (StringUtils.isNotEmpty(req.getOrderId())) {
            params.put("orderId", req.getOrderId());
        }
        PageInfo pageInfo = tAcctDetailDao.getFirstVerifyWithdrawListByPage(req.getPage(), req.getPerPage(), params, "");
        List<WithdrawAcctDetailVo> itemList = pageInfo.getList();
        for (WithdrawAcctDetailVo vo : itemList) {
            vo.setTypeDesc(AccountEnum.AcctType.getValueByKey(vo.getType()));
            vo.setStatusDesc(getBackgroundStatusDesc(vo.getStatus()));
        }
        Map<String, Object> data = new HashMap<>();
        data.put("total", pageInfo.getTotal());
        data.put("hasNextPage", pageInfo.isHasNextPage());
        data.put("list", itemList);
        Map<String, Object> summaryInfo = tAcctDetailDao.getFirstVerifyWithdrawSummaryInfo(params);
        if(summaryInfo != null){
            data.putAll(summaryInfo);
        }
        return ResponseModel.succ(data);
    }

    private String getBackgroundStatusDesc(String status) {
        if (AccountEnum.AcctDetailStatus.INIT.getKey().equals(status)) {
            return AccountEnum.AcctDetailStatus.INIT.getValue();
        } else if (AccountEnum.AcctDetailStatus.FIRST_VERIFY_FAIL.getKey().equals(status)) {
            return "初审拒绝";
        } else if (AccountEnum.AcctDetailStatus.FIRST_VERIFY_SUCCESS.getKey().equals(status)) {
            return "待审核发票";
        } else if (AccountEnum.AcctDetailStatus.HAS_SIGN_CONTRACT.getKey().equals(status)) {
            return "待审核发票";
        } else if (AccountEnum.AcctDetailStatus.HAS_PROVIDE_INVOICE.getKey().equals(status)) {
            return "待审核";
        } else if (AccountEnum.AcctDetailStatus.INVOICE_VERIFY_SUCCESS.getKey().equals(status)) {
            return "已通过";
        } else if (AccountEnum.AcctDetailStatus.INVOICE_VERIFY_FAIL.getKey().equals(status)) {
            return "已拒绝";
        }
        return AccountEnum.AcctDetailStatus.getValueByKey(status);
    }

    @Override
    public WithdrawAcctDetailVo getFirstVerifyWithdrawDetail(AcctReq.BaseReq req) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", req.getId());
        PageInfo pageInfo = tAcctDetailDao.getFirstVerifyWithdrawListByPage(req.getPage(), req.getPerPage(), params, "");
        List<WithdrawAcctDetailVo> itemList = pageInfo.getList();
        for (WithdrawAcctDetailVo vo : itemList) {
            vo.setTypeDesc(AccountEnum.AcctType.getValueByKey(vo.getType()));
            vo.setStatusDesc(getBackgroundStatusDesc(vo.getStatus()));
        }
        if (itemList.size() > 0) {
            return itemList.get(0);
        }
        return null;
    }

    @Override
    public ResponseModel getInvoiceVerifyWithdrawListByPage(AcctReq.QueryReq req) {
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotEmpty(req.getCreatedAtFrom()) && StringUtils.isNotEmpty(req.getCreatedAtTo())) {
            params.put("createdAtFrom", req.getCreatedAtFrom());
            params.put("createdAtTo", req.getCreatedAtTo());
        }
        if (StringUtils.isNotEmpty(req.getQ())) {
            params.put("q", req.getQ());
        }
        if (StringUtils.isNotEmpty(req.getStatus())) {
            params.put("status", req.getStatus());
        }
        if (StringUtils.isNotEmpty(req.getMobile())) {
            params.put("mobile", req.getMobile());
        }
        if (StringUtils.isNotEmpty(req.getType())) {
            params.put("type", req.getType());
        }
        if (StringUtils.isNotEmpty(req.getInvoiceType())) {
            params.put("invoiceType", req.getInvoiceType());
        }
        if (StringUtils.isNotEmpty(req.getOrderId())) {
            params.put("orderId", req.getOrderId());
        }
        if (StringUtils.isNotEmpty(req.getBatchId())) {
            params.put("batchId", req.getBatchId());
        }
        PageInfo pageInfo = tAcctDetailDao.getInvoiceVerifyWithdrawListByPage(req.getPage(), req.getPerPage(), params, "");
        List<WithdrawAcctDetailVo> itemList = pageInfo.getList();
        for (WithdrawAcctDetailVo vo : itemList) {
            vo.setTypeDesc(AccountEnum.AcctType.getValueByKey(vo.getType()));
            vo.setStatusDesc(getBackgroundStatusDesc(vo.getStatus()));
            vo.setInvoice(RespConvertUtil.convertToInvoiceVo(invoiceDao.selectById(vo.getInvoiceId())));
        }
        Map<String, Object> data = new HashMap<>();
        data.put("total", pageInfo.getTotal());
        data.put("hasNextPage", pageInfo.isHasNextPage());
        data.put("list", itemList);
        Map<String, Object> summaryInfo = tAcctDetailDao.getInvoiceVerifyWithdrawSummaryInfo(params);
        if(summaryInfo != null){
            data.putAll(summaryInfo);
        }
        return ResponseModel.succ(data);
    }

    @Override
    public WithdrawAcctDetailVo getInvoiceVerifyWithdrawDetail(AcctReq.BaseReq req) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", req.getId());
        PageInfo pageInfo = tAcctDetailDao.getInvoiceVerifyWithdrawListByPage(req.getPage(), req.getPerPage(), params, "");
        List<WithdrawAcctDetailVo> itemList = pageInfo.getList();
        for (WithdrawAcctDetailVo vo : itemList) {
            vo.setTypeDesc(AccountEnum.AcctType.getValueByKey(vo.getType()));
            vo.setStatusDesc(getBackgroundStatusDesc(vo.getStatus()));
            vo.setInvoice(RespConvertUtil.convertToInvoiceVo(invoiceDao.selectById(vo.getInvoiceId())));
        }
        if (itemList.size() > 0) {
            return itemList.get(0);
        }
        return null;
    }

    @Override
    public TAcct findAcctByPartyIdAndType(Integer partyId, String type) {
        TAcct acct = tAcctDao.getByPartyIdType(partyId, type);
        if (acct == null) {
            acct = new TAcct();
            acct.setPartyId(partyId);
            acct.setType(type);
            acct.setCreateTime(new Date());
            acct.setUpdateTime(new Date());
            int result = tAcctDao.insert(acct);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        return acct;
    }

    @Override
    public TAcct findAcctById(Integer acctId) {
        return tAcctDao.selectById(acctId);
    }

    @Override
    public TAcctDetail findDetailById(Long detailId) {
        return tAcctDetailDao.selectById(detailId);
    }

    @Override
    public boolean updateAcctDetail(TAcctDetail acctDetail) {
        return tAcctDetailDao.updateById(acctDetail) == 1;
    }

    @Override
    public TInvoice findInvoiceById(Integer invoiceId) {
        return tInvoiceDao.selectById(invoiceId);
    }

    @Override
    public boolean saveBatchOrder(Long batchOrderId, Integer operatorId) {
        TBatchOrder order = new TBatchOrder();
        order.setId(batchOrderId);
        order.setInitOperatorId(operatorId);
        return tBatchOrderDao.insert(order) == 1;
    }

    @Override
    public boolean updateBatchOrder(TBatchOrder order) {
        return tBatchOrderDao.updateById(order) == 1;
    }

    @Override
    public boolean saveBatchDetailRef(Long batchOrderId, Long detailId) {
        TBatchDetailRef ref = new TBatchDetailRef();
        ref.setBatchId(batchOrderId);
        ref.setDetailId(detailId);
        return tBatchDetailRefDao.insert(ref) == 1;
    }

    @Override
    public boolean delteBatchDetailRef(Long batchOrderId) {
        tBatchDetailRefDao.deleteByBatchId(batchOrderId);
        return true;
    }

    @Override
    public boolean delteBatchDetailRefById(Integer refId) {
        TBatchDetailRef ref = new TBatchDetailRef();
        ref.setId(refId);
        ref.setIsDelete(true);
        return tBatchDetailRefDao.updateById(ref) == 1;
    }

    @Override
    public boolean checkDetailRefExist(Long detailId) {
        TBatchDetailRefCondition condition = new TBatchDetailRefCondition();
        condition.setDetailId(detailId);
        condition.setIsDelete(false);
        TBatchDetailRef ref = tBatchDetailRefDao.selectFirst(condition);
        return ref != null;
    }

    @Override
    public boolean updateInvoice(TInvoice invoice) {
        return tInvoiceDao.updateById(invoice) == 1;
    }

    @Override
    public TBatchOrder findMaxOrder() {
        return tBatchOrderDao.selectMaxId();
    }

    @Override
    public boolean saveInvoice(TInvoice invoice) {
        return tInvoiceDao.insert(invoice) == 1;
    }

    private Long insertAcctDetail(Integer acctId, String type, BigDecimal amount, BigDecimal availAmt,BigDecimal totalAmt,BigDecimal lockAmt, String sourceOrderId, Integer bankAccountId,Integer shopId){

        TAcctDetail acctDetail = new TAcctDetail();
        Long detailId = Long.parseLong(RandomNumberGenerator.generatorOrderNum(4));
        acctDetail.setId(detailId);
        acctDetail.setAcctId(acctId);
        acctDetail.setType(type);
        acctDetail.setAmount(amount);
        acctDetail.setAvailAmt(availAmt);
        acctDetail.setBackupAmt(amount);
        acctDetail.setSourceOrderId(sourceOrderId);
        acctDetail.setBankAccountId(bankAccountId);
        acctDetail.setTotalAmt(totalAmt);
        acctDetail.setLockAmt(lockAmt);
        acctDetail.setInviteShopId(shopId);
        if(!type.equals(AccountEnum.AcctOperateType.WITHDRAW.getKey())){
            acctDetail.setStatus(AccountEnum.AcctDetailStatus.HAS_PAY.getKey());
        }else{
            acctDetail.setStatus(AccountEnum.AcctDetailStatus.INIT.getKey());
        }
        tAcctDetailDao.insert(acctDetail);
        return detailId;
    }

    @Override
    public PageInfoResp<TBatchOrder> searchBatchOrder(SearchBatchReq req) {

        PageHelper.startPage(req.getPage(),req.getPerPage());

        Long orderNo = null;
        if(!StringUtils.isEmpty(req.getBatchOrderNo())){
            orderNo = Long.parseLong(req.getBatchOrderNo());
        }
        List<TBatchOrder> orderList = tBatchOrderDao.searchBatchOrder(orderNo,req.getStatus(),req.getBeginTime(),req.getEndTime());
        PageInfo<TBatchOrder> orderPageInfo = new PageInfo<TBatchOrder>(orderList);
        PageInfoResp<TBatchOrder> infoResp = new PageInfoResp<TBatchOrder>();
        infoResp.setList(orderPageInfo.getList());
        infoResp.setTotal(orderPageInfo.getTotal());
        infoResp.setHasNextPage(orderPageInfo.isHasNextPage());
        return infoResp;
    }

    @Override
    public PageInfoResp<TAcctDetail> getWithdrawRecord(SearchBatchReq req) {
        PageInfoResp<TAcctDetail> resp = new PageInfoResp<TAcctDetail>();
        TAcct acct = tAcctDao.getByPartyIdType(req.getPartyId(),req.getType());
        if (acct == null) {
            resp.setList(Collections.EMPTY_LIST);
            return resp;
        }
        PageInfo<TAcctDetail> pageInfo = tAcctDetailDao.getWithdrawList(req.getPage(),req.getPerPage(),acct.getId());
        if(pageInfo != null){
            resp.setList(pageInfo.getList());
            resp.setHasNextPage(pageInfo.isHasNextPage());
            resp.setTotal(pageInfo.getTotal());
        }
        return resp;
    }

    @Override
    public void saveTAcct(TAcct acct) {
        int result =tAcctDao.insert(acct);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
    }

    @Override
    public List<TAcctDetail> getNoBatchDetail() {
        return tAcctDetailDao.getNoBatchDetail();
    }

    @Override
    public AcctResp getAcctByPartyIdAndType(Integer partyId, String type) {
        TAcct acct = findAcctByPartyIdAndType(partyId,type);
        AcctResp resp = new AcctResp();
        resp.setAvailAmt(acct.getAvailAmt());
        resp.setLockAmt(acct.getLockAmt());
        resp.setTotalAmt(acct.getTotalAmt());
        return resp;
    }

    @Override
    @Transactional
    public boolean saveContract(Long detailId, String contractId, String viewUrl, String downUrl) {
        WithdrawAgreement withdrawAgreement = new WithdrawAgreement();
        withdrawAgreement.setAcctDetailId(detailId);
        withdrawAgreement.setContractId(contractId);
        withdrawAgreement.setViewpdfUrl(viewUrl);
        withdrawAgreement.setDownloadUrl(downUrl);
        withdrawAgreementDao.insert(withdrawAgreement);

        TAcctDetail updateParam = new TAcctDetail();
        updateParam.setId(detailId);
        updateParam.setContractId(contractId);

        return tAcctDetailDao.updateById(updateParam) == 1;
    }


    @Override
    public WithdrawAgreement getWithdrawAgreement(Long detailId) {
        WithdrawAgreementCondition condition = new WithdrawAgreementCondition();
        condition.setAcctDetailId(detailId);
        return withdrawAgreementDao.selectFirst(condition);
    }

    @Override
    public boolean updateWithdrawAgreement(WithdrawAgreement withdrawAgreement) {
        return withdrawAgreementDao.updateById(withdrawAgreement) == 1;
    }

    @Override
    public TAcct saveAcctIfNeed(Integer partyId, String type) {
        return findAcctByPartyIdAndType(partyId,type);
    }
}
