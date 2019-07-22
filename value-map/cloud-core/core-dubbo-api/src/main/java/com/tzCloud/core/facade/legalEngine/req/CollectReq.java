package com.tzCloud.core.facade.legalEngine.req;

import com.tzCloud.arch.common.RequestModel;
import lombok.Data;

/**
 * @author xiaolei
 * @create 2019-04-24 15:22
 */
public class CollectReq {

    @Data
    public static class Collect extends RequestModel
    {
        private Integer id;
        private Integer accountId;
        private String collectType;
        private String collectKey;
    }
}
