package com._360pai.core.service.enrolling;


import java.util.List;

import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.facade.enrolling.req.EnrollingReq.activityIdTypeReq;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.NotifyPartyEnrollingActivity;
import com.github.pagehelper.PageInfo;

public interface NotifyPartyEnrollingActivityService{


    PageInfo getNotifiedList(ActivityIdReqDto req);

    int saveNotify(NotifyPartyEnrollingActivity notice);

	NotifyPartyEnrollingActivity getFilterModel(activityIdTypeReq req);

	Integer deleteModel(String id);

	NotifyPartyEnrollingActivity getNotifyPartyById(Integer noticeModelId);

	List<NotifyPartyEnrollingActivity> getNotifyListByActivityId(Integer noticeModelId);

	int partyIdBind(String accountId, String partyId);

	List<EnrollingActivity> getBeginIn5MinuteList(Integer accountId, Integer partyId);

	List<EnrollingActivity> getEndIn5MinuteList(Integer accountId, Integer partyId);
}