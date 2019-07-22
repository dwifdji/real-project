package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.assistant.PlatformAnnouncementCondition;
import com._360pai.core.dao.assistant.PlatformAnnouncementDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.assistant.req.PlatformAnnouncementReq;
import com._360pai.core.model.assistant.PlatformAnnouncement;
import com._360pai.core.service.assistant.PlatformAnnouncementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PlatformAnnouncementServiceImpl implements PlatformAnnouncementService {

    @Autowired
    private PlatformAnnouncementDao platformAnnouncementDao;


    @Override
    public PageInfo selectPlatformAnnouncementList(int page, int perPage, PlatformAnnouncementCondition condition, String typeFlag) {
        PageHelper.startPage(page, perPage);
        PageHelper.orderBy("public_at desc");
        List<PlatformAnnouncement> platformAnnouncements = platformAnnouncementDao.selectList(condition);
        List<PlatformAnnouncement> list = new ArrayList<>();
        if(typeFlag != null && typeFlag.equals("2")) {
            return new PageInfo<>(platformAnnouncements);
        }else{
            for (PlatformAnnouncement platformAnnouncement : platformAnnouncements) {
                Date expiredAt = platformAnnouncement.getExpiredAt();
                if ((System.currentTimeMillis()) - expiredAt.getTime() > 0) {
                    continue;
                }
                list.add(platformAnnouncement);
            }
            return new PageInfo<>(list);
        }
    }

    @Override
    public int addPlatformAnnouncement(PlatformAnnouncement params) {
        if (StringUtils.isBlank(params.getTitle()))
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "标题不能为空");
        if (null == params.getPublicAt()) throw new BusinessException(ApiCallResult.FAILURE.getCode(), "发布时间不能为空");
        if (null == params.getExpiredAt()) throw new BusinessException(ApiCallResult.FAILURE.getCode(), "过期时间不能为空");
        if (StringUtils.isBlank(params.getCategory()))
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "类型不能为空");
        return platformAnnouncementDao.insert(params);
    }

    @Override
    public int editPlatformAnnouncement(PlatformAnnouncement params) {
        PlatformAnnouncement byId = findById(params);
        if (byId == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的新闻不存在");
        }
        if (null == params.getPublicAt()) throw new BusinessException(ApiCallResult.FAILURE.getCode(), "发布时间不能为空");
        if (null == params.getExpiredAt()) throw new BusinessException(ApiCallResult.FAILURE.getCode(), "过期时间不能为空");
        return platformAnnouncementDao.updateById(params);
    }

    @Override
    public int deletePlatformAnnouncement(PlatformAnnouncement params) {
        PlatformAnnouncement byId = findById(params);
        if (byId == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "删除的新闻不存在");
        }
        return platformAnnouncementDao.deletePlatformAnnouncement(params.getId());
    }

    @Override
    public Object detail(PlatformAnnouncement params, PlatformAnnouncementCondition condition) {
        return findByIdAndCategory(params, condition);
    }

    @Override
    public PlatformAnnouncement getDetailById(PlatformAnnouncementCondition condition) {

        return platformAnnouncementDao.selectFirst(condition);
    }

    @Override
    public ResponseModel view(PlatformAnnouncementReq req) {
        if (req.getId() != null) {
            platformAnnouncementDao.addViewCount(req.getId());
        }
        return ResponseModel.succ();
    }

    private PlatformAnnouncement findById(PlatformAnnouncement params) {
        PlatformAnnouncementCondition condition = new PlatformAnnouncementCondition();
        condition.setId(params.getId());
        return platformAnnouncementDao.selectOneResult(condition);
    }

    private PlatformAnnouncement findByIdAndCategory(PlatformAnnouncement params, PlatformAnnouncementCondition con) {
        PlatformAnnouncementCondition condition = new PlatformAnnouncementCondition();
        condition.setId(params.getId());
        condition.setCategory(con.getCategory());
        return platformAnnouncementDao.selectOneResult(condition);
    }
}