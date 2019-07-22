package com._360pai.core.facade.account.vo;

import lombok.Getter;
import lombok.Setter;
import org.omg.PortableInterceptor.INACTIVE;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: PermissionVo
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/8 23:51
 */
@Getter
@Setter
public class PermissionVo implements Serializable {
    private Integer permissionId;
    private String permissionCode;
    private String permissionName;
    private String permissionType;
    private String url = "";
}
