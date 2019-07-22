package com.winback.core.service.assistant;


import com.winback.core.dto.assistant.PushMsgDto;

/**
 * 推送消息service
 *
 */
public interface PushMessageService {

    /**
     * 发送消息
     *
     */
    void pushMsg(PushMsgDto msg);



    /**
     * 推送诉讼或者 执行步骤
     *
     */
    void pushStepMsg(Integer recordId);
 }
