package com.winback.core.facade.account.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: Menu
 * @ProjectName winback
 * @Description:
 * @date 2019/1/30 14:13
 */
@Data
public class Permission implements Serializable {
    /**
     * 自定id,主要供前端展示权限列表分类排序使用.
     */
    private Integer id;
    /**
     * 归属菜单,前端判断并展示菜单使用,
     */
    private String buttonCode;
    /**
     * 菜单的中文释义
     */
    private String buttonName;
    /**
     * 权限的代码/通配符,对应代码中@RequiresPermissions 的value
     */
    private String permissionCode;
    /**
     * 本权限的中文释义
     */
    private String permissionName;
    /**
     * 是否本菜单必选权限, 1.必选 2非必选 通常是"列表"权限是必选
     */
    private Boolean requiredPermission;
    /**
     * 按钮类型：0普通，1特殊
     */
    private String permissionType;
    /**
     * 按钮类型：0普通，1特殊
     */
    private String url;
}
