package com._360pai.applet.controller.account.resp;

import lombok.Data;

/**
 * Created by RuQ on 2018/8/19 18:58
 */
@Data
public class AccountBaseInfo {

    private Integer extBindId;

    private Integer accountId;

    private Integer currentPartyId;

    private String openId;
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
    private String avatarUrl;
    /**
     * 姓名
     */
    private String accountAuthName;
    /**
     * 认证时间
     */
    private String accountAuthTime;
    /**
     * 类型
     */
    private String type;
    /**
     * 账户总额
     */
    private String accountAmount;
    /**
     * 可用余额
     */
    private String availableAmount;
    /**
     * 锁定金额
     */
    private String lockAmount;
    /**
     * 是否绑定东方付通
     */
    private Boolean isPayBind = false;
    /**
     * 是否绑定法大大
     */
    private Boolean isFddBind = false;
    /**
     * 账户是否认证
     */
    private Boolean isAccountAuth = false;
    /**
     * 是否个人认证
     */
    private Boolean isUserAuth = false;
    /**
     * 认证申请状态（0 未申请 1 申请中 2 申请成功 3 申请失败）
     */
    private String authStatus;
    /**
     * 认证申请状态描述
     */
    private String authStatusDesc;
    /**
     * 我的店铺id
     */
    private Integer shopId;
    /**
     * 机构id
     */
    private Integer agencyId;
    /**
     * 是否机构管理员
     */
    private Boolean isAgencyAdmin = false;
    /**
     * 我的邀请数量
     */
    private Integer inviteCount = 0;
    /**
     * 我的关注数量
     */
    private Integer favorCount = 0;
    /**
     * 店铺名称
     */
    private String shopName = "";

    /**
     * 未读播报数量
     */
    private Integer unreadBroadcastCount = 0;
}
