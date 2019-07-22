package com._360pai.core.service.comprador;

import com._360pai.core.condition.comprador.TCompradorRecommendCondition;
import com._360pai.core.facade.comprador.req.MyRecommendListReq;
import com._360pai.core.facade.comprador.req.RecommendReq;
import com._360pai.core.facade.comprador.resp.RecommendDetailForAdminResp;
import com._360pai.core.facade.comprador.resp.RecommendDetailResp;
import com._360pai.core.model.comprador.TCompradorRecommend;

import java.util.List;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/4 15:27
 */
public interface CompradorRecommendService {
    int recommendAdd(TCompradorRecommend recommend);

    int recommendMatchSuccess(RecommendReq req);

    int recommendFinished(RecommendReq req);

    List<RecommendDetailResp> recommendList(TCompradorRecommendCondition condition);

    List<RecommendDetailResp> myRecommendList(MyRecommendListReq req);

    List<RecommendDetailForAdminResp> recommendListForAdmin(TCompradorRecommendCondition condition);
}
