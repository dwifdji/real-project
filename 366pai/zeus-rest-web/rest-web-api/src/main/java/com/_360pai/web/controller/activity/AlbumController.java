package com._360pai.web.controller.activity;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.activity.AlbumFacade;
import com._360pai.core.facade.activity.req.AlbumReq;
import com._360pai.web.controller.AbstractController;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/16 10:32
 */
@RestController
public class AlbumController extends AbstractController {

    @Reference(version = "1.0.0")
    AlbumFacade albumFacade;

    /**
     * 拍卖专场列表
     */
    @GetMapping(value = "/open/album/list")
    public ResponseModel list(AlbumReq.QueryReq req) {
        req.setStatus("1");
        return ResponseModel.succ(albumFacade.getAlbumListByPage(req));
    }

    /**
     * 首页拍卖专场列表
     */
    @GetMapping(value = "/open/album/sticky/list")
    public ResponseModel stickyList(AlbumReq.AlbumIdReq req) {
        return ResponseModel.succ(albumFacade.getStickyList(req));
    }

    /**
     * 拍卖专场详情
     */
    @GetMapping(value = "/open/album/detail")
    public ResponseModel detail(AlbumReq.AlbumIdReq req) {
        Assert.notNull(req.getAlbumId(), "albumId 参数不能为空");
        return ResponseModel.succ(albumFacade.getAlbum(req));
    }

    /**
     * 拍卖专场关联的活动列表
     */
    @GetMapping(value = "/open/album/related/activity/list")
    public ResponseModel relatedActivityList(AlbumReq.AlbumIdReq req) {
        Assert.notNull(req.getAlbumId(), "albumId 参数不能为空");
        return ResponseModel.succ(albumFacade.getFrontRelatedActivityList(req));
    }
}
