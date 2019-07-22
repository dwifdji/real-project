package com._360pai.core.facade.shop.dto;

import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchRecordListDto extends RequestModel {

    private String query;

    private String shopId;

    private String openId;

    private String beginTime;

    private String endTime;
}
