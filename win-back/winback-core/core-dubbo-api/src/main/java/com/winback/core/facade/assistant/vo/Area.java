package com.winback.core.facade.assistant.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: Area
 * @ProjectName winback
 * @Description:
 * @date 2019/1/28 14:55
 */
@Data
public class Area implements Serializable {
    /**
     * 区域code
     */
    private java.lang.String code;
    /**
     * 区域名称
     */
    private java.lang.String name;
    /**
     * 管理的城市code
     */
    private java.lang.String cityCode;
    /**
     * 城市名称
     */
    private java.lang.String cityName;
}
