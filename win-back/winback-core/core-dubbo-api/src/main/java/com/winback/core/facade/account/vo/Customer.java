package com.winback.core.facade.account.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: Customer
 * @ProjectName winback
 * @Description:
 * @date 2019/1/28 09:47
 */
@Data
public class Customer implements Serializable {
    /**
     * 主键
     */
    private java.lang.Integer id;
    /**
     * 手机号
     */
    private java.lang.String mobile;
    /**
     * 昵称
     */
    private java.lang.String nickName;
    /**
     * 头像
     */
    private java.lang.String headImgUrl;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
}
