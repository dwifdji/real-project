package com.winback.core.provider.operate;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.ResponseModel;
import com.winback.core.commons.constants.OperateEnum;
import com.winback.core.facade.operate.OperationFacade;
import com.winback.core.facade.operate.req.OperationReq;
import com.winback.core.service.operate.OperationService;
import com.winback.core.vo.operate.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * create by liuhaolei on 2019-01-22
 * 运营管理生产者
 */
@Component
@Service(version = "1.0.0")
public class OperationProvider implements OperationFacade {

    @Autowired
    private OperationService operationService;

    @Override
    public ResponseModel saveReleaseArea(OperationReq.SaveOperateIconReq releaseAreaReq) {
        Integer count = operationService.saveReleaseArea(releaseAreaReq);
        if (count != null) {
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }

    @Override
    public ResponseModel updateOrDeleteReleaseArea(OperationReq.UpdateOperateIconReq releaseAreaReq) {
        Integer count = operationService.updateOrDeleteReleaseArea(releaseAreaReq);
        if (count != null) {
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }

    @Override
    public ResponseModel getReleaseAreaList(OperationReq.ListOperateIconReq releaseAreaReq) {
        PageInfo pageInfo = operationService.getReleaseAreaList(releaseAreaReq);

        PageInfoResp page = new PageInfoResp(pageInfo);
        return ResponseModel.succ(page);
    }

    @Override
    public ResponseModel saveAdvertisingSpace(OperationReq.SaveAdvertisingSpace req) {
        Integer count = operationService.saveAdvertisingSpace(req);
        if (count != null) {
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }

    @Override
    public ResponseModel updateOrDeleteAS(OperationReq.UpdateAdvertisingSpace req) {

        Integer count = operationService.updateOrDeleteAS(req);
        if (count != null) {
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }

    @Override
    public ResponseModel getAdvertisingSpaceList(OperationReq.ListAdvertisingSpaceReq req) {
        PageInfo pageInfo = operationService.getAdvertisingSpaceList(req);

        PageInfoResp page = new PageInfoResp(pageInfo);
        return ResponseModel.succ(page);
    }

    @Override
    public ResponseModel getBannerList(OperationReq.AppBannerListReq req) {
        List<HomePageBannerVO> homePageBannerVOS = operationService.getBannerList(req);

        Map<String, Object> result = new HashMap<>();
        result.put("list", homePageBannerVOS);
        return ResponseModel.succ(result);
    }

    @Override
    public ResponseModel getOperateIconList(OperationReq.OperateIconListReq req) {

        List<HomeIconGroupTypeVO> operateIconGroupTypeVOS = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            String type = String.valueOf(i);
            req.setGroupType(type);

            HomeIconGroupTypeVO operateIconGroupTypeVO =
                    new HomeIconGroupTypeVO(type, OperateEnum.OperateIconEnum.getDescByType(type));
//            List<OperateIconListVO> operateIconListVOS = operationService.getOperateIconList(type);
//            operateIconGroupTypeVO.setOperateIconListVOS(operateIconListVOS);

            operateIconGroupTypeVOS.add(operateIconGroupTypeVO);
        }

        return ResponseModel.succ(operateIconGroupTypeVOS);
    }


    @Override
    public ResponseModel getHomeIconCategory(OperationReq.OperateIconListReq req) {
        List<HomeIconCategoryGroupTypeVO> homeIconCategoryGroupTypeVOS = new ArrayList<>();
        for (int i = 3; i < 5; i++) {
            String type = String.valueOf(i);
            List<HomeIconCategoryVO> homeIconCategoryVOS = null;
//
//            if(OperateEnum.OperateIconEnum.QUALITY_CASE.getType().equals(type)) {
//                homeIconCategoryVOS = operationService.getQualityCaseIcons();
//            }else if(OperateEnum.OperateIconEnum.CONTRACT_MODEL.getType().equals(type)){
//                homeIconCategoryVOS = operationService.getContractModelIcons();
//            }

            HomeIconCategoryGroupTypeVO homeIconCategoryGroupTypeVO =
                    new HomeIconCategoryGroupTypeVO(type, OperateEnum.OperateIconEnum.getDescByType(type));
            homeIconCategoryGroupTypeVO.setHomeIconCategoryVOS(homeIconCategoryVOS);

            homeIconCategoryGroupTypeVOS.add(homeIconCategoryGroupTypeVO);
        }
        return ResponseModel.succ(homeIconCategoryGroupTypeVOS);
    }

    @Override
    public ResponseModel getAnnouncementList(OperationReq.AnnouncementListReq req) {
        PageInfo pageInfo = operationService.getAnnouncementList(req);

        PageInfoResp page = new PageInfoResp(pageInfo);
        return ResponseModel.succ(page);
    }

    @Override
    public ResponseModel saveOperateAnnouncement(OperationReq.AnnouncementSaveReq req) {

        Integer count = operationService.saveOperateAnnouncement(req);
        if (count != null) {
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }

    @Override
    public ResponseModel updateOrDeleteOA(OperationReq.AnnouncementUpdateReq req) {

        Integer count = operationService.updateOrDeleteOA(req);
        if (count != null) {
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }

    @Override
    public ResponseModel getHomeAnnouncementList(OperationReq.HomeAnnouncementReq req) {

        PageInfo pageInfo = operationService.getHomeAnnouncementList(req);

        PageInfoResp page = new PageInfoResp(pageInfo);
        return ResponseModel.succ(page);
    }

    @Override
    public ResponseModel getOperateIconById(OperationReq.ListOperateIconReq releaseAreaReq) {

        OperateIconListVO operateIconListVO = operationService.getOperateIconById(releaseAreaReq);
        return ResponseModel.succ(operateIconListVO);
    }

    @Override
    public ResponseModel getAdvertisingSpaceById(OperationReq.ListAdvertisingSpaceReq req) {
        AdvertisingSpaceListVO advertisingSpaceListVO = operationService.getAdvertisingSpaceById(req);
        return ResponseModel.succ(advertisingSpaceListVO);
    }

    @Override
    public ResponseModel getAnnouncementById(OperationReq.AnnouncementListReq req) {
        AnnouncementDetailVO announcementDetailVO = operationService.getAnnouncementById(req);
        return ResponseModel.succ(announcementDetailVO);
    }

    @Override
    public ResponseModel getQuickRelease(OperationReq.OperateIconListReq req) {
        List<QuickReleaseVO> quickReleaseVOS = operationService.getCaseTypeList(req.getLimitSize());
        Map<String, Object> result = new HashMap<>();
        result.put("list", quickReleaseVOS);
        return ResponseModel.succ(result);
    }

    @Override
    public ResponseModel getNavigationBar(OperationReq.OperateIconListReq req) {
        List<OperateIconListVO> operateIconListVOS = operationService.getOperateIconList(req.getGroupType(), req.getLimitSize());
        Map<String, Object> result = new HashMap<>();
        result.put("list", operateIconListVOS);
        return ResponseModel.succ(result);
    }

    @Override
    public ResponseModel getContractModel(OperationReq.OperateIconListReq req) {
        List<HomeContractCategoryVO> homeIconCategoryVOS = operationService.getContractModelIcons(req.getLimitSize());
        Map<String, Object> result = new HashMap<>();
        result.put("list", homeIconCategoryVOS);
        return ResponseModel.succ(result);
    }

    @Override
    public ResponseModel getQualityCase(OperationReq.OperateIconListReq req) {
        List<HomeIconCategoryVO> homeIconCategoryVOS = operationService.getQualityCaseIcons(req.getLimitSize());
        Map<String, Object> result = new HashMap<>();
        result.put("list", homeIconCategoryVOS);
        return ResponseModel.succ(result);
    }

    @Override
    public ResponseModel getCaseBriefList() {
        List<CaseBriefVO> caseBriefList = operationService.getCaseBriefList();

        Map<String, Object> result = new HashMap<>();
        result.put("list", caseBriefList);
        return ResponseModel.succ(result);
    }

    @Override
    public ResponseModel getContractTypeList() {
        List<ContractTypeVO> contractTypeList = operationService.getContractTypeList();
        Map<String, Object> result = new HashMap<>();
        result.put("list", contractTypeList);
        return ResponseModel.succ(result);
}

    @Override
    public ResponseModel getAllCaseType() {
        List<CaseTypeVO> caseTypeVOS = operationService.getAllCaseType();
        Iterator<CaseTypeVO> itr = caseTypeVOS.iterator();
        while (itr.hasNext()) {
            CaseTypeVO vo = itr.next();
            if ("5".equals(vo.getCaseTypeId())) {
                itr.remove();
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("list", caseTypeVOS);
        return ResponseModel.succ(result);
    }

    @Override
    public ResponseModel getAllCaseStep(OperationReq.OpenType openType) {
        List<CaseStepVO> caseStepVOS = operationService.getAllCaseStep(openType);
        Map<String, Object> result = new HashMap<>();
        result.put("list", caseStepVOS);
        return ResponseModel.succ(result);
    }
}
