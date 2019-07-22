package com._360pai.core.provider.activity;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.activity.InstructionsContentFacade;
import com._360pai.core.facade.activity.req.InstructionsContentReq;
import com._360pai.core.facade.activity.vo.InstructionsContentListVo;
import com._360pai.core.facade.asset.vo.AssetVo;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.assistant.InstructionsContent;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.activity.AuctionActivityService;
import com._360pai.core.service.asset.AssetService;
import com._360pai.core.service.assistant.InstructionsContentService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: InstructionsContentProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/21 17:02
 */
@Component
@Service(version = "1.0.0")
public class InstructionsContentProvider implements InstructionsContentFacade {
    @Autowired
    private InstructionsContentService instructionsContentService;
    @Autowired
    private AuctionActivityService auctionActivityService;
    @Autowired
    private AssetService assetService;
    @Autowired
    private AccountService accountService;

    @Override
    public PageInfoResp<InstructionsContentListVo> selectInstructionsContentList(InstructionsContentReq req) {
        return instructionsContentService.selectInstructionsContent(req.getPage(), req.getPerPage());
    }

    @Override
    public ResponseModel findInstructionsContentById(Integer id) {
        InstructionsContent req = new InstructionsContent();
        req.setId(id);
        InstructionsContent resp = instructionsContentService.findInstructionsContentById(req);
        return ResponseModel.succ(resp);
    }

    @Override
    public int addInstructionsContent(InstructionsContentReq req) {
        return instructionsContentService.addInstructionsContent(copyInstructionsContent(req));
    }

    @Override
    public int editInstructionsContent(InstructionsContentReq req) {
        return instructionsContentService.editInstructionsContent(copyInstructionsContent(req));
    }

    @Override
    public int deleteInstructionsContent(InstructionsContentReq req) {
        return instructionsContentService.deleteInstructionsContent(copyInstructionsContent(req));
    }

    @Override
    public String specialDetail(InstructionsContentReq req) {
        return auctionActivityService.specialDetail(req.getActivityId());
    }

    @Override
    public String announcement(InstructionsContentReq req) {
        return instructionsContentService.announcement(req.getActivityId());
    }

    @Override
    public String buyerMustKnow(InstructionsContentReq req) {
        return instructionsContentService.buyerMustKnow(req.getActivityId());
    }

    @Override
    public Object getActivityById(InstructionsContentReq req) {
        return auctionActivityService.getById(req.getActivityId());
    }

    @Override
    public String disposalAnnouncement(InstructionsContentReq req) {
        return instructionsContentService.disposalAnnouncement(req.getDisposalId());
    }

    @Override
    public String enrollingBuyerMustKnow(InstructionsContentReq req) {
        return instructionsContentService.enrollingBuyerMustKnow(req.getActivityId(), req.getAgencyCode());
    }

    @Override
    public String enrollingAnnouncement(InstructionsContentReq req) {
        return instructionsContentService.enrollingAnnouncement(req.getActivityId(), req.getAgencyCode());
    }

    @Override
    public String realAnnouncement(InstructionsContentReq req) {
        return instructionsContentService.realAnnouncement(req.getActivityId(), req.getAgencyCode());
    }

    @Override
    public String obligatoryAnnouncement(InstructionsContentReq req) {
        return instructionsContentService.obligatoryAnnouncement(req.getActivityId(), req.getAgencyCode());
    }

    @Override
    public ResponseModel getAppletAgreement(String type) {
        return instructionsContentService.getAppletAgreement(type);
    }

    private InstructionsContent copyInstructionsContent(InstructionsContentReq req) {
        InstructionsContent instructionsContent = new InstructionsContent();
        BeanUtils.copyProperties(req, instructionsContent);
        return instructionsContent;
    }


    @Override
    public Object getLeaseActivityInfo(InstructionsContentReq req) {
        return instructionsContentService.getLeaseAnnouncement(Integer.parseInt(req.getAssetId()));
    }

    @Override
    public Object getLeaseBuyerMustKnow(InstructionsContentReq req) {
        return instructionsContentService.getLeaseBuyerMustKnow(req);
    }
}
