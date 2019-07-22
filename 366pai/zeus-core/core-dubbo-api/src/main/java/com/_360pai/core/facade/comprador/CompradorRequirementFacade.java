package com._360pai.core.facade.comprador;

import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.comprador.req.*;
import com._360pai.core.facade.comprador.resp.CompradorDetailResp;
import com._360pai.core.facade.comprador.resp.CompradorListByBuildingTypeResp;

import java.util.List;

/**
 * 描述 资产大买办
 *
 * @author : whisky_vip
 * @date : 2018/8/29 16:52
 */
public interface CompradorRequirementFacade {


    int requirementAuditNoPass(CompradorRequirementReq.RequirementStatusUpdate req);

    /**
     * 描述 返回 数据 id
     *
     * @author : whisky_vip
     * @date : 2018/9/17 16:54
     */
    int requirementAdd(CompradorRequirementReq.CompradorRequirementAdd req);

    CompradorDetailResp requirementDetail(CompradorRequirementQueryReq req, Boolean isAdmin);

    PageUtils.Page requirementListForPlatform(RequirementListForPlatformReq req);

    PageUtils.Page requirementListForAdmin(RequirementAdminQueryRep req);

    List<CompradorListByBuildingTypeResp> requirementListByBuildingType();

    int requirementFinished(CompradorRequirementQueryReq req);

    PageUtils.Page myRequirementList(MyRequirementListReq req);

    int requirementUpdate(CompradorRequirementReq.RequirementUpdate req);

    int requirementAuditPass(CompradorRequirementReq.RequirementStatusUpdate req);
}
