package com._360pai.core.facade.activity;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.activity.req.InstructionsContentReq;
import com._360pai.core.facade.activity.vo.InstructionsContentListVo;
import com.github.pagehelper.PageInfo;

public interface InstructionsContentFacade {
    PageInfoResp<InstructionsContentListVo> selectInstructionsContentList(InstructionsContentReq req);

    ResponseModel findInstructionsContentById(Integer id);

    int addInstructionsContent(InstructionsContentReq req);

    int editInstructionsContent(InstructionsContentReq req);

    int deleteInstructionsContent(InstructionsContentReq req);

    String specialDetail(InstructionsContentReq req);

    String announcement(InstructionsContentReq req);

    String buyerMustKnow(InstructionsContentReq req);

    Object getActivityById(InstructionsContentReq req);

    String disposalAnnouncement(InstructionsContentReq req);

    String enrollingBuyerMustKnow(InstructionsContentReq activityId);

    String enrollingAnnouncement(InstructionsContentReq req);

    String realAnnouncement(InstructionsContentReq req);

    String obligatoryAnnouncement(InstructionsContentReq req);

    ResponseModel getAppletAgreement(String type);


    Object getLeaseActivityInfo(InstructionsContentReq req);


    Object getLeaseBuyerMustKnow(InstructionsContentReq req);

 }
