package com._360pai.core.facade.activity.req;

import com._360pai.arch.common.RequestModel;

/**
 * @author : whisky_vip
 * @date : 2018/8/15 12:50
 */
public class AlbumsReq {
    public static class AlbumIdReq extends RequestModel {
        private Long albumId;

        public Long getAlbumId() {
            return albumId;
        }

        public void setAlbumId(Long albumId) {
            this.albumId = albumId;
        }
    }

    public static class QueryReq extends RequestModel {
        private String q;

        public String getQ() {
            return q;
        }

        public void setQ(String q) {
            this.q = q;
        }
    }

    public static class StickAlbumReq extends RequestModel {

    }

    public static class StickAlbumIdReq extends RequestModel {
        private Integer id;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
    public static class StickAlbumPostReq extends RequestModel {
        private Long albumId;
        private Long orderNumber;

        public Long getAlbumId() {
            return albumId;
        }

        public void setAlbumId(Long albumId) {
            this.albumId = albumId;
        }

        public Long getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(Long orderNumber) {
            this.orderNumber = orderNumber;
        }
    }
}
