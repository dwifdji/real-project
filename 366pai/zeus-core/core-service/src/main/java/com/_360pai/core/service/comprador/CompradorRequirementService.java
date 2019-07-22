package com._360pai.core.service.comprador;

import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.comprador.req.*;
import com._360pai.core.facade.comprador.resp.CompradorDetailResp;
import com._360pai.core.facade.comprador.resp.CompradorListByBuildingTypeResp;
import com._360pai.core.facade.comprador.resp.MyRequirementDetailResp;
import com._360pai.core.model.comprador.TCompradorRequirement;

import java.util.List;

/**
 * 描述 资产大买办
 *
 * @author : whisky_vip
 * @date : 2018/8/31 11:24
 */
public interface CompradorRequirementService {


    int requirementAuditNoPass(CompradorRequirementReq.RequirementStatusUpdate req);

    int requirementAdd(TCompradorRequirement tCompradorRequirement);

    CompradorDetailResp getTCompradorRequirementDetail(Integer requirementId, Integer partyId, Boolean isAdmin);

    PageUtils.Page requirementListForPlatform(RequirementListForPlatformReq req);

    PageUtils.Page requirementListForAdmin(RequirementAdminQueryRep req);

    List<CompradorListByBuildingTypeResp> requirementListByBuildingType();

    int requirementFinished(CompradorRequirementQueryReq req);

    List<MyRequirementDetailResp> myRequirementList(MyRequirementListReq req);

    int requirementUpdate(CompradorRequirementReq.RequirementUpdate req);

    Integer getRecomenderCount(Integer id);

    int requirementAuditPass(CompradorRequirementReq.RequirementStatusUpdate req);
}
