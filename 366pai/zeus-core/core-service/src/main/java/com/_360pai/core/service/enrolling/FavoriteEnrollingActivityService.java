package com._360pai.core.service.enrolling;


import java.util.List;

import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.dto.enrolling.FocusActivityDto;
import com._360pai.core.facade.enrolling.req.EnrollingReq.activityIdTypeReq;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.FavoriteEnrollingActivity;
import com.github.pagehelper.PageInfo;

public interface FavoriteEnrollingActivityService{


    PageInfo getFocusList(ActivityIdReqDto req);

    int saveFocus(FavoriteEnrollingActivity req);

	FavoriteEnrollingActivity getFilterModel(activityIdTypeReq req);

	Integer deleteModel(String id);

	Integer cancelMyFocusList(List<String> focusList);

	List<FavoriteEnrollingActivity> getFocusActivityList(Integer partyId, Integer accountId);

	int partyIdBind(String accountId, String partyId);

	List<EnrollingActivity> getBeginIn5MinuteList(Integer accountId, Integer partyId);

	List<EnrollingActivity> getEndIn5MinuteList(Integer accountId, Integer partyId);

}