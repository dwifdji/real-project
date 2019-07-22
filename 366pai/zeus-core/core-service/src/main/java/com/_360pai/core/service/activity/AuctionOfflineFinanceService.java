package com._360pai.core.service.activity;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.dto.AuctionOfflineFinaceDto;
import com._360pai.core.facade.activity.req.AuctionOfflineFinanceReq;
import com._360pai.core.facade.activity.vo.AuctionOfflineFinaceVo;
import com._360pai.core.model.activity.TAuctionOfflineFinance;

import java.util.List;

/**
 * @author RuQ
 * @Title: AuctionOfflineFinanceService
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/4/28 15:15
 */
public interface AuctionOfflineFinanceService {

    public PageInfoResp<AuctionOfflineFinaceDto> searchOfflineFinanceList(AuctionOfflineFinanceReq req);

    public List<AuctionOfflineFinaceVo> searchAllOfflineFinanceList(AuctionOfflineFinanceReq req);

    public AuctionOfflineFinaceDto getDetailById(Integer financeId);

    public boolean updateFinanceInfo(AuctionOfflineFinanceReq req);

    public boolean saveFinanceInfo(TAuctionOfflineFinance finance);


}
