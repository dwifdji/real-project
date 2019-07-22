package com._360pai.core.facade.disposal.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2018-10-10 15:03
 */
@Getter
@Setter
@ToString
public class City implements Serializable {
    private static final long serialVersionUID = 5503219011868623715L;
    private String id;
    private String name;

    private String provinceId;

    private String provinceName;

    private String areaId;

    private String areaName;
}
