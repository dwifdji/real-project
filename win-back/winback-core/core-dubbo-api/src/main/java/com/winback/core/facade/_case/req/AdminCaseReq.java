package com.winback.core.facade._case.req;

import com.winback.arch.common.AdminReq;
import lombok.Data;

/**
 * @author xdrodger
 * @Title: AdminCaseReq
 * @ProjectName winback
 * @Description:
 * @date 2019/1/28 17:59
 */
public class AdminCaseReq {

    @Data
    public static class QueryReq extends AdminReq {
        private Integer accountId;
        private String q;
        private Boolean pageFlag = false;
    }

    @Data
    public static class AddCaseBriefReq extends AdminReq {
        /**
         * 名称
         */
        private String name;
        /**
         * 是否展示（默认 0 否， 1 是）
         */
        private Boolean display;
        /**
         * 排序号
         */
        private Integer orderNum;
        /**
         * 案由大类id
         */
        private Integer caseBigBriefId;
    }

    @Data
    public static class EditCaseBriefReq extends AdminReq {
        private Integer id;
        /**
         * 名称
         */
        private String name;
        /**
         * 是否展示（默认 0 否， 1 是）
         */
        private Boolean display;
        /**
         * 排序号
         */
        private Integer orderNum;
        /**
         * 案由大类id
         */
        private Integer caseBigBriefId;
    }

    @Data
    public static class DeleteCaseBriefReq extends AdminReq {
        private Integer id;
        /**
         * 案由大类id
         */
        private Integer caseBigBriefId;
    }
}
