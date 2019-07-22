package com._360pai.core.service.withfudig;

import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.condition.withfudig.TWithfudigRequirementInvestCondition;
import com._360pai.core.facade.withfudig.req.WithfudigRequirementInvestReq;
import com._360pai.core.facade.withfudig.resp.WithfudigRequirementInvestDetailResp;
import com._360pai.core.model.withfudig.TWithfudigRequirementInvest;

import java.util.List;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/6 16:24
 */
public interface WithfudigRequirementInvestService {
    List<WithfudigRequirementInvestDetailResp> requirementInvestList(TWithfudigRequirementInvestCondition condition);

    int requirementInvestMatchSuccess(WithfudigRequirementInvestReq.RequirementInvestRemark req);

    int requirementInvestRemark(WithfudigRequirementInvestReq.RequirementInvestRemark req);

    int requirementInvestAdd(TWithfudigRequirementInvest tWithfudigRequirementInvest);

    PageUtils.Page myRequirementInvestList(WithfudigRequirementInvestReq.MyRequirementInvestList req);
}
