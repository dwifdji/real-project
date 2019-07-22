package com._360pai.core.service.assistant;


import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.activity.req.InstructionsContentReq;
import com._360pai.core.facade.activity.vo.InstructionsContentListVo;
import com._360pai.core.model.assistant.InstructionsContent;
import com.github.pagehelper.PageInfo;

public interface InstructionsContentService {

	InstructionsContent findInstructionsContentByName(InstructionsContent params);
	
	InstructionsContent findInstructionsContentById(InstructionsContent params);
	
    PageInfoResp<InstructionsContentListVo> selectInstructionsContent(int page, int perPage);

    int addInstructionsContent(InstructionsContent params);

    int editInstructionsContent(InstructionsContent params);

    int deleteInstructionsContent(InstructionsContent params);

    String announcement(Integer activityId);

    String buyerMustKnow(Integer name);

    String disposalAnnouncement(Integer disposalId);

    String enrollingBuyerMustKnow(Integer activityId);

    String enrollingBuyerMustKnow(Integer activityId, String agencyCode);

    String enrollingAnnouncement(Integer activityId);

    String enrollingAnnouncement(Integer activityId, String agencyCode);

    String realAnnouncement(Integer activityId);

    String realAnnouncement(Integer activityId, String agencyCode);

    String obligatoryAnnouncement(Integer activityId);

    String obligatoryAnnouncement(Integer activityId, String agencyCode);

    ResponseModel getAppletAgreement(String type);

    Object getLeaseAnnouncement(Integer activityId);

    Object getLeaseBuyerMustKnow(InstructionsContentReq req);
}