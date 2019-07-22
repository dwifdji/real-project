package com._360pai.core.provider.comprador;

import com._360pai.arch.common.utils.OrderCodeGenerator;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.common.constants.CompradorEnum;
import com._360pai.core.facade.comprador.CompradorRequirementFacade;
import com._360pai.core.facade.comprador.req.*;
import com._360pai.core.facade.comprador.resp.CompradorDetailResp;
import com._360pai.core.facade.comprador.resp.CompradorListByBuildingTypeResp;
import com._360pai.core.facade.comprador.resp.MyRequirementDetailResp;
import com._360pai.core.model.comprador.TCompradorRequirement;
import com._360pai.core.service.comprador.CompradorRequirementService;
import com._360pai.core.utils.ServiceMessageUtils;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 描述 资产大买办
 *
 * @author : whisky_vip
 * @date : 2018/8/29 16:55
 */
@Component
@Service(version = "1.0.0")
public class CompradorRequirementProvider implements CompradorRequirementFacade {

    @Autowired
    private CompradorRequirementService compradorRequirementService;
    @Autowired
    private ServiceMessageUtils         serviceMessageUtils;

    @Override
    public int requirementAuditNoPass(CompradorRequirementReq.RequirementStatusUpdate req) {
        return compradorRequirementService.requirementAuditNoPass(req);
    }
    @Override
    public int requirementAuditPass(CompradorRequirementReq.RequirementStatusUpdate req) {
        return compradorRequirementService.requirementAuditPass(req);
    }
    @Override
    public int requirementAdd(CompradorRequirementReq.CompradorRequirementAdd req) {
        TCompradorRequirement tCompradorRequirement = new TCompradorRequirement();
        BeanUtils.copyProperties(req, tCompradorRequirement);
        tCompradorRequirement.setCreatedTime(new Date());
        tCompradorRequirement.setRequirementNo(OrderCodeGenerator.INSTANCE.getOrderCode(OrderCodeGenerator.COMPRADOR_REQUIREMENT_NO));
        tCompradorRequirement.setRequirementStatus(CompradorEnum.RequirementStatus.WAITING_PASS.getValue() + "");
        int i = compradorRequirementService.requirementAdd(tCompradorRequirement);
        if (i > 0) {
            serviceMessageUtils.compradorAdd(tCompradorRequirement.getId());
        }
        return i;
    }

    @Override
    public CompradorDetailResp requirementDetail(CompradorRequirementQueryReq req, Boolean isAdmin) {
        return compradorRequirementService.getTCompradorRequirementDetail(req.getRequirementId(), req.getPartyId(), isAdmin);
    }

    @Override
    public PageUtils.Page requirementListForPlatform(RequirementListForPlatformReq req) {

        return compradorRequirementService.requirementListForPlatform(req);
    }

    @Override
    public PageUtils.Page requirementListForAdmin(RequirementAdminQueryRep req) {
        return compradorRequirementService.requirementListForAdmin(req);
    }

    @Override
    public List<CompradorListByBuildingTypeResp> requirementListByBuildingType() {
        return compradorRequirementService.requirementListByBuildingType();
    }

    @Override
    public PageUtils.Page myRequirementList(MyRequirementListReq req) {
        List<MyRequirementDetailResp> list = compradorRequirementService.myRequirementList(req);
        return PageUtils.fen(req.getPage(), req.getPerPage(), list);
    }

    @Override
    public int requirementUpdate(CompradorRequirementReq.RequirementUpdate req) {
        return compradorRequirementService.requirementUpdate(req);
    }



    @Override
    public int requirementFinished(CompradorRequirementQueryReq req) {
        return compradorRequirementService.requirementFinished(req);
    }

}
