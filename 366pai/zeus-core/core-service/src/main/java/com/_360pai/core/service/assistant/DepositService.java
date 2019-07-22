package com._360pai.core.service.assistant;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.condition.assistant.DepositCondition;
import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.assistant.req.DepositReq;
import com._360pai.core.facade.assistant.resp.DepositResp;
import com._360pai.core.facade.assistant.vo.DepositVo;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.assistant.Deposit;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DepositService{


    PageInfo getAllByActivityId(int page, int perPage, DepositCondition condition, String orderBy);

    public boolean saveDeposit(Deposit deposit);

    public boolean yxUserHasBuy(Integer activityId);

    public List<Deposit> findDeposit(DepositCondition depositCondition);

    public boolean updateDepositById(Deposit deposit);

    Deposit getByActivityIdPartId(Integer activityId, Integer partyId);

    PageInfoResp<PartyAccount> getParticipatingPartiesByPage(ActivityReq.ActivityId req);

    int countParticipantNumber(Integer activityId);

    int countParticipantNumber(Integer activityId, Integer agencyId);

    PageInfoResp<PartyAccount> getAgencyParticipatingPartiesByPage(ActivityReq.ActivityId req);

    List<Deposit> getDepositByActivityId(Integer activityId);
    List<Deposit> selectNoDealYX(Integer activityId);

    Deposit getDepositById(Long depositId);

    PageInfo myDepositList(int page, int perPage, Integer partyId,String categoryId,String name);

    PageInfoResp<DepositVo> getDepositOfflineListByPage(DepositReq.QueryReq req);

    PageInfoResp<DepositVo> getDepositOfflineRefundListByPage(DepositReq.QueryReq req);

    DepositResp receiveDeposit(DepositReq.OfflineConfirmReq req);

    DepositResp refundDeposit(DepositReq.OfflineConfirmReq req);

    DepositResp transferDeposit(DepositReq.OfflineConfirmReq req);

    int updateDealYX(Integer level);

    List<AuctionActivity> getBeginIn5MinuteList(Integer partyId);

    List<AuctionActivity> getEndIn5MinuteList(Integer partyId);
}