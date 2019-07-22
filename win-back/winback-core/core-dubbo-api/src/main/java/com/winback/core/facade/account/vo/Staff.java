package com.winback.core.facade.account.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xdrodger
 * @Title: Staff
 * @ProjectName winback
 * @Description:
 * @date 2019/1/30 14:12
 */
@Data
public class Staff implements Serializable {
    /**
     *
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
     * 邮箱
     */
    private String email;
    /**
     * QQ号
     */
    private String qq;
    /**
     * 部门
     */
    private String dept;
    /**
     * 备注
     */
    private String memo;
    /**
     * 状态（0：禁用，1：启用）
     */
    private Boolean status;
    /**
     * 是否管理员
     */
    @JSONField(serialize = false)
    private Boolean adminFlag;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 角色列表
     */
    private List<Role> roles;
    /**
     * 特殊权限列表
     */
    private List<Permission> specialPermissions;
}
