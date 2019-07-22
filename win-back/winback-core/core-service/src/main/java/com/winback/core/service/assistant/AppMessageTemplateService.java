package com.winback.core.service.assistant;

import com.winback.core.model.assistant.TAppMessageTemplate;

/**
 * @author xdrodger
 * @Title: AppMessageTemplateService
 * @ProjectName winback
 * @Description:
 * @date 2019-05-06 14:58
 */
public interface AppMessageTemplateService {

    TAppMessageTemplate findBy(String sendType, String type);
}
