package com._360pai.core.facade.account.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: InviteCommissionVo
 * @ProjectName zeus
 * @Description:
 * @date 2018/12/3 16:18
 */
@Data
public class InviteCommissionVo implements Serializable {
    private String id;
    /**
     * 店铺推荐码
     */
    private String inviteCode;
    /**
     * 店主姓名
     */
    private String name;
    /**
     * 店铺注册手机号
     */
    private String mobile;
    /**
     * 开通店铺时间
     */
    private Date createTime;
    /**
     * 所得推荐费
     */
    private BigDecimal shopInvitedCommission;
    /**
     * 获取推荐费时间
     */
    private Date receiveTime;
    /**
     * 下级店铺推荐码
     */
    private String subInviteCode;
    /**
     * 下级店主姓名
     */
    private String subName;
    /**
     * 下级店铺注册手机号
     */
    private String subMobile;

}
