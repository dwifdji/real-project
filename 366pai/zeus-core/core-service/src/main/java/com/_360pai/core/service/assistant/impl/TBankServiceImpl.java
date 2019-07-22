package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.ListResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.account.TBankAccountCondition;
import com._360pai.core.dao.account.*;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.TBankAccountReq;
import com._360pai.core.facade.account.vo.TBankAccountVo;
import com._360pai.core.facade.assistant.vo.BankVo;
import com._360pai.core.model.account.TAcct;
import com._360pai.core.model.account.TBank;
import com._360pai.core.model.account.TBankAccount;
import com._360pai.core.model.account.TParty;
import com._360pai.core.service.assistant.TBankService;
import com._360pai.core.utils.ReqConvertUtil;
import com._360pai.core.utils.RespConvertUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author xdrodger
 * @Title: TBankServiceImpl
 * @ProjectName zeus
 * @Description:
 * @date 2018/12/3 13:25
 */
@Service
public class TBankServiceImpl implements TBankService {
    @Autowired
    private TBankDao tBankDao;
    @Autowired
    private TBankAccountDao tBankAccountDao;
    @Autowired
    private TAcctDao tAcctDao;
    @Autowired
    private TPartyDao tPartyDao;
    @Autowired
    private TAcctDetailDao tAcctDetailDao;

    @Override
    public ListResp<BankVo> getAllBanks() {
        ListResp<BankVo> resp = new ListResp<>();
        List<TBank> list = tBankDao.selectAll();
        List<BankVo> resultList = new ArrayList<>();
        for (TBank bank : list) {
            BankVo vo = new BankVo();
            BeanUtils.copyProperties(bank, vo);
            vo.setValue(vo.getId() + "");
            resultList.add(vo);
        }
        resp.setList(resultList);
        return resp;
    }

    @Override
    public ListResp<TBankAccountVo> getTBankAccounts(TBankAccountReq.BaseReq req) {
        ListResp<TBankAccountVo> resp = new ListResp<>();
        TAcct acct = tAcctDao.getByPartyIdType(req.getPartyId(), req.getPartyType());
        if (acct == null) {
            resp.setList(Collections.EMPTY_LIST);
            return resp;
        }
        TBankAccountCondition condition = new TBankAccountCondition();
        condition.setAcctId(acct.getId());
        condition.setIsDelete(false);
        List<TBankAccount> list = tBankAccountDao.selectList(condition);
        List<TBankAccountVo> resultList = new ArrayList<>();
        for (TBankAccount bankAccount : list) {
            TBankAccountVo vo = RespConvertUtil.convertToTBankAccountVo(bankAccount);
            if (StringUtils.isNotEmpty(vo.getBankCode())) {
                TBank bank = tBankDao.getByCode(vo.getBankCode());
                if (bank != null) {
                    vo.setBankName(bank.getName());
                    vo.setImgUrl(bank.getImgUrl());
                }
            } else {
                vo.setImgUrl(SystemConstant.DEFAULT_BANK_LOGO);
            }
            if (tAcctDetailDao.hasUncompletedWithdrawRecords(bankAccount.getId())) {
                vo.setIsCanUnbind(false);
            } else {
                vo.setIsCanUnbind(true);
            }
            resultList.add(vo);
        }
        resp.setList(resultList);
        return resp;
    }

    @Override
    public ResponseModel addTBankAccount(TBankAccountReq.CreateReq req) {
        TAcct acct = tAcctDao.getByPartyIdType(req.getPartyId(), req.getPartyType());
        if (acct == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TBankAccount bankAccount = ReqConvertUtil.convertToTBankAccount(req);
        bankAccount.setAcctId(acct.getId());

        if (StringUtils.isNotEmpty(req.getBankCode())) {
            TBank bank = tBankDao.getByCode(req.getBankCode());
            if (bank == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            bankAccount.setBankName(bank.getName());
        }
        int result = tBankAccountDao.insert(bankAccount);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return ResponseModel.succ();
    }

    @Override
    public ResponseModel unbindTBankAccount(TBankAccountReq.BaseReq req) {
        TBankAccount bankAccount = tBankAccountDao.selectById(req.getId());
        if (bankAccount == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TAcct acct = tAcctDao.getByPartyIdType(req.getPartyId(), req.getPartyType());
        if (acct == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!bankAccount.getAcctId().equals(acct.getId())) {
            throw new BusinessException("只能解绑自己的银行卡");
        }
        if (tAcctDetailDao.hasUncompletedWithdrawRecords(bankAccount.getId())) {
            throw new BusinessException("该银行卡有提现记录，无法解绑");
        }
        bankAccount.setIsDelete(true);
        int result = tBankAccountDao.updateById(bankAccount);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return ResponseModel.succ();
    }

    @Override
    public TBankAccount getBankAccountById(Integer bankAccountId) {
        return tBankAccountDao.selectById(bankAccountId);
    }

    @Override
    public TBank getBankByCode(String bankCode) {
        return tBankDao.getByCode(bankCode);
    }

    @Override
    public ListResp<TBankAccountVo> getPlatformTBankAccounts(TBankAccountReq.BaseReq req) {
        ListResp<TBankAccountVo> resp = new ListResp<>();
        TBankAccountCondition condition = new TBankAccountCondition();
        condition.setAcctId(-1);
        if (StringUtils.isNotEmpty(req.getStatus())) {
            condition.setIsDelete("1".equals(req.getStatus()) ? false : true);
        }
        List<TBankAccount> list = tBankAccountDao.selectList(condition);
        List<TBankAccountVo> resultList = new ArrayList<>();
        for (TBankAccount bankAccount : list) {
            TBankAccountVo vo = RespConvertUtil.convertToTBankAccountVo(bankAccount);
            if (StringUtils.isNotEmpty(vo.getBankCode())) {
                TBank bank = tBankDao.getByCode(vo.getBankCode());
                if (bank != null) {
                    vo.setBankName(bank.getName());
                    vo.setImgUrl(bank.getImgUrl());
                }
            }
            resultList.add(vo);
        }
        resp.setList(resultList);
        return resp;
    }

    @Override
    public ResponseModel addPlatformTBankAccount(TBankAccountReq.PlatformCreateReq req) {
        TBankAccount bankAccount = ReqConvertUtil.convertToTBankAccount(req);
        bankAccount.setAcctId(-1);
        int result = tBankAccountDao.insert(bankAccount);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return ResponseModel.succ();
    }

    @Override
    public ResponseModel updatePlatformTBankAccount(TBankAccountReq.PlatformUpdateReq req) {
        TBankAccount bankAccount = tBankAccountDao.selectById(req.getId());
        if (bankAccount == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        bankAccount = ReqConvertUtil.convertToTBankAccount(req);
        int result = tBankAccountDao.updateById(bankAccount);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return ResponseModel.succ();
    }

    @Override
    public ResponseModel togglePlatformTBankAccountStatus(TBankAccountReq.BaseReq req) {
        TBankAccount bankAccount = tBankAccountDao.selectById(req.getId());
        if (bankAccount == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (StringUtils.isEmpty(req.getStatus())) {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        if (!"1".equals(req.getStatus()) && !"0".equals(req.getStatus())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if ("0".equals(req.getStatus())) {
            if (tAcctDetailDao.hasRelatedWithdrawRecords(bankAccount.getId())) {
                throw new BusinessException("该银行账户存在出款记录，无法禁用");
            }
        }
        bankAccount.setIsDelete("1".equals(req.getStatus()) ? false : true);
        int result = tBankAccountDao.updateById(bankAccount);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return ResponseModel.succ();
    }
}
