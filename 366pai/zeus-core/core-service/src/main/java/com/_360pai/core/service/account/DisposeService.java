package com._360pai.core.service.account;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.account.req.AccountReq;
import com._360pai.core.facade.account.req.DisposeProviderApplyReq;
import com._360pai.core.facade.account.req.DisposeProviderReq;
import com._360pai.core.facade.account.resp.DisposeProviderApplyResp;
import com._360pai.core.facade.account.resp.DisposeProviderResp;
import com._360pai.core.facade.account.vo.DisposeProvider;
import com._360pai.core.facade.account.vo.DisposeProviderApplyVo;
import com._360pai.core.facade.account.vo.DisposeProviderVo;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.core.model.account.TDisposeProviderApply;

import java.util.List;

/**
 * Created by RuQ on 2018/8/27 15:42
 *
 * 服务处置商
 */
public interface DisposeService {

    boolean saveDisposeApply(TDisposeProviderApply disposeProviderApply);
    List<TDisposeProviderApply> getApplyRecordByAccountId(Integer accountId, String status);
    boolean saveDispose(TDisposeProvider disposeProvider);
    boolean updateDisposeApply(TDisposeProviderApply disposeProviderApply);
    boolean updateDispose(TDisposeProvider disposeProvider);
    TDisposeProvider getDisposeProviderByPartyId(Integer partyId);

    DisposeProviderApplyResp disposeProviderApply(DisposeProviderApplyReq.CreateReq req);

    PageInfoResp<DisposeProviderApplyVo> getDisposeProviderApplyListByPage(DisposeProviderApplyReq.QueryReq req);

    DisposeProviderApplyResp.DetailResp getDisposeProviderApply(DisposeProviderApplyReq.BaseReq req);

    DisposeProviderApplyResp approveDisposeProviderApply(DisposeProviderApplyReq.BaseReq req);

    DisposeProviderApplyResp rejectDisposeProviderApply(DisposeProviderApplyReq.BaseReq req);

    PageInfoResp<DisposeProviderVo> getDisposeProviderListByPage(DisposeProviderReq.QueryReq req);

    DisposeProviderResp.DetailResp getDisposeProvider(DisposeProviderReq.BaseReq req);

    DisposeProviderResp updateDisposeProvider(DisposeProviderReq.UpdateReq req);

    TDisposeProviderApply getDisposeProviderApplyByPartyId(Integer partyId);

    int addDisposeProviderNoPartyId(DisposeProviderApplyReq.CreateReq req);

    int addDisposeProviderHasPartyId(DisposeProviderApplyReq.CreateReq req);

    PageInfoResp<DisposeProvider> getRecommendDisposeProviderList(AccountReq.QueryReq req);

    List<DisposeProvider> getDisposeProviders(List<TDisposeProvider> list);

    DisposeProvider getDisposeProvider(TDisposeProvider model);
}
