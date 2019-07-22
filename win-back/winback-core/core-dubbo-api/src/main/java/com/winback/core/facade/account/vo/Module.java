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
public class Module implements Serializable {
    /**
     * 模块code
     */
    private String code;
    /**
     * 模块名称
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
     * 子模块列表
     */
    private List<SubModule> subModules = Collections.EMPTY_LIST;
    /**
     * 链接
     */
    private String url;
}
