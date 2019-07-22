package com._360pai.core.facade.comprador.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/4 16:35
 */
@Data
public class RequirementListForPlatformReq extends RequestModel {

    private Integer areaFrom;
    private Integer areaTo;
    private Integer priceFrom;
    private Integer priceTo;

    private String orderByPrice;
    private String orderByArea;

    private Integer cityId;
    private String  buildingType;

}
