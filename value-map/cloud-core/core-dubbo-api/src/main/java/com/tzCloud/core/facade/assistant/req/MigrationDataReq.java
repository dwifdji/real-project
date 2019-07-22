package com.tzCloud.core.facade.assistant.req;

import com.tzCloud.arch.common.RequestModel;
import lombok.Data;

/**
 * @author xdrodger
 * @Title: MigrationDataReq
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/5/16 15:27
 */
public class MigrationDataReq {

    @Data
    public static class CaseToElasticSearchReq extends RequestModel {
        private String docId;
        private Integer maxId;
        private boolean update;
        /**
         * 每页数量.
         */
        private int perPage = 5000;

        /**
         * 同步多少页
         */
        private int pageNum = -1;
    }

    @Data
    public static class CaseExtraDataToElasticSearchReq extends RequestModel {
        private String docId;
        private Integer maxId;
        private boolean update;
        /**
         * 每页数量.
         */
        private int perPage = 5000;

        /**
         * 同步多少页
         */
        private int pageNum = -1;
    }
}
