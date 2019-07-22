package com._360pai.core.model.assistant;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class LeaseAreaModel implements Serializable {
    private String provinceId;
    private String provinceName;
    private String cityId;
    private String cityName;
    private String areaId;
    private String areaName;
    private String townId;
    private String townName;
}
