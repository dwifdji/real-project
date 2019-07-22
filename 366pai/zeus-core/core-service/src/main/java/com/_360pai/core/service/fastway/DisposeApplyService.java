package com._360pai.core.service.fastway;

import com._360pai.core.condition.fastway.TFastwayDisposeApplyCondition;
import com._360pai.core.facade.fastway.resp.DisposeCompanyVO;
import com._360pai.core.model.fastway.TFastwayDisposeApply;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-11-26 10:58
 */
public interface DisposeApplyService {
    int lawyerApply(Integer accountId, JSONObject applyFiled, String source, Integer partyId);
    int lawOfficeApply(Integer accountId, JSONObject applyFiled, String source, Integer partyId);
    List<TFastwayDisposeApply> findByParam(Map<String, Object> query);
    List<TFastwayDisposeApply> findByCondition(TFastwayDisposeApplyCondition condition);
    int sendDocByEmail(String[] emailAddress);
    Map<String, Object> getLawyerAuthInfoByMobile(String mobile);
    List<DisposeCompanyVO> getLawOfficeAuthInfoByMobile(String mobile);
}
