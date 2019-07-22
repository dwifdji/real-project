package com._360pai.admin.controller.activity;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.activity.req.AlbumsReq;
import com._360pai.core.facade.assistant.StickyAlbumsFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : whisky_vip 首页专场设置
 * @date : 2018/8/20 14:40
 */
@RestController
public class StickyAlbumsController {
    @Reference(version = "1.0.0")
    StickyAlbumsFacade stickyAlbumsFacade;

    /**
     * 置首页专场列表
     */
    @GetMapping(value = "/admin/sticky_albums")
    public ResponseModel auctionOrder(AlbumsReq.StickAlbumReq req) {
        return ResponseModel.succ(stickyAlbumsFacade.getByPage(req));
    }

    /**
     * 新增 sticky_album
     */
    @PostMapping(value = "/admin/add_sticky_album")
    public ResponseModel add(@RequestBody AlbumsReq.StickAlbumPostReq req) {
        Assert.notNull(req.getAlbumId(), "albumId 不能为空");
        Assert.notNull(req.getOrderNumber(), "orderNumber 不能为空");
        int i = stickyAlbumsFacade.insert(req);
        if (i > 0) {
            return ResponseModel.succ();
        } else {
            return ResponseModel.fail("操作失败");
        }
    }

    /**
     * 删除 sticky_album
     */
    @PostMapping(value = "/admin/delete_sticky_album")
    public ResponseModel delete(@RequestBody AlbumsReq.StickAlbumIdReq req) {
        Assert.notNull(req.getId(), "id 不能为空");
        int i = stickyAlbumsFacade.delete(req.getId());
        if (i > 0) {
            return ResponseModel.succ();
        } else {
            return ResponseModel.fail("操作失败");
        }
    }
}
