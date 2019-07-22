package com._360pai.core.provider.assistant;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.assistant.THallFacade;
import com._360pai.core.facade.assistant.req.AppletHallActivityReq;
import com._360pai.core.facade.assistant.req.THallReq;
import com._360pai.core.facade.assistant.vo.BackAppletHallActivity;
import com._360pai.core.model.assistant.THall;
import com._360pai.core.service.assistant.THallService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: THallProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/27 13:02
 */
@Component
@Service(version = "1.0.0")
public class THallProvider implements THallFacade {
    @Autowired
    private THallService tHallService;

    @Override
    public PageInfo hallList(THallReq req) {
        return tHallService.hallList(req.getPage(),req.getPerPage(),Copy(req));
    }

    @Override
    public int addHall(THallReq req) {
        return tHallService.addHall(Copy(req));
    }

    @Override
    public int editHall(THallReq req) {
        return tHallService.editHall(Copy(req));
    }

    @Override
    public int deleteHall(THallReq req) {
        return tHallService.deleteHall(Copy(req));
    }

    @Override
    public Object detailHall(THallReq req) {
        return tHallService.detailHall(req.getId());
    }

    @Override
    public PageInfoResp<BackAppletHallActivity> getAppletHallActivityList(AppletHallActivityReq.QueryReq req) {
        return tHallService.getAppletHallActivityList(req);
    }

    @Override
    public Integer addAppletHallActivity(AppletHallActivityReq.AddReq req) {
        return tHallService.addAppletHallActivity(req);
    }

    @Override
    public Integer editAppletHallActivity(AppletHallActivityReq.EditReq req) {
        return tHallService.editAppletHallActivity(req);
    }

    @Override
    public Integer deleteAppletHallActivity(AppletHallActivityReq.DeleteReq req) {
        return tHallService.deleteAppletHallActivity(req);
    }


    private THall Copy(THallReq req) {
        THall hall = new THall();
        BeanUtils.copyProperties(req, hall);
        return hall;
    }
}
