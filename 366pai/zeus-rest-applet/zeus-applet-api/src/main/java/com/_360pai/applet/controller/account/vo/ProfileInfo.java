package com._360pai.applet.controller.account.vo;

import com._360pai.core.common.constants.AppletEnum;
import com._360pai.core.facade.account.vo.AccountAuthVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created by RuQ on 2018/8/19 18:58
 */
@Data
public class ProfileInfo implements Serializable {
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
    private String accountAmount = "0.00";
    /**
     * 可用余额
     */
    private String availableAmount  = "0.00";
    /**
     * 锁定金额
     */
    private String lockAmount  = "0.00";
    /**
     * 账户总额
     */
    private String accountAmountDesc = "0.00";
    /**
     * 可用余额
     */
    private String availableAmountDesc  = "0.00";
    /**
     * 锁定金额
     */
    private String lockAmountDesc  = "0.00";
    /**
     * 是否绑定账户
     */
    private Boolean isAccountBind = false;
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
     * 认证申请状态（NO_APPLY 未申请 PENDING 申请中 APPROVED 申请成功 REJECT 申请失败）
     */
    private String applyStatus;
    /**
     * 认证申请状态描述
     */
    private String applyStatusDesc;
    /**
     * 开店状态
     */
    private String openShopStatus;
    /**
     * 开店状态描述
     */
    private String openShopStatusDesc;
    /**
     * 我的店铺id
     */
    private String shopId;
    /**
     * 机构id
     */
    private Integer agencyId;
    /**
     * 是否机构管理员
     */
    private Boolean isAgencyAdmin;
    /**
     * 认证账户列表
     */
    private List<AccountAuthVo> accountAuthList = Collections.EMPTY_LIST;
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
     * 店铺邀请码
     */
    private String inviteCode = "";

    private boolean subscribe;
}
