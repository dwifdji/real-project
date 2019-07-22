package com._360pai.core.facade.assistant;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.assistant.req.DepositReq;
import com._360pai.core.facade.assistant.resp.DepositResp;
import com._360pai.core.facade.assistant.vo.DepositVo;
import com.github.pagehelper.PageInfo;

public interface DepositFacade {
    PageInfo myDepositList(DepositReq.BaseReq req);

    PageInfoResp<DepositVo> getDepositOfflineListByPage(DepositReq.QueryReq req);

    DepositResp.DetailResp getDepositOffline(DepositReq.BaseReq req);

    PageInfoResp<DepositVo> getDepositOfflineRefundListByPage(DepositReq.QueryReq req);

    DepositResp.DetailResp getDepositOfflineRefund(DepositReq.BaseReq req);

    DepositResp receiveDeposit(DepositReq.OfflineConfirmReq req);

    DepositResp refundDeposit(DepositReq.OfflineConfirmReq req);

    DepositResp transferDeposit(DepositReq.OfflineConfirmReq req);
}
