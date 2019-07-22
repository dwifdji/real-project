package com.winback.core.facade.account.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xdrodger
 * @Title: Menu
 * @ProjectName winback
 * @Description:
 * @date 2019/1/30 14:13
 */
@Data
public class Menu implements Serializable {
    private Integer id;
    /**
     * 菜单code
     */
    private String code;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 排序号
     */
    private Integer orderNum;
    /**
     * 是否选中（1：是，0：否）
     */
    private Boolean selected = false;
    /**
     * 权限列表
     */
    private List<Permission> permissions;
    /**
     * 链接
     */
    private String url;
}
