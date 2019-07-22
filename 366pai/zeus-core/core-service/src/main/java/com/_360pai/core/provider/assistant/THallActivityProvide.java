package com._360pai.core.provider.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.THallActivityFacade;
import com._360pai.core.facade.assistant.req.THallActivityReq;
import com._360pai.core.facade.assistant.req.THallEnrollingActivityReq;
import com._360pai.core.model.assistant.THallActivity;
import com._360pai.core.model.assistant.THallEnrollingActivity;
import com._360pai.core.service.assistant.THallActivityService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: THallActivityProvide
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/18 18:41
 */
@Component
@Service(version = "1.0.0")
public class THallActivityProvide implements THallActivityFacade {

    @Autowired
    private THallActivityService tHallActivityService;

    @Override
    public Object getTHallActivityList(THallActivityReq req) {
        return tHallActivityService.getTHallActivityList(req.getHallId());
    }

    @Override
    public Object getAssetType() {
        return tHallActivityService.getAssetType();
    }

    @Override
    public Object getEnrollingType() {
        return tHallActivityService.getEnrollingType();
    }

    @Override
    public ResponseModel getHallEnrollingActivities(Integer hallId) {
        return tHallActivityService.getHallEnrollingActivities(hallId);
    }

    @Override
    public PageInfo tHallActivityList(THallActivityReq req) {
        return tHallActivityService.tHallActivityList(req);
    }

    @Override
    public int addTHallActivity(THallActivityReq req) {
        return tHallActivityService.addTHallActivity(CopyActivity(req));
    }

    @Override
    public int editTHallActivityList(THallActivityReq req) {
        return tHallActivityService.editTHallActivityList(CopyActivity(req));
    }

    @Override
    public int deleteTHallActivityList(THallActivityReq req) {
        return tHallActivityService.deleteTHallActivityList(CopyActivity(req));
    }

    @Override
    public PageInfo tEnrollingHallActivityList(THallEnrollingActivityReq req) {
        return tHallActivityService.tEnrollingHallActivityList(req);
    }

    @Override
    public int addEnrollingHallActivity(THallEnrollingActivityReq req) {
        return tHallActivityService.addEnrollingHallActivity(CopyEnrollingActivity(req));
    }

    @Override
    public int editEnrollingHallActivity(THallEnrollingActivityReq req) {
        return tHallActivityService.editEnrollingHallActivity(CopyEnrollingActivity(req));
    }

    @Override
    public int deleteEnrollingHallActivity(THallEnrollingActivityReq req) {
        return tHallActivityService.deleteEnrollingHallActivity(CopyEnrollingActivity(req));
    }

    @Override
    public Object detailTHallActivityList(THallActivityReq req) {
        return tHallActivityService.detailTHallActivity(req.getId());
    }

    @Override
    public Object detailEnrollingTHallActivity(THallEnrollingActivityReq req) {
        return tHallActivityService.detailEnrollingTHallActivity(req.getId());
    }

    @Override
    public Object getAssetLeaseType() {
        return tHallActivityService.getAssetLeaseType();
    }

    private THallActivity CopyActivity(THallActivityReq req) {
        THallActivity tHallActivity = new THallActivity();
        BeanUtils.copyProperties(req, tHallActivity);
        return tHallActivity;
    }

    private THallEnrollingActivity CopyEnrollingActivity(THallEnrollingActivityReq req) {
        THallEnrollingActivity tHallActivity = new THallEnrollingActivity();
        BeanUtils.copyProperties(req, tHallActivity);
        return tHallActivity;
    }
}
