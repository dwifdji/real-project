package com.winback.core.facade.account.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: Account
 * @ProjectName winback
 * @Description:
 * @date 2019/1/28 15:51
 */
@Data
public class Account implements Serializable {
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
     * 注册来源
     */
    private java.lang.String registerSource;
    /**
     * 注册来源描述
     */
    private java.lang.String registerSourceDesc;
    /**
     * 渠道来源
     */
    private java.lang.String source;
    /**
     * 邀请码
     */
    private java.lang.String inviteCode;
    /**
     * 创建时间
     */
    private java.util.Date createTime;

    /**
     * 分配的案件数量
     */
    private int allocatedCaseNum;
    /**
     * 完成的案件数量
     */
    private int successfulCaseNum;
    /**
     * 项目经理标志
     */
    private java.lang.Boolean projectManagerFlag;
}
