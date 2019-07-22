package com._360pai.core.facade.account.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xdrodger
 * @Title: StaffVo
 * @ProjectName zeus
 * @Description:
 * @date 11/09/2018 17:15
 */
@Getter
@Setter
public class StaffVo implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private Boolean isAdmin;
    /**
     *
     */
    private String mobile;
    /**
     *
     */
    private String qq;
    /**
     * 状态（0：禁用，1：启用）
     */
    private String status;
    private Date createTime;
    /**
     * 角色描述
     */
    private String roleDesc;
    /**
     * 角色列表
     */
    private List<RoleVo> roles;
    /**
     * 特殊权限列表
     */
    private List<PermissionVo> specialPermissions;
    /**
     * 是否拥有特殊权限
     */
    private Boolean hasSpecialPermission;
    /**
     * 部门
     */
    private String dept;
    /**
     * 职位
     */
    private String job;
    /**
     * 工号
     */
    private String empNo;
    /**
     * 公司邮箱
     */
    private String companyEmail;
}
