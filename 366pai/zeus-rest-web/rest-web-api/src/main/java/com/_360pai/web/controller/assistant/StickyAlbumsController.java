package com._360pai.web.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.core.filter.PropertyFilterFactory;
import com._360pai.core.facade.activity.req.AlbumsReq;
import com._360pai.core.facade.assistant.StickyAlbumsFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述:
 *
 * @param:
 * @return:
 * @auther: zxiao
 * @date: 2018/9/18 14:46
 */
@RestController
public class StickyAlbumsController {
    @Reference(version = "1.0.0")
    StickyAlbumsFacade stickyAlbumsFacade;

    /**
     * 置首页专场列表
     */
    @GetMapping(value = "/open/sticky_albums")
    public ResponseModel auctionOrder(AlbumsReq.StickAlbumReq req) {
        return ResponseModel.succ(stickyAlbumsFacade.auctionOrder(req), PropertyFilterFactory.create(new
                String[]{"albumId", "orderNumber"}));
    }
}
