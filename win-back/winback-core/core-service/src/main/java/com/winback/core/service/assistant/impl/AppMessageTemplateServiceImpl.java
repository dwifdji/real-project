package com.winback.core.service.assistant.impl;

import com.winback.core.dao.assistant.TAppMessageTemplateDao;
import com.winback.core.model.assistant.TAppMessageTemplate;
import com.winback.core.service.assistant.AppMessageTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xdrodger
 * @Title: AppMessageTemplateServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019-05-06 14:59
 */
@Slf4j
@Service
public class AppMessageTemplateServiceImpl implements AppMessageTemplateService {

    @Autowired
    private TAppMessageTemplateDao appMessageTemplateDao;


    @Override
    public TAppMessageTemplate findBy(String sendType, String type) {
        return appMessageTemplateDao.findBy(sendType, type);
    }
}
