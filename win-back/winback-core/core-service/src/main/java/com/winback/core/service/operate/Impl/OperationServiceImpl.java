package com.winback.core.service.operate.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.utils.DateUtil;
import com.winback.core.condition.operate.TOperateBannerCondition;
import com.winback.core.condition.operate.TOperateIconInfoCondition;
import com.winback.core.dao._case.TCaseBriefDao;
import com.winback.core.dao._case.TCaseDao;
import com.winback.core.dao._case.TCaseStepDao;
import com.winback.core.dao._case.TCaseTypeDao;
import com.winback.core.dao.contract.TContractTypeDao;
import com.winback.core.dao.operate.TOperateAnnouncementDao;
import com.winback.core.dao.operate.TOperateBannerDao;
import com.winback.core.dao.operate.TOperateIconInfoDao;
import com.winback.core.dto.operate.AdvertisingSpaceDto;
import com.winback.core.dto.operate.OperateAnnouncementDto;
import com.winback.core.dto.operate.OperateIconDto;
import com.winback.core.facade.operate.req.OperationReq;
import com.winback.core.model.contract.TContractType;
import com.winback.core.model.operate.TOperateAnnouncement;
import com.winback.core.model.operate.TOperateBanner;
import com.winback.core.model.operate.TOperateIconInfo;
import com.winback.core.service.operate.OperationService;
import com.winback.core.vo.operate.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OperationServiceImpl implements OperationService {
    @Autowired
    private TOperateIconInfoDao tOperateIconInfoDao;
    @Autowired
    private TOperateBannerDao tOperateBannerDao;
    @Autowired
    private TOperateAnnouncementDao tOperateAnnouncementDao;
    @Autowired
    private TCaseBriefDao tCaseBriefDao;
    @Autowired
    private TContractTypeDao tContractTypeDao;
    @Autowired
    private TCaseTypeDao tCaseTypeDao;
    @Autowired
    private TCaseStepDao tCaseStepDao;

    @Override
    public Integer saveReleaseArea(OperationReq.SaveOperateIconReq releaseAreaReq) {
        TOperateIconInfo tOperateIconInfo = new TOperateIconInfo();

        tOperateIconInfo.setGroupType(Integer.parseInt(releaseAreaReq.getGroupType()));
        tOperateIconInfo.setImgUrl(releaseAreaReq.getImgUrl());
        tOperateIconInfo.setOnlineFlag(true);
        tOperateIconInfo.setSort(releaseAreaReq.getSort() == null? null:Integer.parseInt(releaseAreaReq.getSort()));
        tOperateIconInfo.setName(releaseAreaReq.getName());
        tOperateIconInfo.setDeleteFlag(false);
        tOperateIconInfo.setCreateTime(new Date());
        tOperateIconInfo.setUpdateTime(new Date());
        tOperateIconInfo.setType(Integer.parseInt(releaseAreaReq.getType()));

        return tOperateIconInfoDao.insert(tOperateIconInfo);
    }

    @Override
    public Integer updateOrDeleteReleaseArea(OperationReq.UpdateOperateIconReq releaseAreaReq) {
        TOperateIconInfo tOperateIconInfo = tOperateIconInfoDao.selectById(releaseAreaReq.getId());

        if("0".equals(releaseAreaReq.getUpdateFlag())) {
            tOperateIconInfo.setGroupType(Integer.parseInt(releaseAreaReq.getGroupType()));
            tOperateIconInfo.setImgUrl(releaseAreaReq.getImgUrl());
            tOperateIconInfo.setSort(releaseAreaReq.getSort() == null? null:Integer.parseInt(releaseAreaReq.getSort()));
            tOperateIconInfo.setName(releaseAreaReq.getName());
            tOperateIconInfo.setUpdateTime(new Date());
            tOperateIconInfo.setType(Integer.parseInt(releaseAreaReq.getType()));
        }else {
            tOperateIconInfo.setDeleteFlag(true);
        }

        return tOperateIconInfoDao.updateById(tOperateIconInfo);
    }

    @Override
    public PageInfo getReleaseAreaList(OperationReq.ListOperateIconReq releaseAreaReq) {
        PageHelper.startPage(releaseAreaReq.getPage(), releaseAreaReq.getPerPage());
        OperateIconDto params = new OperateIconDto();
        params.setGroupType(releaseAreaReq.getGroupType());

        List<OperateIconListVO> operateIconListVOS = tOperateIconInfoDao.getReleaseAreaList(params);
        return new PageInfo(operateIconListVOS);
    }

    @Override
    public Integer saveAdvertisingSpace(OperationReq.SaveAdvertisingSpace req) {
        TOperateBanner tOperateBanner = new TOperateBanner();

        tOperateBanner.setName(req.getName());
        tOperateBanner.setNameDesc(req.getDesc());
        tOperateBanner.setImgUrl(req.getImgUrl());
        tOperateBanner.setJumpUrl(req.getJumpUrl());
        tOperateBanner.setType(req.getType());
        tOperateBanner.setBeginTime(req.getBeginTime());
        tOperateBanner.setEndTime(req.getEndTime());
        if("1".equals(req.getOnlineFlag())){
            tOperateBanner.setOnlineFlage(false);
        }else{
            tOperateBanner.setOnlineFlage(true);
        }
        tOperateBanner.setDeleteFlag(false);
        tOperateBanner.setCreateTime(new Date());
        tOperateBanner.setUpdateTime(new Date());
        tOperateBanner.setSort(req.getSort() == null? null: Integer.parseInt(req.getSort()));
        tOperateBanner.setFixedJumpType(req.getFixedJumpType());
        return tOperateBannerDao.insert(tOperateBanner);
    }

    @Override
    public Integer updateOrDeleteAS(OperationReq.UpdateAdvertisingSpace req) {
        TOperateBanner tOperateBanner = tOperateBannerDao.selectById(req.getId());
        tOperateBanner.setId(Integer.parseInt(req.getId()));

        if("0".equals(req.getUpdateFlag())) {
            tOperateBanner.setName(req.getName());
            tOperateBanner.setNameDesc(req.getDesc());
            tOperateBanner.setImgUrl(req.getImgUrl());
            tOperateBanner.setJumpUrl(req.getJumpUrl());
            tOperateBanner.setType(req.getType());
            tOperateBanner.setBeginTime(req.getBeginTime());
            tOperateBanner.setEndTime(req.getEndTime());
            tOperateBanner.setOnlineFlage("1".equals(req.getOnlineFlage()));
            tOperateBanner.setCreateTime(new Date());
            tOperateBanner.setUpdateTime(new Date());
            tOperateBanner.setSort(req.getSort() == null? null: Integer.parseInt(req.getSort()));
            tOperateBanner.setFixedJumpType(req.getFixedJumpType());
        }else {
            tOperateBanner.setDeleteFlag(true);
        }

        return tOperateBannerDao.updateById(tOperateBanner);
    }

    @Override
    public PageInfo getAdvertisingSpaceList(OperationReq.ListAdvertisingSpaceReq req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());

        AdvertisingSpaceDto params = new AdvertisingSpaceDto();
        params.setType(req.getType());

        List<AdvertisingSpaceListVO> advertisingSpaceListVOS = tOperateBannerDao.getAdvertisingSpaceList(params);

        return new PageInfo(advertisingSpaceListVOS);
    }

    @Override
    public List<HomePageBannerVO> getBannerList(OperationReq.AppBannerListReq req) {
        AdvertisingSpaceDto params = new AdvertisingSpaceDto();
        params.setType(req.getType());

        List<HomePageBannerVO> homePageBannerVOS = tOperateBannerDao.getBannerList(params);
        return homePageBannerVOS;
    }

    @Override
    public List<OperateIconListVO> getOperateIconList(String type, Integer limitSize) {
        OperateIconDto params = new OperateIconDto();
        params.setGroupType(type);
        params.setLimitSize(limitSize);

        return tOperateIconInfoDao.getReleaseAreaList(params);
    }

    @Override
    public List<HomeIconCategoryVO> getQualityCaseIcons(Integer limitSize) {

        return tOperateIconInfoDao.getQualityCaseIcons(limitSize);
    }

    @Override
    public List<HomeContractCategoryVO> getContractModelIcons(Integer limitSize) {

        return tOperateIconInfoDao.getContractModelIcons(limitSize);
    }

    @Override
    public Integer saveOperateAnnouncement(OperationReq.AnnouncementSaveReq req) {
        TOperateAnnouncement tOperateAnnouncement = new TOperateAnnouncement();

        tOperateAnnouncement.setName(req.getName());
        tOperateAnnouncement.setType(req.getType());
        tOperateAnnouncement.setNameDesc(req.getDesc());
        tOperateAnnouncement.setBeginTime(req.getBeginTime());
        tOperateAnnouncement.setEndTime(req.getEndTime());
        tOperateAnnouncement.setCreateTime(new Date());
        tOperateAnnouncement.setUpdateTime(new Date());
        tOperateAnnouncement.setDeleteFlag(false);

        return tOperateAnnouncementDao.insert(tOperateAnnouncement);
    }

    @Override
    public Integer updateOrDeleteOA(OperationReq.AnnouncementUpdateReq req) {
        TOperateAnnouncement tOperateAnnouncement = tOperateAnnouncementDao.selectById(req.getId());

        if("0".equals(req.getUpdateFlag())) {
            tOperateAnnouncement.setName(req.getName());
            tOperateAnnouncement.setType(req.getType());
            tOperateAnnouncement.setNameDesc(req.getDesc());
            tOperateAnnouncement.setBeginTime(req.getBeginTime());
            tOperateAnnouncement.setEndTime(req.getEndTime());
            tOperateAnnouncement.setUpdateTime(new Date());
        }else {
            tOperateAnnouncement.setDeleteFlag(true);
        }

        return tOperateAnnouncementDao.updateById(tOperateAnnouncement);
    }

    @Override
    public PageInfo getAnnouncementList(OperationReq.AnnouncementListReq req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());
        OperateAnnouncementDto params = new OperateAnnouncementDto();
        params.setType(req.getType());

        List<OperateAnnouncementVO> operateAnnouncementVOS = tOperateAnnouncementDao.getAnnouncementList(params);

        return new PageInfo(operateAnnouncementVOS);
    }

    @Override
    public PageInfo getHomeAnnouncementList(OperationReq.HomeAnnouncementReq req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());
        OperateAnnouncementDto params = new OperateAnnouncementDto();
        params.setType(req.getType());

        List<HomeAnnouncementVO> homeAnnouncementList = tOperateAnnouncementDao.getHomeAnnouncementList(params);
        return new PageInfo(homeAnnouncementList);
    }

    @Override
    public OperateIconListVO getOperateIconById(OperationReq.ListOperateIconReq releaseAreaReq) {
        OperateIconListVO operateIconListVO = new OperateIconListVO();

        TOperateIconInfo tOperateIconInfo = tOperateIconInfoDao.selectById(releaseAreaReq.getId());
        operateIconListVO.setGroupType(tOperateIconInfo.getGroupType().toString());
        operateIconListVO.setType(tOperateIconInfo.getType().toString());
        operateIconListVO.setId(tOperateIconInfo.getId().toString());
        operateIconListVO.setSort(tOperateIconInfo.getSort());
        operateIconListVO.setImgUrl(tOperateIconInfo.getImgUrl());
        operateIconListVO.setName(tOperateIconInfo.getName());

        return operateIconListVO;
    }

    @Override
    public AdvertisingSpaceListVO getAdvertisingSpaceById(OperationReq.ListAdvertisingSpaceReq req) {
        AdvertisingSpaceListVO advertisingSpaceListVO = new AdvertisingSpaceListVO();

        TOperateBanner tOperateBanner = tOperateBannerDao.selectById(req.getId());
        advertisingSpaceListVO.setType(tOperateBanner.getType());
        advertisingSpaceListVO.setBeginTime(DateUtil.getNormDateStr(tOperateBanner.getBeginTime()));
        advertisingSpaceListVO.setEndTime(DateUtil.getNormDateStr(tOperateBanner.getEndTime()));
        advertisingSpaceListVO.setName(tOperateBanner.getName());
        advertisingSpaceListVO.setId(tOperateBanner.getId());
        advertisingSpaceListVO.setJumpUrl(tOperateBanner.getJumpUrl());
        advertisingSpaceListVO.setSort(tOperateBanner.getSort());
        advertisingSpaceListVO.setOnlineFlage(tOperateBanner.getOnlineFlage()?0:1);
        advertisingSpaceListVO.setNameDesc(tOperateBanner.getNameDesc());
        advertisingSpaceListVO.setFixedJumpType(tOperateBanner.getFixedJumpType());
        return advertisingSpaceListVO;
    }

    @Override
    public AnnouncementDetailVO getAnnouncementById(OperationReq.AnnouncementListReq req) {
        AnnouncementDetailVO announcementDetailVO = tOperateAnnouncementDao.getAnnouncementById(req.getId());

        return announcementDetailVO;
    }

    @Override
    public List<CaseBriefVO> getCaseBriefList() {
        return tCaseBriefDao.getCaseBriefList();
    }

    @Override
    public List<ContractTypeVO> getContractTypeList() {

        return tContractTypeDao.getContractTypeList();
    }

    @Override
    public List<CaseTypeVO> getAllCaseType() {
        return tCaseTypeDao.getAllCaseType();
    }

    @Override
    public List<CaseStepVO> getAllCaseStep(OperationReq.OpenType openType) {
        return tCaseStepDao.getAllCaseStep(openType.getType());
    }

    @Override
    public List<QuickReleaseVO> getCaseTypeList(Integer limitSize) {
        return tOperateIconInfoDao.getCaseTypeList(limitSize);
    }


}
