package com._360pai.core.service.finance;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.finance.resp.AdjustedRecordDTO;

/**
 * @author xiaolei
 * @create 2018-09-29 18:48
 */
public interface ServiceAdjustedRecordService {

    PageInfoResp<AdjustedRecordDTO> getAdjustedRecordByPartyIdAndStatus(Integer partyId, int adjustedStatus, int pageNum, int pageSize);
    PageInfoResp<AdjustedRecordDTO> getAdjustedRecordByWithdrawNo(String withdrawNo, int pageNum, int pageSize);
}
