package com._360pai.core.facade.finance;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.finance.req.AdjustedRecordReq;

/**
 * @author xiaolei
 * @create 2018-10-03 14:54
 */
public interface ServiceAdjustedRecordFacade {

    /**
     * 获取用户的分润记录
     * @param req
     * @return
     */
    PageInfoResp getAdjustedRecord(AdjustedRecordReq.GetAdjustedRecord req);

    /**
     * 根据提现编号获取分润记录
     * @param req
     * @return
     */
    PageInfoResp getAdjustedRecordByWithdrawNo(AdjustedRecordReq.GetAdjustedRecord req);

}
