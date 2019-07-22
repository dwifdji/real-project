package com.tzCloud.core.service.message;

import com.tzCloud.core.dto.com.PushMsgDto;
import com.tzCloud.core.model.message.TMapMessageTemplate;

public interface MessageService {

    void sendEmail(TMapMessageTemplate template, PushMsgDto msg);
}
