package com._360pai.admin.controller.activity;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.activity.AlbumFacade;
import com._360pai.core.facade.activity.req.AlbumReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/16 10:32
 */
@RestController
public class AlbumController {

    @Reference(version = "1.0.0")
    AlbumFacade albumFacade;

    /**
     * 拍卖专场列表
     */
    @RequiresPermissions("pmgl_hdzc:list")
    @GetMapping(value = "/admin/album/list")
    public ResponseModel list(AlbumReq.QueryReq req) {
        return ResponseModel.succ(albumFacade.getAlbumListByPage(req));
    }

    /**
     * 拍卖专场详情
     */
    @GetMapping(value = "/admin/album/detail")
    public ResponseModel detail(AlbumReq.AlbumIdReq req) {
        Assert.notNull(req.getAlbumId(), "albumId 参数不能为空");
        return ResponseModel.succ(albumFacade.getAlbum(req));
    }

    /**
     * 创建拍卖专场
     */
    @PostMapping(value = "/admin/album/create")
    public ResponseModel create(@Valid @RequestBody AlbumReq.AlbumCreateReq req) {
        return ResponseModel.succ(albumFacade.createAlbum(req));
    }

    /**
     * 更新拍卖专场
     */
    @PostMapping(value = "/admin/album/update")
    public ResponseModel update(@Valid @RequestBody AlbumReq.AlbumUpdateReq req) {
        return ResponseModel.succ(albumFacade.updateAlbum(req));
    }

    /**
     * 拍卖专场关联的活动列表
     */
    @GetMapping(value = "/admin/album/related/activities")
    public ResponseModel relatedActivities(AlbumReq.AlbumIdReq req) {
        Assert.notNull(req.getAlbumId(), "albumId 参数不能为空");
        return ResponseModel.succ(albumFacade.getRelatedActivityList(req));
    }

    /**
     * 拍卖专场添加专场活动
     */
    @PostMapping(value = "/admin/album/add/activities")
    public ResponseModel addActivities(@RequestBody AlbumReq.AlbumIdReq req) {
        Assert.notNull(req.getAlbumId(), "albumId 参数不能为空");
        Assert.notNull(req.getActivityId(),"活动ID不能为空");
        Assert.notNull(req.getActivityType(),"活动类型不能为空");
        return ResponseModel.succ(albumFacade.addActivityList(req));
    }

    /**
     * 拍卖专场添加专场活动
     */
    @PostMapping(value = "/admin/album/edit/activities")
    public ResponseModel editActivities(@RequestBody AlbumReq.AlbumIdReq req) {
        Assert.notNull(req.getAlbumId(), "albumId 参数不能为空");
        Assert.notNull(req.getActivityId(),"活动ID不能为空");
        Assert.notNull(req.getActivityType(),"活动类型不能为空");
        return ResponseModel.succ(albumFacade.editActivityList(req));
    }


    /**
     * 拍卖专场删除专场活动
     */
    @PostMapping(value = "/admin/album/delete/activities")
    public ResponseModel deleteActivities(@RequestBody AlbumReq.AlbumIdReq req) {
        Assert.notNull(req.getAlbumId(), "albumId 参数不能为空");
        Assert.notNull(req.getActivityId(),"活动ID不能为空");
        Assert.notNull(req.getActivityType(),"活动类型不能为空");
        return ResponseModel.succ(albumFacade.deleteActivityList(req));
    }

    /**
     * 上线专场活动
     */
    @PostMapping(value = "/admin/album/online")
    public ResponseModel online(@RequestBody AlbumReq.AlbumIdReq req) {
        Assert.notNull(req.getAlbumId(), "albumId 参数不能为空");
        return ResponseModel.succ(albumFacade.online(req));
    }

    /**
     * 下线专场活动
     */
    @PostMapping(value = "/admin/album/offline")
    public ResponseModel offline(@RequestBody AlbumReq.AlbumIdReq req) {
        Assert.notNull(req.getAlbumId(), "albumId 参数不能为空");
        return ResponseModel.succ(albumFacade.offline(req));
    }
}
