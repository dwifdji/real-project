package com._360pai.core.facade.activity.req;
import com._360pai.arch.common.RequestModel;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xdrodger
 * @Title: AlbumReq
 * @ProjectName zeus
 * @Description:
 * @date 07/09/2018 09:27
 */
public class AlbumReq extends RequestModel {

    @Data
    public static class AlbumIdReq extends RequestModel {
        private Integer albumId;

        private Integer activityId;
        private String activityType;
        private Integer orderNum;

    }

    public static class QueryReq extends RequestModel {
        private String q;
        private String status;

        public String getQ() {
            return q;
        }

        public void setQ(String q) {
            this.q = q;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    @Getter
    @Setter
    public static class AlbumCreateReq extends RequestModel {
        /**
         *
         */
        @NotBlank
        private String name;
        /**
         *
         */
        @NotBlank
        private String img;
        /**
         *
         */
        @NotNull
        private java.util.Date beginAt;
        /**
         *
         */
        @NotNull
        private java.util.Date endAt;
        /**
         *
         */
        private String remark;
        /**
         *
         */
        private java.util.Date previewAt;
        /**
         *
         */
        private String description;
        /**
         *
         */
        @NotBlank
        private String detailImg;
    }

    @Getter
    @Setter
    public static class AlbumUpdateReq extends RequestModel {
        @NotNull
        private Integer id;
        private String name;
        private String img;
        private java.util.Date beginAt;
        private java.util.Date endAt;
        private String remark;
        private java.util.Date previewAt;
        private String description;
        private String detailImg;
    }
}
