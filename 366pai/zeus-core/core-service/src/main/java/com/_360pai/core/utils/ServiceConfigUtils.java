package com._360pai.core.utils;

import com._360pai.core.common.constants.ServiceConfigEnum;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/30 11:29
 */
public class ServiceConfigUtils {
    private ServiceConfigUtils() {

    }

    /**
     * 描述 处置类型: 10：尽调 11：评估 12：司法处置\r\n13：执行处置 14：清房 15：催收  16：查找财产线索
     */
    public static ServiceConfigEnum convertStatus(String disposalType) {
        switch (disposalType) {
            case "10":
                return ServiceConfigEnum.DIPOSAL_ADJUSTED;
            case "11":
                return ServiceConfigEnum.DIPOSAL_EVALUATION;
            case "12":
                return ServiceConfigEnum.DIPOSAL_JUDICIAL;
            case "13":
                return ServiceConfigEnum.DIPOSAL_EXECUTION;
            case "14":
                return ServiceConfigEnum.DIPOSAL_CLEAR_HOUSE;
            case "15":
                return ServiceConfigEnum.DIPOSAL_COLLECTION;
            case "16":
                return ServiceConfigEnum.DIPOSAL_FIND_PROPERTY_LEADS;
            default:
                return ServiceConfigEnum.DISPOSAL_REQUIREMENT_PRICE;
        }
    }
}
