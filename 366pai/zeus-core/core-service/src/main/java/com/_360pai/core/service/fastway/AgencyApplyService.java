package com._360pai.core.service.fastway;

import com._360pai.core.condition.fastway.TFastwayAgencyApplyCondition;
import com._360pai.core.model.account.TCompany;
import com._360pai.core.model.fastway.TFastwayAgencyApply;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author xiaolei
 * @create 2018-11-29 14:37
 */
public interface AgencyApplyService {
    int auctionApply(JSONObject applyFiled, Integer accountId, String source, Integer partyId);
    List<TFastwayAgencyApply> selectByCondition(TFastwayAgencyApplyCondition condition);
    List<TCompany> findByAccountId(Integer accountId);
    String getAgencyAuthCode(Integer accountId, Integer partyId, Integer agencyId);
    void checkAgencyAbbr(String abbr);
}
