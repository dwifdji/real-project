package com._360pai.core.facade.finance;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.finance.req.BusinessRecordReq;
import com._360pai.core.facade.finance.resp.PurchaseHistoryDTO;

import java.util.List;

/**
 * @author xiaolei
 * @create 2018-10-03 07:08
 */
public interface ServiceBusinessRecordFacade {

    /**
     * 获取用户的购买记录
     * @param buyId
     * @return
     */
    PageInfoResp<PurchaseHistoryDTO> getPurchaseHistoryByBuyerPartyId(BusinessRecordReq.GetBusinessRecord req);
}
