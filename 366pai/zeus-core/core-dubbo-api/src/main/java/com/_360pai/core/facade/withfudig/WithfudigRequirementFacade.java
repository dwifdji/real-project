package com._360pai.core.facade.withfudig;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.withfudig.req.WithfudigRequirementReq;
import com._360pai.core.facade.withfudig.resp.WithfudigRequirementDetailResp;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/6 15:55
 */
public interface WithfudigRequirementFacade {

    PageUtils.Page requirementListForAdmin(WithfudigRequirementReq.RequirementListForAdmin req);

    WithfudigRequirementDetailResp requirementDetail(WithfudigRequirementReq.RequirementDetailReq req,Boolean isAdmin);

    int updateRequirementStatus(WithfudigRequirementReq.RequirementStatusUpdate req);

    int requirementFinished(WithfudigRequirementReq.RequirementId req);

    PageUtils.Page myRequirementList(WithfudigRequirementReq.MyRequirementList req);

    PageUtils.Page requirementListForPlatform(WithfudigRequirementReq.RequirementListForPlatform req);

    int requirementAdd(WithfudigRequirementReq.RequirementAdd req);

    int requirementRelateAssertId(WithfudigRequirementReq.RequirementRelateAssertId req);

    int requirementUpdate(WithfudigRequirementReq.RequirementUpdate req);

    int updateFollowCount(String requirementId);

    int specialNoticeUpdate(WithfudigRequirementReq.SpecialNoticeUpdate req);

    PageUtils.Page requirementFollowList(WithfudigRequirementReq.RequirementListForPlatform req);
}
