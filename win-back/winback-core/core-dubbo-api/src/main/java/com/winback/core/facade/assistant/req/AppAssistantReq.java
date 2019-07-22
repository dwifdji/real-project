package com.winback.core.facade.assistant.req;

import com.winback.arch.common.AppReq;
import lombok.Data;

import java.util.Date;

/**
 * @author xdrodger
 * @Title: AppReq
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 16:29
 */
public class AppAssistantReq {
    @Data
    public static class GetHelpItemReq extends AppReq {
        private Integer itemId;
    }

    @Data
    public static class BuriedPointReq extends AppReq {
        private String pointKey;
        private String pointDesc;
        private String pointType;
        private String buzId;
        private String buzParams;

        /**
         * 下面的参数不需要前端传
         */
        private String deviceMark;
        private String deviceType;
        private String deviceToken;
        private String userId;
        private String userType;
        private Date createTime;
        private Date updateTime;
        private String city;
        private String province;
        private String ip;

        private Integer id;
    }

    @Data
    public static class BuriedPointUpdateReq extends AppReq {

        private Integer id;
        private Date updateTime;
    }

    @Data
    public static class CheckUpdateReq extends AppReq {
    }
}
