package com._360pai.core.facade.applet.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wcq
 */
@Data
public class AppletVisitVo implements Serializable {
    private String type;
    private String name;//访客名称
    private String imgUrl;//访客url
    private String timeInfo;//访客时间信息




}
