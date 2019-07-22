package com.winback.core.facade.assistant.req;

import com.winback.arch.common.AdminReq;
import lombok.Data;

/**
 * @author xdrodger
 * @Title: AppReq
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 16:29
 */
public class AdminAssistantReq {


    @Data
    public static class ImportContractFileToDbReq extends AdminReq {
        private String filePath;
    }
}
