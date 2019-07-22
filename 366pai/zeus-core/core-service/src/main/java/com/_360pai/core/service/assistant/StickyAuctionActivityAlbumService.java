package com._360pai.core.service.assistant;


import com._360pai.core.condition.assistant.StickyAuctionActivityAlbumCondition;
import com.github.pagehelper.PageInfo;

import java.util.List;
import com._360pai.core.model.assistant.StickyAuctionActivityAlbum;
import com.github.pagehelper.PageInfo;

public interface StickyAuctionActivityAlbumService {


    PageInfo getAllByPage(int page, int perPage, StickyAuctionActivityAlbumCondition condition, String orderBy);

    int deleteById(Integer id);

    int insert(Long albumId, Long orderNumber);

    PageInfo auctionOrder(int page, int perPage, StickyAuctionActivityAlbumCondition condition, String order_number_);
}