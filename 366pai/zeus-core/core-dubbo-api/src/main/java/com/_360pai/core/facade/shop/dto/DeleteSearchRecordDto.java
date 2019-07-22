package com._360pai.core.facade.shop.dto;

import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteSearchRecordDto extends RequestModel {

    private String shopId;

    private String[] records;

    private String deleteType; //0全部删除 1部分删除
}
