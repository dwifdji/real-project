package com._360pai.web.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.core.filter.PropertyFilterFactory;
import com._360pai.core.facade.assistant.PlatformAnnouncementFacade;
import com._360pai.core.facade.assistant.req.PlatformAnnouncementReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: PlatformAnnouncementController  首页新闻中心
 * @ProjectName zeus-rest-web
 * @Description:
 * @date 2018/8/21 16:16
 */
@RestController
public class PlatformAnnouncementController {

    @Reference(version = "1.0.0")
    private PlatformAnnouncementFacade platformAnnouncementFacade;

    /**
     * 功能描述:  首页新闻列表
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 9:21
     */
    @GetMapping(value = "/open/platform_news")
    public ResponseModel selectNewList(PlatformAnnouncementReq req) {
        //首页只查询新闻类类型
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        req.setCategory(PlatformAnnouncementReq.NEWS);
        PageInfo pageInfo = platformAnnouncementFacade.selectPlatformAnnouncementList(req);
        //过滤属性
        model.setContent(pageInfo, PropertyFilterFactory.create(new String[]{"detail", "createdAt", "expiredAt", "url", "category"}));
        return model;
    }

    /**
     * 功能描述:  首页平台公告
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 9:21
     */
    @GetMapping(value = "/open/platform_announcements")
    public ResponseModel selectPlatformAnnouncement(PlatformAnnouncementReq req) {
        //首页只查询平台公告类型
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        req.setCategory(PlatformAnnouncementReq.ANNOUNCEMENT);
        PageInfo pageInfo = platformAnnouncementFacade.selectPlatformAnnouncementList(req);
        model.setContent(pageInfo, PropertyFilterFactory.create(new String[]{"detail", "createdAt", "expiredAt", "url", "category"}));
        return model;
    }

    /**
     * 功能描述:  新闻公告详情
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 9:21
     */
    @GetMapping(value = "/open/platform_news/detail")
    public ResponseModel newDetail(PlatformAnnouncementReq req) {
        //首页只查询新闻类类型
        Assert.notNull(req.getId(), "新闻公告id不能为空");
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        req.setCategory(PlatformAnnouncementReq.NEWS);
        Object pageInfo = platformAnnouncementFacade.detail(req);
        model.setContent(pageInfo);
        return model;
    }

    /**
     * 功能描述:  平台公告详情
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 9:21
     */
    @GetMapping(value = "/open/platform_announcements/detail")
    public ResponseModel detail(PlatformAnnouncementReq req) {
        //首页只查询新闻类类型
        Assert.notNull(req.getId(), "平台公告id不能为空");
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        req.setCategory(PlatformAnnouncementReq.ANNOUNCEMENT);
        Object pageInfo = platformAnnouncementFacade.detail(req);
        model.setContent(pageInfo);
        return model;
    }

    /**
     * 平台公告浏览量增加
     */
    @PostMapping(value = "/open/platform_announcements/detail/view")
    public ResponseModel view(@RequestBody PlatformAnnouncementReq req) {
        return platformAnnouncementFacade.view(req);
    }
}