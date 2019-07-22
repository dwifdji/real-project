package com._360pai.core.facade.account.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: AccountExtBindIVo
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/23 14:02
 */
@Data
public class AccountExtBindIVo implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     * 账户id
     */
    private Integer accountId;
    /**
     * 外部类型（APPLET：小程序）
     */
    private String extType;
    /**
     * 外部用户id
     */
    private String extUserId;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String headImgUrl;
    /**
     * 当前账户角色
     */
    private Integer currentPartyId;
    /**
     * 邀请码
     */
    private Integer inviteCode;
    /**
     * 邀请类型（DP：店铺，JG：机构）
     */
    private String inviteType;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 机构id
     */
    private Integer agencyId;
    /**
     * 是否机构管理员
     */
    private Boolean isAgencyAdmin = false;
    /**
     * 我的店铺id
     */
    private Integer shopId;
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
