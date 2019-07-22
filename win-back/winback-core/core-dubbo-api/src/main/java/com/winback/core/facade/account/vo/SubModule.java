package com.winback.core.facade.account.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author xdrodger
 * @Title: Module
 * @ProjectName winback
 * @Description:
 * @date 2019/1/30 14:12
 */
@Data
public class SubModule implements Serializable {
    /**
     * 子模块code
     */
    private String code;
    /**
     * 子模块名称
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
     * 菜单列表
     */
    private List<Menu> menus = Collections.EMPTY_LIST;
    /**
     * 链接
     */
    private String url;
}
