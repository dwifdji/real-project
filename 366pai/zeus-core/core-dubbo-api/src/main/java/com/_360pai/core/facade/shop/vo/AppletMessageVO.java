package com._360pai.core.facade.shop.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AppletMessageVO implements Serializable {

    private Integer id;

    /**
     * 消息类型 备用字段
     */
    private String type;
    /**
     * 消息标题
     */
    private String name;
    /**
     * 消息内容
     */
    private String context;

    /**
     *
     */
    private String createTime;

    /**
     * 是否阅读标识
     */
    private String readFlag;


}
