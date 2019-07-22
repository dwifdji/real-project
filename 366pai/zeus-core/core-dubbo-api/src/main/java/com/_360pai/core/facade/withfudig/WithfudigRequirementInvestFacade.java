package com._360pai.core.facade.withfudig;

import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.comprador.req.RequirementRecommenderQueryReq;
import com._360pai.core.facade.withfudig.req.WithfudigRequirementInvestReq;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/6 15:56
 */
public interface WithfudigRequirementInvestFacade {
    PageUtils.Page requirementInvestList(WithfudigRequirementInvestReq.RequirementInvestList req);

    int requirementInvestMatchSuccess(WithfudigRequirementInvestReq.RequirementInvestRemark req);

    int requirementInvestRemark(WithfudigRequirementInvestReq.RequirementInvestRemark req);

    int requirementInvestAdd(WithfudigRequirementInvestReq.RequirementInvestAdd req);

    PageUtils.Page myRequirementInvestList(WithfudigRequirementInvestReq.MyRequirementInvestList req);
}
