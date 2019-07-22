package com._360pai.core.facade.account.resp;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LeaseStaffResp implements Serializable {
    /**
     *
     */
    private java.lang.Integer id;
    /**
     * 公司id
     */
    private java.lang.Integer partId;
    /**
     * 员工名称
     */
    private java.lang.String name;

    /**
     * 员工名称
     */
    private java.lang.String fadadaId;

    /**
     * 员工手机号
     */
    private java.lang.String mobile;
    /**
     * 账号id
     */
    private java.lang.Integer accountId;
    /**
     * 经办人权限
     */
    private java.lang.Boolean agentFlag;
    /**
     * 初审权限
     */
    private java.lang.Boolean trialFlag;
    /**
     * 终审权限标志
     */
    private java.lang.Boolean finalFlag;
    /**
     * 禁用标志
     */
    private java.lang.Boolean forbidFlag;


    private java.lang.Integer comId;


    private java.lang.String code;


    private java.lang.String desc;



}
