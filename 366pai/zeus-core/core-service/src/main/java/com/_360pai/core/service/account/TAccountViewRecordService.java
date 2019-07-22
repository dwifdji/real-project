package com._360pai.core.service.account;

import com._360pai.core.facade.account.req.AcctReq;
import com._360pai.core.vo.activity.TAccountViewRecordVO;

import java.util.List;

public interface TAccountViewRecordService {

    void findAndSaveViewRecord(String accountId,String partyId, String activityId, String key);

    void updateActivityByPartyId(AcctReq.ViewRecordRequest viewRecordRequest);
}
