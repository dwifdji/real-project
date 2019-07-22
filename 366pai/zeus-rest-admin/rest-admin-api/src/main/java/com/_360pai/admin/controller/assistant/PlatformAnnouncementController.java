package com._360pai.admin.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.PlatformAnnouncementFacade;
import com._360pai.core.facade.assistant.req.PlatformAnnouncementReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: PlatformAnnouncementController  新闻公告
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/21 16:16
 */
@RestController
public class PlatformAnnouncementController {

    @Reference(version = "1.0.0")
    private PlatformAnnouncementFacade platformAnnouncementFacade;

    /**
     * 功能描述:  新闻公告列表
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 9:21
     */
    @RequiresPermissions("yygl_xwzxgl:xwptgg_list")
    @GetMapping(value = "/admin/platform_announcements/list")
    public ResponseModel selectPlatformAnnouncementList(PlatformAnnouncementReq req) {
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        req.setTypeFlag("2");               //区分后台查询以及主站查询
        PageInfo pageInfo = platformAnnouncementFacade.selectPlatformAnnouncementList(req);
        model.setContent(pageInfo);
        return model;
    }

    /**
     * 功能描述: 新建新闻
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 9:21
     */
    @PostMapping(value = "/admin/platform_announcements/add")
    public ResponseModel addPlatformAnnouncement(@RequestBody PlatformAnnouncementReq req) {
        Assert.notNull(req.getTitle(), "标题不能为空");
        Assert.notNull(req.getExpiredAt(), "过期时间不能为空");
        Assert.notNull(req.getPublicAt(), "发布时间不能为空");
        Assert.notNull(req.getCategory(), "类型不能为空");

        //新增过期时间跟预展时间校验
        if(req.getExpiredAt().getTime() < req.getPublicAt().getTime()) {
            return  ResponseModel.fail("过期时间必要要大于发布时间");
        }

        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        int i = platformAnnouncementFacade.addPlatformAnnouncement(req);
        if (i <= 0) {
            return ResponseModel.fail();
        }
        return model;
    }

    /**
     * 功能描述: 修改新闻
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 9:21
     */
    @PostMapping(value = "/admin/platform_announcements/edit")
    public ResponseModel editPlatformAnnouncement(@RequestBody PlatformAnnouncementReq req) {
        Assert.notNull(req.getId(), "id不能为空");
        Assert.notNull(req.getTitle(), "标题不能为空");
        Assert.notNull(req.getExpiredAt(), "过期时间不能为空");
        Assert.notNull(req.getPublicAt(), "发布时间不能为空");
        Assert.notNull(req.getCategory(), "类型不能为空");
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        int i = platformAnnouncementFacade.editPlatformAnnouncement(req);
        if (i <= 0) {
            return ResponseModel.fail();
        }
        return model;
    }

    /**
     * 功能描述: 删除新闻
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 9:21
     */
    @PostMapping(value = "/admin/platform_announcements/delete")
    public ResponseModel deletePlatformAnnouncement(@RequestBody PlatformAnnouncementReq req) {
        Assert.notNull(req.getId(), "id不能为空");
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        int i = platformAnnouncementFacade.deletePlatformAnnouncement(req);
        if (i <= 0) {
            return ResponseModel.fail();
        }
        return model;
    }
}