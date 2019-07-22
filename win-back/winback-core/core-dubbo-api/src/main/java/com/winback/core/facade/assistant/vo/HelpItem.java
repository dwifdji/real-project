package com.winback.core.facade.assistant.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: HelpItem
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 16:33
 */
@Data
public class HelpItem implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 链接url
     */
    private String link;
    /**
     * 是否展示（0 否 1 是）
     */
    private Boolean display;
    /**
     * 排序号
     */
    private Integer orderNum;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
}
