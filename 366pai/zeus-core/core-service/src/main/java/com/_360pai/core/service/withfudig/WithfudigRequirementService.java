package com._360pai.core.service.withfudig;

import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.condition.withfudig.TWithfudigRequirementCondition;
import com._360pai.core.facade.withfudig.req.WithfudigRequirementReq;
import com._360pai.core.facade.withfudig.resp.MyRequirementDetailResp;
import com._360pai.core.facade.withfudig.resp.WithfudigRequirementDetailResp;
import com._360pai.core.model.withfudig.TWithfudigRequirement;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/6 16:22
 */
public interface WithfudigRequirementService {
    int requirementAdd(TWithfudigRequirement tWithfudigRequirement);

    PageUtils.Page requirementListForAdmin(WithfudigRequirementReq.RequirementListForAdmin req);

    WithfudigRequirementDetailResp requirementDetail(WithfudigRequirementReq.RequirementDetailReq req ,Boolean isAdmin);

    int updateRequirementStatus(WithfudigRequirementReq.RequirementStatusUpdate req);

    int requirementFinished(WithfudigRequirementReq.RequirementId req);

    List<MyRequirementDetailResp> myRequirementList(WithfudigRequirementReq.MyRequirementList req);

    WithfudigRequirementDetailResp getRequirementDetail(Integer requirementId);

    int requirementRelateAssertId(WithfudigRequirementReq.RequirementRelateAssertId req);

    int requirementUpdate(WithfudigRequirementReq.RequirementUpdate req);

    int updateFollowCount(String requirementId);

    int specialNoticeUpdate(WithfudigRequirementReq.SpecialNoticeUpdate req);

    PageUtils.Page selectListForPlatform(WithfudigRequirementReq.RequirementListForPlatform req);

    PageUtils.Page requirementFollowList(WithfudigRequirementReq.RequirementListForPlatform req);
}
