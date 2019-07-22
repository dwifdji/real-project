package com.winback.core.facade.assistant.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: Province
 * @ProjectName winback
 * @Description:
 * @date 2019/1/28 14:31
 */
@Data
public class Province implements Serializable {
    /**
     * 省份code
     */
    private java.lang.String code;
    /**
     * 省份名称
     */
    private java.lang.String name;
}
