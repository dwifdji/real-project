package com._360pai.core.provider.assistant;

import com._360pai.core.facade.assistant.FileFacade;
import com._360pai.core.service.assistant.TFileService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/11/21 10:57
 */
@Component
@Service(version = "1.0.0")
public class FileProvider implements FileFacade {

    @Autowired
    private TFileService tFileService;

    @Override
    public String watermark(String url) {
        return tFileService.watermark(url);
    }
}
