package com._360pai.core.facade.assistant.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

/**
 * @author xdrodger
 * @Title: AppletHallActivityReq
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/2/28 19:15
 */
public class AppletHallActivityReq {

    public static class QueryReq extends RequestModel {

    }

    @Data
    public static class AddReq extends RequestModel {
        private Integer activityId;
        private String type;
        private Integer orderNum;
    }

    @Data
    public static class EditReq extends RequestModel {
        private Integer id;
        private Integer orderNum;
    }

    @Data
    public static class DeleteReq extends RequestModel {
        private Integer id;
    }
}
