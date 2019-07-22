package com._360pai.core.service.assistant;


import com._360pai.arch.common.ListResp;
import com._360pai.core.facade.account.req.BankAccountReq;
import com._360pai.core.facade.account.resp.BankAccountResp;
import com._360pai.core.facade.account.vo.BankAccountVo;
import com._360pai.core.facade.assistant.vo.BankVo;

/**
 * 银行类企业-银行账户
 */
public interface BankService{
    ListResp<BankVo> getAllBanks();

    ListResp<BankAccountVo> getBankAccounts(BankAccountReq.BaseReq req);

    BankAccountResp addBankAccount(BankAccountReq.CreateReq req);

    BankAccountResp updateBankAccount(BankAccountReq.UpdateReq req);

    BankAccountResp deleteBankAccount(BankAccountReq.BaseReq req);
}