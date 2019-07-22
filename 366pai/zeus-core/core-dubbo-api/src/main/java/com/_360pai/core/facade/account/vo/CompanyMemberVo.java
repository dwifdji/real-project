package com._360pai.core.facade.account.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: CompanyMemberVo
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/9 17:20
 */
@Getter
@Setter
public class CompanyMemberVo implements Serializable {
    /**
     * 成员id
     */
    private Integer id;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 名称
     */
    private String name;
    /**
     * 是否管理员
     */
    private Boolean isAdmin;
}
