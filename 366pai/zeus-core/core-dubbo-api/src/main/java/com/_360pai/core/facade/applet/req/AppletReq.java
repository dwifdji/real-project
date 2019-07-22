package com._360pai.core.facade.applet.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

/**
 * @author xdrodger
 * @Title: AppletReq
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/26 14:37
 */
public class AppletReq {
    @Data
    public static class InviteRecordReq extends RequestModel {
        private Integer shopId;
    }
}
