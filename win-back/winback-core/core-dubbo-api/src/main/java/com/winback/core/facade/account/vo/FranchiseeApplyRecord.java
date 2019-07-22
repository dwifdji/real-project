package com.winback.core.facade.account.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: FranchiseeApplyRecord
 * @ProjectName winback
 * @Description:
 * @date 2019/1/29 15:50
 */
@Data
public class FranchiseeApplyRecord implements Serializable {
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
     * 申请状态(PENDING：审核中,APPROVED：审核通过,REJECT：审核拒绝)
     */
    private java.lang.String status;
    /**
     * 申请状态描述
     */
    private java.lang.String statusDesc;
    /**
     * 原因
     */
    private java.lang.String reason;
    /**
     * 审核人Id
     */
    private java.lang.Integer operatorId;
    /**
     * 审核人
     */
    private java.lang.String operator;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
}
