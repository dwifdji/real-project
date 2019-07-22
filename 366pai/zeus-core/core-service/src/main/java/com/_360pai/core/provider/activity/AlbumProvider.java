package com._360pai.core.provider.activity;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ListResp;
import com._360pai.core.facade.activity.AlbumFacade;
import com._360pai.core.facade.activity.req.AlbumReq;
import com._360pai.core.facade.activity.resp.AlbumResp;
import com._360pai.core.facade.activity.vo.AlbumVo;
import com._360pai.core.facade.activity.vo.AuctionActivityVo;
import com._360pai.core.service.activity.AuctionActivityAlbumService;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 描述:
 * @author : whisky_vip
 * @date : 2018/8/16 15:14
 */
@Component
@Service(version = "1.0.0")
public class AlbumProvider implements AlbumFacade {
    @Autowired
    private AuctionActivityAlbumService auctionActivityAlbumService;

    @Override
    public PageInfoResp<AlbumVo> getAlbumListByPage(AlbumReq.QueryReq req) {
        return auctionActivityAlbumService.getAlbumListByPage(req);
    }

    @Override
    public AlbumResp getAlbum(AlbumReq.AlbumIdReq req) {
        return auctionActivityAlbumService.getAlbum(req);
    }

    @Override
    public AlbumResp createAlbum(AlbumReq.AlbumCreateReq req) {
        return auctionActivityAlbumService.createAlbum(req);
    }

    @Override
    public AlbumResp updateAlbum(AlbumReq.AlbumUpdateReq req) {
        return auctionActivityAlbumService.updateAlbum(req);
    }

    @Override
    public PageInfoResp<Map> getRelatedActivityList(AlbumReq.AlbumIdReq req) {
        return auctionActivityAlbumService.getRelatedActivityList(req);
    }

    @Override
    public PageInfoResp<Map> getFrontRelatedActivityList(AlbumReq.AlbumIdReq req) {
        return auctionActivityAlbumService.getFrontRelatedActivityList(req);
    }

    @Override
    public AlbumResp addActivityList(AlbumReq.AlbumIdReq req) {
        return auctionActivityAlbumService.addActivityList(req);
    }

    @Override
    public AlbumResp editActivityList(AlbumReq.AlbumIdReq req) {
        return auctionActivityAlbumService.editActivityList(req);
    }

    @Override
    public AlbumResp deleteActivityList(AlbumReq.AlbumIdReq req) {
        return auctionActivityAlbumService.deleteActivities(req);
    }

    @Override
    public AlbumResp online(AlbumReq.AlbumIdReq req) {
        return auctionActivityAlbumService.online(req);
    }

    @Override
    public AlbumResp offline(AlbumReq.AlbumIdReq req) {
        return auctionActivityAlbumService.offline(req);
    }

    @Override
    public ListResp<AlbumVo> getStickyList(AlbumReq.AlbumIdReq req) {
        return auctionActivityAlbumService.getStickyList(req);
    }

}
