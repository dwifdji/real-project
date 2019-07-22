package com.winback.core.facade.account.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: Franchisee
 * @ProjectName winback
 * @Description:
 * @date 2019/1/29 16:29
 */
@Data
public class Franchisee implements Serializable {
    /**
     * 主键
     */
    private java.lang.Integer id;
    /**
     * 账户id
     */
    private java.lang.Integer accountId;
    /**
     * 手机号
     */
    private java.lang.String mobile;
    /**
     * 姓名
     */
    private java.lang.String name;
    /**
     * 加盟商类型(user：个人，company：企业)
     */
    private java.lang.String type;
    /**
     * 身份证号码
     */
    private java.lang.String certificateNumber;
    /**
     * 身份证正面照
     */
    private java.lang.String certificateFrontImg;
    /**
     * 身份证背面照
     */
    private java.lang.String certificateBackImg;
    /**
     * 营业执照图片
     */
    private java.lang.String licenseImg;
    /**
     * 营业执照号
     */
    private java.lang.String licenseNumber;
    /**
     * 自我介绍
     */
    private java.lang.String selfIntroduction;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
}
