package com.tzCloud.valuation.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: AccountInfo
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 15:30
 */
@Data
public class LoginInfo implements Serializable {
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
    private String name;
    /**
     * 性别（U 未知 F 女 M 男）
     */
    private String gender;
    /**
     * 公司
     */
    private String company;
    /**
     * 行业
     */
    private String industry;
    /**
     * 地址
     */
    private String address;
    /**
     * 职务
     */
    private String title;
}
