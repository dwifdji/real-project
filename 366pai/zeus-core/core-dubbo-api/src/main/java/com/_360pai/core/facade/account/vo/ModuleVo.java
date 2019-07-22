package com._360pai.core.facade.account.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author xdrodger
 * @Title: ModuleVo
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/7 21:48
 */
@Getter
@Setter
public class ModuleVo implements Serializable {
    private String moduleCode;
    private String moduleName;
    private String selected;
    private List<MenuVo> menus;
    private String url = "";
    private Integer moduleOrder;
}
