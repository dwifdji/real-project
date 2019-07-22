package com._360pai.core.facade.shop.dto;

import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShopEnrollingSearchDto extends RequestModel {

    private String type;

    private String status;

    private String beginPrice;

    private String endPrice;

    private Integer provinceId;

    private Integer cityId;

    private Integer shopId;

    private String outFlag;

    private String query;
}
