package com._360pai.core.service.activity;


import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ListResp;
import com._360pai.core.facade.activity.req.AlbumReq;
import com._360pai.core.facade.activity.resp.AlbumResp;
import com._360pai.core.facade.activity.vo.AlbumVo;
import com._360pai.core.facade.activity.vo.AuctionActivityVo;
import com.alibaba.fastjson.JSONArray;

import java.util.Map;

public interface AuctionActivityAlbumService {

    PageInfoResp<AlbumVo> getAlbumListByPage(AlbumReq.QueryReq req);

    AlbumResp getAlbum(AlbumReq.AlbumIdReq req);

    PageInfoResp<Map> getRelatedActivityList(AlbumReq.AlbumIdReq req);

    PageInfoResp<Map> getFrontRelatedActivityList(AlbumReq.AlbumIdReq req);

    ListResp<AlbumVo> getStickyList(AlbumReq.AlbumIdReq req);

    AlbumResp createAlbum(AlbumReq.AlbumCreateReq req);

    AlbumResp updateAlbum(AlbumReq.AlbumUpdateReq req);

    AlbumResp addActivityList(AlbumReq.AlbumIdReq req);

    AlbumResp editActivityList(AlbumReq.AlbumIdReq req);

    AlbumResp deleteActivities(AlbumReq.AlbumIdReq req);

    AlbumResp online(AlbumReq.AlbumIdReq req);

    AlbumResp offline(AlbumReq.AlbumIdReq req);

}