package com._360pai.core.facade.activity.resp;

import com._360pai.arch.common.BaseResp;
import com._360pai.core.facade.activity.vo.AlbumVo;
import com._360pai.core.facade.activity.vo.AuctionActivityVo;

import java.util.List;

/**
 * @author xdrodger
 * @Title: AlbumResp
 * @ProjectName zeus
 * @Description:
 * @date 07/09/2018 09:21
 */
public class AlbumResp extends BaseResp {

    private Integer albumId;

    private AlbumVo album;

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public AlbumVo getAlbum() {
        return album;
    }

    public void setAlbum(AlbumVo album) {
        this.album = album;
    }

    public static class AlbumListResp extends BaseResp {
        private List<AuctionActivityVo> list;

        public List<AuctionActivityVo> getList() {
            return list;
        }

        public void setList(List<AuctionActivityVo> list) {
            this.list = list;
        }
    }
}
