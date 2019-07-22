package com._360pai.core.provider.assistant;

import com._360pai.core.condition.assistant.StickyAuctionActivityAlbumCondition;
import com._360pai.core.facade.assistant.StickyAlbumsFacade;
import com._360pai.core.facade.activity.req.AlbumsReq;
import com._360pai.core.service.assistant.StickyAuctionActivityAlbumService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : whisky_vip
 * @date : 2018/8/20 14:49
 */
@Component
@Service(version = "1.0.0")
public class StickyAlbumsProvider implements StickyAlbumsFacade {
    @Autowired
    private StickyAuctionActivityAlbumService stickyAuctionActivityAlbumService;
    @Override
    public Object getByPage(AlbumsReq.StickAlbumReq req) {
        StickyAuctionActivityAlbumCondition condition = new StickyAuctionActivityAlbumCondition();
        PageInfo pageInfo = stickyAuctionActivityAlbumService.getAllByPage(req.getPage(),req.getPerPage(),condition,"order_number ");
        return pageInfo;
    }

    @Override
    public int delete(Integer id) {
        return stickyAuctionActivityAlbumService.deleteById(id);
    }

    @Override
    public int insert(AlbumsReq.StickAlbumPostReq req) {
        return stickyAuctionActivityAlbumService.insert(req.getAlbumId(),req.getOrderNumber());
    }

    @Override
    public Object auctionOrder(AlbumsReq.StickAlbumReq req) {
        StickyAuctionActivityAlbumCondition condition = new StickyAuctionActivityAlbumCondition();
        PageInfo pageInfo = stickyAuctionActivityAlbumService.auctionOrder(req.getPage(),req.getPerPage(),condition,"order_number ");
        return pageInfo;
    }
}
