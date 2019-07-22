package com.winback.core.service.assistant;

import com.winback.arch.common.Device;
import com.winback.core.facade.assistant.req.AppAssistantReq;
import com.winback.core.facade.assistant.resp.AppAssistantResp;

/**
 * @author xdrodger
 * @Title: AssistantService
 * @ProjectName winback
 * @Description:
 * @date 2019/1/29 10:38
 */
public interface AssistantService {
    Device getDevice(Integer accountId);

    void saveDevice(Integer accountId, Device device);

    void simpleSaveDevice(Integer accountId, Device device);

    AppAssistantResp.CheckUpdateResp checkUpdate(AppAssistantReq.CheckUpdateReq req);
}
