package com.winback.core.service.operate;

import com.github.pagehelper.PageInfo;
import com.winback.core.facade.operate.req.OperationReq;
import com.winback.core.vo.operate.*;

import java.util.List;

public interface OperationService {

    Integer saveReleaseArea(OperationReq.SaveOperateIconReq releaseAreaReq);

    Integer updateOrDeleteReleaseArea(OperationReq.UpdateOperateIconReq releaseAreaReq);

    PageInfo getReleaseAreaList(OperationReq.ListOperateIconReq releaseAreaReq);

    Integer saveAdvertisingSpace(OperationReq.SaveAdvertisingSpace req);

    Integer updateOrDeleteAS(OperationReq.UpdateAdvertisingSpace req);

    PageInfo getAdvertisingSpaceList(OperationReq.ListAdvertisingSpaceReq req);

    List<HomePageBannerVO> getBannerList(OperationReq.AppBannerListReq req);

    List<OperateIconListVO> getOperateIconList(String type, Integer limitSize);

    List<HomeIconCategoryVO> getQualityCaseIcons(Integer limitSize);

    List<HomeContractCategoryVO> getContractModelIcons(Integer limitSize);

    Integer saveOperateAnnouncement(OperationReq.AnnouncementSaveReq req);

    Integer updateOrDeleteOA(OperationReq.AnnouncementUpdateReq req);

    PageInfo getAnnouncementList(OperationReq.AnnouncementListReq req);

    PageInfo getHomeAnnouncementList(OperationReq.HomeAnnouncementReq req);

    OperateIconListVO getOperateIconById(OperationReq.ListOperateIconReq releaseAreaReq);

    AdvertisingSpaceListVO getAdvertisingSpaceById(OperationReq.ListAdvertisingSpaceReq req);

    AnnouncementDetailVO getAnnouncementById(OperationReq.AnnouncementListReq req);

    List<CaseBriefVO> getCaseBriefList();

    List<ContractTypeVO> getContractTypeList();

    List<CaseTypeVO> getAllCaseType();

    List<CaseStepVO> getAllCaseStep(OperationReq.OpenType openType);

    List<QuickReleaseVO> getCaseTypeList( Integer limitSize);
}
