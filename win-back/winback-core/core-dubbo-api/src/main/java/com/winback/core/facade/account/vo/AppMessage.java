package com.winback.core.facade.account.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: AppMessage
 * @ProjectName winback
 * @Description:
 * @date 2019/1/28 10:39
 */
@Data
public class AppMessage implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 消息类型
     */
    private String type;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 已读标志
     */
    private Boolean readFlag;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 创建时间
     */
    private String createTimeDesc;

    /**
     * 参数信息
     */
    private String param;


    /**
     * 跳转的主键id
     */
    private String primaryKey;


}
