package com._360pai.core.facade.assistant.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: CityVo
 * @ProjectName zeus
 * @Description:
 * @date 06/09/2018 14:25
 */
@Data
public class CityVo implements Serializable {

    private Integer id;
    private String name;
    private Integer provinceId;
    private String provinceName;
    private Integer areaId;
    private String areaName;
}
