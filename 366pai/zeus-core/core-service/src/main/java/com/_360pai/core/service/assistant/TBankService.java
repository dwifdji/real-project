package com._360pai.core.service.assistant;

import com._360pai.arch.common.ListResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.req.TBankAccountReq;
import com._360pai.core.facade.account.vo.TBankAccountVo;
import com._360pai.core.facade.assistant.vo.BankVo;
import com._360pai.core.model.account.TBank;
import com._360pai.core.model.account.TBankAccount;

/**
 * @author xdrodger
 * @Title: TBankService
 * @ProjectName zeus
 * @Description: 提现用银行相关
 * @date 2018/12/3 13:25
 */
public interface TBankService {
    ListResp<BankVo> getAllBanks();

    ListResp<TBankAccountVo> getTBankAccounts(TBankAccountReq.BaseReq req);

    ResponseModel addTBankAccount(TBankAccountReq.CreateReq req);

    ResponseModel unbindTBankAccount(TBankAccountReq.BaseReq req);

    TBankAccount getBankAccountById(Integer bankAccountId);

    TBank getBankByCode(String bankCode);

    ListResp<TBankAccountVo> getPlatformTBankAccounts(TBankAccountReq.BaseReq req);

    ResponseModel addPlatformTBankAccount(TBankAccountReq.PlatformCreateReq req);

    ResponseModel updatePlatformTBankAccount(TBankAccountReq.PlatformUpdateReq req);

    ResponseModel togglePlatformTBankAccountStatus(TBankAccountReq.BaseReq req);
}
