package com.winback.arch.common;

import com.winback.arch.common.enums.SideType;
import lombok.Data;

/**
 * admin请求通用信息
 */
@Data
public class AdminReq extends RequestModel {
    /**
     * 请求来源
     */
    private SideType sideType = SideType.ADMIN;
}
