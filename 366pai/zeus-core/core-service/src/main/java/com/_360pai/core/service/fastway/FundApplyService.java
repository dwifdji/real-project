package com._360pai.core.service.fastway;

import com._360pai.core.condition.fastway.TFastwayFundApplyCondition;
import com._360pai.core.model.fastway.TFastwayFundApply;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-12-07 09:59
 */
public interface FundApplyService {
    int userApply   (JSONObject applyFiled, Integer accountId, String source, Integer partyId);
    int companyApply(JSONObject applyFiled, Integer accountId, String source, Integer partyId);
    List<TFastwayFundApply> selectByCondition(TFastwayFundApplyCondition condition);
    Map<String, Object> userFundAuthInfo(Integer accountId);
    Map<String, Object> companyFundAuthInfo(Integer accountId);
}
