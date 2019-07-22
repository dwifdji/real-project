package com._360pai.core.facade.account.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xdrodger
 * @Title: AccountVo
 * @ProjectName zeus
 * @Description:
 * @date 11/09/2018 15:05
 */
@Getter
@Setter
public class AccountVo implements Serializable {
    /**
     * 主键
     */
    private java.lang.Integer id;
    /**
     * 手机号
     */
    private java.lang.String mobile;
    /**
     * 注册来源
     */
    private java.lang.String registerSource;
    /**
     * 渠道来源
     */
    private java.lang.String source;
    /**
     * 状态(0:无效,1:有效)
     */
    private java.lang.Integer status;
    /**
     * 默认送拍机构
     */
    private AgencyVo defaultAgency;
    /**
     * 从属于某个机构的管理员
     */
    private AgencyVo agency;
    /**
     * 是否是机构的申请者
     */
    private java.lang.Integer isAgencyAdmin;
    /**
     * 是否可以查看保留价
     */
    private java.lang.Boolean canCheckReservePrice;
    /**
     * 创建时间
     */
    private java.util.Date createTime;

    private UserVo user;

    private List<CompanyVo> companies;
}
