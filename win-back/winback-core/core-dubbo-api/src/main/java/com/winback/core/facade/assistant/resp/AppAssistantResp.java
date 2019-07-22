package com.winback.core.facade.assistant.resp;

import com.winback.arch.common.RespModel;
import lombok.Data;

/**
 * @author xdrodger
 * @Title: AppAssistantResp
 * @ProjectName winback
 * @Description:
 * @date 2019-05-06 18:16
 */
public class AppAssistantResp {

    @Data
    public static class CheckUpdateResp extends RespModel {
        /**
         * 版本
         */
        private String version;
        /**
         * 弹窗提示标志（0 否 1 是）
         */
        private Boolean alertFlag;
        /**
         * 强制更新标志（0 否 1 是）
         */
        private Boolean forceFlag;
        /**
         * 描述
         */
        private String desc;
    }
}
