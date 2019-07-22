package com._360pai.core.service.comprador;

import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.condition.comprador.TCompradorRequirementRecommenderCondition;
import com._360pai.core.facade.comprador.req.MyRequirementRecommenderListReq;
import com._360pai.core.facade.comprador.req.RequiementRecommenderRemarkReq;
import com._360pai.core.facade.comprador.resp.RequirementRecommenderDetailResp;
import com._360pai.core.model.comprador.TCompradorRequirementRecommender;

import java.util.List;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/4 15:28
 */
public interface CompradorRequirementRecommenderService {
    int requirementRecommenderAdd(TCompradorRequirementRecommender tCompradorRequirementRecommender);

    List<RequirementRecommenderDetailResp> requirementRecommenderList(TCompradorRequirementRecommenderCondition condition);

    int requirementRecommenderRemark(RequiementRecommenderRemarkReq req);


    int requirementRecommenderMatchSuccess(RequiementRecommenderRemarkReq req);

    PageUtils.Page myRequirementRecommenderList(MyRequirementRecommenderListReq req);

    boolean hasRecommend(Integer partyId, Integer requirementId);
}
