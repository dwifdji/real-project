package com._360pai.core.facade.activity;

import com._360pai.arch.common.ListResp;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.activity.req.AlbumReq;
import com._360pai.core.facade.activity.resp.AlbumResp;
import com._360pai.core.facade.activity.vo.AlbumVo;
import com._360pai.core.facade.activity.vo.AuctionActivityVo;

import java.util.Map;

/**
 * 描述:
 * @author : whisky_vip
 * @date : 2018/8/16 15:13
 */
public interface AlbumFacade {

    PageInfoResp<AlbumVo> getAlbumListByPage(AlbumReq.QueryReq req);

    AlbumResp getAlbum(AlbumReq.AlbumIdReq req);

    AlbumResp createAlbum(AlbumReq.AlbumCreateReq req);

    AlbumResp updateAlbum(AlbumReq.AlbumUpdateReq req);

    PageInfoResp<Map> getRelatedActivityList(AlbumReq.AlbumIdReq req);

    PageInfoResp<Map> getFrontRelatedActivityList(AlbumReq.AlbumIdReq req);

    AlbumResp addActivityList(AlbumReq.AlbumIdReq req);

    AlbumResp editActivityList(AlbumReq.AlbumIdReq req);

    AlbumResp deleteActivityList(AlbumReq.AlbumIdReq req);

    AlbumResp online(AlbumReq.AlbumIdReq req);

    AlbumResp offline(AlbumReq.AlbumIdReq req);

    ListResp<AlbumVo> getStickyList(AlbumReq.AlbumIdReq req);
}
