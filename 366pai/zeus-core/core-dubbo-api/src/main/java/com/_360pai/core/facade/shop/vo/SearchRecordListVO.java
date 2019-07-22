package com._360pai.core.facade.shop.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class SearchRecordListVO implements Serializable {

    private String recordId;

    private String name;

    private String searchDate;

    private String searchContent;
}
