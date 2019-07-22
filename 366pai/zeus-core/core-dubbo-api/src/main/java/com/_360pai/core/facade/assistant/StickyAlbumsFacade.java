package com._360pai.core.facade.assistant;

import com._360pai.core.facade.activity.req.AlbumsReq;

/**
 * @author : whisky_vip
 * @date : 2018/8/20 14:41
 */
public interface StickyAlbumsFacade {

    Object getByPage(AlbumsReq.StickAlbumReq req);

    int delete(Integer id);

    int insert(AlbumsReq.StickAlbumPostReq req);

    Object auctionOrder(AlbumsReq.StickAlbumReq req);
}
