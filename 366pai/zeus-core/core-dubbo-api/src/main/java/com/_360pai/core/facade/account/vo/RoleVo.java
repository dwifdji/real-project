package com._360pai.core.facade.account.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xdrodger
 * @Title: RoleVo
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/7 22:42
 */
@Getter
@Setter
public class RoleVo implements Serializable {
    private Integer id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色描述
     */
    private String description;
    private java.util.Date createTime;
    private String permissionDesc;

    private List<ModuleVo> moduleList;
}
