package com.winback.core.facade.account.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xdrodger
 * @Title: Role
 * @ProjectName winback
 * @Description:
 * @date 2019/1/30 14:13
 */
@Data
public class Role implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 状态（0：禁用，1：启用）
     */
    private Boolean status;
    /**
     * 角色描述
     */
    private String description;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 模块列表
     */
    private List<Module> modules;
}
