package com.tzCloud.core.facade.legalEngine.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: Court
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019-04-25 19:46
 */
@Data
public class Court implements Serializable {
    private String name;
    private String type;
    private String province = "";
    private String city = "";
}
