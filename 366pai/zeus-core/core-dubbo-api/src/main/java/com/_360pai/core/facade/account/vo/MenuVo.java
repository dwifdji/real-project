package com._360pai.core.facade.account.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author xdrodger
 * @Title: MenuVo
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/7 21:46
 */
@Getter
@Setter
public class MenuVo implements Serializable {
    private String menuCode;
    private String menuName;
    private String selected;
    private List<PermissionVo> permissions;
    private String url = "";
    private String icon = "";
    private Integer menuOrder;
}
