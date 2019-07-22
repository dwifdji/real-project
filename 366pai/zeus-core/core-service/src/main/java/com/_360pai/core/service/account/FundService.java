package com._360pai.core.service.account;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.req.FundProviderApplyReq;
import com._360pai.core.facade.account.req.FundProviderReq;
import com._360pai.core.facade.account.resp.FundProviderApplyResp;
import com._360pai.core.facade.account.resp.FundProviderResp;
import com._360pai.core.facade.account.vo.FundProviderApplyVo;
import com._360pai.core.facade.account.vo.FundProviderVo;
import com._360pai.core.facade.fastway.resp.CompanyFundDetailVO;
import com._360pai.core.facade.fastway.resp.UserFundDetailVO;
import com._360pai.core.model.account.TFundProvider;
import com._360pai.core.model.account.TFundProviderApply;

import java.util.List;

/**
 * Created by RuQ on 2018/8/27 15:38
 *
 * 资金供应商
 */
public interface FundService {

    boolean saveFundApply(TFundProviderApply fundProviderApply);
    List<TFundProviderApply> getApplyRecordByAccountId(Integer accountId, String status);

    boolean saveFund(TFundProvider fundProvider);
    boolean updateFundApply(TFundProviderApply fundProviderApply);
    boolean updateFundApply(TFundProvider fundProvider);
    TFundProvider getFundProviderByAccountId(Integer accountId);

    TFundProvider getFundProviderByPartyId(Integer partyId);

    FundProviderApplyResp fundProviderApply(FundProviderApplyReq.CreateReq req);

    PageInfoResp<FundProviderApplyVo> getFundProviderApplyListByPage(FundProviderApplyReq.QueryReq req);

    FundProviderApplyResp.DetailResp getFundProviderApply(FundProviderApplyReq.BaseReq req);

    FundProviderApplyResp approveFundProviderApply(FundProviderApplyReq.BaseReq req);

    void updateFundProviderApply(FundProviderApplyReq.UpdateReq req);

    FundProviderApplyResp rejectFundProviderApply(FundProviderApplyReq.BaseReq req);

    PageInfoResp<FundProviderVo> getFundProviderListByPage(FundProviderReq.QueryReq req);

    FundProviderResp.DetailResp getFundProvider(FundProviderReq.BaseReq req);

    FundProviderResp updateFundProvider(FundProviderReq.UpdateReq req);

    ResponseModel getFundProvider(Integer providerId);

    ResponseModel getFundProviderApply(Integer applyId);

}
