package com.tzCloud.core.facade.account.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: AccountInfo
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 15:33
 */
@Data
public class AccountInfo implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String headImgUrl;
    /**
     * 姓名
     */
    private java.lang.String name;
    /**
     * 性别（U 未知 F 女 M 男）
     */
    private java.lang.String gender;
    /**
     * 公司
     */
    private java.lang.String company;
    /**
     * 行业
     */
    private java.lang.String industry;
    /**
     * 地址
     */
    private java.lang.String address;
    /**
     * 职务
     */
    private java.lang.String title;
}
