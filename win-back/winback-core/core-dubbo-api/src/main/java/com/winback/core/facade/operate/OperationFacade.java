package com.winback.core.facade.operate;

import com.winback.arch.common.ResponseModel;
import com.winback.core.facade.operate.req.OperationReq;

/**
 * create by liuhaolei on 2019-1-22
 * 运营管理facade
 */
public interface OperationFacade {


    ResponseModel saveReleaseArea(OperationReq.SaveOperateIconReq releaseAreaReq);

    ResponseModel updateOrDeleteReleaseArea(OperationReq.UpdateOperateIconReq releaseAreaReq);

    ResponseModel getReleaseAreaList(OperationReq.ListOperateIconReq releaseAreaReq);

    ResponseModel saveAdvertisingSpace(OperationReq.SaveAdvertisingSpace req);

    ResponseModel updateOrDeleteAS(OperationReq.UpdateAdvertisingSpace req);

    ResponseModel getAdvertisingSpaceList(OperationReq.ListAdvertisingSpaceReq req);

    ResponseModel getBannerList(OperationReq.AppBannerListReq req);

    ResponseModel getOperateIconList(OperationReq.OperateIconListReq req);

    ResponseModel getHomeIconCategory(OperationReq.OperateIconListReq req);

    ResponseModel getAnnouncementList(OperationReq.AnnouncementListReq req);

    ResponseModel saveOperateAnnouncement(OperationReq.AnnouncementSaveReq req);

    ResponseModel updateOrDeleteOA(OperationReq.AnnouncementUpdateReq req);

    ResponseModel getHomeAnnouncementList(OperationReq.HomeAnnouncementReq req);

    ResponseModel getOperateIconById(OperationReq.ListOperateIconReq releaseAreaReq);

    ResponseModel getAdvertisingSpaceById(OperationReq.ListAdvertisingSpaceReq req);

    ResponseModel getAnnouncementById(OperationReq.AnnouncementListReq req);

    ResponseModel getQuickRelease(OperationReq.OperateIconListReq req);

    ResponseModel getNavigationBar(OperationReq.OperateIconListReq req);

    ResponseModel getContractModel(OperationReq.OperateIconListReq req);

    ResponseModel getQualityCase(OperationReq.OperateIconListReq req);

    ResponseModel getCaseBriefList();

    ResponseModel getContractTypeList();

    ResponseModel getAllCaseType();

    ResponseModel getAllCaseStep(OperationReq.OpenType openType);
}
