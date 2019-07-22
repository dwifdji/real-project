package com.winback.arch.common;

import com.winback.arch.common.enums.SideType;
import lombok.Data;

/**
 * app请求通用信息
 */
@Data
public class AppReq extends RequestModel {
    /**
     * 请求来源
     */
    private SideType sideType = SideType.APP;
    /**
     * 设备信息
     */
    private Device device;
}
