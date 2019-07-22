package com._360pai.core.service.fastway;

import com._360pai.core.model.fastway.TFastwayDisposeApply;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-11-26 15:18
 */
public interface DisposeApplyAdminService {
    PageInfo findByParam(Map<String, Object> query, int pageNum, int pageSize);
    TFastwayDisposeApply findById(Integer applyId);
    int lawyerUpdate(Integer applyId, JSONObject applyFiled, Integer operatorId);
    int lawOfficeUpdate(Integer applyId, JSONObject applyFiled, Integer operatorId);
    int lawyerVerify(String applyStatus, Integer applyId, Integer operatorId, String refuseReason, Integer openAccountOperatorId, Integer businessOperatorId);
    int lawOfficeVerify(String applyStatus, Integer applyId, Integer operatorId, String refuseReason, Integer openAccountOperatorId, Integer businessOperatorId);
}
