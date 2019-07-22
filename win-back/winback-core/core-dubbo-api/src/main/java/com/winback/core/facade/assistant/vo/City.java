package com.winback.core.facade.assistant.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: City
 * @ProjectName winback
 * @Description:
 * @date 2019/1/28 14:31
 */
@Data
public class City implements Serializable {
    /**
     * 城市code
     */
    private java.lang.String code;
    /**
     * 城市名称
     */
    private java.lang.String name;
    /**
     * 省份code
     */
    private java.lang.String provinceCode;
    /**
     * 省份名称
     */
    private java.lang.String provinceName;
}
