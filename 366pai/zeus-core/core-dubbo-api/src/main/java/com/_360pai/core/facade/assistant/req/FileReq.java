package com._360pai.core.facade.assistant.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/11/20 16:19
 */
public class FileReq {
    @Data
    public static class Watermark extends RequestModel {
        private String url;
    }
}
