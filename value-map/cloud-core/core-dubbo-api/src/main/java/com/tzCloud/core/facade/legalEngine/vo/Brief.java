package com.tzCloud.core.facade.legalEngine.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: Brief
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019-04-25 19:50
 */
@Data
public class Brief implements Serializable {
    /**
     * 一级案由
     */
    private Integer firstId;
    private Integer secondId;
    private Integer thirdId;
    private Integer fourthId;
    private Integer fifthId;
    /**
     * 案件案由名称
     */
    private String name;
}
